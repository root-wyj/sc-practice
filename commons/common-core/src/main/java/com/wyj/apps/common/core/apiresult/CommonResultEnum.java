package com.wyj.apps.common.core.apiresult;

import lombok.Getter;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/14
 */

@Getter
public enum CommonResultEnum implements IResultEnum{

    SUCCESS("000000", "SUCCESS"),
    FAILED("000002", "FAILED"),
    SYSTEM_ERROR("000003", "ERROR"),
    DISTRIBUTE_LOCK_FAILED("000004", "distribute lock failed"),

    // 文件相关 000100 开始
    FILE_NULL("000101", "文件不能为空"),
    FILE_ERROR("000102", "上传文件失败"),
    FILE_UNSUPPORT_FORMAT("000103", "不支持的文件格式"),

    // 微服务相关 000200 开始
    MIC_SERVER_FAILED("000201", "微服务调用失败"),
    MIC_SERVER_TIMEOUT("000202", "微服务调用超时"),
    MIC_SERVER_TIMEOUT_HYSTRIX("000203", "微服务调用超时熔断"),
    MIC_SERVER_CHECK_ENCRYPT_FAIL("000204", "微服务校验失败"),

    ;

    private String code;
    private String msg;

    CommonResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
