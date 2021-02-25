package com.client.binance.runnable;

public abstract class ScheduledJob implements Runnable, Work {

    @Override
    public void run() {
        worker();
    }
}
