package com.example.ojbackend.servcie.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ojbackend.common.ErrorCode;
import com.example.ojbackend.entity.Quest;
import com.example.ojbackend.entity.QuestSubmit;
import com.example.ojbackend.entity.User;
import com.example.ojbackend.entity.dto.quest.JudgeInfo;
import com.example.ojbackend.entity.dto.quest.QuestionSubmitRequest;
import com.example.ojbackend.entity.enums.QuestSubmitEnum;
import com.example.ojbackend.exception.BusinessException;
import com.example.ojbackend.mapper.QuestSubmitMapper;
import com.example.ojbackend.servcie.QuestService;
import com.example.ojbackend.servcie.QuestSubmitService;
import jakarta.annotation.Resource;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author cuixi
* @description 针对表【quest_submit(帖子点赞)】的数据库操作Service实现
* @createDate 2023-09-04 18:45:04
*/
@Service
public class QuestSubmitServiceImpl extends ServiceImpl<QuestSubmitMapper, QuestSubmit>
    implements QuestSubmitService {

    @Resource
    private QuestService questService;

    @Override
    public long doQuestionSubmit(QuestionSubmitRequest request, User loginUser) {
        //检验语言是否正确
        String language = request.getLanguage();
        QuestSubmitEnum questSubmitEnum = QuestSubmitEnum.getEnumByValue(language);
        if (questSubmitEnum == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"语言选择错误");
        }
        long questId = request.getQuestId();
        Quest quest = questService.getById(questId);
        if (quest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long userId = loginUser.getId();
        QuestSubmit questSubmit = new QuestSubmit();
        questSubmit.setQuestionId(questId);
        questSubmit.setUserId(userId);
        questSubmit.setCode(questSubmit.getCode());
        questSubmit.setJudgeInfo("{}");
        questSubmit.setStatus(QuestSubmitEnum.WAITING.getValue());
        boolean save = this.save(questSubmit);
        if (!save){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return questSubmit.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int QuestionInner(long userId, long questId) {
        QuestSubmit questSubmit = new QuestSubmit();
        questSubmit.setUserId(userId);
        questSubmit.setQuestionId(questId);
        QueryWrapper<QuestSubmit> queryWrap = new QueryWrapper<>();
        QuestSubmit oldQuestSubmit = this.getOne(queryWrap);
        boolean result;
        if (oldQuestSubmit != null){
            result = this.remove(queryWrap);
            if (result){
                /*result = questService.update();*/
            }
        }
        return 0;
    }
}




