import java.util.Random;

public class TestGreeter {
    public static void main(String[] args) {
        System.out.println("main has started");
        ThreadQue arrivalQueue = new ThreadQue();
        Random random = new Random();

        Thread greeterThread = new Thread(new Greeter(arrivalQueue));
        greeterThread.start();

        for (int i = 1; i <= 30; i++) {
            Thread customerThread = new Thread(new Customer(i, arrivalQueue));
            customerThread.start();

            try {
                Thread.sleep(10 + random.nextInt(91));   // 10-100ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            greeterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main finished");
    }
}