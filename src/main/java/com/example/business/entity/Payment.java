package com.example.business.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "PAYMENTS")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdPayment;

    @Column(name="DATE_TRANSACTION", nullable=false, updatable=false)
    private String dateTransaction = Utils.getTime();

    @Column(name = "ACCOUNT_ORIGIN", updatable = true, nullable = false, length = 20)
    private String accountOrigin;

    @Column(name = "BALANCE_ACCOUNT_ORIGIN", updatable = true, nullable = true)
    private BigDecimal balanceAccountOrigin;

    @Column(name = "ACCOUNT_DESTINATION", updatable = true, nullable = false, length = 20)
    private String accountDestination;

    @Column(name = "BALANCE_ACCOUNT_DESTINATION", updatable = true, nullable = true)
    private BigDecimal balanceAccountDestination;

    @Column(name = "BALANCE_TRANSFER", updatable = true, nullable = true)
    private BigDecimal balanceTransfer;

    @Column(name = "OPERATION_NUMBER", updatable = true, nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String operationNumber;
}
