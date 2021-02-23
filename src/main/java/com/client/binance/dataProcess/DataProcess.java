package com.client.binance.dataProcess;

import com.client.binance.entity.SocketData;
import com.client.binance.hunter.Hunter;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.client.binance.maps.AvgMaps.*;
import static com.client.binance.maps.CoinPump.*;
import static com.client.binance.maps.WholeData.*;

public class DataProcess {
    public void execute(SocketData socketData) {
        String symbol = socketData.getS();
        try {
            minuteAllData.get(symbol).add(socketData);
            hourlyAllData.get(symbol).add(socketData);
//        dailyAllData.get(symbol).add(socketData);

        } catch (Exception e) {
            minuteAvg.put(symbol, 0f);
            hourlyAvg.put(symbol, 0f);
//            dailyAvg.put(symbol, 0f);
            coinLastPrice.put(symbol, 0f);
            minutePump.put(symbol, new ArrayList<>());
            hourlyPump.put(symbol, new ArrayList<>());
//            dailyPump.put(symbol, new ArrayList<>());
            minuteAllData.put(symbol, new ConcurrentLinkedQueue<>());
            hourlyAllData.put(symbol, new ConcurrentLinkedQueue<>());
//            dailyAllData.put(symbol, new ConcurrentLinkedQueue<>());
            minuteAllData.get(symbol).add(socketData);
            hourlyAllData.get(symbol).add(socketData);
        }

        Hunter hunter = new Hunter();
        hunter.calculateAvgForPump(socketData);
        coinLastPrice.put(symbol, socketData.getP());
        lastTime = socketData.getEobj();
    }
}
