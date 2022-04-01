package com.example.demo07.service;


import com.example.demo07.bean.TaChat;
import com.example.demo07.mapper.TaChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaChatService {
    @Autowired
    TaChatMapper taChatMapper;
    public List<TaChat> getTaChatByOrderId(Long orderId){
        return taChatMapper.getTaChatByOrderId(orderId);
    }
    public TaChat insertTaChat(TaChat taChat){
      taChatMapper.insertTaChat(taChat);
      return taChatMapper.getTaChatByChatId(taChat.getChatId());
    }
    public TaChat getTaChatByChatId(Long chatId){
        return taChatMapper.getTaChatByChatId(chatId);
    }
}
