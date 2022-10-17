package com.example.business.repository;

import com.example.business.entity.Account;
import com.example.business.entity.Customer;
import com.example.business.entity.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Objects;
import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment, Long> {


    @Override
    Payment save(Payment payment);

    @Query(value = "SELECT * FROM ACCOUNTS ACC WHERE ACC.ACCOUNT_NUMBER  = :accountNumber",
            nativeQuery = true)
    Optional<Payment> getBalanceByAccountOrigin(@Param("accountNumber") String accountNumber);

    @Query(value = "SELECT * FROM ACCOUNTS ACC WHERE ACC.ACCOUNT_NUMBER  = :accountNumber",
            nativeQuery = true)
    Optional<Payment> getBalanceByDestination(@Param("accountNumber") String accountNumber);

    //    @Query(value = "SELECT * FROM CUSTOMERS CUS JOIN ACCOUNTS A ON A.FK_CUSTOMER = CUS.ID_CUSTOMER WHERE A.ACCOUNT_NUMBER = :accountNumber", nativeQuery = true)
    @Query(value = "SELECT * FROM ACCOUNTS ACC WHERE ACC.ACCOUNT_NUMBER  = :accountNumber",
            nativeQuery = true)
    Optional<Account> getDataWithAccountOrigin(@Param("accountNumber") String accountNumber);

}
