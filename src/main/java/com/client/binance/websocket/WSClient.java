package com.client.binance.websocket;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

public class WSClient {
    String WSURI = "wss://stream.binance.com:9443/stream?streams=btcusdt@aggTrade/ethusdt@aggTrade/ethbtc@aggTrade/xrpusdt@aggTrade/bnbusdt@aggTrade/bnbbtc@aggTrade/btcbusd@aggTrade/xrpbtc@aggTrade/ltcusdt@aggTrade/eosusdt@aggTrade/linkusdt@aggTrade/busdusdt@aggTrade/adausdt@aggTrade/eosbtc@aggTrade/bchusdt@aggTrade/ltcbtc@aggTrade/linkbtc@aggTrade/ethbusd@aggTrade/trxusdt@aggTrade/xlmusdt@aggTrade/trxbtc@aggTrade/adabtc@aggTrade/neousdt@aggTrade/etcusdt@aggTrade/neobtc@aggTrade/xlmbtc@aggTrade/vetusdt@aggTrade/wavesbtc@aggTrade/xmrbtc@aggTrade/trxeth@aggTrade/etcbtc@aggTrade/bnbeth@aggTrade/yfiusdt@aggTrade/ontusdt@aggTrade/iotabtc@aggTrade/dotusdt@aggTrade/icxbtc@aggTrade/ontbtc@aggTrade/xtzusdt@aggTrade/bchabcusdt@aggTrade/nanobtc@aggTrade/eoseth@aggTrade/uniusdt@aggTrade/xrpeth@aggTrade/batbtc@aggTrade/bttusdt@aggTrade/zrxbtc@aggTrade/dashbtc@aggTrade/zecusdt@aggTrade/btcusdc@aggTrade/xvgbtc@aggTrade/usdcusdt@aggTrade/bchabcbtc@aggTrade/zecbtc@aggTrade/bccbtc@aggTrade/atomusdt@aggTrade/enjbtc@aggTrade/arnbtc@aggTrade/dashusd" +
            "t@aggTrade/btceur@aggTrade/qtumusdt@aggTrade/omgbtc@aggTrade/bchbtc@aggTrade/qtumbtc@aggTrade/wanbtc@aggTrade/bandusdt@aggTrade/wtcbtc@aggTrade/thetabtc@aggTrade/wavesusdt@aggTrade/elfbtc@aggTrade/zilbtc@aggTrade/xmrusdt@aggTrade/tusdusdt@aggTrade/iostusdt@aggTrade/sxpusdt@aggTrade/zilusdt@aggTrade/algousdt@aggTrade/bccusdt@aggTrade/adaeth@aggTrade/kncbtc@aggTrade/thetausdt@aggTrade/icxusdt@aggTrade/paxusdt@aggTrade/iotausdt@aggTrade/bnbbusd@aggTrade/omgusdt@aggTrade/linketh@aggTrade/mdabtc@aggTrade/maticusdt@aggTrade/btcpax@aggTrade/yfiiusdt@aggTrade/iostbtc@aggTrade/venbtc@aggTrade/mtlbtc@aggTrade/xembtc@aggTrade/sushiusdt@aggTrade/stratbtc@aggTrade/grtusdt@aggTrade/lendbtc@aggTrade/bqxbtc@aggTrade/lskbtc@aggTrade/atombtc@aggTrade/xrpbusd@aggTrade/yfibtc@aggTrade/neoeth@aggTrade/kavausdt@aggTrade/manabtc@aggTrade/aionbtc@aggTrade/algobtc@aggTrade/rvnbtc@aggTrade/nulsbtc@aggTrade/ltceth@aggTrade/dgdbtc@aggTrade/xtzbtc@aggTrade/gvtbtc@aggTrade/xlmeth@aggTrade/fetbtc@aggTrade/bandbtc@aggTrade/btgbtc@aggTrade/gt" +
            "obtc@aggTrade/fetusdt@aggTrade/batusdt@aggTrade/maticbtc@aggTrade/mcobtc@aggTrade/compusdt@aggTrade/powrbtc@aggTrade/qkcbtc@aggTrade/wabibtc@aggTrade/renbtc@aggTrade/steembtc@aggTrade/engbtc@aggTrade/sxpbtc@aggTrade/bcptbtc@aggTrade/lrcbtc@aggTrade/evxbtc@aggTrade/dogeusdt@aggTrade/astbtc@aggTrade/btctusd@aggTrade/tusdbtc@aggTrade/rlcbtc@aggTrade/bcdbtc@aggTrade/vetbtc@aggTrade/filusdt@aggTrade/neblbtc@aggTrade/cmtbtc@aggTrade/sntbtc@aggTrade/eurusdt@aggTrade/bchsvbtc@aggTrade/icxeth@aggTrade/blzbtc@aggTrade/storjbtc@aggTrade/dotbtc@aggTrade/xvgeth@aggTrade/kmdbtc@aggTrade/btsbtc@aggTrade/gasbtc@aggTrade/tomobtc@aggTrade/winusdt@aggTrade/adxbtc@aggTrade/aebtc@aggTrade/iotaeth@aggTrade/lunbtc@aggTrade/repbtc@aggTrade/aaveusdt@aggTrade/trxbnb@aggTrade/renusdt@aggTrade/pptbtc@aggTrade/bttbnb@aggTrade/erdusdt@aggTrade/crvusdt@aggTrade/srmusdt@aggTrade/zrxusdt@aggTrade/vibebtc@aggTrade/arkbtc@aggTrade/appcbtc@aggTrade/loombtc@aggTrade/snxusdt@aggTrade/poebtc@aggTrade/dltbtc@aggTrade/btttrx@aggTrade/dntbtc@aggTrade/" +
            "rcnbtc@aggTrade/zileth@aggTrade/qspbtc@aggTrade/egldusdt@aggTrade/funbtc@aggTrade/tntbtc@aggTrade/gxsbtc@aggTrade/ambbtc@aggTrade/kavabtc@aggTrade/bchsvusdt@aggTrade/grsbtc@aggTrade/oaxbtc@aggTrade/cvcbtc@aggTrade/enjusdt@aggTrade/btcngn@aggTrade/skybtc@aggTrade/xzcbtc@aggTrade/lendusdt@aggTrade/vibbtc@aggTrade/cndbtc@aggTrade/etceth@aggTrade/onteth@aggTrade/tomousdt@aggTrade/reqbtc@aggTrade/dockbtc@aggTrade/solusdt@aggTrade/etheur@aggTrade/subbtc@aggTrade/arneth@aggTrade/qlcbtc@aggTrade/brdbtc@aggTrade/nanoeth@aggTrade/enjeth@aggTrade/insbtc@aggTrade/unibtc@aggTrade/veneth@aggTrade/zrxeth@aggTrade/solbtc@aggTrade/zenbtc@aggTrade/poabtc@aggTrade/oneusdt@aggTrade/saltbtc@aggTrade/iosteth@aggTrade/gntbtc@aggTrade/tnbbtc@aggTrade/nasbtc@aggTrade/ltcbusd@aggTrade/trxxrp@aggTrade/agibtc@aggTrade/snglsbtc@aggTrade/ethusdc@aggTrade/ostbtc@aggTrade/tfuelusdt@aggTrade/hcbtc@aggTrade/ltcbnb@aggTrade/rsrusdt@aggTrade/bntbtc@aggTrade/yoyobtc@aggTrade/pivxbtc@aggTrade/databtc@aggTrade/mthbtc@aggTrade/edobtc@aggTrade/qtumet" +
            "h@aggTrade/bateth@aggTrade/sysbtc@aggTrade/ncashbtc@aggTrade/xrpbnb@aggTrade/omgeth@aggTrade/lrcusdt@aggTrade/rdnbtc@aggTrade/veteth@aggTrade/cdtbtc@aggTrade/stormbtc@aggTrade/cvcusdt@aggTrade/elfeth@aggTrade/ongbtc@aggTrade/ftmusdt@aggTrade/ankrusdt@aggTrade/nanousdt@aggTrade/hsrbtc@aggTrade/snmbtc@aggTrade/celrusdt@aggTrade/polybtc@aggTrade/waveseth@aggTrade/fuelbtc@aggTrade/waneth@aggTrade/hbarusdt@aggTrade/arpausdt@aggTrade/sushibtc@aggTrade/knceth@aggTrade/yfiibtc@aggTrade/viabtc@aggTrade/ethpax@aggTrade/lendeth@aggTrade/navbtc@aggTrade/eurbusd@aggTrade/chzusdt@aggTrade/wintrx@aggTrade/mithbtc@aggTrade/iotxbtc@aggTrade/winbnb@aggTrade/hbarbtc@aggTrade/wrxusdt@aggTrade/ognbtc@aggTrade/wrxbtc@aggTrade/hotusdt@aggTrade/wprbtc@aggTrade/linkbusd@aggTrade/celrbtc@aggTrade/alphausdt@aggTrade/bchbusd@aggTrade/nulsusdt@aggTrade/snxbtc@aggTrade/wtceth@aggTrade/eosbusd@aggTrade/1inchusdt@aggTrade/adabusd@aggTrade/xmreth@aggTrade/ognusdt@aggTrade";
    public void webSocketClient() throws URISyntaxException, IOException, TimeoutException {
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
}
