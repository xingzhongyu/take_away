package com.example.demo07.util;

import com.alibaba.fastjson.JSON;
import com.example.demo07.bean.TaChat;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;


public class TaChatDecoder implements Decoder.Text<TaChat> {
    @Override
    public TaChat decode(String chat) throws DecodeException {
        return JSON.parseObject(chat,TaChat.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
