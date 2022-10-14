package com.example.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utils.Utils;

import javax.persistence.*;
import java.math.BigDecimal;



@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccount;

    @Column(name="DATE_TRANSACTION", nullable=false, updatable=false)
    private String dateTransaction = Utils.getTime();

    @Column(name = "ACCOUNT_NUMBER", updatable = true, nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(name = "BALANCE", updatable = true, nullable = true)
    private BigDecimal balance;
}
