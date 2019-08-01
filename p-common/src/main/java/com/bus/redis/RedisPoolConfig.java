package com.bus.redis;

import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/11 10:32
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class RedisPoolConfig implements InitializingBean {
   private Integer maxTotal = 100;

   private Integer maxIdle = 10;

   private Integer maxWaitMillis = 3000;

   private boolean testOnBorrow = true;

   private boolean testWhileIdle = true;

   private boolean testOnReturn = true;

   private Integer softMinEvictableIdleTimeMillis = 1000;

   private Integer minEvictableIdleTimeMillis = 1000;

   private Integer numTestsPerEvictionRun = 100;

   private boolean blockWhenExhausted ;

   private JedisPoolConfig jedisPoolConfig;

   @Override
   public void afterPropertiesSet() throws Exception {
      jedisPoolConfig = new JedisPoolConfig();
      jedisPoolConfig.setMaxTotal(maxTotal);
      jedisPoolConfig.setMaxIdle(maxIdle);
      jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
      jedisPoolConfig.setTestOnBorrow(testOnBorrow);
      jedisPoolConfig.setTestOnReturn(testOnReturn);
      jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);
      jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
      jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
      jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
   }

   public Integer getMaxTotal() {
      return maxTotal;
   }

   public void setMaxTotal(Integer maxTotal) {
      this.maxTotal = maxTotal;
   }

   public Integer getMaxIdle() {
      return maxIdle;
   }

   public void setMaxIdle(Integer maxIdle) {
      this.maxIdle = maxIdle;
   }

   public Integer getMaxWaitMillis() {
      return maxWaitMillis;
   }

   public void setMaxWaitMillis(Integer maxWaitMillis) {
      this.maxWaitMillis = maxWaitMillis;
   }

   public boolean isTestOnBorrow() {
      return testOnBorrow;
   }

   public void setTestOnBorrow(boolean testOnBorrow) {
      this.testOnBorrow = testOnBorrow;
   }

   public boolean isTestWhileIdle() {
      return testWhileIdle;
   }

   public void setTestWhileIdle(boolean testWhileIdle) {
      this.testWhileIdle = testWhileIdle;
   }

   public boolean isTestOnReturn() {
      return testOnReturn;
   }

   public void setTestOnReturn(boolean testOnReturn) {
      this.testOnReturn = testOnReturn;
   }

   public Integer getSoftMinEvictableIdleTimeMillis() {
      return softMinEvictableIdleTimeMillis;
   }

   public void setSoftMinEvictableIdleTimeMillis(Integer softMinEvictableIdleTimeMillis) {
      this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
   }

   public Integer getMinEvictableIdleTimeMillis() {
      return minEvictableIdleTimeMillis;
   }

   public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
      this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
   }

   public Integer getNumTestsPerEvictionRun() {
      return numTestsPerEvictionRun;
   }

   public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
      this.numTestsPerEvictionRun = numTestsPerEvictionRun;
   }

   public JedisPoolConfig getJedisPoolConfig() {
      return jedisPoolConfig;
   }

   public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
      this.jedisPoolConfig = jedisPoolConfig;
   }

   public boolean isBlockWhenExhausted() {
      return blockWhenExhausted;
   }

   public void setBlockWhenExhausted(boolean blockWhenExhausted) {
      this.blockWhenExhausted = blockWhenExhausted;
   }
}
