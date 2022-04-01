package com.example.demo07.util;


import com.alibaba.fastjson.JSON;
import com.example.demo07.bean.TaChat;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class TaChatEncoder implements Encoder.Text<TaChat> {
    @Override
    public String encode(TaChat taChat) throws EncodeException {
        return JSON.toJSONString(taChat);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
