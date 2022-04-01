package com.example.demo07.service;


import com.example.demo07.bean.*;
import com.example.demo07.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaCommentService {
    @Autowired
    TaCommentMapper taCommentMapper;
    @Autowired
    TaOrderMapper taOrderMapper;
    @Autowired
    TaFoodMapper taFoodMapper;
    @Autowired
    TaDinerMapper taDinerMapper;
    @Autowired
    TaOrderService taOrderService;
    @Autowired
    TaUserMapper taUserMapper;
    public TaComment insertTaComment(TaComment taComment){
        Long num = taCommentMapper.insertTaComment(taComment);
        TaOrder taOrder=taOrderMapper.getTaOrderByOrderId(taComment.getOrderId());
        taOrder.setStatus(2);//评论
        taOrderMapper.changeTaOrder(taOrder);
        if(num==1&&"shop".equals(taComment.getType())){
            TaFood taFood=taFoodMapper.getTaFoodByFoodId(taOrder.getFoodId());
            TaDiner taDiner=taDinerMapper.getTaDinerByDinerId(taFood.getDinerId());
            long commentNum=taDiner.getCommentNum();
            commentNum++;
            taDiner.setCommentNum(commentNum);
            if(commentNum==1){
                taDiner.setScore(taComment.getScore());
            }else {
                taDiner.setScore((taDiner.getScore()*(commentNum-1)+taComment.getScore())/commentNum);
            }
            taDinerMapper.changeTaDiner(taDiner);
        }
        return taCommentMapper.getTaCommentByCommentId(taComment.getCommentId());
    }
    public TaComment getTaComment(Long orderId,String type){

        return taCommentMapper.getTaComment(orderId, type);
    }
    public List<Web_Comment> getTaCommentByDinerId(Long dinerId){
        List<Web_Comment> web_comments=new ArrayList<>();
        List<Web_Order> web_orders=taOrderService.getTaOrder(dinerId,"singleShop");
        for (Web_Order web_order:web_orders){
            TaOrder taOrder=web_order.getTaOrder();
            Long ta=taOrder.getOrderId();
            TaComment taComment=taCommentMapper.getTaComment(taOrder.getOrderId(),"shop");
            if(taComment!=null){
                web_comments.add(new Web_Comment(taComment
                        ,taUserMapper.getUserById(taOrder.getUserId()),web_order.getTaFood()));
            }
        }
        return web_comments;
    }
    public List<Web_Comment> getTaCommentByCourierId(Long courierId){
        List<Web_Comment> web_comments=new ArrayList<>();
        List<TaOrder> taOrders=taOrderMapper.getTaOrderByCourierId(courierId);
        for (TaOrder taOrder:taOrders){
            TaComment temp=taCommentMapper.getTaComment(taOrder.getOrderId(),"courier");
            if(temp!=null){
                web_comments.add(new Web_Comment(temp,
                        taUserMapper.getUserById(taOrder.getUserId()),taFoodMapper.getTaFoodByFoodId(taOrder.getFoodId())));
            }
        }
        return web_comments;
    }
}
