<template>
  <div style="padding: 10px">
    <!--    功能区域-->
    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search1" placeholder="请输入起点站" style="width: 15%" clearable></el-input>
      <el-input v-model="search4" placeholder="请输入中转站" style="width: 15%" clearable></el-input>
      <el-input v-model="search2" placeholder="请输入终点站" style="width: 15%" clearable></el-input>
      <el-date-picker v-model="search3" value-format="YYYY-MM-DD" type="date"  clearable></el-date-picker>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
      {{ date }}
    </div>
    <el-table label="换乘票" width="1000"
        v-loading="loading"
        :data="tableData"
        :span-method="objectSpanMethod"
        border
        stripe
        style="width: 100%"
        @selection-change="handleSelectionChange"
    >

      <el-table-column
          prop="transferId"
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
      <!--      <el-table-column-->
      <!--          prop="departureData"-->
      <!--          label="发车日期">-->
      <!--      </el-table-column>-->
      <el-table-column
          prop="departureTime"
          label="发车时间"
          width="150">
      </el-table-column>
      <!--      <el-table-column-->
      <!--          prop="arrivalData"-->
      <!--          label="到站日期">-->
      <!--      </el-table-column>-->
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
          prop="totalPrice"
          label="总价">
      </el-table-column>
      <el-table-column
          prop="ticketRemain"
          label="余票">
      </el-table-column>
      <el-table-column
          prop="minTicket"
          label="总余票">
      </el-table-column>


      <el-table-column label="操作" width="140" align="center">
        <template #default="scope">
          <el-button type="primary"  size="mini" @click="buy(scope.row)"  v-if="scope.row.minTicket != 0&&date<scope.row.timeString">订票</el-button>
          <el-alert title="暂时无票" type="info" center show-icon :closable="false" v-if="scope.row.minTicket === 0"/>
          <el-alert title="已发车" type="warning"  center show-icon :closable="false" v-if="date>scope.row.timeString&&scope.row.ticketRemain != 0"/>
        </template>
      </el-table-column>
    </el-table>

<!--    </div>-->
  </div>

</template>

<script>


import request from "@/utils/request";
import moment from "moment";

export default {
  name: 'Transfer',
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
      search4:'',
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
    this.date=moment(this.aDate).format("YYYY-MM-DD")+" "+this.aDate.getHours().toString()+":"
    if(this.aDate.getMinutes().toString().length==1)
      this.date+="0"
    this.date+=this.aDate.getMinutes().toString()+":"+this.aDate.getSeconds()

    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    // 请求服务端，确认当前登录用户的 合法信息
    request.get("/user/" + this.user.id).then(res => {
      if (res.code === '0') {
        this.user = res.data
      }
    })
    //
    // this.load()
  },
  methods: {


    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        if (rowIndex % 2 === 0) {
          return {
            rowspan: 2,
            colspan: 1,
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      } else if (columnIndex === 8) {
        if (rowIndex % 2 === 0) {
          return {
            rowspan: 2,
            colspan: 1,
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      }else if (columnIndex === 10) {
        if (rowIndex % 2 === 0) {
          return {
            rowspan: 2,
            colspan: 1,
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      }else if (columnIndex === 11) {
        if (rowIndex % 2 === 0) {
          return {
            rowspan: 2,
            colspan: 1,
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      }
    },


    buy(bookRow) {
      // this.load()
      // this.loading = true
      request.put("/order/checkTransfer", bookRow).then(res => {
        this.ticketNow=res.data
      })
      // this.load()
      // this.loading = true
      // this.$router.replace('/order')
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
      if(this.search1===''){
        this.$message({
          type: "error",
          message: "缺少起点站"
        })
        return;
      }
      if(this.search2===''){
        this.$message({
          type: "error",
          message: "缺少终点站"
        })
        return;
      }
      this.loading = true
      request.get("/book/doubleCheck", {
        params: {
          search1: this.search1,
          search2: this.search2,
          search3: this.search3,
          search4: this.search4
        }
      }).then(res => {
        this.loading = false
        this.tableData = res.data
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
