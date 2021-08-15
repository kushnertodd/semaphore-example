// Java implementation of a producer and consumer
// that use semaphores to control synchronization.
  
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
  
class Q {
  
    // semCon initialized with 0 permits
    // to ensure put() executes first
    static Semaphore semCon = new Semaphore(0);
  
    static Semaphore semProd = new Semaphore(3);
 
    static ArrayList<Integer> q = new ArrayList<>();


    void log(String message) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.format("%s: %s%n", formatter.format(date), message);
    }
  
    // to get an item from buffer
    void get()
    {
        try {
            // Before consumer can consume an item,
            // it must acquire a permit from semCon
            semCon.acquire();
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
  
        // consumer consuming an item
        Integer item;
        synchronized(q) {
            item = q.remove(0);
        }
        log("Consumer consumed item : " + item);
        try {
            log("Consumer: sleeping 4 seconds...");
            Thread.sleep(4000);
            log("Consumer: ... awake!");
        } catch (Exception e) {
        }
  
        // After consumer consumes the item,
        // it releases semProd to notify producer
        semProd.release();
    }
  
    // to put an item in buffer
    void put(int item)
    {
        try {
            // Before producer can produce an item,
            // it must acquire a permit from semProd
            semProd.acquire();
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
  
        // producer producing an item
        synchronized(q) {
            q.add(item);
        }
  
        log("Producer produced item : " + item);
  
        // After producer produces the item,
        // it releases semCon to notify consumer
        semCon.release();
    }
}
