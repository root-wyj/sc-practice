package com.wyj.apps.sc.commerce.good.web;

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
@RequestMapping("/good")
@Slf4j
public class GoodController {

    @GetMapping("/info/{goodNo}")
    public ApiResultVO<String> good(@PathVariable("goodNo") String goodNo) {
        log.info("goodInfo:{}", goodNo);
        return ApiResultVO.buildSuccess("good/info/" + goodNo);
    }

}
