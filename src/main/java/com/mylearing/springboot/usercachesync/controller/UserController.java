package com.mylearing.springboot.usercachesync.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mylearing.springboot.usercachesync.UserCacheSyncApplication;
import com.mylearing.springboot.usercachesync.entity.User;
import com.mylearing.springboot.usercachesync.repository.UserRepository;



@RestController
@CacheConfig(cacheNames = "alluserscache")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@PostMapping("/cache-sync")
	public ResponseEntity createUser (@RequestBody User user) {
		
		String port = environment.getProperty("local.server.port");
		ObjectMapper mapper = new ObjectMapper();
		if (port.equals("8080")) {
			try {
				String json = mapper.writeValueAsString(user);
				rabbitTemplate.convertAndSend(UserCacheSyncApplication.EXCHANGE,"8080", json);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
			}
		}
		
		if (port.equals("8085")) {
			try {
				String json = mapper.writeValueAsString(user);
				rabbitTemplate.convertAndSend(UserCacheSyncApplication.EXCHANGE,"8085", json);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
			}
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	
	
	@GetMapping("/cache-sync")
	@Cacheable("alluserscache")
	public List<User> getUser () {
		List<User> users = userRepository.findAll();
		return users.stream().collect(Collectors.toList());
	}
	
	
}
