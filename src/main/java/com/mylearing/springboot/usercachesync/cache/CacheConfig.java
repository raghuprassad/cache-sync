package com.mylearing.springboot.usercachesync.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;

@Configuration
public class CacheConfig {

	@Bean
    public Config hazelCastConfig() {

        Config config = new Config();
        config.setInstanceName("hazelcast-cache");

        MapConfig allUsersCache = new MapConfig();
        allUsersCache.setTimeToLiveSeconds(20);
        allUsersCache.setEvictionPolicy(EvictionPolicy.LFU);
        config.getMapConfigs().put("alluserscache",allUsersCache);

        MapConfig usercache = new MapConfig();
        usercache.setTimeToLiveSeconds(20);
        usercache.setEvictionPolicy(EvictionPolicy.LFU);
        config.getMapConfigs().put("usercache",usercache);

        return config;
    }
}

