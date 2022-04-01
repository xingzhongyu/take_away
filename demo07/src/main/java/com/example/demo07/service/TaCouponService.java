package com.example.demo07.service;

import com.example.demo07.bean.TaCoupon;
import com.example.demo07.mapper.TaCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaCouponService {
    @Autowired
    TaCouponMapper taCouponMapper;
    @Autowired
    TaCouponUserService taCouponUserService;
    public TaCoupon insertTaCoupon(TaCoupon taCoupon){
        taCouponMapper.insertTaCoupon(taCoupon);
        taCouponUserService.taCouponInit(taCoupon.getCouponId());
        return taCouponMapper.getTaCouponByCouponId(taCoupon.getCouponId());
    }
    public List<TaCoupon> getTaCoupon(){
        return taCouponMapper.getTaCoupon();
    }
    public List<TaCoupon> getTaCouponByUserId(Long userId){
        List<TaCoupon> taCoupons=taCouponMapper.getTaCouponByUserId(userId);
        Stream<TaCoupon> taCouponStream= taCoupons.stream().filter(taCoupon -> {
            try {
                Date start=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(taCoupon.getStartTime());
                Calendar calEnd=Calendar.getInstance();
                Calendar calSta=Calendar.getInstance();
                calSta.setTime(start);
                calEnd.setTime(start);
                calEnd.add(Calendar.DATE, (int) taCoupon.getDays());
                Calendar calendar1=Calendar.getInstance();
                return calEnd.after(calendar1)&&calSta.before(calendar1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        });
        return taCouponStream.collect(Collectors.toList());
    }
    @Transactional
    public Long deleteTaCoupon(Long couponId){
        taCouponUserService.deleteTaCoupon(couponId);
        return taCouponMapper.deleteTaCoupon(couponId);
    }
    public Long changeTaCoupon(TaCoupon taCoupon){
        return taCouponMapper.changeTaCoupon(taCoupon);
    }
}
