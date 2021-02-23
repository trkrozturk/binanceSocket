package com.client.binance.mapUpdate;

import com.client.binance.entity.SocketData;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static com.client.binance.maps.AvgMaps.hourlyAvg;
import static com.client.binance.maps.AvgMaps.lastTime;
import static com.client.binance.maps.WholeData.hourlyAllData;

public class HourlyMapUpdate implements Runnable {
    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Thread.sleep(60000);
            hourlyAllData.keySet().forEach(this::syncData);

        }

    }

    void syncData(String s) {
        Queue<SocketData> hourlyTradeObjs = hourlyAllData.get(s);
        List<SocketData> collect = hourlyTradeObjs.parallelStream().takeWhile(aLong -> lastTime - aLong.getEobj() > 3600000).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            hourlyTradeObjs.removeAll(collect);
        }
        double balance = 0;
        for (SocketData hourlyTradeObj : hourlyTradeObjs)
            balance += hourlyTradeObj.getInstantVolume();

        hourlyAvg.put(s, (float) balance / hourlyTradeObjs.size());
        hourlyAllData.get(s).removeAll(collect);
    }
}
