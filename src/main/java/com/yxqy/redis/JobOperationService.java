package com.yxqy.redis;

import com.yxqy.domain.JobMsg;
import com.yxqy.domain.Status;

import java.util.List;

/**
 * @author 拓仲 on 2020/3/10
 */
public interface JobOperationService {

    /**
     * 获取Job元数据
     *
     * @param jobId 任务编号
     * @return 任务信息
     */
    JobMsg getJob(String jobId);

    /**
     * 添加Job到元数据池
     *
     * @param jobMsg 任务信息
     */
    void addJobToPool(JobMsg jobMsg);

    /**
     * 删除元数据此任务
     *
     * @param jobId 任务编号
     */
    void removeJobToPool(String jobId);

    /**
     * 更新元任务池任务的状态
     *
     * @param jobId  任务编号
     * @param status 任务状态
     */
    void updateJobStatus(String jobId, Status status);

    /**
     * 根据JobId删除元数据
     *
     * @param jobId 任务编号
     */
    void deleteJobToPool(String jobId);

    /**
     * 加一个Job到指定Bucket
     *
     * @param bucketName Bucket
     * @param JobId      任务编号
     * @param score      失效时间
     */
    void addBucketJob(String bucketName, String JobId, double score);

    /**
     * 从指定Bucket删除一个Job
     *
     * @param bucketName Bucket
     * @param jobId      任务编号
     */
    void removeBucketKey(String bucketName, String jobId);

    /**
     * 添加一个Job到 可执行队列
     *
     * @param readyName 实时队列
     * @param jobId     任务编号
     */
    void addReadyTime(String readyName, String jobId);


    /**
     * 获取一个实时队列中的第一个数据
     *
     * @return 任务信息
     */
    String getReadyJob();

    /**
     * 获取指定个数实时队列中的数据 不是用的POP方式 需要手動刪除
     *
     * @param size 获取数量
     * @return 任务列表
     */
    List<String> getReadyJob(int size);

    /**
     * 刪除实时队列中的一个数据
     *
     * @param jobId 任务编号
     * @return 删除标识
     */
    boolean removeReadyJob(String jobId);

    /**
     * 获取bucket中最顶端的一个Job
     *
     * @param bucketName 延时队列
     * @return 任务信息
     */
    String getBucketTop1Job(String bucketName);

    /**
     * 批量获取顶端数据 只获取满足条件的数据 最多<code>size</code>行
     *
     * @param bucketName 延时队列
     * @param size       任务数量
     * @return 任务列表
     */
    List<String> getBucketTopJobs(String bucketName, int size);

    /**
     * 清空队列
     */
    void clearAll();
}
