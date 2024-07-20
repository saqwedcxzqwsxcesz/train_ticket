<template>
  <div style="padding: 10px">
    <!--    功能区域-->

    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search1" placeholder="请输入起点站" style="width: 20%" clearable></el-input>
      <el-input v-model="search2" placeholder="请输入终点站站" style="width: 20%" clearable></el-input>
      <el-date-picker v-model="search3" value-format="YYYY-MM-DD" type="date"  clearable></el-date-picker>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="transfer">换乘</el-button>
      {{ date }}
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
          width="150"
          sortable>
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
          <el-button type="primary"  size="mini" @click="buy(scope.row)"  v-if="scope.row.ticketRemain != 0&&date<scope.row.timeString">订票</el-button>
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
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";
import moment from "moment";

export default {
  name: 'Book',
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
      search3: null,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      filesUploadUrl: "http://" + window.server.filesUploadUrl + ":9090/files/upload",
      ids: [],
      aDate:null,
      date:null,
      min1:null,
      a:null
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
    transfer(){
      this.$router.replace('/transfer')
    },

    buy(bookRow) {
      // this.load()
      // this.loading = true
      request.put("/order/check", bookRow).then(res => {
        this.ticketNow=res.data
      })
      this.load()
      this.loading = true
      // request.get("/order/buy/" + bookRow.id).then(res => {
      //   // 请求成功跳转沙箱支付的页面
      //   window.open(res.data)
      // })
      this.$router.replace('/order')
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
          search3: this.search3
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
