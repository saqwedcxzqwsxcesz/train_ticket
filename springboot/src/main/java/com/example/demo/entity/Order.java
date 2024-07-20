package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_order")
public class Order {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String name1;
    private BigDecimal totalPrice;
    private BigDecimal payPrice;
    private BigDecimal discount;
    private BigDecimal transportPrice;
    private String orderNo;
    private Integer userId;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;
    private Integer state;
    private String originStation;
    private String destination;

    private String transferStation;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date arrivalTime;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date arrivalTime1;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date departureTime1;

    private int car;
    private int seat;
    private int ticket;
    private int BookId;

    private int car1;
    private int seat1;
    private int ticket1;
    private int BookId1;

}
