package com.example.demo07.mapper;

import com.example.demo07.bean.TaCouponUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface TaCouponUserMapper {
    Long insertTaCouponUser(TaCouponUser taCouponUser);
    void changeTaCouponUser(TaCouponUser taCouponUser);
    void TaCouponInit(List<TaCouponUser> taCouponUsers);
    @Transactional
    void deleteTaCoupon(Long couponId);
}
