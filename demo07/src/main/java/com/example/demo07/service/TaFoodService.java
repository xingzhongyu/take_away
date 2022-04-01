package com.example.demo07.service;

import com.example.demo07.bean.TaFood;
import com.example.demo07.mapper.TaFoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaFoodService {
    @Autowired
    TaFoodMapper taFoodMapper;
    public TaFood insertTaFood(TaFood taFood){
         long num=taFoodMapper.insertTaFood(taFood);
         if(num>0) {
             return taFoodMapper.getTaFoodByFoodId(taFood.getFoodId());
         }else {
             return taFood;
         }
    }
    public Long deleteTaFood(Long foodId){
        return taFoodMapper.deleteTaFood(foodId);
    }
    public TaFood getTaFoodByFoodId(Long FoodId){
        return taFoodMapper.getTaFoodByFoodId(FoodId);
    }
    public List<TaFood> getTaFoodByCategory(String category,Long dinerId){
        return taFoodMapper.getTaFoodByCategory(category,dinerId);
    }
    public List<TaFood> getTaFoodByDinerId(Long dinerId){
        return taFoodMapper.getTaFoodByDinerId(dinerId);
    }
    public Long changeTaFood(TaFood taFood) {
        return taFoodMapper.changeTaFood(taFood);
    }
}
