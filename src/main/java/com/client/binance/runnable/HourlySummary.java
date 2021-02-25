package com.client.binance.runnable;

import java.io.FileWriter;
import java.io.IOException;

import static com.client.binance.maps.CoinPump.hourlyPump;

public class HourlySummary extends ScheduledJob {
    public void worker() {
        var ref = new Object() {
            String textFile = "";
        };
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
    }
}
