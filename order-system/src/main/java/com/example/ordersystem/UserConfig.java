package com.example.ordersystem;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * diret
 */
@Configuration
public class UserConfig {
    @Bean
    public DirectExchange direct(){
        return new DirectExchange("tut.direct");
    }
    //@Profile("receiver")
    private static class ReceiverConfig{

        @Bean
        public  Queue autoDeleteQueue1(){
            return new AnonymousQueue();
        }


        @Bean
        public Binding bingding1a(DirectExchange direct,Queue autoDeleteQueue1){
            return BindingBuilder.bind(autoDeleteQueue1).to(direct).with("order");
        }

        // @Bean
        // public OrderReceiver receiver(){
        //     return new OrderReceiver();
        // }

    }

   

}