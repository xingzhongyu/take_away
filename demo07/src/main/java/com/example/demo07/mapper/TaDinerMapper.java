package com.example.demo07.mapper;

import com.example.demo07.bean.TaDiner;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaDinerMapper {
    List<TaDiner> getTaDinerByUserId(Long userId);
    List<TaDiner> getTaDiner();
    void insertTaDiner(TaDiner taDiner);
    TaDiner getTaDinerByDinerId(Long dinerId);
    void changeTaDiner(TaDiner taDiner);
    List<TaDiner> searchTaDiner(String word);
}
