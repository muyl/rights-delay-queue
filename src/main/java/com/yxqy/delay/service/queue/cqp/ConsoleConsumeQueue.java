package com.yxqy.delay.service.queue.cqp;

import com.yxqy.delay.common.extension.ExtNamed;
import com.yxqy.delay.domain.JobMessage;
import com.yxqy.delay.common.exception.ConsumeQueueException;
import com.yxqy.delay.service.queue.core.ConsumeQueueProvider;
import com.yxqy.delay.common.util.JsonUtil;

/**
 * @author 拓仲 on 2020/3/11
 */
@ExtNamed("consoleCQ")
public class ConsoleConsumeQueue implements ConsumeQueueProvider {


    @Override
    public void consumer(JobMessage job) throws ConsumeQueueException {
        System.out.println(String.format("invoke topic %s json:%s", job.getTopic(),
                JsonUtil.convertObjectToJSON(job)));
    }

}
