package core.backend.task4;

import java.util.Random;

public class Producer implements Runnable {

    private static final Random random = new Random();

    private boolean runFlag;
    private final BlockingObjectPool intPool;

    public Producer(BlockingObjectPool intPool) {
        this.intPool = intPool;
        runFlag = true;
    }

    @Override
    public void run() {
        while (runFlag) {
            intPool.take(random.nextInt());
        }
        System.out.println("Producer stopped");
    }

    public void stop() {
        runFlag = false;
        intPool.notifyAllForFull();
    }
}
