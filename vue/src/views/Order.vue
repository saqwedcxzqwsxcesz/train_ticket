<template>
  <div style="padding: 10px">
    <!--    功能区域-->
<!--    <div style="margin: 10px 0">-->
<!--      <el-button type="primary" @click="add" v-if="user.role === 1">新增</el-button>-->

<!--    </div>-->

    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入订单编号" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table
        v-loading="loading"
        :data="tableData"
        border
        stripe
        style="width: 1360px"

    >
<!--      <el-table-column-->
<!--          width="50"-->
<!--          prop="id"-->
<!--          label="ID"-->
<!--          sortable-->
<!--      >-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--          width="50"-->
<!--          prop="name"-->
<!--          label="车次编号">-->
<!--      </el-table-column>-->
      <el-table-column
          width="160"
          prop="orderNo"
          label="订单编号"
          sortable
          fixed>
      </el-table-column>
      <el-table-column label="类型">
        <template #default="scope">
          <el-tag class="ml-2" v-if="scope.row.bookId1===0">直达</el-tag>
          <el-tag class="ml-2" type="success" v-if="scope.row.bookId1!=0">换乘</el-tag>
        </template>

      </el-table-column>
      <el-table-column
          prop="totalPrice"
          label="总价">
      </el-table-column>
<!--      <el-table-column-->
<!--          prop="payPrice"-->
<!--          label="实付款">-->
<!--      </el-table-column>-->
      <el-table-column
          prop="originStation"
          label="起点站">
      </el-table-column>
      <el-table-column
          prop="destination"
          label="终点站">
      </el-table-column>
<!--      <el-table-column-->
<!--          width="90"-->
<!--          prop="departureData"-->
<!--          label="发车日期">-->
<!--      </el-table-column>-->
      <el-table-column
          width="90"
          prop="departureTime"
          label="发车时间">
      </el-table-column>
<!--      <el-table-column-->
<!--          width="90"-->
<!--          prop="arrivalData"-->
<!--          label="到站日期">-->
<!--      </el-table-column>-->
      <el-table-column
          width="90"
          prop="arrivalTime"
          label="到站时间">
      </el-table-column>
