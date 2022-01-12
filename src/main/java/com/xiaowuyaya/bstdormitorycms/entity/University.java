package com.xiaowuyaya.bstdormitorycms.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * (University)实体类
 *
 * @author xiaowuyaya
 * @since 2021-12-31 13:06:49
 */
@Data
@TableName("university")
public class University{
    /**
     * 大学id
     */
    @TableId(type = IdType.AUTO)
    private Integer universityId;
    /**
     * 大学名称
     */
    private String universityName;

    private String role;

}

