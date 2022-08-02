package com.client.binance.websocket;

import com.client.binance.entity.SocketData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.client.binance.maps.TestMap.minuteMap;


public class DataTransformer {
    static JsonParser jsonParser = new JsonParser();

    public static void transformObject(String message) {
        JsonObject data = jsonParser.parse(message).getAsJsonObject().get("data").getAsJsonObject();
        SocketData socketData = new Gson().fromJson(data, SocketData.class);
        //System.out.println(socketData.toString());
        DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS");
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(socketData.getT());
        // now we format the res by using SimpleDateFormat¥¥
        //System.out.println(obj.format(res));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(sdf.format(new Date()));
            //System.out.println(date.getTime());
            //System.out.println(socketData.getT().toString());
            //System.out.println("-----------------");
            Long time = (Long) date.getTime();

            if (socketData.getT().toString().startsWith(time.toString().substring(0, 8))) {
                if (minuteMap.containsKey(time)) {
                    minuteMap.get(time).add(socketData);

                } else {
                    Set<Long> longs = minuteMap.keySet();
                    //minuteMap.remove(longs.stream().findFirst())
                    minuteMap.put(time, new ConcurrentLinkedQueue<>());
                    minuteMap.get(time).add(socketData);
                    System.out.println(minuteMap);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

//        socketData.createInstantVolume();
        // DataProcess dataProcess = new DataProcess();
        // dataProcess.execute(socketData);

    }
}
