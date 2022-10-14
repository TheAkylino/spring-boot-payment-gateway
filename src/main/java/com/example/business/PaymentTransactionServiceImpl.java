package com.example.business;

import com.example.business.entity.Account;
import com.example.business.entity.Payment;
import com.example.business.repository.AccountRepository;
import com.example.business.repository.PaymentRepository;
import com.example.exceptions.PaymentTransactionException;
import io.reactivex.Maybe;
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
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public Single<Account> saveAccount(Account account)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentTransactionServiceImpl", "saveAccount");
        return validatingAccount(account).map(acc -> {
            accountRepository.save(account);
            return Single
                    .just(account)
                    .doOnError(throwable ->
                            log.info("Error {}.{} method, with error {}", "PaymentTransactionServiceImpl",
                                    "saveAccount", throwable.getMessage()));
        }).toFuture().get();
    }

    @Override
    public Single<Account> updateAccount(Account account) {
        return null;
    }

    @Override
    public Maybe<Account> findByAccountNumber(String accountNumber) {
        log.info("Starting {}.{} method", "PaymentTransactionServiceImpl", "findByAccountNumber");
        Optional<Account> findAccountNumber =
                Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber)
                        .orElseThrow(() -> new PaymentTransactionException(ACCOUNT_NUMBER_NOT_FOUND)));
        return findAccountNumber.isPresent() ? Maybe.just(findAccountNumber.get()) : Maybe.empty();
    }


    @Override
    public Single<Payment> savePaymentTransaction(Payment Payment)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentTransactionServiceImpl", "saveAccount");
        return validatingPayment(Payment).map(acc -> {
            paymentRepository.save(Payment);
            return Single
                    .just(Payment)
                    .doOnError(throwable ->
                            log.info("Error {}.{} method, with error {}", "PaymentTransactionServiceImpl",
                                    "saveAccount", throwable.getMessage()));
        }).toFuture().get();
    }


    private Single<Account> validatingAccount(Account account) {
        String accountNumber;
        BigDecimal currentBalance;
        if (account != null) {
            accountNumber = account.getAccountNumber();
            if (accountNumber == null) throw new PaymentTransactionException(ACCOUNT_NUMBER_REQUIRED);
            if (accountNumber.isEmpty()) throw new PaymentTransactionException(ACCOUNT_NUMBER_EMPTY);

            currentBalance = account.getBalance();
            if (currentBalance == null) throw new PaymentTransactionException(BALANCE_REQUIRED);
            if (currentBalance.toString().contains("-")) throw new PaymentTransactionException(BALANCE_NOT_NEGATIVE);

            Optional<Account> findAccountExists = accountRepository.findByAccountNumber(accountNumber);
            findAccountExists.ifPresent(value -> {
                account.setIdAccount(value.getIdAccount());
                account.setBalance(value.getBalance().add(currentBalance));
            });

        } else {
            throw new PaymentTransactionException(OBJECT_ACCOUNT_REQUIRED);
        }
        return Single.just(account);
    }

    private Single<Payment> validatingPayment(Payment payment) {
        String accountOrigin;
        String accountDestination;
        BigDecimal balanceTransfer;
        if (payment != null) {
            payment.setPaymentId(Utils.getPaymentId());
            payment.setOperationNumber(String.valueOf(Utils.generateRandom()));

            accountOrigin = payment.getAccountOrigin();
            if (accountOrigin == null) throw new PaymentTransactionException(ACCOUNT_ORIGIN_REQUIRED);
            if (accountOrigin.isEmpty()) throw new PaymentTransactionException(ACCOUNT_ORIGIN_EMPTY);
            Optional<Payment> findAccountOrigin = paymentRepository.findByAccountOrigin(accountOrigin);
            if (findAccountOrigin.isEmpty()) {
                throw new PaymentTransactionException(ACCOUNT_ORIGIN_NOT_REGISTERED);
            }

            accountDestination = payment.getAccountDestination();
            if (accountDestination == null) throw new PaymentTransactionException(ACCOUNT_DESTINATION_REQUIRED);
            if (accountDestination.isEmpty()) throw new PaymentTransactionException(ACCOUNT_DESTINATION_EMPTY);
            Optional<Payment> findAccountDestination = paymentRepository.findByAccountDestination(accountOrigin);
            if (findAccountDestination.isEmpty()) {
                throw new PaymentTransactionException(ACCOUNT_DESTINATION_NOT_REGISTERED);
            }

            balanceTransfer = payment.getBalanceTransfer();
            if (balanceTransfer == null) throw new PaymentTransactionException(BALANCE_REQUIRED);
            if (balanceTransfer.toString().contains("-")) throw new PaymentTransactionException(BALANCE_NOT_NEGATIVE);

        } else {
            throw new PaymentTransactionException(OBJECT_PAYMENT_REQUIRED);
        }
        return Single.just(payment);
    }
}
