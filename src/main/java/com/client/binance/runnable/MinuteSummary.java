package com.client.binance.runnable;

import java.io.FileWriter;
import java.io.IOException;

import static com.client.binance.maps.CoinPump.minutePump;

public class MinuteSummary extends ScheduledJob {
    public void worker() {
        var ref = new Object() {
            String textFile = "";
        };
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
}
