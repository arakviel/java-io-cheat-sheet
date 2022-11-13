package com.arakveil.progressbar;

public class ProgressBarRotating extends Thread {

    boolean show = true;

    @Override
    public void run() {
        String animated = "|/-\\";
        int x = 0;
        while (show) {
            System.out.print("\r Processing " + animated.charAt(x++ % animated.length()));
            // Only for demonstration with small files!
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
