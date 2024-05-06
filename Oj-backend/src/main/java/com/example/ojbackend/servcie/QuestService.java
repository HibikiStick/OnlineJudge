package com.example.ojbackend.servcie;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ojbackend.entity.Quest;
import com.example.ojbackend.entity.dto.quest.QuestionQueryRequest;
import com.example.ojbackend.entity.vo.QuestVo;
import jakarta.servlet.http.HttpServletRequest;



/**
* @author cuixi
* @description 针对表【quest(帖子)】的数据库操作Service
* @createDate 2023-09-04 15:14:11
*/
public interface QuestService extends IService<Quest> {
    /**
     * 校验
     */
    void validQuest(Quest quest,Boolean add);

    QueryWrapper<Quest> getQueryWrapper(QuestionQueryRequest question);

    QuestVo getQuestVo(Quest quest, HttpServletRequest request);

    Page<QuestVo> getQuestVoPage(Page<Quest> questPage, HttpServletRequest request);

}
