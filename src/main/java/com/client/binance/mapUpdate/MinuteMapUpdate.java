package com.client.binance.mapUpdate;

import com.client.binance.entity.SocketData;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static com.client.binance.maps.AvgMaps.lastTime;
import static com.client.binance.maps.AvgMaps.minuteAvg;
import static com.client.binance.maps.CoinPump.minutePump;
import static com.client.binance.maps.WholeData.minuteAllData;

public class MinuteMapUpdate implements Runnable {
    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Thread.sleep(10000);
            minuteAllData.keySet().parallelStream().forEach(this::syncData);


        }

    }

    void syncData(String s) {
        Queue<SocketData> minuteTradeObjs = minuteAllData.get(s);
        List<SocketData> collect = minuteTradeObjs.parallelStream().takeWhile(aLong -> lastTime - aLong.getEobj() > 60000).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            minuteTradeObjs.removeAll(collect);
        }
        double balance = 0;
        for (SocketData minuteTradeObj : minuteTradeObjs)
            balance += minuteTradeObj.getInstantVolume();

        minuteAvg.put(s, (float) balance / minuteTradeObjs.size());
        minuteAllData.get(s).removeAll(collect);
    }
}
