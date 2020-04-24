package com.rights.delay.common.constant;

/**
 * 系统返回消息码
 * 区段含义：
 * 第1位（固定x标识，没有特殊含义）
 * 第2位（系统）
 * 第3-4位（功能模块）
 * 第5-8位（错误代码，依次顺延）
 * 例如：X1010001
 *
 * @author shanks
 * @since 2019 -09-20  17:51
 */
public enum ResultEnum {

    /**
     * Trans success result enum
     */
    //全局参数开始
    TRANS_SUCCESS("200", "交易成功"),
    /**
     * No handler found exception result enum
     */
    NO_HANDLER_FOUND_EXCEPTION("404", "请求错误"),
    /**
     * Inner exception result enum
     */
    INNER_EXCEPTION("500", "内部错误"),
    /**
     * Service busy result enum
     */
    SERVICE_BUSY("500101", "系统繁忙"),
    /**
     * Valid data failure result enum
     */
    VALID_DATA_FAILURE("500102", "数据校验不通过"),
    /**
     * Request timeout result enum
     */
    REQUEST_TIMEOUT("500103", "请求超时"),
    /**
     * Service not exists result enum
     */
    SERVICE_NOT_EXISTS("500107", "服务不存在"),
    /**
     * Incr value less then zero result enum
     */
    INCR_VALUE_LESS_THEN_ZERO("500301", "递增数值不能小于0"),
    /**
     * Cache timeout less then zero result enum
     */
    CACHE_TIMEOUT_LESS_THEN_ZERO("500302", "缓存超时时间不能小于0"),
    /**
     * Http media type not supported result enum
     */
    HTTP_MEDIA_TYPE_NOT_SUPPORTED("500303", "HttpMediaTypeNotSupported"),
    /**
     * Http request method not supported result enum
     */
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("500304", "HttpRequestMethodNotSupported"),
    /**
     * Http message not readable exception result enum
     */
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION("500305", "HttpMessageNotReadableException"),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION("500306", "MissingServletRequestParameterException"),
    //全局参数结束

    REQUEST_PARAM_EMPTY("400101", "参数错误"),

    ADD_ORDER_ACTY_MESSAGE_MISS("801001","活动信息不存在"),
    ADD_ORDER_ACTY_LIFE_MISS("801002","活动兑换频率为空"),
    ADD_ORDER_ACTY_STATUS_DOWN("801003","活动已结束"),
    ADD_ORDER_ACTY_STATUS_UP("801004","活动未上架"),
    ADD_ORDER_ACTY_STATUS_START("801005","活动未开始"),
    ADD_ORDER_ACTY_STATUS_END("801006","活动已结束"),
    ;

    private String code;
    private String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get message string.
     *
     * @param code the code
     * @return the string
     */
    public static String getMessage(String code) {
        for (ResultEnum value : ResultEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getMessage();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
