package com.rights.delay.common.exception;


import com.rights.delay.common.constant.ResultEnum;

/**
 * The type Logic exception.
 *
 * @author shanks
 * @since 2019 -09-25  16:02
 */
public class LogicException extends RuntimeException{
    /**
     * 异常信息
     */
    private ResultEnum resultEnum;

    /**
     * Gets result enum.
     *
     * @return the result enum
     */
    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    /**
     * Sets result enum.
     *
     * @param resultEnum the result enum
     */
    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    /**
     * Instantiates a new Logic exception.
     *
     * @param resultEnum the result enum
     */
    public LogicException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }
}



