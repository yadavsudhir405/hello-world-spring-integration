package com.example.com.example.channel.serviceActivatorCombination;

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
public class FooExample {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(FooExample.class, args);

		DirectChannel messageChannel = ctx.getBean("channel123", DirectChannel.class);
		messageChannel.send(new GenericMessage<Object>("Hello world"));
	}

	@Bean
	public DirectChannel channel123(){
		return new DirectChannel();
	}

	@Bean
	public PublishSubscribeChannel channel2(){
		return new PublishSubscribeChannel();
	}

	@ServiceActivator(inputChannel = "channel123", outputChannel = "channel2")
	public String consumeMessage1(Message<String> message){
		return message.getPayload()+"ServiceActivator1-->";
	}

	@ServiceActivator(inputChannel = "channel2")
	public void consumeMessage2(Message<String> message){
		System.out.println(message.getPayload()+" ServiceActivator2");
	}

}
