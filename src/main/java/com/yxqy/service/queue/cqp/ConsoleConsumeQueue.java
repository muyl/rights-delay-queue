package com.yxqy.service.queue.cqp;

import com.yxqy.common.extension.ExtNamed;
import com.yxqy.domain.JobMessage;
import com.yxqy.common.exception.ConsumeQueueException;
import com.yxqy.service.queue.core.ConsumeQueueProvider;
import com.yxqy.common.util.JsonUtil;

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
