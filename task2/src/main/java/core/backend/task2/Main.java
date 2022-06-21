package core.backend.task2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        System.out.println("\nThreads synchronized on collection\n");

        TaskRunner taskRunner = new TaskRunner(new ArrayList<>());
        taskRunner.start();
    }
}
