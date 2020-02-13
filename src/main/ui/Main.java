package ui;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new PassManagerApp();
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }
}