package com.flooring;

import com.flooring.controller.FlooringMasteryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        FlooringMasteryController controller =
                context.getBean("controller",
                        FlooringMasteryController.class);

        controller.run();
    }
}
