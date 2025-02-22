package org.sayar.net;

import org.apache.catalina.core.ApplicationContext;
import org.sayar.net.Scheduler.Schedule;
import org.sayar.net.Service.activityType.ActivityTypeService;
import org.sayar.net.Tools.Print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.SpringVersion;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NetApplication implements CommandLineRunner {

    @Autowired
    private ActivityTypeService activityTypeService;
    public static String token;


    public static void main(String[] args) {
        SpringApplication.run(NetApplication.class, args);
    }

    //5.2.12
    @Override
    public void run(String... args) throws Exception {
        System.out.println("version: 4");

    }

//	@Autowired//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(NetApplication.class);
//	}
}
