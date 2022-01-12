package com.xiaowuyaya.bstdormitorycms.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * (DormitoryInfo)实体类
 *
 * @author xiaowuyaya
 * @since 2021-12-31 13:07:45
 */
@Data
@TableName("dormitory_info")
public class DormitoryInfo{
    /**
     * 宿舍信息id
     */
    @TableId(type = IdType.AUTO)
    private Integer dormitoryId;
    /**
     * 宿舍楼id
     */
    private Integer buildingId;
    /**
     * 楼层
     */
    private String floor;
    /**
     * 房号
     */
    private Integer roomNo;

    /**
     * 加粉人数
     */
    private Integer fansNo;
    /**
     * 性别 1男 0女 -1未知
     */
    private Integer sex;
    /**
     * 加粉难度 1简单 2一般 3困难
     */
    private Integer toFansDiff;
    /**
     * 到访次数
     */
    private Integer comingTime;
    /**
     * 到访人员
     */
    private String visitors;

    /**
     * 表单填写人数统计
     */
    private Integer dorStatistics;
    /**
     * 备注信息
     */
    private String remarks;

}

