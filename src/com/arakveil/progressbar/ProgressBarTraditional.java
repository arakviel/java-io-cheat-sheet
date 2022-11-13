package com.arakveil.progressbar;

public class ProgressBarTraditional extends Thread {

    public boolean show = true;

    @Override
    public void run() {
        String animated = "=====================";
        int x = 0;
        while (show) {
            System.out.print("\r Processing "
                    + animated.substring(0, x++ % animated.length())
                    + " ");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
