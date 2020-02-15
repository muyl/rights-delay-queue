package com.yxqy.domain;

/**
 * @author 拓仲(牟云龙) on 2020/2/15
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by yanshao on 2019/2/12.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestParam implements Serializable {

    private static final long serialVersionUID = 8608875806674149703L;

    private String data1;
    private String data2;
    private String data3;
    private int data4;

    @Override
    public String toString() {
        //将bean转换成bean string
        return JSON.toJSONString(this);
    }
}
