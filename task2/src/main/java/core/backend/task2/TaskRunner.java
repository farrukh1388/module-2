package core.backend.task2;

import java.util.List;
import java.util.Random;

public class TaskRunner {

    private static final Random random = new Random();

    private final List<Integer> collection;

    public TaskRunner(List<Integer> collection) {
        this.collection = collection;
    }

    class FirstRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (collection) {
                    collection.add(random.nextInt(10));
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    class SecondRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (collection) {
                    System.out.println("\nSum of all numbers:");
                    System.out.println(collection.stream().mapToInt(n -> n).sum());
                }
                try {
                    Thread.sleep(110);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    class ThirdRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (collection) {
                    System.out.println("\nSquare root of sum of squares of all numbers:");
                    System.out.println(Math.sqrt(collection.stream().mapToLong(n -> (long) n * n).sum()));
                }
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void start() {
        Thread firstThread = new Thread(new FirstRunnable());
        Thread secondThread = new Thread(new SecondRunnable());
        Thread thirdThread = new Thread(new ThirdRunnable());

        firstThread.setName("firstThread");
        secondThread.setName("secondThread");
        thirdThread.setName("thirdThread");

        firstThread.start();
        secondThread.start();
        thirdThread.start();
    }
}
