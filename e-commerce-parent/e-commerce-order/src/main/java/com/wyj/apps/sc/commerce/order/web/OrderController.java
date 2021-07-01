package com.wyj.apps.sc.commerce.order.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyj.apps.common.core.apiresult.ApiResultVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyingjie <wuyingjie@kuaishou.com>
 * Created on 2021-04-26
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @GetMapping("/info/{orderNo}")
    public ApiResultVO<String> order(@PathVariable("orderNo") String orderNo) {
        log.info("orderInfo:{}", orderNo);
        return ApiResultVO.buildSuccess("order/info/" + orderNo);
    }

}
