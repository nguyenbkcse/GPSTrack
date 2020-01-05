package com.example.demo.response;

public class BaseResponse<T> {

    private T data;

    private String message;

    protected BaseResponse() {
        
    }
    
    protected BaseResponse(final String message) {
        this.message = message;
    }

    protected BaseResponse(final T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

}
