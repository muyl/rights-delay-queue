package com.rights.delay.service.leader;

import com.rights.delay.common.util.IpUtils;
import com.rights.delay.service.queue.Queue;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Xs.Tao on 2017/7/28.
 */
public class LeaderWorkListener implements LeaderLatchListener {

    /**
     * LOGGER
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(LeaderWorkListener.class);
    private Queue queue;

    @Override
    public void isLeader() {
        LOGGER.warn("Server {}, starting work!", IpUtils.getHostAndIp());
        queue.start();
    }

    @Override
    public void notLeader() {
        LOGGER.warn("Server {},stop work!", IpUtils.getHostAndIp());
        queue.stop();
    }

    /**
     * Sets queue *
     *
     * @param queue queue
     */
    public void setQueue(Queue queue) {
        this.queue = queue;
    }
}
