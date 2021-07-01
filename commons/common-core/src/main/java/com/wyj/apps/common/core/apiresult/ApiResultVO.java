package com.wyj.apps.common.core.apiresult;

import lombok.Data;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/14
 */

@Data
public class ApiResultVO<T> {

    private String code;
    private String msg;
    private T data;

    ApiResultVO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ApiResultVO(IResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    ApiResultVO(IResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = data;
    }

    public static <T> ApiResultVO<T> buildSuccess() {
        return new ApiResultVO<>(CommonResultEnum.SUCCESS);
    }

    public static <T> ApiResultVO<T> buildSuccess(T data) {
        return new ApiResultVO<>(CommonResultEnum.SUCCESS, data);
    }

    public static <T> ApiResultVO<T> buildFailed() {
        return new ApiResultVO<>(CommonResultEnum.FAILED);
    }

    public static <T> ApiResultVO<T> build(IResultEnum resultEnum) {
        return new ApiResultVO<>(resultEnum);
    }

    public static <T> ApiResultVO<T> build(IResultEnum resultEnum, String msg) {
        return new ApiResultVO<>(resultEnum.getCode(), msg);
    }
}
