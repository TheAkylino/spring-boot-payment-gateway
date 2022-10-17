package com.example.business.repository;

import com.example.business.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account,Long> {

    @Override
    Account save(Account account);


    @Query(value = "SELECT * FROM ACCOUNTS ACC WHERE ACC.ACCOUNT_NUMBER  = :accountNumber",
            nativeQuery = true)
    Optional<Account> findByAccountNumber(@Param("accountNumber") String accountNumber);
}
