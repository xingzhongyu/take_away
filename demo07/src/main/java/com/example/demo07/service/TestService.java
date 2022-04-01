package com.example.demo07.service;

import com.example.demo07.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TestMapper testMapper;
    public String test(String name){
        return testMapper.test(name);
    }
}
