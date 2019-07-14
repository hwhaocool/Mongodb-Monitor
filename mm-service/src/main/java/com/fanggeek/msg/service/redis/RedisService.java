//package com.fanggeek.msg.service.redis;
//
//import com.fanggeek.base.service.redis.RedisAbstractService;
//import redis.clients.jedis.ShardedJedisPool;
//
//
///**
// * <br>redis service
// * <br>bean 在 RedisConfig 里注入
// *
// * @author YellowTail
// * @since 2019-06-05
// */
//public class RedisService extends RedisAbstractService {
//    
//    private ShardedJedisPool jedisPoolInit;
//    
//    public RedisService(ShardedJedisPool pool) {
//        jedisPoolInit = pool;
//    }
//
//    @Override
//    protected ShardedJedisPool getJedisPool() {
//        return jedisPoolInit;
//    }
//
//}
