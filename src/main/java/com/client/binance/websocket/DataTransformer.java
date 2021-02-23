package com.client.binance.websocket;

import com.client.binance.dataProcess.DataProcess;
import com.client.binance.entity.SocketData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class DataTransformer {
    static JsonParser jsonParser = new JsonParser();

    public static void transformObject(String message) {
        JsonObject data = jsonParser.parse(message).getAsJsonObject().get("data").getAsJsonObject();
        SocketData socketData = new Gson().fromJson(data, SocketData.class);
        socketData.createInstantVolume();
        DataProcess dataProcess = new DataProcess();
        dataProcess.execute(socketData);

    }
}
