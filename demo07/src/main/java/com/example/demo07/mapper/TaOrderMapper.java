package com.example.demo07.mapper;

import com.example.demo07.bean.TaOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;



@Mapper
@Repository
public interface TaOrderMapper {
    List<TaOrder> getTaOrderByUserId(Long userId);
    List<TaOrder> getTaOrderByDinerUserId(Long userId);
    List<TaOrder> getTaOrderByFoodId(Long foodId);
    List<TaOrder> getTaOrderByCourierId(Long courierId);
    void insertTaOrder(TaOrder taOrder);
    Long changeTaOrder(TaOrder taOrder);
    TaOrder getTaOrderByOrderId(Long orderId);
    List<TaOrder> getTaOrder();
}
