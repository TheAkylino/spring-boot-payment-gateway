package com.example.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "ACCOUNT_NUMBER", length = 20)
    private String accountNumber;

    @Column(name = "BALANCE", updatable = true, nullable = true)
    private BigDecimal balance;

    @Column(name = "ACCOUNT_ORIGIN",  length = 20)
    @JsonIgnore
    private String accountOrigin;

    @Column(name = "BALANCE_ACCOUNT_ORIGIN")
    @JsonIgnore
    private BigDecimal balanceAccountOrigin;

    @Column(name = "ACCOUNT_DESTINATION", length = 20)
    @JsonIgnore
    private String accountDestination;

    @Column(name = "BALANCE_ACCOUNT_Destination")
    @JsonIgnore
    private BigDecimal balanceAccountDestination;

    @Column(name = "BALANCE_TRANSFER")
    @JsonIgnore
    private BigDecimal balanceTransfer;

    @Column(name = "OPERATION_NUMBER")
    @JsonIgnore
    private String operationNumber;
}
