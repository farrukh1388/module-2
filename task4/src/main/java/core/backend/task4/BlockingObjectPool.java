package core.backend.task4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BlockingObjectPool {

    private static final Random random = new Random();

    private final Queue<Integer> queue = new LinkedList<>();
    private final int size;
    private final Object FULL_QUEUE = new Object();
    private final Object EMPTY_QUEUE = new Object();

    public BlockingObjectPool(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            Integer producedValue = random.nextInt();
            System.out.println("Initially added value: " + producedValue);
            queue.add(producedValue);
        }
    }

    private boolean isFull() {
        synchronized (queue) {
            return queue.size() >= size;
        }
    }

    private boolean isEmpty() {
        synchronized (queue) {
            return queue.isEmpty();
        }
    }

    private void waitOnFull() throws InterruptedException {
        synchronized (FULL_QUEUE) {
            System.out.println(Thread.currentThread().getName() + " waits on FULL queue");
            FULL_QUEUE.wait();
        }
    }

    private void waitOnEmpty() throws InterruptedException {
        synchronized (EMPTY_QUEUE) {
            System.out.println(Thread.currentThread().getName() + " waits on EMPTY queue");
            EMPTY_QUEUE.wait();
        }
    }

    public void notifyAllForFull() {
        synchronized (FULL_QUEUE) {
            FULL_QUEUE.notifyAll();
        }
    }

    public void notifyAllForEmpty() {
        synchronized (EMPTY_QUEUE) {
            EMPTY_QUEUE.notifyAll();
        }
    }

    private void notifyForFull() {
        synchronized (FULL_QUEUE) {
            FULL_QUEUE.notify();
        }
    }

    private void notifyForEmpty() {
        synchronized (EMPTY_QUEUE) {
            EMPTY_QUEUE.notify();
        }
    }

    public void take(Integer element) {
        while (isFull()) {
            try {
                waitOnFull();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
                break;
            }
        }
        synchronized (queue) {
            queue.add(element);
            System.out.println(Thread.currentThread().getName() + " added value: " + element);
            System.out.println("Queue size: " + queue.size());
            if (queue.size() > 1) {
                notifyForEmpty();
            }
        }
    }

    public Integer get() {
        if (isEmpty()) {
            try {
                waitOnEmpty();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
                return null;
            }
        }
        synchronized (queue) {
            Integer result = queue.poll();
            System.out.println(Thread.currentThread().getName() + " consumed value: " + result);
            System.out.println("Queue size: " + queue.size());
            if (queue.size() < size) {
                notifyForFull();
            }
            return result;
        }
    }
}
