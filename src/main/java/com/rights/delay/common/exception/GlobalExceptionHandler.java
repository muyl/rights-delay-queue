package com.rights.delay.common.exception;


import com.rights.delay.common.constant.ResultEnum;
import com.rights.delay.common.vo.ReplyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;

/**
 * The type Global exception handler.
 *
 * @author shanks
 * @since 2019 -09-25  16:02
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * Logic exception handler reply.
     *
     * @param e the e
     * @return the reply
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object logicExceptionHandler(Exception e) {
        ReplyVo replyVo;
        if (e instanceof LogicException) {
            LogicException logicException = (LogicException) e;
            replyVo = ReplyVo.builder()
                    .code(logicException.getResultEnum().getCode())
                    .message(logicException.getResultEnum().getMessage())
                    .build();
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            String defaultMessage = Objects.requireNonNull(bindException.getFieldError()).getDefaultMessage();
            replyVo = ReplyVo.builder()
                    .code(ResultEnum.REQUEST_PARAM_EMPTY.getCode())
                    .message(defaultMessage)
                    .build();
        }else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException bindException = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = bindException.getBindingResult();
            String defaultMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            replyVo = ReplyVo.builder()
                    .code(ResultEnum.REQUEST_PARAM_EMPTY.getCode())
                    .message(defaultMessage)
                    .build();
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            replyVo = ReplyVo.builder()
                    .code(ResultEnum.HTTP_MEDIA_TYPE_NOT_SUPPORTED.getCode())
                    .message(ResultEnum.HTTP_MEDIA_TYPE_NOT_SUPPORTED.getMessage())
                    .build();
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            replyVo = ReplyVo.builder()
                    .code(ResultEnum.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getCode())
                    .message(ResultEnum.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getMessage())
                    .build();
        } else if (e instanceof NoHandlerFoundException) {
            replyVo = ReplyVo.builder()
                    .code(ResultEnum.NO_HANDLER_FOUND_EXCEPTION.getCode())
                    .message(ResultEnum.NO_HANDLER_FOUND_EXCEPTION.getMessage())
                    .build();
        } else if (e instanceof MissingServletRequestParameterException) {
            replyVo = ReplyVo.builder()
                    .code(ResultEnum.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION.getCode())
                    .message(ResultEnum.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION.getMessage())
                    .build();
        } else if (e instanceof HttpMessageNotReadableException) {
            replyVo = ReplyVo.builder()
                    .code(ResultEnum.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getCode())
                    .message(ResultEnum.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getMessage())
                    .build();
        } else {
            //如果是系统逻辑异常，不打印
            logger.error("系统异常：" + e.getMessage(), e);
            replyVo = ReplyVo.builder()
                    .code(ResultEnum.INNER_EXCEPTION.getCode())
                    .message(ResultEnum.INNER_EXCEPTION.getMessage())
                    .build();
        }
        logger.info("返回参数：{}", replyVo);
        return replyVo;
    }
}

