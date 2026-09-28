import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    final static int totalCustomers = 32;
    final static int totalAgents = 4;
    static ExecutorService agentPool = Executors.newFixedThreadPool(2);
    static ExecutorService greeterPool = Executors.newFixedThreadPool(1);
    static ExecutorService agentCustomerPool = Executors.newCachedThreadPool();
    static ExecutorService greeterCustomerPool = Executors.newCachedThreadPool();


    public static void main(String[] args) throws InterruptedException {
        greeterPool.submit(new Greeter(1));

        for (int i = 1; i <= totalAgents; i++) {
            agentPool.submit(new Agent(i));
        }

        for (int i = 1; i <= Main.totalCustomers; i++) {
            greeterCustomerPool.submit(new Customer(i));
            Thread.sleep(ThreadLocalRandom.current().nextInt(10,100));
        }

        greeterCustomerPool.shutdown();
        agentCustomerPool.shutdown();
        agentPool.shutdown();
        greeterPool.shutdown();
    }
}
