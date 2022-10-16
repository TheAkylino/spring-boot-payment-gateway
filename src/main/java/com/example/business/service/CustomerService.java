package com.example.business.service;

import com.example.business.entity.Customer;
import io.reactivex.Single;

import java.util.concurrent.ExecutionException;

public interface CustomerService {

    public Single<Customer> createCustomer(Customer customer) throws ExecutionException, InterruptedException;
}
