<template>
  <div class="content-box">
    <div class="container">
      <!-- 根据ID搜索订单 -->
      <el-row>
        <el-input class="input" placeholder="请输入编号" v-model="searchID" clearable>
        </el-input>
        <el-button @click="queryOrderById(searchID)">搜索</el-button>
        <el-dialog :visible.sync="dialogSearchVisible" :modal-append-to-body='false'>
          <el-descriptions title="查询结果" direction="vertical" :column="4" border>
            <el-descriptions-item label="订单编号">{{ searchOrder.orderId }}</el-descriptions-item>
            <el-descriptions-item label="房源编号">{{ searchOrder.houseId }}</el-descriptions-item>
            <el-descriptions-item label="客户编号" :span="2">{{ searchOrder.clientId }}</el-descriptions-item>
            <el-descriptions-item label="员工编号">{{ searchOrder.empId }}</el-descriptions-item>
            <el-descriptions-item label="价格（万元）">{{ searchOrder.salePrice }}</el-descriptions-item>
            <el-descriptions-item label="状态">
              <el-tag size="small">{{ searchOrder.orderStatus }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="订单时间">{{ searchOrder.buildTime }}</el-descriptions-item>
          </el-descriptions>
        </el-dialog>
      </el-row>
      <!-- 订单信息 -->
      <el-table :data="orderData.slice((currentPage - 1) * pagesize, currentPage * pagesize)" border
        highlight-current-row :header-cell-style="{ 'text-align': 'center' }" :cell-style="{ 'text-align': 'center' }"
        style="width: 100%; margin-top: 8px; text-align: center" :default-sort="{ prop: 'date', order: 'descending' }">
        <el-table-column prop="buildTime" label="发布时间" sortable width="160">
        </el-table-column>
        <el-table-column prop="orderId" label="订单编号">
        </el-table-column>
        <el-table-column prop="empId" label="负责员工">
        </el-table-column>
        <el-table-column prop="clientId" label="客户编号">
        </el-table-column>
        <el-table-column prop="houseId" label="房源编号">
        </el-table-column>
        <el-table-column prop="salePrice" label="房屋总价(万元)" sortable>
        </el-table-column>
        <el-table-column prop="orderStatus" label="订单状态"
          :filters="[{ text: '已完成', value: '已完成' }, { text: '待支付', value: '待支付' }, { text: '取消', value: '取消' }]"
          :filter-method="filterTag" filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.orderStatus === '已完成' ? 'success' : (scope.row.orderStatus === '待支付' ? 'warning' : 'danger')"
              disable-transitions>{{
                  scope.row.orderStatus
              }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
          <template slot-scope="scope">
            <el-button @click="updateOrder(scope.row)" size="small">修改</el-button>&nbsp;
            <!-- 订单修改 -->
            <el-dialog title="修改信息" :visible.sync="dialogVisible" width="30%" :modal-append-to-body='false'>
              <el-form ref="updateform" :model="update_Order" label-width="80px">
                <el-form-item label="发布时间">
                  <el-input v-model="update_Order.buildTime"></el-input>
                </el-form-item>
                <el-form-item label="负责员工">
                  <el-select v-model="update_Order.empId" placeholder="请选择员工" clearable>
                    <el-option v-for="item in empData" :key="item.value" :label="item.empName" :value="item.empId">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="客户编号">
                  <el-input v-model="update_Order.clientId" disabled></el-input>
                </el-form-item>
                <el-form-item label="房源编号">
                  <el-input v-model="update_Order.houseId" disabled></el-input>
                </el-form-item>
                <el-form-item label="销售价格">
                  <el-input v-model="update_Order.salePrice"></el-input>
                </el-form-item>
                <el-form-item label="订单状态">
                  <el-select v-model="update_Order.orderStatus">
                    <el-option label="已完成" value="已完成"></el-option>
                    <el-option label="待支付" value="待支付"></el-option>
                    <el-option label="取消" value="取消"></el-option>
                  </el-select>
                </el-form-item>
              </el-form>
              <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateConfirm()">确 定</el-button>
              </span>
            </el-dialog>
            <!-- 订单删除 -->
            <el-button @click="delOrder(scope.row)" type="danger" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="size_change" @current-change="current_change" :current-page="currentPage"
        :page-sizes="[9, 20, 30]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper" :total="orderData.length
        ">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      empData: [],  //员工数据
      searchID: "",
      searchOrder: {
        buildTime: "",
        orderId: "",
        empId: "",
        clientId: "",
        salePrice: "",
        orderStatus: "",
      },
      orderData: [],
      orderID: "",
      houseID: "",
      order: {
        orderId: "",
      },
      update_Order: {
        buildTime: "",
        orderId: "",
        empId: "",
        clientId: "",
        salePrice: "",
        orderStatus: "",
      },
      total: 0,//总条目数
      pagesize: 9,//每页显示条目个数
      currentPage: 1,//当前页数

      dialogSearchVisible: false,  //房源查询结果弹出框
      dialogVisible: false,       //修改表单弹出框
    };
  },
  created() {
    this.getorderData();   //获取订单数据
    this.getEmpData()     //获取员工数据
  },
  methods: {
    //订单状态筛选
    filterTag(value, row) {
      return row.orderStatus === value;
    },

    //监听 pagesize 改变的事件
    size_change(newSize) {
      this.pagesize = newSize
    },
    //监听 页码值 改变的事件
    current_change(newPage) {
      this.currentPage = newPage
    },

    //查询所有订单并且把数据渲染到表格
    getorderData() {
      this.$axios({
        url: "/order/getAllOrder", //请求订单查询的接口
        method: "get", //通过get方法
      })
        .then((res) => {
          console.log(res)
          this.orderData = res.data.data;
        })
        .catch((err) => {
          //请求失败
        });
    },

    //获取员工信息
    getEmpData() {
      this.$axios({
        url: "/emp/getEmp",//请求的后台接口
        method: "get",//get请求方式
      }).then(response => {
        this.empData = response.data.data
        console.log(this.empData)
      }).catch(error => {
        //请求失败
      });
    },

    //根据ID搜索订单
    queryOrderById() {
      console.log(this.searchID)
      this.$axios.get(`/order/queryById/${this.searchID}`).then((request => {
        console.log(request);
        this.searchOrder = request.data.data
        console.log(this.searchOrder);
        this.dialogSearchVisible = true
        this.$message({
          showClose: true,
          message: '查询成功',
          type: 'success'
        });
      })).catch((error) => {
        console.log(error);
      })
      this.searchID = ""
    },

    //通过弹出框进行数据的提交修改
    updateOrder(order) {
      this.update_Order = order;
      console.log(this.update_Order);
      this.dialogVisible = true;
    },

    //绑定表单提交
    updateConfirm() {
      this.$axios({
        url: "/order/update",
        method: "post",
        data:
          this.update_Order,
        header: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      }).then(res => {
        if (res.data != null) {
          this.dialogVisible = false;
        };
        this.$message({
          showClose: true,
          message: '修改成功！',
          type: 'success'
        });
      })
    },

    //绑定按钮进行订单的删除根据订单编号
    delOrder(order) {
      this.$confirm('此操作将永久删除该订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.orderID = order.orderId + "";
        this.houseID = order.houseId
        console.log(this.orderID);
        console.log(this.houseID);
        this.$axios({
          url: "/order/delete/" + this.orderID,
          method: "get",
        })
          .then((res) => {
            this.$axios.get(`/house/changeStatusById/${this.houseID}`).then((request => {
            })).catch((error) => {
            })
            this.getorderData(); //重新刷新页面的数据
          })
          .catch((err) => { });
        this.$message({
          showClose: true,
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          showClose: true,
          type: 'info',
          message: '已取消删除'
        });
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.input {
  width: 200px;
}
</style>