package me.sadbuttrue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimeStorer {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(TimeStorer.class, args)));
    }
}
