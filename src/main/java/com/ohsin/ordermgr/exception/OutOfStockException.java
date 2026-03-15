package com.ohsin.ordermgr.exception;

public class OutOfStockException extends BusinessException {

    public OutOfStockException(String message) {
        super(message);
    }
}