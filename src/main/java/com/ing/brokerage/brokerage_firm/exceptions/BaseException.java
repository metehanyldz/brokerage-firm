package com.ing.brokerage.brokerage_firm.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private HttpStatus status;

    public BaseException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
