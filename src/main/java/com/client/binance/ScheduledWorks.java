package com.client.binance;

import com.client.binance.runnable.HourlyMapUpdate;
import com.client.binance.runnable.HourlySummary;
import com.client.binance.runnable.MinuteMapUpdate;
import com.client.binance.runnable.MinuteSummary;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledWorks {
    private final ScheduledExecutorService hourlyReport =
            Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService minuteReport =
            Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService minuteMapUpdate =
            Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService hourlyMapUpdate =
            Executors.newScheduledThreadPool(1);

    public void start() {
        minuteMapUpdate.scheduleAtFixedRate(new MinuteMapUpdate(), 10, 10, TimeUnit.SECONDS);
        hourlyMapUpdate.scheduleAtFixedRate(new HourlyMapUpdate(), 1, 1, TimeUnit.HOURS);
        minuteReport.scheduleAtFixedRate(new MinuteSummary(), 1, 1, TimeUnit.HOURS);
        hourlyReport.scheduleAtFixedRate(new HourlySummary(), 1, 1, TimeUnit.HOURS);
    }
}