<!--      <el-table-column-->
<!--          prop="car"-->
<!--          label="车厢号">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--          prop="seat"-->
<!--          label="座号">-->
<!--      </el-table-column>-->
      <el-table-column
          width="150"
          prop="createTime"
          label="创建时间">
      </el-table-column>
      <el-table-column
          width="150"
          prop="payTime"
          label="付款时间">
      </el-table-column>
      <el-table-column
          width="60"
          prop="username"
          label="购买人">
      </el-table-column>
      <el-table-column label="支付状态" fixed="right" width="85">
        <template #default="scope">
          <span v-if="scope.row.state === 0" style="color: orange">未支付</span>
          <span v-if="scope.row.state === 1" style="color: green">已支付</span>
          <span v-if="scope.row.state === 2" style="color: red">申请退票中</span>
          <span v-if="scope.row.state === 3" style="color: blue">退票成功</span>
        </template>
      </el-table-column >
      <el-table-column  label="操作"  width="250" fixed="right">
        <template #default="scope">

          <el-popconfirm title="支付？" @confirm="buy(scope.row.id)" v-if="scope.row.state === 0">
            <template #reference>
              <el-button size="mini">支付</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm title="确定取消吗？" @confirm="handleDelete(scope.row.id,scope.row.bookId)" v-if="scope.row.state === 0">
            <template #reference>
              <el-button size="mini" type="danger">取消</el-button>
            </template>
          </el-popconfirm>

            <el-popconfirm title="是否改签？" @confirm="ticketChanging(scope.row)" v-if="scope.row.state === 1">
              <template #reference>
                <el-button size="mini">改签</el-button>
              </template>
            </el-popconfirm>
            <el-popconfirm title="是否退票？" @confirm="refund(scope.row)" v-if="scope.row.state === 1">
              <template #reference>
                <el-button size="mini">退票</el-button>
              </template>
            </el-popconfirm>

          <el-popconfirm title="取消退票？" @confirm="deRefund(scope.row)" v-if="scope.row.state === 2">
            <template #reference>
              <el-button size="mini">取消退票</el-button>
            </template>
          </el-popconfirm>

          <el-button size="mini" @click="checkTicket(scope.row)">查看车票</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="车票" v-model="dialogVisible" width="50%">
      <el-descriptions
          direction="vertical"
          :column="3"
          :size="size"
      >

        <el-descriptions-item label="发车时间" :span=1>
          <div>{{ form.departureTime }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="车号" :span=1>
          <div>{{ form.name }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="到站时间" >
          <div>{{ form.arrivalTime }}</div>
        </el-descriptions-item>

        <el-descriptions-item label="起点站" >
          <div>{{ form.originStation }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="车箱号" >
          <div>{{ form.car }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="终点站" :span=2>
          <div>{{ form.destination }}</div>
        </el-descriptions-item>

        <el-descriptions-item>
        </el-descriptions-item>
        <el-descriptions-item label="座位号" >
          <div>{{ form.seat }}</div>
        </el-descriptions-item>
        <el-descriptions-item>
        </el-descriptions-item>

      </el-descriptions>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">关闭</el-button>
          </span>
      </template>
    </el-dialog>

    <el-dialog title="车票" v-model="dialogVisible1" width="50%">
      <el-descriptions
          direction="vertical"
          :column="7"
          :size="size"
      >

        <el-descriptions-item label="发车时间" :span=1>
          <div>{{ form.departureTime }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="车号" :span=1>
          <div>{{ form.name }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="到站时间" >
          <div>{{ form.arrivalTime1 }}</div>
        </el-descriptions-item>
        <el-descriptions-item>
        </el-descriptions-item>
        <el-descriptions-item label="出站时间" >
          <div>{{ form.departureTime1 }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="车号" :span=1>
          <div>{{ form.name1 }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="到站时间" >
          <div>{{ form.arrivalTime }}</div>
        </el-descriptions-item>

        <el-descriptions-item label="起点站" >
          <div>{{ form.originStation }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="车箱号" >
          <div>{{ form.car }}</div>
        </el-descriptions-item>
        <el-descriptions-item>
        </el-descriptions-item>
        <el-descriptions-item label="中转站" :span=2>
          <div>{{ form.transferStation }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="车箱号" >
          <div>{{ form.car1 }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="终点站" :span=2>
          <div>{{ form.destination }}</div>
        </el-descriptions-item>

        <el-descriptions-item>
        </el-descriptions-item>
        <el-descriptions-item label="座位号" >
          <div>{{ form.seat }}</div>
        </el-descriptions-item>
        <el-descriptions-item>
        </el-descriptions-item>
        <el-descriptions-item>
        </el-descriptions-item>
        <el-descriptions-item>
        </el-descriptions-item>
        <el-descriptions-item label="座位号" >
          <div>{{ form.seat1 }}</div>
        </el-descriptions-item>
        <el-descriptions-item>
        </el-descriptions-item>

      </el-descriptions>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">关闭</el-button>
          </span>
      </template>
    </el-dialog>



    <el-dialog title="车票" v-model="dialogVisible2" width="80%">
      <el-table
          v-loading="loading"
          :data="ChangeDate"
          border
          stripe
          style="width: 100%"
          @selection-change="handleSelectionChange"
      >
        <el-table-column
            prop="id"
            label="ID"
            sortable
        >
        </el-table-column>
        <el-table-column
            prop="name"
            label="车次">
        </el-table-column>
        <el-table-column
            prop="originStation"
            label="起点站">
        </el-table-column>
        <el-table-column
            prop="destination"
            label="终点站">
        </el-table-column>
        <el-table-column
            prop="departureTime"
            label="发车时间"
            width="150">
        </el-table-column>
        <el-table-column
            prop="arrivalTime"
            label="到站时间"
            width="150">
        </el-table-column>
        <el-table-column
            prop="period"
            label="行驶时间">
        </el-table-column>
        <el-table-column
            prop="price"
            label="票价">
        </el-table-column>
        <el-table-column
            prop="ticketRemain"
            label="余票">
        </el-table-column>
        <el-table-column label="操作" width="140" align="center">
          <template #default="scope">
            <el-button type="primary"  size="mini" @click="changeCertain(scope.row)"  v-if="scope.row.ticketRemain != 0&&date<scope.row.timeString">改签</el-button>
            <el-alert title="暂时无票" type="info" center show-icon :closable="false" v-if="scope.row.ticketRemain === 0"/>
            <el-alert title="已发车" type="warning"  center show-icon :closable="false" v-if="date>scope.row.timeString&&scope.row.ticketRemain != 0"/>
          </template>
        </el-table-column>
      </el-table>

      <div style="margin: 10px 0">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="ChangeToTal">
        </el-pagination>
      </div>
    </el-dialog>


  </div>
</template>

<script>


import request from "@/utils/request";
import moment from "moment";

export default {
  name: 'Order',
  components: {

  },
  data() {
    return {
      user: {},
      loading: true,
      form: {},
      dialogVisible1: false,
      dialogVisible: false,
      dialogVisible2:false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      t:'',
      ChangeDate:[],
      ChangeToTal:0,
      ChangeId:0,
      aDate:null,
      date:null
    }
  },
  created() {


    this.aDate=new Date()
    this.date=moment(this.aDate).format("YYYY-MM-DD hh:mm:ss")
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    // 请求服务端，确认当前登录用户的 合法信息
    request.get("/user/" + this.user.id).then(res => {
      if (res.code === '0') {
        this.user = res.data
      }
    })

    this.load()
  },
  methods: {
    /*
    /安全性，有空再改
    */

    refund(row){
      row.state=2;
      request.put("/order", row).then(res => {})
    },

    deRefund(row){

      row.state=1;
      request.put("/order", row).then(res => {})
    },

    ticketChanging(row){
      if(1){
        this.ChangeId=row.id
        request.get("/book/change", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            name:row.name,
            id:row.bookId
          }
        }).then(res => {
          this.loading = false
          this.ChangeDate = res.data.records
          this.ChangeToTal = res.data.total
        })
        this.dialogVisible2=true;
      }

      // row.state=2;
      // request.put("/order", row).then(res => {})
    },
    buy(id){
      request.get("/order/buy/" + id).then(res => {
              // 请求成功跳转沙箱支付的页面
              window.open(res.data)
            })
    },
    load() {
      this.loading = true
      request.get("/order", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          user:this.user.id
        }
      }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    add() {
      this.dialogVisible = true
      this.form = {}
      if (this.$refs['upload']) {
        this.$refs['upload'].clearFiles()  // 清除历史文件列表
      }
    },
    save() {
      if (this.form.id) {  // 更新
        request.put("/order", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }  else {  // 新增
        request.post("/order", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }

          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }
    },
    // handleEdit(row) {
    //   this.form = JSON.parse(JSON.stringify(row))
    //   this.dialogVisible = true
    //   this.$nextTick(() => {
    //     if (this.$refs['upload']) {
    //       this.$refs['upload'].clearFiles()  // 清除历史文件列表
    //     }
    //   })
    // },

    checkTicket(row) {
      this.ChangeId=row.id
      this.form = JSON.parse(JSON.stringify(row))
      if(this.form.bookId1===0)
        this.dialogVisible = true
      else
        this.dialogVisible1 = true
      // this.$nextTick(() => {
      //   if (this.$refs['upload']) {
      //     this.$refs['upload'].clearFiles()  // 清除历史文件列表
      //   }
      // })
    },

    changeCertain(row){
        request.get("/order/change",{
          params: {
            bookId:row.id,
            OrderId:this.ChangeId
          }
        })
      this.dialogVisible2=false;
      this.load()
    },

    handleDelete(id,bookId) {
      console.log(id)
      request.get("/order/checkdelete/" + bookId).then(res => {})
      request.delete("/order/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "已取消订票"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
      })
    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>
