package com.example.demo07.service;

import com.example.demo07.bean.TaCouponUser;
import com.example.demo07.bean.TaUser;
import com.example.demo07.mapper.TaCouponUserMapper;
import com.example.demo07.mapper.TaUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaCouponUserService {
    @Autowired
    TaCouponUserMapper taCouponUserMapper;
    @Autowired
    TaUserMapper taUserMapper;
    public long insertTaCouponUser(TaCouponUser taCouponUser){
        return taCouponUserMapper.insertTaCouponUser(taCouponUser);
    }
    public void changeTaCouponUser(TaCouponUser taCouponUser){
        taCouponUserMapper.changeTaCouponUser(taCouponUser);
    }
    public void taCouponInit(Long couponId){
        List<TaCouponUser> taCouponUsers=new ArrayList<>();
        for (TaUser taUser:taUserMapper.getTaUser()){
            TaCouponUser temp=new TaCouponUser();
            temp.setUserId(taUser.getUserId());
            temp.setCouponId(couponId);
            taCouponUsers.add(temp);
        }
        taCouponUserMapper.TaCouponInit(taCouponUsers);
    }
    @Transactional
    public void deleteTaCoupon(Long couponId){
        taCouponUserMapper.deleteTaCoupon(couponId);
    }
}
