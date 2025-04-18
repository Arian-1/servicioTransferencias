package com.example.transferencia.service.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String msg) {
        super(msg);
    }
}