package com.mju.complaint.domain.model.Exception;

public class NonExceptionReportedReview extends RuntimeException{
    private final ExceptionList exceptionList;

    public NonExceptionReportedReview(ExceptionList exceptionList) {
        super(exceptionList.getMessage());
        this.exceptionList = exceptionList;
    }

    public ExceptionList getExceptionList() {
        return exceptionList;
    }
}
