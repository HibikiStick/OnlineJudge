package com.example.ojbackend.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.ojbackend.entity.Quest;
import com.example.ojbackend.entity.dto.quest.JudgeConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@TableName("quest")
public class QuestVo {

    private final static Gson GSON = new Gson();
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;


    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题Json数组
     */
    private JudgeConfig judgeConfig;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private UserVO userVo;


    public static Quest voToObj(QuestVo questVo){
        if (questVo == null){
            return null;
        }
        Quest quest = new Quest();
        BeanUtils.copyProperties(questVo,quest);
        List<String> tags = questVo.getTags();
        if (tags != null){
            quest.setTags(GSON.toJson(tags));
        }
        if (questVo.getJudgeConfig() != null){
            quest.setJudgeConfig(GSON.toJson(questVo.getJudgeConfig()));
        }
        return quest;
    }

    public static QuestVo objToVo(Quest quest){
        if (quest == null){
            return null;
        }
        QuestVo questVo = new QuestVo();
        BeanUtils.copyProperties(quest,questVo);
        List<String> tags = GSON.fromJson(quest.getTags(),new TypeToken<List<String>>(){}.getType());
        JudgeConfig judgeConfig = GSON.fromJson(quest.getJudgeConfig(), JudgeConfig.class);
        questVo.setTags(tags);
        questVo.setJudgeConfig(judgeConfig);
        return questVo;
    }
}
