// Java implementation of a producer and consumer
// that use semaphores to control synchronization.
  
import java.util.concurrent.Semaphore;
  
// Driver class
class PC {
    public static void main(String args[])
    {
        // creating buffer queue
        Q q = new Q();
  
        // starting consumer thread
        new Consumer(q);
  
        // starting producer thread
        new Producer(q);
    }
}
