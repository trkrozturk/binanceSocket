package com.client.binance.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WSClient {
    String WSURI = "wss://stream.binance.com:9443/stream?streams=";
    public void webSocketClient() throws URISyntaxException {
        fillWsUri();
        WebSocketClient mWs = new WebSocketClient(new URI(WSURI), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
            }

            @Override
            public void onMessage(String message) {
                DataTransformer.transformObject(message);

            }

            @Override
            public void onClose(int i, String s, boolean b) {

            }

            @Override
            public void onError(Exception e) {

            }
        };
        mWs.connect();


    }

    private void fillWsUri() {
        try {
            File myObj = new File("coinList.txt");
            Scanner myReader = new Scanner(myObj);
            String data = "";
            while (myReader.hasNextLine()) {
                data= myReader.nextLine();
            }
            myReader.close();
            if (data !=null && !data.isEmpty())
                parsetext(data);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void parsetext(String data) {
        List<String> strings = new ArrayList<>();
        for (String coin : Arrays.asList(data.split(","))) {
            strings.add(coin.toLowerCase()+"@aggTrade");
        }
        String urlCoins = String.join("/", strings);
        WSURI+=urlCoins;
    }
}
