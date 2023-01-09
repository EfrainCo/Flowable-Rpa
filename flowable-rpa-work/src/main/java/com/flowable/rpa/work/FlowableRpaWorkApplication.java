package com.flowable.rpa.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
public class FlowableRpaWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableRpaWorkApplication.class, args);
    }
}
