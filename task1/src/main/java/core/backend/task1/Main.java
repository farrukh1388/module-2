package core.backend.task1;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        System.out.println("\nUsing HashMap");
        TaskRunner simpleMapTaskRunner = new TaskRunner(20000, new HashMap<>());
        simpleMapTaskRunner.start(executorService);
        Thread.sleep(1000);

        System.out.println("\nUsing Collections.synchronizedMap(HashMap)");
        TaskRunner synchronizedMapTaskRunner = new TaskRunner(20000, Collections.synchronizedMap(new HashMap<>()));
        synchronizedMapTaskRunner.start(executorService);
        Thread.sleep(1000);

        System.out.println("\nUsing ConcurrentHashMap");
        TaskRunner concurrentMapTaskRunner = new TaskRunner(20000, new ConcurrentHashMap<>());
        concurrentMapTaskRunner.start(executorService);

        System.out.println("\nUsing CustomHashMap");
        TaskRunner customMapTaskRunner = new TaskRunner(20000, new CustomHashMap<>());
        customMapTaskRunner.start(executorService);

        executorService.shutdown();
    }
}
