package org.example.hospital.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView Error(HttpServletRequest req, Exception e){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message",e.getMessage());
        modelAndView.setViewName("error");
        logger.error(e.getMessage());
        return modelAndView;
    }
}
