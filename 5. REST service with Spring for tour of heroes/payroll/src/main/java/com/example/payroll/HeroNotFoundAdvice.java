package com.example.payroll;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


//overrides default exception message when specified exception is thrown
//check also previous project (first-web-app)

@ControllerAdvice
class HeroNotFoundAdvice {

  @ResponseBody //render directly, no template
  @ExceptionHandler(HeroNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND) //issues a 404
  String heroNotFoundHandler(HeroNotFoundException ex) {
    return ex.getMessage();
  }
}