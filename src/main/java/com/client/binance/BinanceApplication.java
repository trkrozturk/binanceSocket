package com.client.binance;

import com.client.binance.websocket.WSClient;

import java.net.URISyntaxException;

public class BinanceApplication {

    public static void main(String[] args) throws URISyntaxException {

        WSClient wsClient = new WSClient();
        wsClient.webSocketClient();
        //ScheduledWorks scheduledWorks = new ScheduledWorks();
      //  scheduledWorks.start();
    }

}
