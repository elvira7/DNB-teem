package com.bop.backend.exception;

/**
 * Created by Robert on 19.04.2017.
 */
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {}

    //Constructor that accepts a message
    public InsufficientFundsException(String message)
    {
        super(message);
    }

}
