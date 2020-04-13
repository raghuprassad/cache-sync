package com.mylearing.springboot.usercachesync.listener;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mylearing.springboot.usercachesync.entity.User;
import com.mylearing.springboot.usercachesync.repository.UserRepository;

@Component
public class UserMessageListener {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Environment environment;
	
    public void receiveMessage8080(String message) {
    	User user = new User();
    	
    	
    	/** Setting port at run time based on the routing key */
    	SpringApplication app = new SpringApplication(UserMessageListener.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        //app.run();
        
        logger.info("Environment is set to :: {} for receiveMessage8080", Integer.parseInt(environment.getProperty("local.server.port")));
        
		try {
			user = new ObjectMapper().readValue(message, User.class);
			userRepository.save(user);
		} catch (JsonMappingException ex) {
			logger.error(ex.getMessage());
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		logger.info("Received message in receiveMessage8080.............................................................. <" + user.getUserName() + ">");
    	logger.info("Received message in receiveMessage8080############################################################### <" + user.getRole() + ">");
        logger.info("Message processed...");
    }
    
    public void receiveMessage8085(String message) {
    	User user = new User();
    	
    	
    	/** Setting port at run time based on the routing key */
    	SpringApplication app = new SpringApplication(UserMessageListener.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8085"));
        //app.run();
        logger.info("Environment is set to :: {} for receiveMessage8085", Integer.parseInt(environment.getProperty("local.server.port")));
		try {
			user = new ObjectMapper().readValue(message, User.class);
			userRepository.save(user);
		} catch (JsonMappingException ex) {
			logger.error(ex.getMessage());
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		logger.info("Received message in receiveMessage8085.............................................................. <" + user.getUserName() + ">");
    	logger.info("Received message in receiveMessage8085############################################################### <" + user.getRole() + ">");
        logger.info("Message processed...");
    }
}
