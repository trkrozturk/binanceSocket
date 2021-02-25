package com.client.binance.runnable;

import com.client.binance.entity.SocketData;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static com.client.binance.maps.AvgMaps.hourlyAvg;
import static com.client.binance.maps.AvgMaps.lastTime;
import static com.client.binance.maps.WholeData.hourlyAllData;

public class HourlyMapUpdate extends ScheduledJob {

    public void worker() {
        hourlyAllData.keySet().forEach(this::syncData);
    }

    void syncData(String s) {
        Queue<SocketData> hourlyTradeObjs = hourlyAllData.get(s);
        List<SocketData> collect = hourlyTradeObjs.parallelStream().takeWhile(aLong -> lastTime - aLong.getEobj() > 3600000).collect(Collectors.toList());
        hourlyTradeObjs.removeAll(collect);
        double balance = 0;
        for (SocketData hourlyTradeObj : hourlyTradeObjs)
            balance += hourlyTradeObj.getInstantVolume();

        hourlyAvg.put(s, (float) balance / hourlyTradeObjs.size());
        hourlyAllData.get(s).removeAll(collect);
    }
}
