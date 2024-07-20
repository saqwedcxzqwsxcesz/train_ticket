package com.example.demo.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Book;
import com.example.demo.entity.Book1;
import com.example.demo.entity.Order;
import com.example.demo.mapper.BookMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

@RestController
@RequestMapping("/book")
public class BookController extends BaseController{

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date depatureTime,arrivalTime;
    @Resource
    BookMapper bookMapper;

    @PostMapping
    public Result<?> save(@RequestBody Book Book) {
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        Book.setTimeString(sdf.format(Book.getDepartureTime()));

        String strDateFormat1 = "yyyy-MM-dd";
        SimpleDateFormat sdf1 = new SimpleDateFormat(strDateFormat1);
        Book.setDateString(sdf1.format(Book.getDepartureTime()));

        Book.setTicket(Book.getCar()*Book.getSeat());
        Book.setTicketRemain(Book.getTicket());

        long diff = Book.getArrivalTime().getTime() - Book.getDepartureTime().getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
        String period="";
        if(days!=0)
            period+=days+"天";
        if(hours!=0||!period.equals(""))
            period+=hours+"小时";
        period+=minutes+"分";
        Book.setPeriod(period);
        bookMapper.insert(Book);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Book Book) {
//        DateFormat df3 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
//        DateFormat df7 = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
//        String date3 = df3.format(Book.getDepartureTime());
//        String time3 = df7.format(Book.getDepartureTime());
//        Book.setDateString();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        Book.setTimeString(sdf.format(Book.getDepartureTime()));

        String strDateFormat1 = "yyyy-MM-dd";
        SimpleDateFormat sdf1 = new SimpleDateFormat(strDateFormat1);
        Book.setDateString(sdf1.format(Book.getDepartureTime()));

        Book.setTicket(Book.getCar()*Book.getSeat());
        Book.setTicketRemain(Book.getTicket());

        long diff = Book.getArrivalTime().getTime() - Book.getDepartureTime().getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
        String period="";
        if(days!=0)
            period+=days+"天";
        if(hours!=0||!period.equals(""))
            period+=hours+"小时";
        period+=minutes+"分";
        Book.setPeriod(period);



//        Book.setDepartureTime(new Timestamp(Book.getDepartureTime().getTime()));
//        Book.setArrivalTime(new Timestamp(Book.getArrivalTime().getTime()));
//        Book.setDepartureTime(new java.sql.Date(Book.getDepartureTime().getTime()));
//        Book.setArrivalTime(new java.sql.Date(Book.getArrivalTime().getTime()));
            System.out.println(Book);
        bookMapper.updateById(Book);
        return Result.success();
    }
//      this.form.ticket=this.form.car*this.form.seat
//      this.form.ticketRemain=this.form.ticket
//      this.date=moment(this.form.).format("YYYY-MM-DD")+" "+this.aDate.getHours().toString()+":"
//            if(this.aDate.getMinutes().toString().length==1)
//            this.date+="0"
//            this.date+=this.aDate.getMinutes().toString()+":"+this.aDate.getSeconds()
//            this.form.timeString=date
//      this.form.
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        bookMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        bookMapper.deleteBatchIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        return Result.success(bookMapper.selectById(id));
    }

    /**
     * 注意：这个方法使用的是Mybatis sql的方式做的多表联合查询，大家可以点开，参考下怎么写多表查询
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public Result<?> getByUserId(@PathVariable Integer userId) {
        return Result.success(bookMapper.findByUserId(userId));
    }


    @GetMapping("/change")
    public Result<?> findPageChange(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String id) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
            wrapper.eq(Book::getName, name);
            wrapper.ne(Book::getId,id);
        Page<Book> BookPage = bookMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        System.out.println(id);
        System.out.println("11111111111111111111112");
        return Result.success(BookPage);
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search1,
                              @RequestParam(defaultValue = "") String search2,
                              @RequestParam(defaultValue = "") String search3) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if (StrUtil.isNotBlank(search1)) {
            wrapper.like(Book::getOriginStation, search1);
        }
        if (StrUtil.isNotBlank(search2)) {
            wrapper.like(Book::getDestination, search2);
        }
        if (StrUtil.isNotBlank(search3)){
            wrapper.eq(Book::getDateString,search3.toString());
        }
        Page<Book> BookPage = bookMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        System.out.println(search3);
        return Result.success(BookPage);
    }

    @GetMapping("/doubleCheck")
    public Result<?> findList(@RequestParam(defaultValue = "") String search1,
                              @RequestParam(defaultValue = "") String search2,
                              @RequestParam(defaultValue = "") String search3,
                              @RequestParam(defaultValue = "") String search4) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if (StrUtil.isNotBlank(search1)) {
            wrapper.like(Book::getOriginStation, search1);
        }
        if (StrUtil.isNotBlank(search4)) {
            wrapper.like(Book::getDestination, search4);
        }
        if (StrUtil.isNotBlank(search3)){
            wrapper.eq(Book::getDateString,search3.toString());
        }
        List<Book> BookList = bookMapper.selectList(wrapper);

        LambdaQueryWrapper<Book> wrapper1 = Wrappers.<Book>lambdaQuery();
        if (StrUtil.isNotBlank(search4)) {
            wrapper1.like(Book::getOriginStation, search4);
        }
        if (StrUtil.isNotBlank(search2)) {
            wrapper1.like(Book::getDestination, search2);
        }
        List<Book> BookList1 = bookMapper.selectList(wrapper1);

        List<Book> bookReturn=new ArrayList<>();
        int k=1;
        for(int i=0;i<BookList.size();i++)
            for(int j=0;j<BookList1.size();j++) {
                if(BookList.get(i).getDestination().equals(BookList1.get(j).getOriginStation())&&(BookList.get(i).getArrivalTime().toString().compareTo(BookList1.get(j).getDepartureTime().toString())<0)){
                    BookList.get(i).setAnother(BookList1.get(j).getId());
                    BookList1.get(j).setAnother(BookList.get(i).getId());
                    BookList1.get(j).setTotalPrice(BookList.get(i).getPrice().add(BookList1.get(j).getPrice()));
                    BookList.get(i).setTotalPrice(BookList.get(i).getPrice().add(BookList1.get(j).getPrice()));
                    int remain;
                    if(BookList.get(i).getTicketRemain()<BookList1.get(j).getTicketRemain())
                        remain=BookList.get(i).getTicketRemain();
                    else
                        remain=BookList1.get(j).getTicketRemain();
                    BookList.get(i).setMinTicket(remain);
                    BookList1.get(j).setMinTicket(remain);

                    BookList.get(i).setTransferId(k);
                    BookList1.get(j).setTransferId(k);
                    k++;

                    bookReturn.add(BookList.get(i));
                    bookReturn.add(BookList1.get(j));
                }
            }
        System.out.println(bookReturn);
        System.out.println("1111111111111111111111111111");
        return Result.success(bookReturn);
    }
}
