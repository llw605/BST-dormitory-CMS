package com.xiaowuyaya.bstdormitorycms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.io.Serializable;

/**
 * (Annotation)实体类
 *
 * @author xiaowuyaya
 * @since 2022-01-02 00:39:18
 */
@Data
@EqualsAndHashCode
@TableName("annotation")
public class Annotation {
    /**
     * 公告主键
     */
    @TableId(type = IdType.AUTO)
    private Integer annotationId;
    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 内容
     */
    private String content;

}

