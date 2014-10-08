package com.ccsi.app.exception;

/**
 * @author mbmartinez
 */
public class InsufficientPushCreditsException extends Exception {

    private static final long serialVersionUID = 1L;

    public InsufficientPushCreditsException(String msg) {
        super(msg);
    }
}
