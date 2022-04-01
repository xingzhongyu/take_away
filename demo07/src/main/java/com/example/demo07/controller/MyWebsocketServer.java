package com.example.demo07.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo07.bean.TaChat;
import com.example.demo07.service.TaChatService;
import com.example.demo07.util.TaChatDecoder;
import com.example.demo07.util.TaChatEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat",decoders = {TaChatDecoder.class},encoders = {TaChatEncoder.class})
@Component
@Slf4j
public class MyWebsocketServer implements ApplicationContextAware {
    /**
     * 存放所有在线的客户端
     */
    private static final Map<String, Session> clients = new ConcurrentHashMap<>();
    private static ApplicationContext applicationContext;
    private  static  TaChatService taChatService;
    @OnOpen
    public void onOpen(Session session) {
        log.info("有新的客户端连接了: {}", session.getId());
        //将新用户存入在线的组
        clients.put(session.getId(), session);
    }

    /**
     * 客户端关闭
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        log.info("有用户断开了, id为:{}", session.getId());
        //将掉线的用户移除在线的组里
        clients.remove(session.getId());
    }

    /**
     * 发生错误
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     * @param taChat  消息对象
     */
    @OnMessage
    public void onMessage(TaChat taChat) {
        log.info("服务端收到客户端发来的消息: {}", taChat);
        System.out.println(taChat);
        taChatService=getBean(TaChatService.class);
        TaChat taChat_res=taChatService.insertTaChat(taChat);
        this.sendAll(taChat_res);
    }

    /**
     * 群发消息
     * @param taChat 消息内容
     */
    private void sendAll(TaChat taChat) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            sessionEntry.getValue().getAsyncRemote().sendText(JSON.toJSONString(taChat));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            MyWebsocketServer.applicationContext=applicationContext;
    }
    public static <T> T getBean(Class<T> type) {
        try {
            return applicationContext.getBean(type);
        } catch (NoUniqueBeanDefinitionException e) {   //出现多个，选第一个
            String beanName = applicationContext.getBeanNamesForType(type)[0];
            return applicationContext.getBean(beanName, type);
        }
    }

    public static <T> T getBean(String beanName, Class<T> type) {
        return applicationContext.getBean(beanName, type);
    }
}