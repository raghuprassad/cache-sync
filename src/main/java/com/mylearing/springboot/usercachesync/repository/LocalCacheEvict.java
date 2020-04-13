package com.mylearing.springboot.usercachesync.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class LocalCacheEvict {

    @CacheEvict(cacheNames = "alluserscache",allEntries = true)
    public void evictAllUsersCache() {

    }

    @CacheEvict(cacheNames = "usercache",allEntries = true)
    public void evictUserCache() {

    }

}
