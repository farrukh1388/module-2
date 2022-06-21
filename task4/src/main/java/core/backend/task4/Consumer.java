package core.backend.task4;

public class Consumer implements Runnable {

    private boolean runFlag;
    private final BlockingObjectPool intPool;

    public Consumer(BlockingObjectPool intPool) {
        this.intPool = intPool;
        runFlag = true;
    }

    @Override
    public void run() {
        while (runFlag) {
            intPool.get();
        }
        System.out.println("Consumer stopped");
    }

    public void stop() {
        runFlag = false;
        intPool.notifyAllForEmpty();
    }
}
