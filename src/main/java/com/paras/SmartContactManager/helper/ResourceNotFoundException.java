package com.paras.SmartContactManager.helper;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message)
    {
        super(message);
    }
    public ResourceNotFoundException(String message,Throwable cause){
        
    }


}
