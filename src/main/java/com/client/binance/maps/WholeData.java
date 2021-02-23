package com.client.binance.maps;

import com.client.binance.entity.SocketData;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class WholeData {
    public static Map<String, Queue<SocketData>> minuteAllData = new ConcurrentHashMap<>();
    public static Map<String, Queue<SocketData>> hourlyAllData = new ConcurrentHashMap<>();
//    public static Map<String, Queue<SocketData>> dailyAllData = new ConcurrentHashMap<>();
}
