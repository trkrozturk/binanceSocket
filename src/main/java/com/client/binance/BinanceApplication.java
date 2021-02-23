package com.client.binance;

import com.client.binance.initWorks.InitWorks;
import com.client.binance.mapUpdate.HourlyMapUpdate;
import com.client.binance.mapUpdate.MinuteMapUpdate;
import com.client.binance.websocket.WSClient;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import static com.client.binance.maps.CoinPump.hourlyPump;
import static com.client.binance.maps.CoinPump.minutePump;

public class BinanceApplication {

    public static void main(String[] args) throws URISyntaxException, IOException, TimeoutException {

        InitWorks initWorks = new InitWorks();
        initWorks.fillData();
        WSClient wsClient = new WSClient();
        wsClient.webSocketClient();
        Runnable minute = new MinuteMapUpdate();
        Runnable hour = new HourlyMapUpdate();
        Thread threadMinute = new Thread(minute);
        threadMinute.start();
        Thread threadHour = new Thread(hour);
        threadHour.start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3600000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                var ref = new Object() {
                    String textFile = "";
                };


//                System.out.println("------------------------SAAT-------------------------------------");

                ref.textFile = "------------------------SAAT-------------------------------------\n";
                hourlyPump.forEach((s, longs) -> ref.textFile += s + " : " + longs.toString() + "\n");

                try {
                    FileWriter myWriter = new FileWriter("saatlik.txt");
                    myWriter.write(ref.textFile);
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
//                System.out.println("------------------------DAKIKA-------------------------------------");


                ref.textFile = "------------------------DAKIKA-------------------------------------\n";
                minutePump.forEach((s, longs) -> ref.textFile += s + " : " + longs.toString() + "\n");
                try {
                    FileWriter myWriter = new FileWriter("dakikalik.txt");
                    myWriter.write(ref.textFile);
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }

        }).start();
    }

}
