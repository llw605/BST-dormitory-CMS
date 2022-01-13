package com.xiaowuyaya.bstdormitorycms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (UserRole)实体类
 *
 * @author xiaowuyaya
 * @since 2022-01-06 11:01:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {
    private static final long serialVersionUID = -52281155238481292L;
    @TableId(type = IdType.AUTO)

    private Integer id;
    
    private String role;

}

