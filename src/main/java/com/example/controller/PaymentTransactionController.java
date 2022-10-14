package com.example.controller;

import com.example.business.entity.Account;
import com.example.business.PaymentTransactionService;
import com.example.business.entity.Payment;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@Slf4j
@RestController
@RequestMapping("/api/v1/payment-transaction")
public class PaymentTransactionController {

    @Autowired
    private PaymentTransactionService service;


    @PostMapping("/addAccount")
    public Single<Account> addAccount(@RequestBody Account account)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentTransactionController", "createAccount");
        return service.saveAccount(account)
                .doOnSuccess(s ->
                        log.info("Success {}.{} method - {}", "PaymentTransactionController", "addAccount", s))
                .doOnError(throwable -> log.info("Error {}.{} method, with error {}", "PaymentTransactionController",
                        "addAccount", throwable.getMessage()))
                .doOnTerminate(() ->
                        log.info("Terminate {}.{} method", "PaymentTransactionController", "addAccount"));
    }

    @GetMapping("/account/{accountNumber}")
    public Maybe<Account> findByAccountNumber(@PathVariable String accountNumber) {
        log.info("Starting {}.{} method", "PaymentTransactionController", "createAccount");
        return service.findByAccountNumber(accountNumber)
                .doOnSuccess(s ->
                        log.info("Success {}.{} method - {}", "PaymentTransactionController", "findByAccountNumber", s))
                .doOnError(throwable -> log.info("Error {}.{} method, with error {}", "PaymentTransactionController",
                        "findByAccountNumber", throwable.getMessage()))
                .doOnTerminate(() ->
                        log.info("Terminate {}.{} method", "PaymentTransactionController", "findByAccountNumber"));
    }


    @PostMapping("/p2p")
    public Single<Payment> transactionP2p(@RequestBody Payment payment)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentTransactionController", "createAccount");
        return service.savePaymentTransaction(payment)
                .doOnSuccess(s ->
                        log.info("Success {}.{} method - {}", "PaymentTransactionController", "paymentTransaction", s))
                .doOnError(throwable -> log.info("Error {}.{} method, with error {}", "PaymentTransactionController",
                        "paymentTransaction", throwable.getMessage()))
                .doOnTerminate(() ->
                        log.info("Terminate {}.{} method", "PaymentTransactionController", "paymentTransaction"));
    }

    @PostMapping("/p2m")
    public Single<Payment> transactionP2m(@RequestBody Payment payment)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentTransactionController", "createAccount");
        return service.savePaymentTransaction(payment)
                .doOnSuccess(s ->
                        log.info("Success {}.{} method - {}", "PaymentTransactionController", "paymentTransaction", s))
                .doOnError(throwable -> log.info("Error {}.{} method, with error {}", "PaymentTransactionController",
                        "paymentTransaction", throwable.getMessage()))
                .doOnTerminate(() ->
                        log.info("Terminate {}.{} method", "PaymentTransactionController", "paymentTransaction"));
    }
}
