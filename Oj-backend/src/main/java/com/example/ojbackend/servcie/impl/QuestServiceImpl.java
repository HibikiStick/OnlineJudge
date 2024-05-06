package com.example.ojbackend.servcie.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ojbackend.common.ErrorCode;
import com.example.ojbackend.constant.CommonConstant;
import com.example.ojbackend.entity.Quest;
import com.example.ojbackend.entity.User;
import com.example.ojbackend.entity.dto.quest.QuestionQueryRequest;
import com.example.ojbackend.entity.vo.QuestVo;
import com.example.ojbackend.entity.vo.UserVO;
import com.example.ojbackend.exception.BusinessException;
import com.example.ojbackend.exception.ThrowUtils;
import com.example.ojbackend.mapper.QuestMapper;
import com.example.ojbackend.servcie.QuestService;
import com.example.ojbackend.servcie.UserService;
import com.example.ojbackend.utils.SqlUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author cuixi
* @description 针对表【quest(帖子)】的数据库操作Service实现
* @createDate 2023-09-04 15:14:11
*/
@Service
public class QuestServiceImpl extends ServiceImpl<QuestMapper, Quest>
    implements QuestService {

    @Resource
    private UserService userService;

    /**
     * 校验提交的实体
     * @param quest 前端发送来的quest
     * @param add 是否为新增
     */
    @Override
    public void validQuest(Quest quest, Boolean add) {
        if (quest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = quest.getTitle();
        String content = quest.getContent();
        String tags = quest.getTags();
        String answer = quest.getAnswer();
        String judgeConfig = quest.getJudgeConfig();
        String judegCase = quest.getJudegCase();
        if (add){
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title,content,tags),ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isNotBlank(title) && title.length() > 80){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"内容过长");
        }
        if (StringUtils.isNotBlank(answer) && answer.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"答案过长");
        }
        if (StringUtils.isNotBlank(judegCase) && judegCase.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"判题用例");
        }
        if (StringUtils.isNotBlank(judgeConfig) && judgeConfig.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"判题配置过长");
        }
    }

    /**
     * 查询包装类
     * @param question
     * @return
     */
    @Override
    public QueryWrapper<Quest> getQueryWrapper(QuestionQueryRequest question) {
        QueryWrapper<Quest> queryWrap = new QueryWrapper<>();
        if (question == null){
            return queryWrap;
        }
        Long id = question.getId();
        String title = question.getTitle();
        String content = question.getContent();
        List<String> tags = question.getTags();
        Long userId = question.getUserId();
        String answer = question.getAnswer();
        String sortField = question.getSortField();
        String sortOrder = question.getSortOrder();

        queryWrap.like(StringUtils.isNotBlank(title),"title",title);
        queryWrap.like(StringUtils.isNotBlank(content),"content",content);
        queryWrap.like(StringUtils.isNotBlank(answer),"answer",answer);

        if (CollectionUtils.isNotEmpty(tags)){
            for (String tag: tags){
                queryWrap.like("tags","\"" + tag + "\"");
            }
        }
        queryWrap.like(ObjectUtils.isNotEmpty(id),"id",id);
        queryWrap.like(ObjectUtils.isNotEmpty(userId),"userId",userId);
        queryWrap.eq("isDelete",false);
        queryWrap.orderBy(SqlUtils.validSortField(sortField),sortOrder.equals(CommonConstant.SORT_ORDER_ASC),sortOrder);
        return queryWrap;
    }

    /**
     * 获取VO
     * @param quest
     * @param request
     * @return
     */
    @Override
    public QuestVo getQuestVo(Quest quest, HttpServletRequest request) {
        QuestVo questVo = QuestVo.objToVo(quest);
        Long userId = quest.getUserId();
        User user = null;
        if (userId != null && userId > 0){
            user = userService.getById(userId);
        }
        UserVO userVo = userService.getUserVo(user);
        questVo.setUserVo(userVo);
        return questVo;
    }

    @Override
    public Page<QuestVo> getQuestVoPage(Page<Quest> questPage, HttpServletRequest request) {
        List<Quest> questList = questPage.getRecords();
        Page<QuestVo> questPageList = new Page<>(questPage.getCurrent(), questPage.getSize(),questPage.getTotal());
        if (CollectionUtils.isEmpty(questList)){
            return questPageList;
        }
        //查询关联用户的信息
        Set<Long> userIds = questList.stream().map(Quest::getUserId).collect(Collectors.toSet());
        Map<Long,List<User>> userIdMap = userService.listByIds(userIds).stream().collect(Collectors.groupingBy(User::getId));
        //填充进VO
        List<QuestVo> questVoList = questList.stream().map(quest -> {
            QuestVo questVo = QuestVo.objToVo(quest);
            Long userId = quest.getUserId();
            User user = null;
            if (userIdMap.containsKey(userId)){
                user = userIdMap.get(userId).get(0);
            }
            questVo.setUserVo(userService.getUserVo(user));
            return questVo;
        }).collect(Collectors.toList());
        questPageList.setRecords(questVoList);
        return questPageList;
    }

}




