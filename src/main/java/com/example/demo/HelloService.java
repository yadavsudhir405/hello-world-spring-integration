package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

/**
 * Created by sudhiry on 1/19/18.
 */
@Component
public class HelloService {
    public String sayHello(){
        return "Hello World!!!";
    }
}
