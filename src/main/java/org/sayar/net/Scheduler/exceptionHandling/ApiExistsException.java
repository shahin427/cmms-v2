package org.sayar.net.Scheduler.exceptionHandling;

public class ApiExistsException extends RuntimeException{
    public ApiExistsException(){
        super("The_Object_Exists_SomeWhere_Else");
    }
}
