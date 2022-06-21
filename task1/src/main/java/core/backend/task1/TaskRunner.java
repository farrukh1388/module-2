package core.backend.task1;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TaskRunner {

    private static final Random random = new Random();

    private final int elementsCount;
    private Map<Integer, Integer> map = null;
    private CustomHashMap<Integer, Integer> customHashMap = null;

    public TaskRunner(int elementsCount, Map<Integer, Integer> map) {
        this.elementsCount = elementsCount;
        this.map = map;
    }

    public TaskRunner(int elementsCount, CustomHashMap<Integer, Integer> customHashMap) {
        this.elementsCount = elementsCount;
        this.customHashMap = customHashMap;
    }

    class FirstCallable implements Callable<Boolean> {

        @Override
        public Boolean call() {
            if (map != null) {
                for (int i = 0; i < elementsCount; i++) {
                    map.put(i, random.nextInt());
                }
                return true;
            }
            if (customHashMap != null) {
                for (int i = 0; i < elementsCount; i++) {
                    customHashMap.put(i, random.nextInt());
                }
                return true;
            }
            return false;
        }
    }

    class SecondCallable implements Callable<Boolean> {

        @Override
        public Boolean call() {
            if (map != null) {
                for (int i = 0; i < elementsCount; i++) {
                    map.values().stream().mapToInt(a -> a).sum();
                }
                return true;
            }
            if (customHashMap != null) {
                for (int i = 0; i < elementsCount; i++) {
                    customHashMap.values().stream().mapToInt(a -> a).sum();
                }
                return true;
            }
            return false;
        }
    }

    public void start(ExecutorService executorService) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Future<Boolean> firstFuture = executorService.submit(new FirstCallable());
        Future<Boolean> secondFuture = executorService.submit(new SecondCallable());

        try {
            firstFuture.get();
            System.out.println("First Task completed successfully");
        } catch (ExecutionException e) {
            System.out.println("Caught exception while running first task");
        }

        try {
            secondFuture.get();
            System.out.println("Second Task completed successfully");
        } catch (ExecutionException e) {
            System.out.println("Caught exception while running second task");
        }

        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
