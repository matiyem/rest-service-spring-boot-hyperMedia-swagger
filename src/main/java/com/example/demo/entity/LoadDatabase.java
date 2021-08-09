package com.example.demo.entity;

import com.sun.org.apache.bcel.internal.generic.LoadClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
*Create by User on 7/27/2021
*/
@Configuration
public class LoadDatabase {
    private static final Logger log= LoggerFactory.getLogger(LoadClass.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository){
        //وقتی application context  اجرا تمام commandLineRunner ها لود میشودفقط یکبار
        //با بالا اومدن برنامه این دو تا دیتا به عنوان دیتا وجود دارند
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo Bagginds","burglar")));
            log.info("Preloading" + repository.save(new Employee("Frodo Baggings","thief")));
        };
    }
}
