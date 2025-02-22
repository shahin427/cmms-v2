package org.sayar.net.Scheduler.exceptionHandling;

public class ApiRepetitiveException extends RuntimeException {
        public ApiRepetitiveException() {
            super("ورودی تکراری");
        }
    }
