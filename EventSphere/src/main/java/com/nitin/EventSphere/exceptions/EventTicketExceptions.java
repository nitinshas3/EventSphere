package com.nitin.EventSphere.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EventTicketExceptions extends RuntimeException{


    public EventTicketExceptions() {
    }

    public EventTicketExceptions(Throwable cause) {
        super(cause);
    }

    public EventTicketExceptions(String message) {
        super(message);
    }

    public EventTicketExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public EventTicketExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
