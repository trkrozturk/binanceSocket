package com.client.binance.maps;

import com.client.binance.entity.SocketData;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class TestMap {
    public static Map<Long, Queue<SocketData>> minuteMap = new ConcurrentHashMap<>();

}
