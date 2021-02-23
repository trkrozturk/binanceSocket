package com.client.binance.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class SocketData {
    @Getter
    @Setter
    private final String e;
    private final long T;
    @Getter
    @Setter
    private final String s;
    @Getter
    @Setter
    private final String a;
    @Getter
    @Setter
    private final float p;
    @Getter
    @Setter
    private final float q;
    @Getter
    @Setter
    private final long f;
    @Getter
    @Setter
    private final long l;

    @Getter
    private  float instantVolume ;

    public long getEobj() {
        return T;
    }

    public void createInstantVolume(){
        this.instantVolume = ((l - f) + 1) * q * p;
    }
}
