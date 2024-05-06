package com.example.ojbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ojbackend.entity.Quest;
import org.apache.ibatis.annotations.Mapper;


/**
* @author cuixi
* @description 针对表【quest(帖子)】的数据库操作Mapper
* @createDate 2023-09-04 15:14:11
* @Entity generator.domain.Quest
*/
@Mapper
public interface QuestMapper extends BaseMapper<Quest> {

}




