package com.mylearing.springboot.usercachesync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mylearing.springboot.usercachesync.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
