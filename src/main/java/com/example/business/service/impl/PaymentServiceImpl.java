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

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static utils.Constant.ACCOUNT_DESTINATION_NOT_FOUND;
import static utils.Constant.ACCOUNT_ORIGIN_NOT_FOUND;

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
        return validatetransactionPayment(payment)
                .map(objectpayment -> {
                    paymentRepository.save(payment);
                    return Single.just(payment);
                }).toFuture().get();
    }

    private Single<Payment> validatetransactionPayment(Payment payment) {
        log.info("Starting {}.{} method", "AccountServiceImpl", "validateParametersCustomer");
        // TODO: VALIDAR

        return logicP2P(payment);
    }

    //send money accountOrigin to accountDestination
    private Single<Payment> logicP2P(Payment payment) {
        Account accountOrigin = null;
        Account accountDestination = null;

        accountOrigin = getDataWithAccountOrigin(payment);
        payment.setBalanceAccountOrigin(accountOrigin.getBalance());

        accountDestination = getDataWithAccountDestination(payment);
        payment.setBalanceAccountDestination(accountDestination.getBalance());

        accountOrigin.setBalance(accountOrigin.getBalance().subtract(payment.getBalanceTransfer()));
        accountRepository.save(accountOrigin);
        return Single.just(payment);
    }

    //send money accountDestination to accountOrigin
    private Single<Payment> logicP2M(Payment payment) {

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
