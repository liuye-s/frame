package com.liuye.wyzdz.entities;

import java.util.Date;
import com.liuye.common.entities.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author orange
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ClientUserInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    @TableField("name")
    private String name;

    /**
     * 性别
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 生日
     */
    @TableField("birthday")
    private String birthday;

    /**
     * 学历
     */
    @TableField("education")
    private Integer education;

    /**
     * 学校名称
     */
    @TableField("school_name")
    private String schoolName;

    /**
     * 地址
     */
    @TableField("addr")
    private String addr;

    /**
     * 家庭地址
     */
    @TableField("family_addr")
    private String familyAddr;

    /**
     * 微信号
     */
    @TableField("we_chat")
    private String weChat;

    /**
     * 职业
     */
    @TableField("job")
    private String job;

    /**
     * 身高
     */
    @TableField("height")
    private String height;

    /**
     * 星座
     */
    @TableField("constellation")
    private Integer constellation;

    /**
     * 年收入
     */
    @TableField("income")
    private String income;

    /**
     * 兴趣爱好
     */
    @TableField("interest")
    private String interest;

    /**
     * 自我介绍标签
     */
    @TableField("Introduce_flag")
    private String introduceFlag;

    /**
     * 自我介绍描述
     */
    @TableField("Introduce_desc")
    private String introduceDesc;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime = new Date();

    /**
     * 修改时间
     */
    @TableField("update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime = new Date();


}
