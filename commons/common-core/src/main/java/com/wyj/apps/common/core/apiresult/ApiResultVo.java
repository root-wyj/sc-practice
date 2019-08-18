package com.wyj.apps.common.core.apiresult;

import lombok.Data;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/14
 */

@Data
public class ApiResultVo<T> {

    private String code;
    private String msg;
    private T data;

    ApiResultVo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ApiResultVo(IResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    ApiResultVo(IResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = data;
    }

    public static <T> ApiResultVo<T> buildSuccess() {
        return new ApiResultVo<>(CommonResultEnum.SUCCESS);
    }

    public static <T> ApiResultVo<T> buildSuccess(T data) {
        return new ApiResultVo<>(CommonResultEnum.SUCCESS, data);
    }

    public static <T> ApiResultVo<T> buildFailed() {
        return new ApiResultVo<>(CommonResultEnum.FAILED);
    }

    public static <T> ApiResultVo<T> build(IResultEnum resultEnum) {
        return new ApiResultVo<>(resultEnum);
    }

    public static <T> ApiResultVo<T> build(IResultEnum resultEnum, String msg) {
        return new ApiResultVo<>(resultEnum.getCode(), msg);
    }
}
