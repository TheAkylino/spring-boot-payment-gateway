package com.example.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utils.Utils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAYMENTS")
public class Payment {

    @Id
    @Column(name = "PAYMENT_ID", unique = true)
    @JsonIgnore
    private String paymentId;

    @Column(name="DATE_TRANSACTION", nullable=false, updatable=false)
    private String dateTransaction = Utils.getTime();

    @Column(name = "ACCOUNT_ORIGIN", updatable = true, nullable = false, unique = true, length = 20)
    private String accountOrigin;

    @Column(name = "BALANCE_ACCOUNT_ORIGIN", updatable = true, nullable = true)
    @JsonIgnore
    private BigDecimal balanceAccountOrigin;

    @Column(name = "ACCOUNT_DESTINATION", updatable = true, nullable = false, unique = true, length = 20)
    private String accountDestination;

    @Column(name = "BALANCE_ACCOUNT_Destination", updatable = true, nullable = true)
    @JsonIgnore
    private BigDecimal balanceAccountDestination;

    @Column(name = "BALANCE_TRANSFER", updatable = true, nullable = true)
    private BigDecimal balanceTransfer;

    @Column(name = "OPERATION_NUMBER", updatable = true, nullable = true)
    private String operationNumber;
}
