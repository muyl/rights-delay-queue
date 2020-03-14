package com.yxqy.web;

import com.yxqy.domain.DelayMessage;
import com.yxqy.domain.JobMessage;
import com.yxqy.queue.core.Queue;
import com.yxqy.util.JobIdGenerator;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 拓仲 on 2020/3/14
 */
@RestController
@RequestMapping("/api/delay/")
public class DelayJobController {

    @Resource(name = "redisQueueImpl")
    private Queue redisQueue;

    @RequestMapping(value = "/sendMessage.json", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendMessage(@RequestBody DelayMessage delayMessage) {
        Assert.notNull(delayMessage.getTopic(), "请求参数topic缺失");

        redisQueue.push(JobMessage.builder()
                .topic(delayMessage.getTopic())
                .body(delayMessage.getBody())
                .id(JobIdGenerator.getStringId()).build());

    }
}
