import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ClassCanBeRecord")
public class Agent implements Runnable {
    private final int ID;

    public Agent(int ID) {
        this.ID = ID;
    }

    @Override
    public void run() {
        int customerID;
        int customersPerAgent = Main.totalCustomers/Main.totalAgents;
        for (int i = 0; i < customersPerAgent; i++) {
            try {
                customerID = CallCenter.agentTakeCall();
                System.out.println("Agent " + ID + " started serving customer " + customerID);
                Thread.sleep(ThreadLocalRandom.current().nextInt(20, 200));
                System.out.println("Agent " + ID + " finished serving customer " + customerID);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
