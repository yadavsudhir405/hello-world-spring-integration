package com.example.serviceActivatorChannelCombinations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
/*
   Message--->DirectChannel---->ServiceActivator---->PublishSubscribeChannel--->ServiceActivator
 */
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

	@Bean
	public PollableChannel channel3(){
		return new QueueChannel();
	}

	@ServiceActivator(inputChannel = "channel123", outputChannel = "channel2")
	public String consumeMessage1(Message<String> message){
		return message.getPayload()+"ServiceActivator1-->";
	}

	@ServiceActivator(inputChannel = "channel2",outputChannel = "channel3")
	public String consumeMessage2(Message<String> message){
		return message.getPayload()+"Service Activator2---->Channel3----->";
	}

	@ServiceActivator(inputChannel = "channel3",poller = @Poller(fixedDelay = "1000"))
	public void handleLastMessage(Message<String> message) {
		System.out.println(message.getPayload()+" Message Handleed At ServiceActivator3");
	}


}
