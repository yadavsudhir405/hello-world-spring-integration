package com.example.serviceActivatorDemos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
public class DirectChannelExample {


	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DirectChannelExample.class, args);

		DirectChannel messageChannel = ctx.getBean("messageHub", DirectChannel.class);
		messageChannel.send(new GenericMessage<Object>("Hello world"));
	}

	@Bean
	public DirectChannel messageHub(){
		return new DirectChannel();
	}

	@ServiceActivator(inputChannel = "messageHub")
	public void handleMessage(Message<String> message){
		System.out.println("From Channel "+message.getPayload());
	}








}
