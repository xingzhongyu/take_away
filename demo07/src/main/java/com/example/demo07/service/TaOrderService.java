package com.example.demo07.service;

import com.example.demo07.bean.*;
import com.example.demo07.mapper.TaCourierMapper;
import com.example.demo07.mapper.TaDinerMapper;
import com.example.demo07.mapper.TaFoodMapper;
import com.example.demo07.mapper.TaOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaOrderService {
    @Autowired
    TaOrderMapper taOrderMapper;
    @Autowired
    TaFoodMapper taFoodMapper;
    @Autowired
    TaDinerMapper taDinerMapper;
    @Autowired
    TaCourierService taCourierService;
    @Autowired
    TaCouponService taCouponService;
    @Autowired
    TaCouponUserService taCouponUserService;

    public List<Web_Order> getTaOrder(Long id, String type){
        List<Web_Order> web_orders=new ArrayList<>();
        if ("user".equals(type)){
            for (TaOrder taorder:taOrderMapper.getTaOrderByUserId(id)) {
                TaFood taFood=taFoodMapper.getTaFoodByFoodId(taorder.getFoodId());
                web_orders.add(new Web_Order(taFood,taorder));
            }
        }else if("shop".equals(type)){
            List<TaOrder> taOrders=new ArrayList<>();
            for(TaOrder taOrder:taOrderMapper.getTaOrderByDinerUserId(id)){
                TaFood taFood=taFoodMapper.getTaFoodByFoodId(taOrder.getFoodId());
                TaDiner taDiner=taDinerMapper.getTaDinerByDinerId(taFood.getDinerId());
                web_orders.add(new Web_Order(taFood,taOrder,taDiner));
            }
        }else if("courier".equals(type)){
            for(TaOrder taOrder:taOrderMapper.getTaOrderByCourierId(id)){
                TaFood taFood=taFoodMapper.getTaFoodByFoodId(taOrder.getFoodId());
                TaDiner taDiner=taDinerMapper.getTaDinerByDinerId(taFood.getDinerId());
                web_orders.add(new Web_Order(taFood,taOrder,taDiner));
            }
        }else if ("admin".equals(type)){
            for (TaOrder taOrder:taOrderMapper.getTaOrder()){
                TaFood taFood=taFoodMapper.getTaFoodByFoodId(taOrder.getFoodId());
                TaDiner taDiner=taDinerMapper.getTaDinerByDinerId(taFood.getDinerId());
                web_orders.add(new Web_Order(taFood,taOrder,taDiner));
            }
        }else if("singleShop".equals(type)){
            for (TaFood taFood:taFoodMapper.getTaFoodByDinerId(id)){
                for (TaOrder taOrder:taOrderMapper.getTaOrderByFoodId(taFood.getFoodId())){
                    web_orders.add(new Web_Order(taFood,taOrder));
                }
            }
        }
        return web_orders;
    }
    public TaOrder getTaOrderByOrderId(Long orderId){
        return taOrderMapper.getTaOrderByOrderId(orderId);
    }
    public TaOrder insertTaOrder(TaOrder taOrder){
        TaFood taFood=taFoodMapper.getTaFoodByFoodId(taOrder.getFoodId());
        double totalPrice=taFood.getPrice()*taOrder.getNum();
        taOrderMapper.insertTaOrder(taOrder);
        List<TaCoupon> taCoupons=taCouponService.getTaCouponByUserId(taOrder.getUserId());
        for (TaCoupon taCoupon:taCoupons){
            if(taCoupon.getCategory()==null||taCoupon.getCategory().equals(taFood.getCategory())){
                if ("满减".equals(taCoupon.getType())){
                    if(totalPrice>=taCoupon.getLeast()){
                        totalPrice=totalPrice-Double.parseDouble(taCoupon.getDiscount());
                        TaCouponUser temp=new TaCouponUser();
                        temp.setUserId(taOrder.getUserId());
                        temp.setCouponId(taCoupon.getCouponId());
                        temp.setOrderId(taOrder.getOrderId());
                        taCouponUserService.changeTaCouponUser(temp);
                    }
                }else if("打折".equals(taCoupon.getType())){
                    if (totalPrice>=taCoupon.getLeast()){
                        totalPrice=totalPrice*Double.parseDouble(taCoupon.getDiscount());
                        TaCouponUser temp=new TaCouponUser();
                        temp.setUserId(taOrder.getUserId());
                        temp.setCouponId(taCoupon.getCouponId());
                        temp.setOrderId(taOrder.getOrderId());
                        taCouponUserService.changeTaCouponUser(temp);
                    }
                }
            }
        }
        taOrder.setFinalTotalPrice(totalPrice);
        taOrderMapper.changeTaOrder(taOrder);
        return taOrderMapper.getTaOrderByOrderId(taOrder.getOrderId());
    }
    public Long changeTaOrder(TaOrder taOrder){
        if (taOrder.getStatus()==1){//支付
            TaFood taFood=taFoodMapper.getTaFoodByFoodId(taOrder.getFoodId());
            taFood.setSales(taFood.getSales()+taOrder.getNum());
            taOrder.setCourierId(taCourierService.getTaCourier());
            taFoodMapper.changeTaFood(taFood);
        }
        return taOrderMapper.changeTaOrder(taOrder);
    }
}
