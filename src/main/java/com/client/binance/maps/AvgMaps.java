package com.client.binance.maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AvgMaps {
    public static Map<String, Float> minuteAvg = new ConcurrentHashMap<>();
    public static Map<String, Float> hourlyAvg = new ConcurrentHashMap<>();
//    public static Map<String, Float> dailyAvg = new ConcurrentHashMap<>();
    public static Map<String, Float> coinLastPrice = new ConcurrentHashMap<>();
    public static long lastTime = 0;

}
