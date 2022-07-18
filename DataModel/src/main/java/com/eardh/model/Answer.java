package com.eardh.model;

import com.eardh.model.enums.ResultCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应格式封装
 * @author dahuang
 * @date 2022/6/11 9:21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Answer implements Serializable {

    private static final long serialVersionUID = -4505655308965878999L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 响应数据
     */
    private Object data;

    public Answer(ResultCode code) {
        this.code = code.getCode();
    }

    public Answer(ResultCode code, String message) {
        this.code = code.getCode();
        this.message = message;
    }

    public static Answer success() {
        return new Answer(ResultCode.ok);
    }

    public static Answer success(Object data) {
        Answer answer = new Answer(ResultCode.ok);
        answer.setData(data);
        return answer;
    }

    public static Answer fail(ResultCode code, String message) {
        Answer answer = new Answer(ResultCode.fail);
        answer.setMessage(message);
        answer.setCode(code.getCode());
        return answer;
    }

    public static Answer fail(String message) {
        Answer answer = new Answer(ResultCode.fail);
        answer.setMessage(message);
        return answer;
    }
}
