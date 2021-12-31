package com.xiaowuyaya.bstdormitorycms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (Building)实体类
 *
 * @author xiaowuyaya
 * @since 2021-12-31 13:07:45
 */
@Data
@EqualsAndHashCode
@TableName("building")
public class Building { ;
    /**
     * 宿舍楼id
     */
    @TableId
    private Integer buildingId;
    /**
     * 大学id
     */
    private Integer universityId;
    /**
     * 宿舍楼名称
     */
    private String buildingName;


}

