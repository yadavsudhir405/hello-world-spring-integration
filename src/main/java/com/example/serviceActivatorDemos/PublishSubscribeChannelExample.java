package com.example.serviceActivatorDemos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
public class PublishSubscribeChannelExample {


	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PublishSubscribeChannelExample.class, args);

		PublishSubscribeChannel messageChannel = ctx.getBean("messageHub", PublishSubscribeChannel.class);
		messageChannel.send(new GenericMessage<Object>("Hello world"));
	}

	@Bean
	public PublishSubscribeChannel messageHub(){
		return new PublishSubscribeChannel();
	}

	@ServiceActivator(inputChannel = "messageHub")
	public void handleMessage(Message<String> message){
		System.out.println("ServiceActivator1: from Channel "+message.getPayload());
	}

	@ServiceActivator(inputChannel = "messageHub")
	public void parallelMessageHandler(Message<String> message){
		System.out.println("ServiceActivator2: from Channel "+message.getPayload());

	}








}
