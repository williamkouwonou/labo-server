/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * cvff
 *
 * @author kouwonou
 */
@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {
//

    private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        SpringApplication.run(MainApplication.class, args);

    }
    //Commit

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder bulder) {
        return bulder.sources(new Class[] { MainApplication.class});
        
    }

   

}
