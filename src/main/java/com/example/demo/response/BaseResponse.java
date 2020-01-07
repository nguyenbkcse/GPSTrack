package com.example.demo.response;

public class BaseResponse<T> {

    private T data;

    private String errorMessage;

    protected BaseResponse() {
        
    }
    
    protected BaseResponse(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    protected BaseResponse(final T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
