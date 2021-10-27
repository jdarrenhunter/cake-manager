package com.waracle.cakemgr.exception;

public class DuplicateCakeException extends RuntimeException {
    public DuplicateCakeException(String message) {
        super(message);
    }
}