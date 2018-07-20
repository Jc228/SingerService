package com.distribuida.rest;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class Aplicacion {
    public static final String QUEUE_SPECIFIC_NAME = "appSpecificQueue";

    @Autowired
    private Environment environment;
    public static void main (String[] args) {
        SpringApplication.run(Aplicacion.class, args);
    }
    @RestController
    class ServiceInstanceRestController {
        @Autowired
        private DiscoveryClient discoveryClient;
        @RequestMapping ("/instancias/{applicationName}")
        public List<ServiceInstance> serviceInstancesByApplicationName(
                @PathVariable String applicationName) {
            return this.discoveryClient.getInstances(applicationName);
        }
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}
