package com.example.demo07.mapper;

import com.example.demo07.bean.TaChat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface TaChatMapper {
     List<TaChat> getTaChatByOrderId(Long orderId);
     Long insertTaChat(TaChat taChat);
     TaChat getTaChatByChatId(Long chatId);
}
