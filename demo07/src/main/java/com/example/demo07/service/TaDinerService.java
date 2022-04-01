package com.example.demo07.service;


import com.example.demo07.bean.TaDiner;
import com.example.demo07.bean.TaUser;
import com.example.demo07.mapper.TaDinerMapper;
import com.example.demo07.mapper.TaUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaDinerService {
    @Autowired
    TaUserMapper taUserMapper;
    @Autowired
    TaDinerMapper taDinerMapper;
    public long insertTaDiner(TaDiner taDiner){
        taDinerMapper.insertTaDiner(taDiner);
        return taDiner.getDinerId();
    }
    public List<TaDiner> getTaDinerByUserId(Long userId){
        return taDinerMapper.getTaDinerByUserId(userId);
    }
    public List<TaDiner> getTaDiner(String word){
        if (word==null){
            return taDinerMapper.getTaDiner();
        }else {
            return taDinerMapper.searchTaDiner(word);
        }
    }
}
