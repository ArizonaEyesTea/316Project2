import java.util.LinkedList;

public class ThreadQue {

    private LinkedList<Integer> CustomersIDs;

    public ThreadQue() {
        CustomersIDs = new LinkedList<>();
    }

    public synchronized void add(int id) {
        CustomersIDs.add(id);
        notifyAll();
    }

    public synchronized int remove () {
        while (CustomersIDs.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return CustomersIDs.removeFirst();

    }

}
