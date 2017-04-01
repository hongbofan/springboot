package com.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by yfmacmini001 on 2017/3/2.
 */

@Service
public class RedisUtil{

    @Resource
    private JedisPool redisPool;

    public Long setnx(String key,String value) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            return redis.setnx(key, value);
        } finally {
            redis.close();
        }
    }

    public Set<String> getKeys(String key) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            return redis.keys(key);
        } finally {
            redis.close();
        }
    }

    public boolean unLock(String key){
        return delete(key) != null;
    }

    public boolean checkLock(String key, int second) {

        String lockKey = "lock:" + key;
        try {
            // 1表示之前不存在，设置成功
            if (setnx(lockKey, "lock") == 1) {
                // 设置有限期
                expire(lockKey, second);
                return true;
            } else {
                // 50毫秒的延迟，避免过多请求
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                }
                return false;
            }

        } catch (Exception e) {
            return true;
        }
    }
    public Object set(String key, int expire, String value) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            String msg = redis.set(SafeEncoder.encode(key), SafeEncoder.encode(value));
            if (msg.equals("OK")) {
                return redis.expire(SafeEncoder.encode(key), expire);
            } else {
                return msg;
            }
        } finally {
            redis.close();
        }

    }

    public String setex(String key, int seconds, String value) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            String setex = redis.setex(key, seconds, value);
            return setex;
        } finally {
            redis.close();
        }

    }
    public Object set(String key, int expire, byte[] value) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            String msg = redis.set(SafeEncoder.encode(key), value);
            if (msg.equals("OK")) {
                return redis.expire(SafeEncoder.encode(key), expire);
            } else {
                return msg;
            }
        } finally {
            redis.close();
        }
    }

    public Object append(String key, String value) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            return redis.append(SafeEncoder.encode(key), SafeEncoder.encode(value));
        } finally {
            redis.close();
        }
    }

    public Object append(String key, byte[] value) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            return redis.append(SafeEncoder.encode(key), value);
        } finally {
            redis.close();
        }
    }

    public long incr(String key, long by) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            return redis.incrBy(SafeEncoder.encode(key), by);
        } finally {
            redis.close();
        }
    }
    public long decr(String key, long by) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            return redis.decrBy(SafeEncoder.encode(key), by);
        } finally {
            redis.close();
        }
    }

    public String getString(String key) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            byte[] result = redis.get(SafeEncoder.encode(key));
            if (null != result) {
                return SafeEncoder.encode(result);
            } else {
                return null;
            }
        } finally {
            redis.close();
        }
    }

    public byte[] getBinary(String key) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            return redis.get(SafeEncoder.encode(key));
        } finally {
            redis.close();
        }
    }

    public Object delete(String key) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            return redis.del(SafeEncoder.encode(key));
        } finally {
            redis.close();
        }
    }

    public Long expire(String key, int seconds) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            return redis.expire(SafeEncoder.encode(key), seconds);
        } finally {
            redis.close();
        }
    }

    public String hgetString(String key, String field) {
        Jedis redis = null;
        try {
            redis = redisPool.getResource();
            byte[] hget = redis.hget(key.getBytes(), field.getBytes());
            if (hget == null || hget.length == 0)
                return null;
            return new String(hget);
        } finally {
            redis.close();
        }
    }
}
