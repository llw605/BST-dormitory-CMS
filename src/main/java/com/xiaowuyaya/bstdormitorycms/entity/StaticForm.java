package com.xiaowuyaya.bstdormitorycms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (StaticForm)实体类
 *
 * @author xiaowuyaya
 * @since 2022-01-13 08:58:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaticForm implements Serializable {
    private static final long serialVersionUID = -17889814680886109L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 所属大学
     */
    private Integer universityId;
    /**
     * 状态 0正常 1停用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;


}

