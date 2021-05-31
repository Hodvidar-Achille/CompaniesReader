package com.hodvidar.cr;

import com.hodvidar.cr.utils.recources.ResourceCloser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class.getSimpleName());

    public static void main(String[] args){
        logger.info("\nCompany Reader App start... \n---------------------------------");

        // 1) Read the Json

        // 2) Start async work to retrieve country for each company

        // 3) for each country keep track of total money raised in USD (convert if different)

        // 4) once all async work are done, perform computation to have average value

        // 5) Write down results

        logger.info("\n--------------------------------- \nCompany Reader App end.");
    }
}
