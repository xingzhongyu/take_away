package com.example.demo07.mapper;

import com.example.demo07.bean.TaComment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface TaCommentMapper{
    Long insertTaComment(TaComment taComment);
    TaComment getTaComment(Long orderId,String type);
    TaComment getTaCommentByCommentId(Long commentId);
}
