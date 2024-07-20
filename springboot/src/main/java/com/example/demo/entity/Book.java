package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@TableName("book")
@Data
public class Book {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private BigDecimal price;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    private Date arrivalTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    private Date departureTime;

    private String userId;
    // TableField注解表示数据库不存在的字段，而Java中需要使用，加上这个注解就不会报错
    @TableField(exist = false)
    private String username;
    private String originStation;
    private String destination;
    private int car;
    private int seat;
    private int ticket;
    private String period;
    private int ticketRemain;
    private int ticketNow;
    private String timeString;
    private String dateString;

    @TableField(exist = false)
    private BigDecimal totalPrice;

    @TableField(exist = false)
    private int minTicket;

    @TableField(exist = false)
    private int another;

    @TableField(exist = false)
    private int transferId;
}
