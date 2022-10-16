package com.example.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "DATE_TRANSACTION", nullable = false, updatable = false)
    private String dateTransaction = Utils.getTime();

    @Column(name = "ACCOUNT_NUMBER", length = 20)
    private String accountNumber;

    @Column(name = "BALANCE", updatable = true, nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_customer", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Customer customer;

}
