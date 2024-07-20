package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@TableName("user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private Integer age;
    private String sex;
    private String address;
    @TableField(exist = false)
    private List<Integer> roles;

    @TableField(exist = false)
    private List<Book> bookList;

    @TableField(exist = false)
    private String token;

    private BigDecimal account;

    @TableField(exist = false)
    private Set<Permission> permissions;
    private String type;
    private String identify;
    private String phone;

}
