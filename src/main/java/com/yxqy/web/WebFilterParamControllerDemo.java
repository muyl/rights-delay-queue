package com.yxqy.web;

/**
 * @author 拓仲(牟云龙) on 2020/2/15
 */

import com.alibaba.fastjson.JSON;
import com.yxqy.domain.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by yanshao on 2019/2/12.
 */
@RestController
@RequestMapping(value = {"/web_request"})
public class WebFilterParamControllerDemo {


    @PostMapping(value = "test1")
    public String webReqestParamTest(RequestParam requestParam){
        System.out.println("webReqestParamTest>>>>>>>>>>>>");
        System.out.println(requestParam.toString());
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        return requestParam.toString();

    }

    @GetMapping (value = "test2")
    public String webReqestParamTest2(RequestParam requestParam){
        System.out.println("webReqestParamTest>>>>>>>>>>>>");
        System.out.println(requestParam.toString());
        return requestParam.toString();
    }

}
