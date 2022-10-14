package com.example.business.repository;

import com.example.business.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account,Long> {

    @Override
    Account save (Account account);

    Optional<Account> findByAccountNumber(String accountNumber);

}
