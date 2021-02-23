package com.client.binance.hunter;

import com.client.binance.entity.SocketData;
import com.client.binance.maps.AvgMaps;

import static com.client.binance.maps.AvgMaps.coinLastPrice;
import static com.client.binance.maps.CoinPump.*;

public class Hunter {
    public void calculateAvgForPump(SocketData socketData) {

        Float lastprice = coinLastPrice.get(socketData.getS());
        new Thread(() -> {
            minuteControl(socketData, lastprice);
        }).start();
        new Thread(() -> {
            hourlyControl(socketData, lastprice);
        }).start();
//        new Thread(() -> {
//            dailyControl(socketData,lastprice);
//        }).start();


    }

    private void minuteControl(SocketData socketData, Float lastprice) {
        String symbol = socketData.getS();
        Float minuteAvg = AvgMaps.minuteAvg.get(symbol);
        if (isaPump(socketData, lastprice, minuteAvg)) {
            System.out.println("[Dakikalık]Anlık :  " + String.format("%.7f", socketData.getInstantVolume()) + " ortalama : " +
                    String.format("%.7f", minuteAvg) + " Oran: " + String.format("%.7f", socketData.getInstantVolume() / minuteAvg) + " Time : " + socketData.getEobj() + " " + symbol);
            minutePump.get(symbol).add(socketData.getEobj());
        }
    }

    private void hourlyControl(SocketData socketData, Float lastprice) {
        String symbol = socketData.getS();
        Float hourlyAvg = AvgMaps.hourlyAvg.get(symbol);
        if (isaPump(socketData, lastprice, hourlyAvg)) {
            System.out.println("[Saatlik]Anlık :  " + String.format("%.7f", socketData.getInstantVolume()) + " ortalama : " +
                    String.format("%.7f", hourlyAvg) + " Oran: " + String.format("%.7f", socketData.getInstantVolume() / hourlyAvg) + " Time : " + socketData.getEobj() + " " + symbol);
            hourlyPump.get(symbol).add(socketData.getEobj());
        }
    }

//    private void dailyControl(SocketData socketData, Float lastprice) {
//        String symbol = socketData.getS();
//        Float dailyAvg = AvgMaps.dailyAvg.get(symbol);
//        if (isaPump(socketData, lastprice, dailyAvg)) {
//            System.out.println("[Günlük]Anlık :  " + String.format("%.7f", socketData.getInstantVolume()) + " ortalama : " +
//                    String.format("%.7f", dailyAvg) + " Oran: " + String.format("%.7f", socketData.getInstantVolume() / dailyAvg) + " Time : " + socketData.getEobj() + " " + symbol);
//            dailyPump.get(symbol).add(socketData.getEobj());
//        }
//    }

    private boolean isaPump(SocketData socketData, Float lastprice, Float avgValue) {
        if (avgValue == 0) {
            return false;
        }
        return socketData.getInstantVolume() / avgValue >= 7000 && lastprice < socketData.getP();
    }
}
