package com.examportal.helper.exception;

public class UserFoundException extends Exception {
    public UserFoundException() {
        super("User already present in DB!!");
    }
}
