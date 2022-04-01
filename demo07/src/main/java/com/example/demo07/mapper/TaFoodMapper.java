package com.example.demo07.mapper;

import com.example.demo07.bean.TaFood;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaFoodMapper {
    List<TaFood> getTaFoodByDinerId(Long dinerId);
    Long insertTaFood(TaFood taFood);
    TaFood getTaFoodByFoodId(Long foodId);
    List<TaFood> getTaFoodByCategory(String category,Long dinerId);
    Long changeTaFood(TaFood taFood);
    Long deleteTaFood(Long foodId);
}
