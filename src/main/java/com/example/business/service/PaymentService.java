package com.example.business.service;

import com.example.business.entity.Payment;
import io.reactivex.Single;

import java.util.concurrent.ExecutionException;

public interface PaymentService {

    public Single<Payment> transactionP2P(Payment payment) throws ExecutionException, InterruptedException;
}
