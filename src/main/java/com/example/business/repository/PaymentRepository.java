package com.example.business.repository;

import com.example.business.entity.Account;
import com.example.business.entity.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Override
    Payment save (Payment payment);

    //Optional<Payment> findPaymentTransactionByAccountNumber(String accountNumber);

    Optional<Payment> findByAccountOrigin(String accountNumber);
    Optional<Payment> findByAccountDestination(String accountNumber);
}
