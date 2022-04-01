package com.example.demo07.mapper;

import com.example.demo07.bean.TaCourier;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TaCourierMapper {
    void register(TaCourier taCourier);
    TaCourier login(String username,String password);
    Long getCourier();
    TaCourier getTaCourierByCourierId(Long courierId);
}
