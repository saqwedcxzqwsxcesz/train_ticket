<template>
  <div style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add" >新增</el-button>
      <el-popconfirm
          title="确定删除吗？"
          @confirm="deleteBatch"
      >
        <template #reference>
          <el-button type="danger" >批量删除</el-button>
        </template>
      </el-popconfirm>

    </div>

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
          type="selection"
          width="55">
      </el-table-column>
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
<!--      <el-table-column-->
<!--          prop="departureData"-->
<!--          label="发车日期">-->
<!--      </el-table-column>-->
      <el-table-column
          prop="departureTime"
          label="发车时间">
      </el-table-column>
<!--      <el-table-column-->
<!--          prop="arrivalData"-->
<!--          label="到站日期">-->
<!--      </el-table-column>-->
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
          prop="seat"
          label="每车厢座位数">
      </el-table-column>
      <el-table-column
          prop="car"
          label="车厢数">
      </el-table-column>
      <el-table-column
          prop="ticket"
          label="票数">
      </el-table-column>
      <el-table-column label="操作" width="240">
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" @click="handleAdd(scope.row)">新时段</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
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

      <el-dialog title="提示" v-model="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="车组号">
            <el-input v-model="form.name" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="票价">
            <el-input v-model="form.price" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="起点站">
            <el-input v-model="form.originStation" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="终点站">
            <el-input v-model="form.destination" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="发车时间">
            <el-date-picker
                v-model="form.departureTime"
                type="datetime"
                placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="到站时间">
            <el-date-picker
                v-model="form.arrivalTime"
                type="datetime"
                placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="车厢数">
            <el-input v-model="form.car" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="每车厢座位数">
            <el-input v-model="form.seat" style="width: 80%"></el-input>
          </el-form-item>
<!--          <el-form-item label="" style="visibility: hidden">-->
<!--            <el-input v-model="form.ticket" style="width: 80%"></el-input>-->
<!--          </el-form-item>-->

        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
        </template>
      </el-dialog>

      <el-dialog title="新时段" v-model="dialogVisible1" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="发车时间">
            <el-date-picker
                v-model="form.departureTime"
                type="datetime"
                placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="到站时间">
            <el-date-picker
                v-model="form.arrivalTime"
                type="datetime"
                placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save1">确 定</el-button>
          </span>
        </template>
      </el-dialog>

    </div>
  </div>
</template>

<script>


import request from "@/utils/request";
import moment from "moment";

export default {
  name: 'BookManage',
  components: {

  },
  data() {
    return {
      user: {},
      loading: true,
      form: {},
      dialogVisible: false,
      dialogVisible1:false,
      search: '',
      search1: '',
      search2: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      filesUploadUrl: "http://" + window.server.filesUploadUrl + ":9090/files/upload",
      ids: [],
      i:1
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

    buy(bookId) {
      request.get("/order/buy/" + bookId).then(res => {
        // 请求成功跳转沙箱支付的页面
        window.open(res.data)
      })
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
    save1(){
      this.form.id=null;
      request.post("/book", this.form).then(res => {})
      this.dialogVisible1=false
    },

    handleAdd(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible1=true
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
