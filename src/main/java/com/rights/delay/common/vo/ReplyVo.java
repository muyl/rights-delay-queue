package com.rights.delay.common.vo;

import com.rights.delay.common.constant.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Reply.
 *
 * @param <T> the type parameter
 * @author shanks on 2019-09-26
 */
@Data
@Builder
public class ReplyVo<T> {

    private String code;
    private String message;
    private T      data;

    public ReplyVo() {
    }

    /**
     * Reply vo
     *
     * @param code    code
     * @param message message
     */
    public ReplyVo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Reply vo
     *
     * @param resultEnum result enum
     */
    public ReplyVo(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    /**
     * Reply vo
     *
     * @param resultEnum result enum
     * @param data       data
     */
    public ReplyVo(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }

    public ReplyVo(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

