
public class Customer implements Runnable{

    private final int ID;


    public Customer(int id){
        this.ID = id;
    }

    @Override
    public void run(){
        System.out.println("customer " + ID + " has arrived to greeter" );
        CallCenter.addCall(ID);
    }
}
