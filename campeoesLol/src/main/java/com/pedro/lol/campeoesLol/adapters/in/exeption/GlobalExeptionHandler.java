package com.pedro.lol.campeoesLol.adapters.in.exeption;

import com.pedro.lol.campeoesLol.domain.exeption.ChampionNotFoundExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeptionHandler {

    private final Logger logger =  LoggerFactory.getLogger(GlobalExeptionHandler.class);

    @ExceptionHandler(ChampionNotFoundExeption.class)
    public ResponseEntity<ApiError> handleDomainExeption(ChampionNotFoundExeption domainError){
        return ResponseEntity.unprocessableEntity()
                .body(new ApiError(domainError.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleDomainExeption(Exception unexpectedError){
        String message = "Ops! Ocorreu um erro inesperado!";
        logger.error("", unexpectedError );
        return ResponseEntity.internalServerError()
                .body(new ApiError(message));
    }


    public record ApiError(String message){}
}

