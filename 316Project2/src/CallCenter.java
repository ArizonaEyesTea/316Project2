import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class CallCenter {

    final static int totalCustomers = 30;
    final static int totalAgents = 3;

    static ExecutorService agentPool = Executors.newFixedThreadPool(3);
    static ExecutorService greeterPool = Executors.newFixedThreadPool(1);
    static ExecutorService greeterCustomerPool = Executors.newCachedThreadPool();

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
            int queuePosition = agentQueue.size();
            System.out.println("Customer " + customerID + " is #" + queuePosition + " in the service queue" );
            callsInAgentQueue.release();
        } finally {
            agentQueueLock.unlock();
        }
    }

    public static int agentTakeCall() throws InterruptedException {
        int customerID;

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
        greeterQueueLock.lock();
        try {
            greeterQueue.add(ID);
            callsInGreeterQueue.release();
        } finally {
            greeterQueueLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        greeterPool.submit(new Greeter(1));

        for (int i = 1; i <= totalAgents; i++) {
            agentPool.submit(new Agent(i));
        }

        for (int i = 1; i <= totalCustomers; i++) {
            greeterCustomerPool.submit(new Customer(i));
            Thread.sleep(ThreadLocalRandom.current().nextInt(10, 101));
        }

        greeterCustomerPool.shutdown();
        agentPool.shutdown();
        greeterPool.shutdown();
    }
}