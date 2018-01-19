package com.example.channel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
public class PublishSubscribeChannelExample {


	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PublishSubscribeChannelExample.class, args);
		System.out.println("***********StartingBoot Application ***********");
		PublishSubscribeChannel messageChannel = ctx.getBean("messageHub", PublishSubscribeChannel.class);

		messageChannel.subscribe(ctx.getBean("goodMorningMessageHandler", MessageHandler.class));
		messageChannel.subscribe(ctx.getBean("goodEveningMessageHandler", MessageHandler.class));

		messageChannel.send(new GenericMessage<String>("Sudhir"));

	}

	@Bean
	public MessageChannel messageHub(){
		return new PublishSubscribeChannel();
	}

	@Bean
	public MessageHandler goodMorningMessageHandler(){
		return f->System.out.println("Good morning "+f.getPayload().toString());
	}
	@Bean
	public MessageHandler goodEveningMessageHandler(){
		return f->System.out.println("Good Evening "+f.getPayload().toString());
	}

}
