package com.linlai.bunnyscholar.config;

import com.linlai.bunnyscholar.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
@RestController
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<String> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error("Exception : {}", request, ex);
        String message = ex.getMessage();
        return Result.fail(message);
    }
}
