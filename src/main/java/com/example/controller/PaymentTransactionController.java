package com.example.controller;

import com.example.business.entity.Account;
import com.example.business.entity.Customer;
import com.example.business.entity.Payment;
import com.example.business.service.AccountService;
import com.example.business.service.CustomerService;
import com.example.business.service.PaymentService;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@Slf4j
@RestController
@RequestMapping("/api/v1/payment-transaction")
public class PaymentTransactionController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/addCustomer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Single<Customer> addCustomer(@RequestBody Customer customer)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "customerService", "addCustomer");
        return customerService.createCustomer(customer);
    }

    @PostMapping("/addBalance")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Single<Account> addBalance(@RequestBody Account account)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "customerService", "addCustomer");
        return accountService.addBalance(account);
    }

    @PutMapping("/modifyBalance")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Single<Account> updateBalance(@RequestBody Account account)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "customerService", "addCustomer");
        return accountService.addBalance(account);
    }

    @GetMapping("/consulBalance/{accountNumber}")
    @ResponseStatus(code = HttpStatus.OK)
    public Maybe<Account> getBalanceByAccountNumber(@PathVariable String accountNumber)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "customerService", "addCustomer");
        return accountService.getBalanceByAccountNumber(accountNumber)
                .doOnSuccess(s ->
                        log.info("Success {}.{} method - {}", "PaymentTransactionController", "getBalanceById", s))
                .doOnError(throwable -> log.info("Error {}.{} method, with error {}", "PaymentTransactionController",
                        "getBalanceById", throwable.getMessage()))
                .doOnTerminate(() ->
                        log.info("Terminate {}.{} method", "PaymentTransactionController", "getBalanceById"));
    }

    @PostMapping("/paymentTransaction/p2p")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Single<Payment> paymentTransactionP2P(@RequestBody Payment payment)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentTransactionController", "paymentTransactionP2P");
        return paymentService.transactionP2P(payment);
    }

    @PostMapping("/paymentTransaction/p2m")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Single<Payment> paymentTransactionP2m(@RequestBody Payment payment)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentTransactionController", "paymentTransactionP2m");
        return paymentService.transactionP2M(payment);
    }
}
