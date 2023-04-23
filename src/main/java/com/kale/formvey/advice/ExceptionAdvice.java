package com.kale.formvey.advice;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> baseException(BaseException e) {
        System.out.println("Handle CommonException:" + e.getMessage());
        return new BaseResponse<>(e.getStatus());
    }
}
