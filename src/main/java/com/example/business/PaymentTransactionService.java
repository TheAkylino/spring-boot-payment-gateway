package com.example.business;

import com.example.business.entity.Account;
import com.example.business.entity.Payment;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.concurrent.ExecutionException;

public interface PaymentTransactionService {

    Single<Account> saveAccount(Account account) throws ExecutionException, InterruptedException;

    Single<Account> updateAccount(Account account);
    Maybe<Account> findByAccountNumber(String accountNumber);


    Single<Payment> savePaymentTransaction(Payment payment) throws ExecutionException, InterruptedException;
}
