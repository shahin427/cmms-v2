package org.sayar.net.Scheduler.exceptionHandling;

public class ApiParentFoundException extends RuntimeException{
    public ApiParentFoundException() {
        super("ExistInDataBase");
    }
}
