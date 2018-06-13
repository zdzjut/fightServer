package com.zd.fight.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Spring Boot支持跨域请求的JSONP数据.
 */
@ControllerAdvice(basePackages = {"com.zd.fight.controller"})
public class JSONPConfiguration extends AbstractJsonpResponseBodyAdvice {

    public JSONPConfiguration() {
        super("callback", "jsonp");
    }
}