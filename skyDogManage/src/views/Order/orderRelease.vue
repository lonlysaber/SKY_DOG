<template>
  <div class="content-box">
    <div class="container">
      <el-form ref="insertform" :model="insert_Order" label-width="80px">
        <el-form-item label="发布时间">
          <div class="block">
            <span class="demonstration"></span>
            <el-date-picker v-model="insert_Order.buildTime" type="datetime" placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="房源">
          <el-select v-model="insert_Order.houseId" placeholder="请选择房源" clearable>
            <el-option v-for="item in houseData" :key="item.value" :label="item.cellName" :value="item.houseId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户">
          <el-select v-model="insert_Order.clientId" placeholder="请选择用户" clearable>
            <el-option v-for="item in clientData" :key="item.value" :label="item.clientName" :value="item.clientId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="员工">
          <el-select v-model="insert_Order.empId" placeholder="请选择员工" clearable>
            <el-option v-for="item in empData" :key="item.value" :label="item.empName" :value="item.empId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="销售价格(万元)">
          <el-input v-model="insert_Order.salePrice"></el-input>
        </el-form-item>
        <el-form-item label="订单状态">
          <el-select v-model="insert_Order.orderStatus">
            <el-option label="已完成" value="已完成"></el-option>
            <el-option label="待支付" value="待支付"></el-option>
            <el-option label="取消" value="取消"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="option">
          <el-button type="primary" @click="addOrder">添加</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      insert_Order: {
        buildTime: "",
        houseId: "",
        empId: "",
        clientId: "",
        salePrice: "",
        orderStatus: "",
      },
      clientData: [],
      empData: [],
      houseData: []
    };
  },
  created() {
    this.getClientData() //获取客户数据
    this.getEmpData()    //获取员工数据
    this.getHouseData()  //获取房源数据
  },
  methods: {
    //获取用户信息
    getClientData() {
      this.$axios({
        url: "/client/getClient",//请求的后台接口
        method: "get",//get请求方式
      }).then(response => {
        this.clientData = response.data.data
        console.log(this.clientData)
      }).catch(error => {
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

    //获取房屋信息
    getHouseData() {
      this.$axios({
        url: "/house/queryHouse",//请求的后台接口
        method: "get",//get请求方式
      }).then(response => {
        console.log(response.data.data)
        this.houseData = response.data.data
      }).catch(error => {
        //请求失败
      });
    },

    //添加订单
    addOrder() {
      console.log(this.insert_Order);
      this.insert_Order.buildTime = this.formatting(this.insert_Order.buildTime);
      this.$axios.post('/order/add', this.insert_Order).then((request => {
        if (request.data.message === "添加成功") {
          console.log("添加成功！");
          this.$axios({
            url: "/house/changeHouseStatus",//请求的后台接口
            method: "get",//get请求方式
          }).then(response => {
            console.log(response)
          }).catch(error => {
            //请求失败
          });
          this.$message({
            showClose: true,
            message: '添加订单成功！',
            type: 'success'
          });
        }
        if (request.data.message === "添加失败") {
          this.$message({
            showClose: true,
            message: '添加失败!',
            type: 'error'
          });
        }
        console.log(request)
      })).catch((error) => {
        console.log(error);
      })
      //清空输入框
      this.insert_Order.buildTime = ""
      this.insert_Order.clientId = ""
      this.insert_Order.empId = ""
      this.insert_Order.houseId = ""
      this.insert_Order.orderStatus = ""
      this.insert_Order.salePrice = ""
    },
    //重制
    resetForm() {
      //清空输入框
      this.insert_Order.buildTime = ""
      this.insert_Order.clientId = ""
      this.insert_Order.empId = ""
      this.insert_Order.houseId = ""
      this.insert_Order.orderStatus = ""
      this.insert_Order.salePrice = ""
    },

    formatting(time) {
      var time = new Date(time);
      var y = time.getFullYear();
      var m = time.getMonth() + 1;
      var d = time.getDate();
      var h = time.getHours();
      var mm = time.getMinutes();
      var s = time.getSeconds();
      return (
        y +
        "-" +
        this.repair0(m) +
        "-" +
        this.repair0(d) +
        " " +
        this.repair0(h) +
        ":" +
        this.repair0(mm) +
        ":" +
        this.repair0(s)
      );
    },
    repair0(m) {
      return m < 10 ? '0' + m : m
    },

  },
};
</script>

<style lang="scss" scoped>
</style>