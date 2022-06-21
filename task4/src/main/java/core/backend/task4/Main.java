package core.backend.task4;

import java.util.ArrayList;
import java.util.List;

import static core.backend.task4.ThreadUtil.sleep;
import static core.backend.task4.ThreadUtil.waitForAllThreadsToComplete;

public class Main {
    public static void main(String[] args) {
        BlockingObjectPool intPool = new BlockingObjectPool(10);
        int producerCount = 3;
        int consumerCount = 3;
        List<Thread> threads = new ArrayList<>();
        Producer producer = new Producer(intPool);
        for (int i = 0; i < producerCount; i++) {
            Thread producerThread = new Thread(producer);
            System.out.println("Starting producer: " + producerThread.getName());
            producerThread.start();
            threads.add(producerThread);
        }
        Consumer consumer = new Consumer(intPool);
        for (int i = 0; i < consumerCount; i++) {
            Thread consumerThread = new Thread(consumer);
            System.out.println("Starting consumer: " + consumerThread.getName());
            consumerThread.start();
            threads.add(consumerThread);
        }

        // let threads run for two seconds
        sleep(100);

        // Stop threads
        producer.stop();
        consumer.stop();

        waitForAllThreadsToComplete(threads);
    }
}
