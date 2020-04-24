package com.rights.delay.common.exception;

import com.rights.delay.common.constant.ResultEnum;
import com.rights.delay.common.vo.ReplyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * The type Rest reply wrapper.
 *
 * @author shanks
 * @since 2019 -09-25  16:50
 */
@ControllerAdvice(basePackages = "com.rights.delay.web")
public class GlobalResponseHandler implements ResponseBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalResponseHandler.class);


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object returnValue, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (returnValue instanceof ReplyVo){
            return returnValue;
        }
        ReplyVo<Object> replyVo = ReplyVo.builder()
                .code(ResultEnum.TRANS_SUCCESS.getCode())
                .message(ResultEnum.TRANS_SUCCESS.getMessage())
                .data(returnValue).build();
        logger.info("返回参数：{}", replyVo);
        return replyVo;
    }


}


