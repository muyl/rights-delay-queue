package com.yxqy.queue.cqp;

import com.yxqy.common.extension.ExtNamed;
import com.yxqy.domain.JobMessage;
import com.yxqy.exception.ConsumeQueueException;
import com.yxqy.queue.core.ConsumeQueueProvider;
import com.yxqy.util.JsonUtil;

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
