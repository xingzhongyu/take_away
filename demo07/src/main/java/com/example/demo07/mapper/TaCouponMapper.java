package com.example.demo07.mapper;

import com.example.demo07.bean.TaCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface TaCouponMapper {
    void insertTaCoupon(TaCoupon taCoupon);
    TaCoupon getTaCouponByCouponId(Long couponId);
    List<TaCoupon> getTaCouponByUserId(Long userId);
    List<TaCoupon> getTaCoupon();
    @Transactional
    Long deleteTaCoupon(Long couponId);
    Long changeTaCoupon(TaCoupon taCoupon);
}
