package com.rights.delay.web;

import com.rights.delay.domain.DelayMessage;
import com.rights.delay.domain.JobMessage;
import com.rights.delay.service.queue.Queue;
import com.rights.delay.common.util.JobIdGenerator;
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

        JobMessage job  = new JobMessage();
        job.setBody(delayMessage.getBody());
        job.setTopic(delayMessage.getTopic());
        job.setDelay(delayMessage.getDelay());
        job.setId(JobIdGenerator.getStringId());
        redisQueue.push(job);

    }
}
