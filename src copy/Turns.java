import java.util.concurrent.Semaphore;

public class Turns {
    Semaphore turnW = new Semaphore(1);
    Semaphore turnB = new Semaphore(0);
}
