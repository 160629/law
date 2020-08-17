package com.chinatower.product.legalms.base;

import com.chinatower.product.legalms.RequestHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: G D
 * @Date: 2019/10/24 12:53
 * @Description:控制器支持类
 */
public class BaseController {

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response) {
        RequestHolder.requestLocal.set(request);
        RequestHolder.responseLocal.set(response);
    }
}
