package com.example.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class AccountRequest {
    private Long idAccount;
    private Date dateTransaction;
    private String accountNumber;
    private BigDecimal balance;
}
