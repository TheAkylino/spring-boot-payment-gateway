package com.example.business.service;

import com.example.business.entity.Account;
import com.example.business.entity.Customer;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.concurrent.ExecutionException;

public interface AccountService {

    public Single<Account> addBalance(Account account) throws ExecutionException, InterruptedException;

    public Single<Account> modifyBalance(Account account) throws ExecutionException, InterruptedException;
    public Maybe<Account> getBalanceByAccountNumber(String dni) throws ExecutionException, InterruptedException;
}
