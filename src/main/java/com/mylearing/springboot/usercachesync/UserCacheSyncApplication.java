package com.mylearing.springboot.usercachesync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.mylearing.springboot.usercachesync.listener.UserMessageListener;

@SpringBootApplication
@EnableCaching
public class UserCacheSyncApplication {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public final static String QUEUE_8080 = "queue8080";
	public final static String QUEUE_8085 = "queue8085";
	public final static String ROUTING_KEY_8080 = "8080";
	public final static String ROUTING_KEY_8085 = "8085";
	public final static String EXCHANGE = "user";
	

	@Bean
	Queue queue8080() {
		return new Queue(QUEUE_8080, false);
	}
	
	@Bean
	Queue queue8085() {
		return new Queue(QUEUE_8085, false);
	}
	
	

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(EXCHANGE);
	}

	@Bean
	Binding binding8080(@Qualifier(QUEUE_8080) Queue queue8080, TopicExchange exchange) {
		return BindingBuilder.bind(queue8080).to(exchange).with(ROUTING_KEY_8080);
	}
	
	@Bean
	Binding binding8085(@Qualifier(QUEUE_8085) Queue queue8085, TopicExchange exchange) {
		return BindingBuilder.bind(queue8085).to(exchange).with(ROUTING_KEY_8085);
	}
	
	

	
	/*
	 * @Bean public MessageConverter jsonMessageConverter() { return new
	 * Jackson2JsonMessageConverter(); }
	 */
	 
	
	@Bean
	SimpleMessageListenerContainer container8080(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter8080) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE_8080);
		container.setMessageListener(listenerAdapter8080);
		return container;
	}
	
	@Bean
	SimpleMessageListenerContainer container8085(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter8085) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE_8085);
		container.setMessageListener(listenerAdapter8085);
		return container;
	}
	
	
	 
	
	
    
	
	
	/*
	 * @Bean public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory)
	 * { final RabbitTemplate rabbitTemplate = new
	 * RabbitTemplate(connectionFactory);
	 * rabbitTemplate.setMessageConverter(messageConverter()); return
	 * rabbitTemplate; }
	 * 
	 * @Bean public Jackson2JsonMessageConverter messageConverter() { return new
	 * Jackson2JsonMessageConverter(); }
	 */
	 

	@Bean
	MessageListenerAdapter listenerAdapter8080(UserMessageListener receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage8080");
	}
	
	@Bean
	MessageListenerAdapter listenerAdapter8085(UserMessageListener receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage8085");
	}
	
	

	public static void main(String[] args) {
		SpringApplication.run(UserCacheSyncApplication.class, args);
	}

}
