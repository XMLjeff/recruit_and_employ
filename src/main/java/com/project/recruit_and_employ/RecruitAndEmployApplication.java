package com.project.recruit_and_employ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.project"})

public class RecruitAndEmployApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitAndEmployApplication.class, args);
    }

}
