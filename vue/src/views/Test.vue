<template>
  <div style="padding: 10px">
    <!--    功能区域-->
    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search1" placeholder="请输入起点站" style="width: 20%" clearable></el-input>
      <el-input v-model="search2" placeholder="请输入终点站站" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table
        v-loading="loading"
        :data="tableData"
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
          label="名称">
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
          prop="departureData"
          label="发车日期">
      </el-table-column>
      <el-table-column
          prop="departureTime"
          label="发车时间">
      </el-table-column>
      <el-table-column
          prop="arrivalData"
          label="到站日期">
      </el-table-column>
      <el-table-column
          prop="arrivalTime"
          label="到站时间">
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
      <el-table-column label="操作" width="140">
        <template #default="scope">
          <el-button type="primary" size="mini" @click="dddd(scope.row)">购买</el-button>
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

<!--      <el-dialog title="提示" v-model="dialogVisible" width="30%">-->
<!--        <el-form :model="form" label-width="120px">-->
<!--          <el-form-item label="名称">-->
<!--            <el-input v-model="form.name" style="width: 80%"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="价格">-->
<!--            <el-input v-model="form.price" style="width: 80%"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="起点站">-->
<!--            <el-input v-model="form.originStation" style="width: 80%"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="">-->
<!--            <el-date-picker v-model="form.createTime" value-format="YYYY-MM-DD" type="date" style="width: 80%" clearable></el-date-picker>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
<!--        <template #footer>-->
<!--          <span class="dialog-footer">-->
<!--            <el-button @click="dialogVisible = false">取 消</el-button>-->
<!--            <el-button type="primary" @click="save">确 定</el-button>-->
<!--          </span>-->
<!--        </template>-->
<!--      </el-dialog>-->

    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: 'Test',
  components: {

  },
  data() {
    return {
      user: {},
      loading: true,
      form: {},
      dialogVisible: false,
      search: '',
      search1: '',
      search2: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      filesUploadUrl: "http://" + window.server.filesUploadUrl + ":9090/files/upload",
      ids: [],
      checkData:[],
      checkTotal:0,
      ticketNow:0
    }
  },
  created() {
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
    dddd(bookRow){
      request.put("/order/check", bookRow).then(res => {
        this.ticketNow=res.data
      })
      this.load()
      this.loading = true
      // request.put("/order/check", {
      //   params: {
      //     search1: bookRow
      //   }
      // }).then(res => {
      //   this.ticketNow=res.data
      // })
      // request.get("/order/check", {
      //   params: {
      //     id: bookRow.id,
      //     ticket:bookRow.ticket,
      //     userId: bookRow.userId,
      //     name: bookRow.name,
      //     price: bookRow.price,
      //     departureTime: bookRow.departureTime,
      //     arrivalTime: bookRow.arrivalTime,
      //     destination: bookRow.destination,
      //     originStation: bookRow.originStation,
      //     car: bookRow.car,
      //     seat: bookRow.seat,
      //     period: bookRow.period,
      //     departureData: bookRow.departureData,
      //     arrivalData: bookRow.arrivalData,
      //     ticketRemain:bookRow.ticketRemain,
      //     ticketNow:bookRow.ticketNow
      //   }
      // }).then(res => {
      //   this.ticketNow=res.data
      // })
      // for(let i=1;i<=bookRow.ticket;i++){
      //   this.checkList[i]=0;
      // }
      // for(let i=1;i<=this.checkTotal;i++){
      //   this.checkList[this.checkData[i].ticket]=1;
      // }
      // for(let i=1;i<=bookRow.ticket;i++){
      //   if(this.checkList[i]===0){
      //     this.ticketNow=i;
      //     break;
      //   }
      // }
      // bookRow.ticketNow=this.ticketNow;
      // request.put("/book", bookRow).then(res => {})
      // this.rowInsert.bookId=bookRow.id;
      // this.rowInsert.bookId=bookRow.i;
    },
    buy(bookId,rowChange) {


      // rowChange.ticketNow=1
      // request.put("/book",rowChange).then(res => {})

      // request.get("/order/buy/" + bookId).then(res => {
      //   // 请求成功跳转沙箱支付的页面
      //   window.open(res.data)
      // })
    },
    deleteBatch() {
      if (!this.ids.length) {
        this.$message.warning("请选择数据！")
        return
      }
      request.post("/book/deleteBatch", this.ids).then(res => {
        if (res.code === '0') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleSelectionChange(val) {
      this.ids = val.map(v => v.id)   // [{id,name}, {id,name}] => [id,id]
    },
    filesUploadSuccess(res) {
      console.log(res)
      this.form.cover = res.data
    },
    load() {
      this.loading = true
      request.get("/book", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search1: this.search1,
          search2: this.search2,
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
        request.put("/book", this.form).then(res => {
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
        request.post("/book", this.form).then(res => {
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
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(() => {
        if (this.$refs['upload']) {
          this.$refs['upload'].clearFiles()  // 清除历史文件列表
        }
      })

    },
    handleDelete(id) {
      console.log(id)
      request.delete("/book/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
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
