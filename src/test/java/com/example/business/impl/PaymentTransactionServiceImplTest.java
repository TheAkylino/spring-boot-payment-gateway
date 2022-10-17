package com.example.business.impl;

import com.example.business.entity.Account;
import com.example.business.entity.Customer;
import com.example.business.repository.AccountRepository;
import com.example.business.repository.CustomerRepository;
import com.example.business.service.impl.CustomerServiceImpl;
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
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    private Account account;

    private Customer customer;

    @Test
    @DisplayName("CREAR CLIENTE - CON TODOS SUS VALORES OK")
    void addCustomerOK()
            throws IOException, ExecutionException, InterruptedException {
        customer = new Gson()
                .fromJson(
                        FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:json/addCustomer.json"),
                                StandardCharsets.UTF_8), Customer.class);

        when(customerRepository.save(isA(Customer.class))).thenReturn(customer);
        Assertions.assertNotNull(customer);
        var test = customerService.createCustomer(customer).test();
        test.assertSubscribed();
    }

    @Test
    @DisplayName("CREAR CLIENTE - SIN NOMBRE")
    void addCreateCustomerNameless()
            throws IOException, ExecutionException, InterruptedException {
        customer = new Gson()
                .fromJson(
                        FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:json/addCustomerSinNombre.json"),
                                StandardCharsets.UTF_8), Customer.class);

        AtomicReference<TestObserver> test = null;
        PaymentTransactionException thrown = Assertions.assertThrows(PaymentTransactionException.class, () -> {
            Assertions.assertNotNull(customer);
            test.set(customerService.createCustomer(customer).test());
        });
        Assertions.assertEquals("THE FIRT NAME IS REQUIRED", thrown.getMessage());
    }

    @Test
    @DisplayName("CREAR CLIENTE - SIN APELLIDO")
    void addCreateCustomerLastName()
            throws IOException, ExecutionException, InterruptedException {
        customer = new Gson()
                .fromJson(
                        FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:json/addCustomerSinApellido.json"),
                                StandardCharsets.UTF_8), Customer.class);

        AtomicReference<TestObserver> test = null;
        PaymentTransactionException thrown = Assertions.assertThrows(PaymentTransactionException.class, () -> {
            Assertions.assertNotNull(customer);
            test.set(customerService.createCustomer(customer).test());
        });
        Assertions.assertEquals("THE LAST NAME IS REQUIRED", thrown.getMessage());
    }


    @Test
    @DisplayName("CREAR CLIENTE - SIN DNI")
    void addCreateCustomerdni()
            throws IOException, ExecutionException, InterruptedException {
        customer = new Gson()
                .fromJson(
                        FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:json/addCustomerSinDni.json"),
                                StandardCharsets.UTF_8), Customer.class);

        AtomicReference<TestObserver> test = null;
        PaymentTransactionException thrown = Assertions.assertThrows(PaymentTransactionException.class, () -> {
            Assertions.assertNotNull(customer);
            test.set(customerService.createCustomer(customer).test());
        });
        Assertions.assertEquals("THE DNI IS REQUIRED", thrown.getMessage());
    }


}