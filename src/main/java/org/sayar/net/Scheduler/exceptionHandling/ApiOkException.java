package org.sayar.net.Scheduler.exceptionHandling;

public class ApiOkException extends RuntimeException{
    public ApiOkException(String message) {
        super(message);
    }
}
