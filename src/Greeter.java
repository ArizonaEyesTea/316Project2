import java.util.concurrent.ThreadLocalRandom;

public class Greeter implements Runnable{
    private final int ID;

    public Greeter(int ID) {
        this.ID = ID;
    }

    @Override
    public void run(){
        int customerID;
        for (int i = 0; i < Main.totalCustomers; i++) {
            try {
                customerID = CallCenter.greeterTakeCall();
                System.out.println("greeter started serving customer " + customerID);
                Thread.sleep(ThreadLocalRandom.current().nextInt(20, 200));
                System.out.println("greeter finished serving customer " + customerID);
                CallCenter.addToAgentQueue(customerID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
