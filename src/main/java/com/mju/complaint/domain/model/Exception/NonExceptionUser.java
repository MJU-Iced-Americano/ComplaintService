package com.mju.complaint.domain.model.Exception;

public class NonExceptionUser extends RuntimeException{
    private final ExceptionList exceptionList;

    public NonExceptionUser(ExceptionList exceptionList) {
        super(exceptionList.getMessage());
        this.exceptionList = exceptionList;
    }


    public ExceptionList getExceptionList() {
        return exceptionList;
    }
}
