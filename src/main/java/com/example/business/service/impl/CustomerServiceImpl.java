package com.example.business.service.impl;

import com.example.business.entity.Account;
import com.example.business.entity.Customer;
import com.example.business.repository.CustomerRepository;
import com.example.business.repository.AccountRepository;
import com.example.business.service.CustomerService;
import com.example.exceptions.PaymentTransactionException;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static utils.Constant.OBJECT_CUSTOMER_IS_NULL;

@Slf4j
@Service
public class CustomerServiceImpl  implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository AccountRepository;

    @Override
    public Single<Customer> createCustomer(Customer customer)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "CustomerServiceImpl", "createCustomer");
        return validateParametersCustomer(customer)
                .map(cus -> {
                    customerRepository.save(customer);
                    return Single
                            .just(customer)
                            .doOnError(throwable ->
                                    log.info("Error {}.{} method, with error {}", "CustomerServiceImpl",
                                            "createCustomer", throwable.getMessage()));
                }).toFuture().get();
    }

    private Single<Customer> validateParametersCustomer(Customer customer){
        if (customer == null) throw new PaymentTransactionException(OBJECT_CUSTOMER_IS_NULL);
        // TODO : VALIDAR

        return associateCustomerWithAccountNumber(customer);
    }

    private Single<Customer> associateCustomerWithAccountNumber(Customer customer) {
        List<Account> accountList = new ArrayList<>();
        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountNumber(Utils.generateBankAccount());
        accountList.add(account);

        customer.setAccount(accountList);
        return Single.just(customer);
    }
}
