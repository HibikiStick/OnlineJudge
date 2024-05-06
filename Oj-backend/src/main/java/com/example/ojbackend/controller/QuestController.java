package com.example.ojbackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ojbackend.annotation.AuthCheck;
import com.example.ojbackend.common.BaseResponse;
import com.example.ojbackend.common.DeleteRequest;
import com.example.ojbackend.common.ErrorCode;
import com.example.ojbackend.common.ResultUtils;
import com.example.ojbackend.constant.UserConstant;
import com.example.ojbackend.entity.Quest;
import com.example.ojbackend.entity.User;
import com.example.ojbackend.entity.dto.quest.JudgeCase;
import com.example.ojbackend.entity.dto.quest.JudgeConfig;
import com.example.ojbackend.entity.dto.quest.QuestionAddRequest;
import com.example.ojbackend.entity.dto.quest.QuestionQueryRequest;
import com.example.ojbackend.entity.vo.QuestVo;
import com.example.ojbackend.exception.BusinessException;
import com.example.ojbackend.exception.ThrowUtils;
import com.example.ojbackend.servcie.QuestService;
import com.example.ojbackend.servcie.UserService;
import com.google.gson.Gson;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quest")
@Slf4j
public class QuestController {
    @Resource
    private QuestService questService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    @PostMapping("/add")
    public BaseResponse<Long> addQuest(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request){
        if (questionAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数出错");
        }
        Quest quest = new Quest();
        BeanUtils.copyProperties(questionAddRequest, quest);
        List<String> tags = questionAddRequest.getTags();
        if (tags != null){
            quest.setTags(GSON.toJson(tags));
        }
        JudgeConfig judgeConfigs = questionAddRequest.getJudgeConfig();
        List<JudgeCase> judgeCases = questionAddRequest.getJudgeCase();
        if (judgeCases != null){
            quest.setJudegCase(GSON.toJson(judgeCases));
        }
        if (judgeConfigs != null){
            quest.setJudgeConfig(GSON.toJson(judgeConfigs));
        }
        questService.validQuest(quest,true);
        User user = userService.getLoginUser(request);
        quest.setUserId(user.getId());
        quest.setFavourNum(0);
        quest.setThumbNum(0);
        quest.setAcceptedNum(0);
        quest.setSubmitNum(0);
        boolean result = questService.save(quest);
        //条件成立即抛异常
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        long newQuestId = quest.getId();
        return ResultUtils.success(newQuestId);
    }

    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<QuestVo>> listMyQuestionPage(@RequestBody QuestionQueryRequest quest,HttpServletRequest request){
        if(quest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数出错");
        }
        User user = userService.getLoginUser(request);
        quest.setUserId(user.getId());
        long current = quest.getCurrent();
        long size = quest.getPageSize();
        ThrowUtils.throwIf(size > 20,ErrorCode.PARAMS_ERROR);
        Page<Quest> questPage = questService.page(new Page<>(current,size),questService.getQueryWrapper(quest));
        return ResultUtils.success(questService.getQuestVoPage(questPage,request));

    }

    @PostMapping("/list/page")
    public BaseResponse<Page<Quest>> listQuestByPage(@RequestBody QuestionQueryRequest questionQueryRequest, HttpServletRequest request){

        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        Page<Quest> questPage = questService.page(new Page<>(current,size),questService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questPage);

    }

    /**
     * 更新（仅管理员）
     *
     * @param questionQueryRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updatePost(@RequestBody QuestionQueryRequest questionQueryRequest) {
        if (questionQueryRequest == null || questionQueryRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Quest post = new Quest();
        BeanUtils.copyProperties(questionQueryRequest, post);
        List<String> tags = questionQueryRequest.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        // 参数校验
        questService.validQuest(post, false);
        long id = questionQueryRequest.getId();
        // 判断是否存在
        Quest oldPost = questService.getById(id);
        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = questService.updateById(post);
        return ResultUtils.success(result);
    }


    /**
     * 编辑（用户）
     *
     * @param postEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editPost(@RequestBody QuestionQueryRequest postEditRequest, HttpServletRequest request) {
        if (postEditRequest == null || postEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Quest quest = new Quest();
        BeanUtils.copyProperties(postEditRequest, quest);
        List<String> tags = postEditRequest.getTags();
        if (tags != null) {
            quest.setTags(GSON.toJson(tags));
        }
        // 参数校验
        questService.validQuest(quest, false);
        User loginUser = userService.getLoginUser(request);
        long id = postEditRequest.getId();
        // 判断是否存在
        Quest oldPost = questService.getById(id);
        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldPost.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = questService.updateById(quest);
        return ResultUtils.success(result);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deletePost(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Quest oldPost = questService.getById(id);
        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldPost.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = questService.removeById(id);
        return ResultUtils.success(b);
    }




























}
