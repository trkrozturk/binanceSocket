package com.client.binance.maps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CoinPump {
    public static Map<String, List<Long>> minutePump = new ConcurrentHashMap<>();
    public static Map<String, List<Long>> hourlyPump = new ConcurrentHashMap<>();
//    public static Map<String, List<Long>> dailyPump = new ConcurrentHashMap<>();
}
