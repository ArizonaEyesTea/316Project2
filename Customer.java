
public class Customer implements Runnable{

    private int id;
    private ThreadQue arrivalQueue;


    public Customer(int id, ThreadQue arrivalQueue){
        this.id = id;
        this.arrivalQueue = arrivalQueue;
    }

    @Override
    public void run(){
        arrivalQueue.add(id);

        System.out.println("customer " + id + " has arriived" );


    }
}
