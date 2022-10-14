package com.example.business.impl;

import com.example.business.PaymentTransactionServiceImpl;
import com.example.business.entity.Account;
import com.example.business.repository.AccountRepository;
import com.example.exceptions.PaymentTransactionException;
import com.google.gson.Gson;
import io.reactivex.observers.TestObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static utils.Constant.ACCOUNT_NUMBER_EMPTY;
import static utils.Constant.ACCOUNT_NUMBER_REQUIRED;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceImplTest {

    @InjectMocks
    private PaymentTransactionServiceImpl paymentService;

    @Mock
    private AccountRepository accountRepository;

    private Account account;

    @Test
    @DisplayName("CREAR CUENTA - CON TODOS SUS VALORES OK")
    void saveAccountOk()
            throws IOException, ExecutionException, InterruptedException {
        account = new Gson()
                .fromJson(
                        FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:json/accountOK.json"),
                                StandardCharsets.UTF_8), Account.class);

        when(accountRepository.save(isA(Account.class))).thenReturn(account);
        Assertions.assertNotNull(account);
        var test = paymentService.saveAccount(account).test();
        test.assertSubscribed();
    }

    @Test
    @DisplayName("CREAR CUENTA - SIN EL NUMERO DE CUENTA")
    void saveAccountSinNumeroDeCuenta() throws IOException {
        account = new Gson()
                .fromJson(
                        FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:json/accountSinNumeroDeCuenta.json"),
                                StandardCharsets.UTF_8), Account.class);

        AtomicReference<TestObserver> test = null;
        PaymentTransactionException thrown = Assertions.assertThrows(PaymentTransactionException.class, () -> {
            Assertions.assertNotNull(account);
            test.set(paymentService.saveAccount(account).test());
        });
        Assertions.assertEquals(ACCOUNT_NUMBER_REQUIRED, thrown.getMessage());
    }

    @Test
    @DisplayName("CREAR CUENTA - NUMERO DE CUENTA VACIO")
    void saveAccountNumeroDeCuentaVacia() throws IOException {
        account = new Gson()
                .fromJson(
                        FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:json/accountNumeroDeCuentaVacia.json"),
                                StandardCharsets.UTF_8), Account.class);

        AtomicReference<TestObserver> test = null;
        PaymentTransactionException thrown = Assertions.assertThrows(PaymentTransactionException.class, () -> {
            Assertions.assertNotNull(account);
            test.set(paymentService.saveAccount(account).test());
        });
        Assertions.assertEquals(ACCOUNT_NUMBER_EMPTY, thrown.getMessage());
    }
}