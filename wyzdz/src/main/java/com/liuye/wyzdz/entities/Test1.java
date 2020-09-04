package com.liuye.wyzdz.entities;

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
 * @since 2020-09-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Test1 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("param1")
    private String param1;

    @TableField("param2")
    private String param2;

    @TableField("param3")
    private String param3;

    @TableField("param4")
    private String param4;


}
