import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class CallCenter {

    static ReentrantLock agentQueueLock = new ReentrantLock();
    static Queue<Integer> agentQueue = new LinkedList<>();
    private final static Semaphore callsInAgentQueue = new Semaphore(0);

    static ReentrantLock greeterQueueLock = new ReentrantLock();
    static Queue<Integer> greeterQueue = new LinkedList<>();
    private final static Semaphore callsInGreeterQueue = new Semaphore(0);

    public static void addToAgentQueue(int customerID) {
        agentQueueLock.lock();
        try {
            agentQueue.add(customerID);
            callsInAgentQueue.release();
        } finally {
            agentQueueLock.unlock();
        }
    }

    public static int agentTakeCall() throws InterruptedException {
        int customerID = 0;
        callsInAgentQueue.acquire();
        agentQueueLock.lock();
        try {
            customerID = agentQueue.remove();
        } finally {
            agentQueueLock.unlock();
        }
        return customerID;
    }

    public static int greeterTakeCall() throws InterruptedException {
        int customerID;
        callsInGreeterQueue.acquire();
        greeterQueueLock.lock();
        try {
            customerID = greeterQueue.remove();
        } finally {
            greeterQueueLock.unlock();
        }
        return customerID;
    }

    public static void addCall(int ID) {
        greeterQueue.add(ID);
        callsInGreeterQueue.release();
    }
}
