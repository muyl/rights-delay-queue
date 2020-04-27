package com.rights.delay.service.redis;

import com.google.common.collect.Lists;
import com.rights.delay.common.util.NamedUtil;
import com.rights.delay.domain.Status;
import com.rights.delay.service.redis.support.RedisQueueProperties;
import com.rights.delay.domain.JobMessage;
import com.rights.delay.service.redis.bucket.BucketTask;
import com.rights.delay.service.redis.support.RedisSupport;
import com.rights.delay.common.util.JsonUtil;
import lombok.Setter;
import org.springframework.util.Assert;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Job operation service
 *
 * @author 拓仲 on 2020/3/10
 */
public class JobOperationServiceImpl implements JobOperationService {

    @Setter
    private RedisSupport         redisSupport;
    @Setter
    private RedisQueueProperties properties;

    private String getPoolName() {
        String name = NamedUtil.buildRealTimeName(properties.getPrefix(), properties.getName(), properties.getOriginPool());
        return name;
    }

    private String geReadyName() {
        String name = NamedUtil.buildRealTimeName(properties.getPrefix(), properties.getName(), properties.getReadyName());
        return name;
    }

    @Override
    public JobMessage getJob(String jobId) {
        String text = redisSupport.getHashKey(getPoolName(), jobId);
        return JsonUtil.convertJSONToObject(text, JobMessage.class);
    }

    @Override
    public void addJobToPool(JobMessage jobMessage) {
        redisSupport.hashPut(getPoolName(), jobMessage.getId(), JsonUtil.convertObjectToJSON(jobMessage));
    }

    @Override
    public void removeJobToPool(String jobId) {
        redisSupport.deleteHashKeys(getPoolName(), jobId);
    }

    @Override
    public void updateJobStatus(String jobId, Status status) {
        JobMessage msg = getJob(jobId);
        Assert.notNull(msg, String.format("JobId %s 数据已不存在", jobId));
        msg.setStatus(status.ordinal());
        addJobToPool(msg);
    }

    @Override
    public void deleteJobToPool(String jobId) {
        redisSupport.deleteHashKeys(getPoolName(), jobId);
    }

    @Override
    public void addBucketJob(String bucketName, String JobId, double score) {
        redisSupport.zadd(bucketName, JobId, score);
    }

    @Override
    public void removeBucketKey(String bucketName, String jobId) {
        redisSupport.zrem(bucketName, jobId);
    }

    @Override
    public String getBucketTop1Job(String bucketName) {
        double to = Long.valueOf(System.currentTimeMillis() + BucketTask.TIME_OUT).doubleValue();
        Set<String> sets = redisSupport.zrangeByScore(bucketName, 0, to, 0, 1);
        if (sets != null && sets.size() > 0) {
            return Objects.toString(sets.toArray()[0]);
        }
        return null;
    }

    @Override
    public List<String> getBucketTopJobs(String bucketName, int size) {
        double to = Long.valueOf(System.currentTimeMillis() + BucketTask.TIME_OUT).doubleValue();
        Set<Tuple> tuples = redisSupport.zrangeByScoreWithScores(bucketName, 0, to, 0, size);
        List<String> lsts = Lists.newArrayList();
        for (Tuple tuple : tuples) {
            if (tuple.getScore() <= System.currentTimeMillis()) {
                lsts.add(tuple.getElement());
            } else {
                break;
            }
        }
        return lsts;
    }

    @Override
    public void addReadyTime(String readyName, String jobId) {
        redisSupport.rightPush(readyName, jobId);
    }

    @Override
    public String getReadyJob() {
        return redisSupport.leftPop(geReadyName());
    }

    @Override
    public List<String> getReadyJob(int size) {
        List<String> root = redisSupport.lrange(geReadyName(), 0, size);
        if (null != root && root.size() > 0) {
            root = Lists.reverse(root);
        }
        return root;
    }

    @Override
    public boolean removeReadyJob(String jobId) {
        return redisSupport.lrem(getReadyJob(), jobId);
    }


    @Override
    public void clearAll() {

    }
}
