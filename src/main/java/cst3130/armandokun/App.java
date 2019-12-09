package cst3130.armandokun;

import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cst3130.armandokun.iPhoneScraper;

public class App {
    public static void main(String[] args) {

        System.out.println("Beginning Scrapping...");
        new iPhoneScraper();
    }

    /** Uses Spring Annotation configuration to set up and run application */

}
