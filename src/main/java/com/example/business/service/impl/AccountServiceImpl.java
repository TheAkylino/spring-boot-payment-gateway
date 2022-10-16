package com.example.business.service.impl;

import com.example.business.entity.Account;
import com.example.business.entity.Customer;
import com.example.business.repository.AccountRepository;
import com.example.business.repository.CustomerRepository;
import com.example.business.service.AccountService;
import com.example.exceptions.PaymentTransactionException;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static utils.Constant.*;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Single<Account> addBalance(Account account) throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "AccountServiceImpl", "addBalance");
        return validateParametersCustomer(account)
                .map(objectAccount -> {
                    accountRepository.save(account);
                    return Single.just(account);
                }).toFuture().get();
    }

    @Override
    public Single<Account> modifyBalance(Account account)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "AccountServiceImpl", "modifyBalance");
        return validateParametersCustomer(account)
                .map(objectAccount -> {
                    accountRepository.save(account);
                    return Single.just(account);
                }).toFuture().get();
    }


    private Single<Account> validateParametersCustomer(Account account) {
        log.info("Starting {}.{} method", "AccountServiceImpl", "validateParametersCustomer");
        if (account == null) throw new PaymentTransactionException(OBJECT_CUSTOMER_IS_NULL);
        if (account.getAccountNumber() != null) {
            Optional<Account> findAccountNumber = Optional
                    .ofNullable(accountRepository.findByAccountNumber(account.getAccountNumber())
                    .orElseThrow(() -> new PaymentTransactionException(ACCOUNT_NUMBER_NOT_FOUND)));
            account.setIdAccount(findAccountNumber.get().getIdAccount());
            account.setAccountNumber(findAccountNumber.get().getAccountNumber());

            if (findAccountNumber.get().getBalance() != null) {
                // balance ya existente
                account.setBalance(findAccountNumber.get().getBalance());
            } else {
                account.setBalance(account.getBalance());
            }
            account.setDateTransaction(findAccountNumber.get().getDateTransaction());
            account.setCustomer(findAccountNumber.get().getCustomer());
        } else {
            throw new PaymentTransactionException(ACCOUNT_NUMBER_REQUIRED);
        }

        if (account.getAccountNumber().isEmpty()) throw new PaymentTransactionException(ACCOUNT_NUMBER_EMPTY);

        if (account.getBalance() == null) throw new PaymentTransactionException(BALANCE_REQUIRED);
        if (account.getBalance().toString().contains("-")) throw new PaymentTransactionException(BALANCE_NOT_NEGATIVE);

        return Single.just(account);
    }

    @Override
    public Maybe<Account> getBalanceByAccountNumber(String accountNumber)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentTransactionServiceImpl", "findByAccountNumber");
        Optional<Account> findAccountNumber =
                Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber)
                        .orElseThrow(() -> new PaymentTransactionException(ACCOUNT_NUMBER_NOT_FOUND)));
        return findAccountNumber.isPresent() ? Maybe.just(findAccountNumber.get()) : Maybe.empty();
    }

}
