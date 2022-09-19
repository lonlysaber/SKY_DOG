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
            <el-descriptions-item label="商品编号">{{ searchOrder.productId }}</el-descriptions-item>
            <el-descriptions-item label="客户编号" :span="2">{{ searchOrder.addressId }}</el-descriptions-item>
            <el-descriptions-item label="员工编号">{{ searchOrder.userId }}</el-descriptions-item>
            <el-descriptions-item label="价格（万元）">{{ searchOrder.productPrice }}</el-descriptions-item>
            <el-descriptions-item label="状态">
              <el-tag size="small">{{ searchOrder.orderStatus }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="订单时间">{{ searchOrder.createTime }}</el-descriptions-item>
          </el-descriptions>
        </el-dialog>
      </el-row>
      <!-- 订单信息 -->
      <el-table :data="orderData" border
        highlight-current-row :header-cell-style="{ 'text-align': 'center' }" :cell-style="{ 'text-align': 'center' }"
        style="width: 100%; margin-top: 8px; text-align: center" :default-sort="{ prop: 'date', order: 'descending' }">
        <el-table-column prop="createTime" label="创建时间" sortable width="160">
        </el-table-column>
        <el-table-column prop="orderId" label="订单编号">
        </el-table-column>
        <el-table-column prop="userId" label="买家编号">
        </el-table-column>
        <el-table-column prop="addressId" label="地址编号">
        </el-table-column>
        <el-table-column prop="productId" label="商品编号">
        </el-table-column>
        <el-table-column prop="productDto.productPrice" label="价格" sortable>
        </el-table-column>
        <el-table-column prop="productCount" label="数量">
        </el-table-column>
        <el-table-column prop="payTime" label="支付时间" sortable width="160">
        </el-table-column>
        <el-table-column prop="orderStatus" label="订单状态"
          :filters="[{ text: '待收货', value: '待收货' }, { text: '待发货', value: '待发货' }, 
          { text: '待支付', value: '待支付' },{ text: '已收货', value: '已收货' },{ text: '取消', value: '取消' }]"
         filter-placement="bottom-end">
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
                <el-form-item label="创建时间">
                  <el-input v-model="update_Order.createTime"></el-input>
                </el-form-item>
                <el-form-item label="客户编号">
                  <el-input v-model="update_Order.addressId" disabled></el-input>
                </el-form-item>
                <el-form-item label="商品编号">
                  <el-input v-model="update_Order.productId" disabled></el-input>
                </el-form-item>
                <el-form-item label="销售价格">
                  <el-input v-model="update_Order.productDto.productPrice"></el-input>
                </el-form-item>
                <el-form-item label="销售数量">
                  <el-input v-model="update_Order.productCount"></el-input>
                </el-form-item>
                <el-form-item label="订单状态">
                  <el-select v-model="update_Order.orderStatus">
                    <el-option label="待收货" value="待收货"></el-option>
                    <el-option label="待发货" value="待发货"></el-option>
                    <el-option label="待支付" value="待支付"></el-option>
                    <el-option label="已收货" value="已收货"></el-option>
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
      <el-pagination 
          @size-change="handlesizeChange"
          @current-change="handleCurrentChange"
          :page-size="pageSize" 
          layout="total, sizes, prev, pager, next, jumper" :total="total">
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
        createTime: "",
        orderId: "",
        userId: "",
        addressId: "",
        productPrice: "",
        orderStatus: "",
      },
      orderData: [],
      orderID: "",
      productId: "",
      order: {
        orderId: "",
      },
      update_Order: {
        productDto:{
          productPrice:""
        }
      },
      total: 0,//总条目数
      pageSize: 10,//每页显示条目个数
      currentPage: 1,//当前页数

      dialogSearchVisible: false,  //房源查询结果弹出框
      dialogVisible: false,       //修改表单弹出框
    };
  },
  created() {
    this.getOrder();   //获取订单数据
    this.getEmpData()     //获取员工数据
  },
  methods: {
    handleCurrentChange(val){
        console.log(val);
        this.currentPage = val
        this.getOrder()
    },
    handlesizeChange(val){
        console.log(val);
        this.pageSize = val
        this.getOrder()
    },

    //查询所有订单并且把数据渲染到表格
    getOrder() {
      this.$axios({
        url: "/order/getOrder", //请求订单查询的接口
        method: "post",
        data:{
          currentPage:this.currentPage,
          pageSize:this.pageSize
        }
      })
        .then((res) => {
          console.log(res)
          this.orderData = res.data.data.data;
          this.total = res.data.data.count
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
        this.productId = order.productId
        console.log(this.orderID);
        console.log(this.productId);
        this.$axios({
          url: "/order/delete/" + this.orderID,
          method: "get",
        })
          .then((res) => {
            this.$axios.get(`/house/changeStatusById/${this.productId}`).then((request => {
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