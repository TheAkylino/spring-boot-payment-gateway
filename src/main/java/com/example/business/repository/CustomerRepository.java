package com.example.business.repository;

import com.example.business.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository  extends CrudRepository<Customer,Long> {

    @Override
    Customer save(Customer customer);

}
