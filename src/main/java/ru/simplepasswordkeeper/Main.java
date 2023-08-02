package ru.simplepasswordkeeper;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.simplepasswordkeeper.gui.frame.UserListFrame;

import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(SpringConfig.class);
        var frame = context.getBean(UserListFrame.class);

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
   }
}