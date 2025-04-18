package com.example;


public class MyModException extends Exception  {
    private String errorCode;
    
    public MyModException(){}


    public MyModException(String message){
        super("From MyMod-------/n" + message);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
