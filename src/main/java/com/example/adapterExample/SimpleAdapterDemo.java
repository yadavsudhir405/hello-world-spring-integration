package com.example.adapterExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by sudhiry on 1/19/18.
 *  Message--->QueueChannel----->InboundAdapter----->DirectChannel---->ServiceActivator
 */
@SpringBootApplication
public class SimpleAdapterDemo {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(SimpleAdapterDemo.class, args);
        PollableChannel sendToQueue = ctx.getBean("queueChannel", PollableChannel.class);

        for(int i=0;i<5;i++){
            sendToQueue.send(new GenericMessage<String>("Hello world"));
            TimeUnit.SECONDS.sleep(2);
        }

    }

    @Bean
    public DirectChannel channel1(){
       return new DirectChannel();
    }

    @Bean
    public PollableChannel queueChannel(){
        return new QueueChannel();
    }


    @ServiceActivator(inputChannel = "channel1")
    public void handleMessageToServiceActivator(Message<String> message){
        System.out.println("Received Message from Channel1: "+message.getPayload());
    }

}
    @Component
    class DemoAdapter{

    private final PollableChannel pollableChannel;

    public DemoAdapter(PollableChannel queueChannel) {
        this.pollableChannel = queueChannel;
    }

    @InboundChannelAdapter(channel = "channel1", poller = @Poller(fixedDelay = "1000"))
    public Message<?> handleInputMessage(){
        return pollableChannel.receive();
    }
}
