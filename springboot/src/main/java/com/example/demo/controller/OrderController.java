package com.example.demo.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Book;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.OrderMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Resource
    OrderMapper OrderMapper;

    @Resource
    BookMapper bookMapper;

    @PostMapping
    public Result<?> save(@RequestBody Order Order) {
        OrderMapper.insert(Order);
        return Result.success();
    }


    @PutMapping
    public Result<?> update(@RequestBody Order Order) {
        OrderMapper.updateById(Order);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        Order order=OrderMapper.selectById(id);

        Book book=bookMapper.selectById(order.getBookId());
        book.setTicketRemain(book.getTicketRemain()+1);
        bookMapper.updateById(book);
        if(order.getBookId1()!=0){
            Book book1=bookMapper.selectById(order.getBookId1());
            book1.setTicketRemain(book1.getTicketRemain()+1);
            bookMapper.updateById(book1);
        }
        OrderMapper.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteTicket/{id}")
    public Result<?> delete1(@PathVariable Long id) {
        Order order=OrderMapper.selectById(id);

        Book book=bookMapper.selectById(order.getBookId());
        book.setTicketRemain(book.getTicketRemain()+1);
        bookMapper.updateById(book);
        if(order.getBookId1()!=0){
            Book book1=bookMapper.selectById(order.getBookId1());
            book1.setTicketRemain(book1.getTicketRemain()+1);
            bookMapper.updateById(book1);
        }
        order.setSeat(0);
        order.setSeat1(0);
        order.setCar(0);
        order.setCar1(0);
        order.setTicket(0);
        order.setTicket1(0);
        order.setBookId(0);
        order.setBookId1(0);
        order.setState(3);
        OrderMapper.updateById(order);
        return Result.success();
    }



    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(OrderMapper.selectById(id));
    }

    @GetMapping("/buy/{orderId}")
    public Result<?> buy(@PathVariable Long orderId) {
        Order order=OrderMapper.selectById(orderId);
        String payUrl = "http://localhost:9090/alipay/pay?subject=" + order.getName() + "&traceNo=" + order.getOrderNo() + "&totalAmount=" + order.getTotalPrice();
        System.out.println(payUrl);
        return Result.success(payUrl);

    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "") String user) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Order::getUserId, user);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Order::getOrderNo, search);
        }
        Page<Order> OrderPage = OrderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        System.out.println(user);
        return Result.success(OrderPage);
    }


    @GetMapping("list")
    public Result<?> findPage1(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Order::getOrderNo, search);
        }
        Page<Order> OrderPage = OrderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        System.out.println("11111111111111111111111111111");
        return Result.success(OrderPage);
    }

    @PutMapping("/check")
    public Result<?> update(@RequestBody Book book) {
        System.out.println(book);
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        wrapper.like(Order::getBookId, book.getId());
        List<Order> OrderList = OrderMapper.selectList(wrapper);

        LambdaQueryWrapper<Order> wrapper1 = Wrappers.<Order>lambdaQuery();
        wrapper1.like(Order::getBookId1, book.getId());
        List<Order> OrderList1 = OrderMapper.selectList(wrapper1);

        int length=OrderList.size();

        int length1=OrderList1.size();

        int ticketTotal=book.getTicket();
        int checkList[]=new int[ticketTotal+1000];
        int result=0;
        for(int i=1;i<=ticketTotal;i++){
            checkList[i]=0;
        }
//        System.out.println(checkList);
        for(int i=1;i<=length;i++){
            checkList[OrderList.get(i-1).getTicket()]=1;
        }

        for(int i=1;i<=length1;i++){
            checkList[OrderList1.get(i-1).getTicket1()]=1;
        }
        for(int i=1;i<=ticketTotal;i++){
            if(checkList[i]==0){
                result=i;
                break;
            }
        }
        book.setTicketNow(result);
        book.setTicketRemain(book.getTicketRemain()-1);
        bookMapper.updateById(book);

        int bookId=book.getId();
        String orderNo = IdUtil.getSnowflake().nextIdStr();
        User user = getUser();
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setTotalPrice(book.getPrice());
        order.setPayPrice(book.getPrice());
        order.setTransportPrice(BigDecimal.ZERO);
        order.setUserId(user.getId());
        order.setUsername(user.getUsername());
        order.setName(book.getName());
        order.setOriginStation(book.getOriginStation());
        order.setDestination(book.getDestination());
        order.setArrivalTime(book.getArrivalTime());
        order.setDepartureTime(book.getDepartureTime());
        order.setTicket(result);
        order.setCar((result-1)/book.getSeat()+1);
        order.setSeat((result-1)%book.getSeat()+1);
        order.setBookId(book.getId());

        System.out.println(book);
        System.out.println(order);

        save(order);

        return Result.success(result);
    }


    @PutMapping("/checkTransfer")
    public Result<?> update1(@RequestBody Book book) {

        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        wrapper.like(Order::getBookId, book.getId());
        List<Order> OrderList = OrderMapper.selectList(wrapper);

        LambdaQueryWrapper<Order> wrapper1 = Wrappers.<Order>lambdaQuery();
        wrapper1.like(Order::getBookId1, book.getId());
        List<Order> OrderList1 = OrderMapper.selectList(wrapper1);

        int length=OrderList.size();
        int length1=OrderList1.size();
        int ticketTotal=book.getTicket();
        int checkList[]=new int[ticketTotal+1000];
        int result=0;

        for(int i=1;i<=ticketTotal;i++){
            checkList[i]=0;
        }
//        System.out.println(checkList);
        for(int i=1;i<=length;i++){
            checkList[OrderList.get(i-1).getTicket()]=1;
        }

        for(int i=1;i<=length1;i++){
            checkList[OrderList1.get(i-1).getTicket1()]=1;
        }

        for(int i=1;i<=ticketTotal;i++){
            if(checkList[i]==0){
                result=i;
                break;
            }
        }

        LambdaQueryWrapper<Order> wrapper2 = Wrappers.<Order>lambdaQuery();
        wrapper2.like(Order::getBookId, book.getAnother());
        List<Order> OrderList2 = OrderMapper.selectList(wrapper2);

        LambdaQueryWrapper<Order> wrapper3 = Wrappers.<Order>lambdaQuery();
        wrapper3.like(Order::getBookId1, book.getAnother());
        List<Order> OrderList3 = OrderMapper.selectList(wrapper3);

        Book bookAnother =bookMapper.selectById(book.getAnother());

        int length2=OrderList2.size();
        int length3=OrderList3.size();
        int ticketTotal2=bookAnother.getTicket();
        int checkList2[]=new int[ticketTotal2+1];
        int result2=0;

        for(int i=1;i<=ticketTotal2;i++){
            checkList2[i]=0;
        }

        for(int i=1;i<=length2;i++){
            checkList2[OrderList2.get(i-1).getTicket()]=1;
        }

        for(int i=1;i<=length3;i++){
            checkList2[OrderList3.get(i-1).getTicket1()]=1;
        }

        for(int i=1;i<=ticketTotal2;i++){
            if(checkList2[i]==0){
                result2=i;
                break;
            }
        }




        System.out.println(book);
        System.out.println(result);

        book.setTicketNow(result);
        book.setTicketRemain(book.getTicketRemain()-1);
        bookMapper.updateById(book);

        bookAnother.setTicketNow(result2);
        bookAnother.setTicketRemain(bookAnother.getTicketRemain()-1);
        bookMapper.updateById(bookAnother);

        int bookId=book.getId();
        String orderNo = IdUtil.getSnowflake().nextIdStr();
        User user = getUser();
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setTotalPrice(book.getTotalPrice());
        order.setPayPrice(book.getTotalPrice());
        order.setTransportPrice(BigDecimal.ZERO);
        order.setUserId(user.getId());
        order.setUsername(user.getUsername());
        order.setName(book.getName());
        order.setName1(bookAnother.getName());
        order.setOriginStation(book.getOriginStation());
        order.setTransferStation(book.getDestination());
        order.setDestination(bookAnother.getDestination());

        order.setArrivalTime(bookAnother.getArrivalTime());
        order.setArrivalTime1(book.getArrivalTime());
        order.setDepartureTime(book.getDepartureTime());
        order.setDepartureTime1(bookAnother.getDepartureTime());

        order.setTicket(result);
        order.setTicket1(result2);

        order.setCar((result-1)/book.getSeat()+1);
        order.setCar1((result2-1)/bookAnother.getSeat()+1);
        order.setSeat((result-1)%book.getSeat()+1);
        order.setSeat1((result2-1)%bookAnother.getSeat()+1);
        order.setBookId(book.getId());
        order.setBookId1(bookAnother.getId());
        save(order);

        System.out.println(book);
        System.out.println(bookAnother);
        System.out.println(order);
        return Result.success(result);
    }


    @GetMapping("/change")
    public Result<?> change(@RequestParam(defaultValue = "") int bookId,
                            @RequestParam(defaultValue = "") int OrderId) {
        Order order1=OrderMapper.selectById(OrderId);
        Book book1=bookMapper.selectById(order1.getBookId());
        book1.setTicketRemain(book1.getTicketRemain()+1);
        bookMapper.updateById(book1);

        Book book=bookMapper.selectById(bookId);
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        wrapper.like(Order::getBookId, book.getId());
        List<Order> OrderList = OrderMapper.selectList(wrapper);

        LambdaQueryWrapper<Order> wrapper1 = Wrappers.<Order>lambdaQuery();
        wrapper1.like(Order::getBookId1, book.getId());
        List<Order> OrderList1 = OrderMapper.selectList(wrapper1);

        int length=OrderList.size();

        int length1=OrderList1.size();

        int ticketTotal=book.getTicket();
        int checkList[]=new int[ticketTotal+1000];
        int result=0;
        for(int i=1;i<=ticketTotal;i++){
            checkList[i]=0;
        }
//        System.out.println(checkList);
        for(int i=1;i<=length;i++){
            checkList[OrderList.get(i-1).getTicket()]=1;
        }

        for(int i=1;i<=length1;i++){
            checkList[OrderList1.get(i-1).getTicket1()]=1;
        }
        for(int i=1;i<=ticketTotal;i++){
            if(checkList[i]==0){
                result=i;
                break;
            }
        }
        book.setTicketNow(result);

        book.setTicketRemain(book.getTicketRemain()-1);
        bookMapper.updateById(book);

        String orderNo = IdUtil.getSnowflake().nextIdStr();
        User user = getUser();
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setTotalPrice(book.getPrice());
        order.setPayPrice(book.getPrice());
        order.setTransportPrice(BigDecimal.ZERO);
        order.setUserId(user.getId());
        order.setUsername(user.getUsername());
        order.setName(book.getName());
        order.setOriginStation(book.getOriginStation());
        order.setDestination(book.getDestination());
        order.setArrivalTime(book.getArrivalTime());
        order.setDepartureTime(book.getDepartureTime());
        order.setTicket(result);
        order.setCar((result-1)/book.getSeat()+1);
        order.setSeat((result-1)%book.getSeat()+1);
        order.setBookId(book.getId());
        order.setState(1);
        order.setPayTime(new Date(System.currentTimeMillis()));



        System.out.println(book);
        System.out.println(order);

        save(order);
        OrderMapper.deleteById(OrderId);

        System.out.println(order);
        System.out.println(OrderId);
        return Result.success();
    }
}
