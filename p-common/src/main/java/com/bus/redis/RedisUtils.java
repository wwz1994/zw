package com.bus.redis;

import org.springframework.beans.factory.InitializingBean;
import com.bus.redis.RedisPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/09/04 10:44
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class RedisUtils implements InitializingBean{
    private static RedisPoolConfig redisPoolConfig;
    private static String host;
    private static Integer port;
    private static Integer expire;
    private static Integer timeout = 10000;
    private static String password;
    private static JedisPool jedisPool;
    private static Jedis jedis;
    private static Lock lock = new ReentrantLock(true);
    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    static  class  ObjectUtils{
        public static boolean isEmpty(CharSequence charSequence){
            if(charSequence == null || "".equals(charSequence)){
                return true;
            }
            return false;
        }
    }
    private synchronized static void init(){
        if(jedisPool == null){
            JedisPoolConfig config = redisPoolConfig.getJedisPoolConfig();
            if(!ObjectUtils.isEmpty(password)){
                 jedisPool = new JedisPool(config,host,port,timeout,password);
            }else if(timeout!=0){
                jedisPool = new JedisPool(config,host,port,timeout);
            }else{
                jedisPool = new JedisPool(config,host,port);
            }
        }

    }

    private static Jedis getJedis(){
        if(jedis == null){
            if(jedisPool == null){
                init();
            }
           return  jedis = jedisPool.getResource();
        }
        return jedis;
    }

    /**
     * 获取字符串
     * @param name
     * @return
     */
    public static String getString(String name){
         jedis = getJedis();
        if(jedis != null){
            return jedis.get(name);
        }
        return null;
    }
    public static byte[] getBytes(byte[] name){
         jedis = getJedis();
        if(jedis != null){
            return jedis.get(name);
        }
        return null;
    }

    public static void setBytes(byte[] name,byte[] bytes){

        jedis = getJedis();
        if(jedis != null){
            if(!ObjectUtils.isEmpty(jedis.set(name, bytes))){
                if(expire !=null && expire !=0){
                    jedis.expire(name,expire);
                }
            }
        }
    }
    public static void setBytes(String name,byte[] bytes){
        jedis = getJedis();
        if(jedis != null){
            byte[] bytes1 = name.getBytes();
            if(!ObjectUtils.isEmpty(jedis.set(bytes1, bytes))){
                if(expire !=null && expire !=0){
                    jedis.expire(bytes1,expire);
                }
            }
        }
    }
    public static byte[] getBytes(String name){
        jedis = getJedis();
        if(jedis != null){
            return jedis.get(name.getBytes());
        }
        return null;
    }
    /**
     * 设置字符串
     * @param name
     * @param value
     */
    public static void setString(String name,String value){
        jedis = getJedis();
        if(jedis != null){
                 if(!ObjectUtils.isEmpty(jedis.set(name, value))){
                     if(expire !=null && expire !=0){
                     jedis.expire(name,expire);
                 }
             }
        }
    }

    /**
     * 设置集合
     * @param name
     */
    public static void setList(String name,List<String>list){
        String[] strings = new String[list.size()];
        jedis = getJedis();
        if(jedis != null){
            if(jedis.lpush(name,list.toArray(strings))!=null){
                if(expire !=null && expire !=0) {
                    jedis.expire(name, expire);
                }
            }
        }
    }

    /**
     * 获取集合数据
     * @param name
     * @return
     */
    public static List<String> getList(String name){
        jedis = getJedis();
        if(jedis != null){
            return jedis.lrange(name,0,10000);
        }
        return null;
    }

    /**
     * 设置Set集合
     * @param name
     * @param value
     */
    public static void setSet(String name,String...value){
        jedis = getJedis();
        if(jedis != null){
            if(jedis.sadd(name, value)!=null){
                if(expire !=null && expire !=0) {
                    jedis.expire(name, expire);
                }
            }
        }
    }

    /**
     * 获取Set集合数据
     * @param name
     * @return
     */
    public static Set<String> getSet(String name){
        jedis = getJedis();
        if(jedis != null){
            return jedis.smembers(name);
        }
        return null;
    }

    /**
     * 设置Set集合
     * @param name
     * @param value
     */
    public static void setHash(String name,Map<String,String> value){
        jedis = getJedis();
        if(jedis != null){
            if(jedis.hmset(name, value)!=null){
                if(expire !=null && expire !=0) {
                    jedis.expire(name, expire);
                }
            }
        }
    }

    /**
     * 获取Map集合数据
     * @param name
     * @return
     */
    public static Map<String,String> getHash(String name){
        jedis = getJedis();
        if(jedis != null){
            return jedis.hgetAll(name);
        }
        return null;
    }

    /**
     * 移除key
     * @param key
     * @return
     */
    public static boolean removeKey(String key){
        jedis = getJedis();
        if(jedis != null){
            return jedis.del(key)!=null?true:false;
        }
        return false;
    }
    public static boolean removeKey(byte[] key){
        jedis = getJedis();
        if(jedis != null){
            return jedis.del(key)!=null?true:false;
        }
        return false;
    }

    /** 
          * 对象转数组 
          * @param obj 
          * @return 
          */
    public static byte[] getByte(Object o){
        byte[] bytes = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(stream);
            outputStream.writeObject(o);
            bytes = stream.toByteArray();
            outputStream.flush();
            outputStream.close();
            stream.close();
        }catch (Exception e){

        }
        return bytes;

    }

    public static Object getObj(byte[] bytes){
        Object object = null;
        try{
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            object = objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        }catch (Exception e){

        }
        return object;

    }
    public RedisPoolConfig getRedisPoolConfig() {
        return redisPoolConfig;
    }

    public void setRedisPoolConfig(RedisPoolConfig redisPoolConfig) {
        this.redisPoolConfig = redisPoolConfig;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
