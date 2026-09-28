import java.util.Random;

public class Greeter implements Runnable{
    
    private ThreadQue arrivalQueue;
    private Random random = new Random();

    public Greeter(ThreadQue arrivalQueue) {
        this.arrivalQueue = arrivalQueue;
    }


    @Override 
    public void run(){

        for (int i = 0; i < 30; i++) {
            int customerID = arrivalQueue.remove();

            try{
                Thread.sleep(20 + random.nextInt(181));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Greeter greets customer " + customerID);
        }
    }
}
