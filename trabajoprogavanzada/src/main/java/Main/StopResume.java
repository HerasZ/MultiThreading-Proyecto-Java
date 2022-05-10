package Main;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* 
    THE GOAL OF THIS CLASS IS ONLY TO LOCK THREADS WHEN THE STOP
    BUTTON IS PUSHED ON UI.Main, AND RESUME THEM WHEN THE OTHER 
    CORRESPONDING BUTTON IS PRESSED
 */
public class StopResume {

    private boolean close = false;
    private Lock lock = new ReentrantLock();
    private Condition stop = lock.newCondition();

    public void look() {
        try {
            lock.lock();
            while (close) {
                try {
                    stop.await();
                } catch (InterruptedException ie) {
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void open() {
        try {
            lock.lock();
            close = false;
            stop.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void close() {
        try {
            lock.lock();
            close = true;
        } finally {
            lock.unlock();
        }
    }
}
