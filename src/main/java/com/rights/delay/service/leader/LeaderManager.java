package com.rights.delay.service.leader;


import com.rights.delay.service.queue.Lifecycle;

/**
 * Created by Xs.Tao on 2017/7/28.
 */
public interface LeaderManager extends Lifecycle {

    /**
     * Is leader boolean
     *
     * @return the boolean
     */
    boolean isLeader();



}
