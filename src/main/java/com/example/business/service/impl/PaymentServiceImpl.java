package com.example.business.service.impl;

import com.example.business.entity.Account;
import com.example.business.entity.Payment;
import com.example.business.repository.AccountRepository;
import com.example.business.repository.PaymentRepository;
import com.example.business.service.PaymentService;
import com.example.exceptions.PaymentTransactionException;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Utils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static utils.Constant.*;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Single<Payment> transactionP2P(Payment payment)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentServiceImpl", "transactionP2P");
        return validatetransactionPaymentP2P(payment)
                .map(objectpayment -> {
                    paymentRepository.save(payment);
                    return Single.just(payment);
                }).toFuture().get();
    }

    @Override
    public Single<Payment> transactionP2M(Payment payment)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentServiceImpl", "transactionP2M");
        return validatetransactionPaymentP2M(payment)
                .map(objectpayment -> {
                    paymentRepository.save(payment);
                    return Single.just(payment);
                }).toFuture().get();
    }

    private Single<Payment> validatetransactionPaymentP2P(Payment payment) {
        log.info("Starting {}.{} method", "AccountServiceImpl", "validateParametersCustomer");
        if (payment != null) {

            if(payment.getAccountOrigin() == null) throw new PaymentTransactionException(ACCOUNT_ORIGIN_REQUIRED);
            if(payment.getAccountOrigin().isEmpty()) throw new PaymentTransactionException(ACCOUNT_ORIGIN_EMPTY);

            if(payment.getBalanceTransfer() == null) throw new PaymentTransactionException(BALANCE_REQUIRED);
            if(payment.getBalanceTransfer().toString().contains("-")) throw new PaymentTransactionException(BALANCE_NOT_NEGATIVE);

            if(payment.getAccountDestination() == null) throw new PaymentTransactionException(ACCOUNT_DESTINATION_REQUIRED);
            if(payment.getAccountDestination().isEmpty()) throw new PaymentTransactionException(ACCOUNT_DESTINATION_EMPTY);
        } else {
            throw new PaymentTransactionException("EL OBJECTO PATMENT NO PUEDE ESTAR NULL");
        }
        return logicP2P(payment);
    }

    private Single<Payment> validatetransactionPaymentP2M(Payment payment) {
        log.info("Starting {}.{} method", "AccountServiceImpl", "validateParametersCustomer");
        if (payment != null) {
            if(payment.getAccountOrigin() == null) throw new PaymentTransactionException(ACCOUNT_ORIGIN_REQUIRED);
            if(payment.getAccountOrigin().isEmpty()) throw new PaymentTransactionException(ACCOUNT_ORIGIN_EMPTY);

            if(payment.getBalanceTransfer() == null) throw new PaymentTransactionException(BALANCE_REQUIRED);
            if(payment.getBalanceTransfer().toString().contains("-")) throw new PaymentTransactionException(BALANCE_NOT_NEGATIVE);

            if(payment.getAccountDestination() == null) throw new PaymentTransactionException(ACCOUNT_DESTINATION_REQUIRED);
            if(payment.getAccountDestination().isEmpty()) throw new PaymentTransactionException(ACCOUNT_DESTINATION_EMPTY);
        } else {
            throw new PaymentTransactionException("EL OBJECTO PATMENT NO PUEDE ESTAR NULL");
        }
        return logicP2M(payment);
    }


    //send money accountOrigin to accountDestination
    private Single<Payment> logicP2P(Payment payment) {
        Account accountOrigin = null;
        Account accountDestination = null;

        // Se aplica primero la logica para obtener el saldo de la cuenta origen y se descuenta
        // Al momento de transferir a la cuenta destino
        accountOrigin = getDataWithAccountOrigin(payment);
        payment.setBalanceAccountOrigin(accountOrigin.getBalance());
        accountOrigin.setBalance(accountOrigin.getBalance().subtract(payment.getBalanceTransfer()));
        accountRepository.save(accountOrigin);

        // Se aplica la logica para obtener el saldo de la cuenta destino y se agrega
        // Al momento de transferir a la cuenta origen
        accountDestination = getDataWithAccountDestination(payment);
        payment.setBalanceAccountDestination(accountDestination.getBalance());
        accountDestination.setBalance(payment.getBalanceTransfer().add(accountDestination.getBalance()));
        accountRepository.save(accountDestination);

        payment.setOperationNumber(Utils.generateNumberOperarion());
        return Single.just(payment);
    }

    //send money accountDestination to accountOrigin
    private Single<Payment> logicP2M(Payment payment) {
        Account accountOrigin = null;
        Account accountDestination = null;

        accountDestination = getDataWithAccountDestination(payment);
        payment.setBalanceAccountDestination(accountDestination.getBalance());
        accountDestination.setBalance(accountDestination.getBalance().subtract(payment.getBalanceTransfer()));
        accountRepository.save(accountDestination);


        accountOrigin = getDataWithAccountOrigin(payment);
        payment.setBalanceAccountOrigin(accountOrigin.getBalance());
        accountOrigin.setBalance(accountOrigin.getBalance().add(payment.getBalanceTransfer()));
        accountRepository.save(accountOrigin);


        payment.setOperationNumber(Utils.generateNumberOperarion());
        return Single.just(payment);
    }


    private Account getDataWithAccountOrigin(Payment payment) {
        Account account = new Account();
        Optional<Account> findAccountNumber = Optional
                .ofNullable(accountRepository.findByAccountNumber(payment.getAccountOrigin())
                        .orElseThrow(() -> new PaymentTransactionException(ACCOUNT_ORIGIN_NOT_FOUND)));
        return findAccountNumber.get();
    }

    private Account getDataWithAccountDestination(Payment payment) {
        Account account = new Account();
        Optional<Account> findAccountNumber = Optional
                .ofNullable(accountRepository.findByAccountNumber(payment.getAccountDestination())
                        .orElseThrow(() -> new PaymentTransactionException(ACCOUNT_DESTINATION_NOT_FOUND)));
        return findAccountNumber.get();
    }
}
