package com.wyj.apps.common.core.exception;

import com.wyj.apps.common.core.apiresult.IResultEnum;
import lombok.Getter;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/14
 */

@Getter
public class BusinessException extends RuntimeException{

    private String code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(IResultEnum resultEnum) {
        this(resultEnum.getCode(), resultEnum.getMsg());
    }

    public BusinessException(IResultEnum resultEnum, String message) {
        this(resultEnum.getCode(), message);
    }

}
