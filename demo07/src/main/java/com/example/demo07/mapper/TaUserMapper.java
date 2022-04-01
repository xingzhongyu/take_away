package com.example.demo07.mapper;

import com.example.demo07.bean.TaUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaUserMapper {
    void register(TaUser taUser);
    TaUser login(String nickname,String password);
    TaUser getUserById(Long userId);
    List<TaUser> getTaUser();
}
