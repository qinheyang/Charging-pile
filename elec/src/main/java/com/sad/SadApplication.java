package com.sad;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SadApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext run= SpringApplication.run(SadApplication.class,args);
     
    }
}
