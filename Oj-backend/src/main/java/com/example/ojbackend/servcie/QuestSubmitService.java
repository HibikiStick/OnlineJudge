package com.example.ojbackend.servcie;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ojbackend.entity.QuestSubmit;
import com.example.ojbackend.entity.User;
import com.example.ojbackend.entity.dto.quest.QuestionSubmitRequest;


/**
* @author cuixi
* @description 针对表【quest_submit(帖子点赞)】的数据库操作Service
* @createDate 2023-09-04 18:45:04
*/
public interface QuestSubmitService extends IService<QuestSubmit> {
    /**
     * 提交题目
     * @param questId 题目ID
     * @param loginUser 当前用户
     * @return
     */
    long doQuestionSubmit(QuestionSubmitRequest request, User loginUser);

    /**
     * 封装事务的方法
     * @param userId
     * @param postId
     * @return
     */
    int QuestionInner(long userId, long questId);
}
