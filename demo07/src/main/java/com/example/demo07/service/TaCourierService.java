package com.example.demo07.service;

import com.example.demo07.bean.TaCourier;
import com.example.demo07.mapper.TaCourierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaCourierService {
    @Autowired
    TaCourierMapper taCourierMapper;
    public TaCourier register(TaCourier taCourier){
        taCourierMapper.register(taCourier);
        return taCourierMapper.getTaCourierByCourierId(taCourier.getCourierId());
    }
    public TaCourier login(String username,String password){
        return taCourierMapper.login(username, password);
    }
    public Long getTaCourier(){
        return taCourierMapper.getCourier();
    }
}
