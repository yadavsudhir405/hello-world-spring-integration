package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication
public class PollableQueueChannelExample {


	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PollableQueueChannelExample.class, args);

		PollableChannel messageChannel = ctx.getBean("messageHub", QueueChannel.class);
		Producer producer = ctx.getBean("producer", Producer.class);
		Consumer consumer = ctx.getBean("consumer", Consumer.class);
		new Thread(()->{
			consumer.start();
		}).start();

		for(int i=0;i<5;i++){
			producer.sendMessage(String.valueOf(i));
		}

	}

	@Bean
	public PollableChannel messageHub(){
		return new QueueChannel(10);
	}

	@Bean
	public Producer producer(PollableChannel messageHub){
		return new Producer(messageHub);
	}

	@Bean
	public Consumer consumer(PollableChannel messageHub){
		return new Consumer(messageHub);
	}

	private static class Producer{


		private final PollableChannel outChannel;

		public Producer(PollableChannel outChannel){
			this.outChannel = outChannel;
		}

		public void sendMessage(String message){
			outChannel.send(new GenericMessage<String>(message));
		}
	}

	private static class Consumer{

		private final PollableChannel inchannel;

		public Consumer(PollableChannel inchannel) {
			this.inchannel = inchannel;
		}

		public void start(){
			while(true){
				System.out.println("Received from the channel "+ inchannel.receive().getPayload());
			}
		}
	}



}
