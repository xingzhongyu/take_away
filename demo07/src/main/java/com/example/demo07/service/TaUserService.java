package com.example.demo07.service;


import com.example.demo07.bean.TaDiner;
import com.example.demo07.bean.TaUser;
import com.example.demo07.mapper.TaUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class TaUserService {
    @Autowired
    TaUserMapper taUserMapper;
    public TaUser register(TaUser taUser){
        taUser.setPassword(DigestUtils.md5DigestAsHex(taUser.getPassword().getBytes()));
       taUserMapper.register(taUser);
        return getUserById(taUser.getUserId());
    }
    public TaUser login(String nickname,String password){
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        return taUserMapper.login(nickname, password);
    }
    public TaUser getUserById(long userId){
        return taUserMapper.getUserById(userId);
    }


}
