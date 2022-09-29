<template>
  <div class="payOrder">
    <div class="top">
      <TopBar></TopBar>
    </div>
    <div class="payorder">
      <div class="steps">
        <div class="logo">
          <img src="@/assets/img/logo.png" @click="gotoHome" alt="" />
        </div>
        <div class="step">
          <div class="one">
            <div
              class="item"
              :style="
                order.payStatu >= 1
                  ? 'background-color: cornflowerblue;color: white;border: 1px cornflowerblue solid;'
                  : 'background-color: white;color: black;border: 1px black solid;'
              "
            >
              1
            </div>
            <div class="stepItem">拍下商品</div>
          </div>
          <div class="line"></div>
          <div class="two">
            <div
              class="item"
              :style="
                order.payStatu >= 2
                  ? 'background-color: cornflowerblue;color: white;border: 1px cornflowerblue solid;'
                  : 'background-color: white;color: black;border: 1px black solid;'
              "
            >
              2
            </div>
            <div class="stepItem">付款到支付宝</div>
          </div>
          <div class="line"></div>
          <div class="thr">
            <div
              class="item"
              :style="
                order.payStatu >= 3
                  ? 'background-color: cornflowerblue;color: white;border: 1px cornflowerblue solid;'
                  : 'background-color: white;color: black;border: 1px black solid;'
              "
            >
              3
            </div>
            <div class="stepItem">确认收货</div>
          </div>
          <div class="line"></div>
          <div class="four">
            <div
              class="item"
              :style="
                order.payStatu >= 4
                  ? 'background-color: cornflowerblue;color: white;border: 1px cornflowerblue solid;'
                  : 'background-color: white;color: black;border: 1px black solid;'
              "
            >
              4
            </div>
            <div class="stepItem">评价</div>
          </div>
        </div>
      </div>
      <div class="adddiv">
        <div class="heard">
          <h4>选择收货地址</h4>
        </div>
        <div class="adds">
          <div
            class="address"
            v-for="item in adds"
            :key="item.key"
            @click="choice(item)"
            :style="
              checked == item.addressId
                ? 'border:2px solid cornflowerblue;'
                : 'border:2px solid black'
            "
          >
            <div class="zone">
              <span>{{ item.zone }}</span>
              <span>({{ item.consigneeName }})</span>
            </div>
            <div class="fulladd">
              <span>{{ item.fullAddress }}</span>

              <span>{{ item.consigneePhone }}</span>
            </div>
            <div class="choiced" v-if="checked == item.addressId">
              <span @click="dialogVisible = true">修改</span>
              <img src="@/assets/img/choiced.png" alt="" />
            </div>
          </div>
        </div>
      </div>
      <el-dialog
        title="地址修改"
        :visible.sync="dialogVisible"
        width="50%"
        :before-close="handleClose"
      >
        <el-form
          label-position="right"
          label-width="80px"
          :model="formLabelAlign"
        >
          <el-form-item label="地址">
            <el-input v-model="formLabelAlign.zone"></el-input>
          </el-form-item>
          <el-form-item label="收货人">
            <el-input v-model="formLabelAlign.consigneeName"></el-input>
          </el-form-item>
          <el-form-item label="详细地址">
            <el-input v-model="formLabelAlign.fullAddress"></el-input>
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="formLabelAlign.consigneePhone"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="modifyAdd">确 定</el-button>
        </span>
      </el-dialog>

      <div class="checkOrder">
        <div class="heard">
          <h4>确认订单信息</h4>
        </div>
        <div class="formHeard">
          <ul>
            <li class="first"><b>店铺宝贝</b></li>
            <li><b>商品属性</b></li>
            <li><b>单价</b></li>
            <li><b>数量</b></li>
            <li><b>小计</b></li>
          </ul>
        </div>
        <hr />
        <div class="products" v-for="product in products" :key="product.key">
          <div class="productInfo">
            <div class="dec">
              <div class="image">
                <img :src="product.img.img1" alt="" />
              </div>
              <div class="productDec">
                <span>{{ product.productName }}</span>
              </div>
            </div>
            <div class="attribute">
              <div class="scale" v-if="product.scale">{{ product.scale }}</div>
              <div class="feature" v-if="product.feature">
                {{ product.feature }}
              </div>
              <div v-else><span>无</span></div>
            </div>
            <div class="price">
              <div class="span">
                <span>{{ product.productPrice }}</span>
              </div>
            </div>
            <div class="num">
              <el-input-number
                v-model="product.num"
                @change="handleChange"
                :min="1"
                :max="99"
              ></el-input-number>
            </div>
            <div class="sum">
              <span>{{ (product.productPrice * product.num).toFixed(1) }}</span>
            </div>
          </div>
        </div>
        <hr />
        <div class="finalCheck">
          <div class="totalInfo">
            <div class="sum">
              <span>实付款：</span>
              <span class="chara">￥</span>
              <span class="digtal">{{ sum.toFixed(1) }}</span>
            </div>
            <div class="add">
              <span>寄送至：</span>
              <span class="fulladd">{{ formLabelAlign.fullAddress }}</span>
            </div>
            <div class="userInfo">
              <span>收货人：</span>
              <span class="info">
                {{ formLabelAlign.consigneeName }}
                &nbsp;
                {{ formLabelAlign.consigneePhone }}
              </span>
            </div>
          </div>
          <div class="check">
            <button @click="cancel">取消</button>
            <button
              @click="submit"
              v-track="{
                triggerType: 'click',
                currentUrl: $route.path,
                productId2: products,
                actionType: 'buy-click',
              }"
            >
              提交订单
            </button>
          </div>
        </div>
      </div>
      <el-dialog
        :visible.sync="flashVisible"
        width="30%"
        :before-close="flashClose"
        center
      >
        <span>订单正在生成，请勿重复下单</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="flashVisible = false">关 闭</el-button>
          <el-button type="primary" style="width: 110px" @click="checkPay">
            查询订单支付状态
          </el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script >
import TopBar from "@/components/TopBar.vue";
import axios from "axios";
export default {
  name: "",
  data() {
    return {
      productId: "",
      scale: "",
      feature: "",
      adds: [],
      checked: 10001,
      idx: 0,
      curshow: true,
      num: 1,
      products: [],
      sum: 100,
      dialogVisible: false,
      formLabelAlign: {
        addressId: "",
        zone: "",
        consigneeName: "",
        fullAddress: "",
        consigneePhone: "",
      },
      order: {
        curId: "",
        priceSum: 0,
        payStatu: 1,
      },
      flashVisible: false,
      orderIds: [],
      finishNum: 0,
    };
  },
  created() {
    this.products = this.$route.query.products || this.$route.query.checkOrder;
    this.products = JSON.parse(this.products);
    // console.log(this.products);
    this.scale = this.$route.query.scale;
    this.feature = this.$route.query.feature;
    this.whereFrom();
    this.handleChange();
    // this.getProductById();
    this.getAddress();
    // console.log(this.productIds, this.scale, this.feature);
  },
  methods: {
    submits() {},
    getProductById() {
      console.log(this.productIds);
      this.productIds.forEach((product) =>
        axios({
          method: "get",
          url: "/product/queryById/" + product.productId,
        }).then((res) => {
          this.products.push(res.data.data);
          // console.log(this.products)
        })
      );
    },
    // 加载框关闭
    flashClose(done) {
      done();
    },
    // 选择地址
    choice(address) {
      this.formLabelAlign = address;
      this.checked = address.addressId;
      console.log(this.formLabelAlign.addressId);
    },
    // 购物车和商品详情页传递参数格式不一致，需要统一
    whereFrom() {
      if (this.products[0].productDto) {
        let tmpProducts = [];
        this.products.forEach((item) => {
          item.productDto.num = item.productCount;
          tmpProducts.push(item.productDto);
        });
        this.products = tmpProducts;
        console.log(this.products);
      }
    },
    // 计数器数量改变
    handleChange(value) {
      let sum = 0;
      // 计算总金额
      this.products.forEach((product) => {
        sum += product.num * product.productPrice;
        // console.log(product)
      });
      this.sum = sum;
    },
    // 跳转到主页
    gotoHome() {
      this.$router.push("/");
    },
    // 取消支付
    cancel() {
      this.$router.go(-1);
    },
    // 提交支付
    submit() {
      let resultSum = 0;
      this.finishNum = 0;
      this.products.forEach((value) => {
        axios({
          method: "post",
          url: "/order/add",
          data: {
            userId: this.$cookies.get("token"),
            cartId: value.cartId || null,
            addressId: this.formLabelAlign.addressId,
            productId: value.productId,
            scaleId: value.scaleId || null,
            productCount: value.num,
            orderStatus: "finished",
            createTime: Date.now(),
          },
        }).then((res) => {
          this.orderIds.push(res.data);
          this.order.curId = this.orderIds[0].data;
          resultSum++;
          console.log(this.order);

          this.gotoPay(resultSum);
        });
      });
      this.flashVisible = true;
      this.order.priceSum = this.sum;
    },
    // 验证支付宝支付是否完成
    checkPay(id = this.order.curId) {
      axios({
        method: "get",
        url: "/alipay/requestQuery",
        params: {
          outTradeNo: this.order.curId,
        },
      }).then((res) => {
        console.log(res);
        if (res.data == "【查询返回交易状态】TRADE_SUCCESS") {
          this.$message({
            message: "支付完成",
            type: "success",
          });
          this.order.payStatu = 2;

          this.$message({
              message: "支付完成，即将跳转到订单页面",
              type: "success",
          });
          setTimeout(
            this.$router.push({
              path: "/me/order",
              query: {
                activeName: "已完成",
              },
            })  
          ,3000)
            
          return true;
        }
        if (res.data == "【查询返回交易状态】WAIT_BUYER_PAY") {
          this.$message({
            message: "等待支付",
            type: "success",
          });
        }
        if (res.data == "【查询返回交易状态】null") {
          this.$message({
            message: "请先扫码",
            type: "success",
          });
        }
      });
    },
    // 改变订单状态
    modifyOrderStatus(){
      this.orderIds.forEach(id=>{
        axios({
          method:'post',
          url:'/order/update',
          params:{
            orderId:id,
            orderStatus:'已完成',
            paryTime:Date.now(),
            userId:this.$cookies.get('token')
          }
        }).then(res=>{
          console.log(res)
        })
      })
    },
    // 支付宝支付
    gotoPay(resultSum) {
      if (resultSum == this.products.length) {
        console.log(resultSum, "执行");
        axios({
          method: "post",
          url: "/alipay/orderPay",
          params: {
            outTradeNo: this.order.curId,
            subject: "支付宝支付",
            totalAmount: this.order.priceSum,
          },
        }).then((res) => {
          console.log(res, res.data);
          let win = window.open();
          win.document.write(res.data);
          
          // 验证是否支付完成
          if (this.checkPay(this.order.curId)) {
            
          }
        });
      } else {
        console.log(resultSum, "等待");
      }
    },
    // 关闭弹窗
    handleClose(done) {
      done();
    },
    // 确认地址修改
    confirmModify() {
      // console.log(this.formLabelAlign)
      axios({
        method: "post",
        url: "/address/updateAddress",
        data: this.formLabelAlign,
      });
    },
    // 修改地址
    modifyAdd(done) {
      this.$confirm("确认修改？")
        .then((_) => {
          this.confirmModify();
          done();
        })
        .catch((_) => {});
    },
    // 获取个人地址
    getAddress() {
      axios({
        method: "get",
        url: "/address/getMyAddress/" + this.$cookies.get("token"),
      }).then((res) => {
        // console.log(res.data.data)
        this.adds = res.data.data;
        this.checked = this.adds[0].addressId;
        this.formLabelAlign = this.adds[0];
      });
    },
  },
  components: { TopBar },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  font-size: 12px;
}
.payOrder .payorder {
  margin: 0 100px 0 100px;
  text-align: left;
}
/* 上部logo和步骤 */
.payOrder .steps {
  display: flex;
  justify-content: space-around;
}
.payOrder .steps .logo {
  width: 100px;
  flex: 50%;
}
h4 {
  font-size: 16px;
  margin: 10px 0;
}
hr {
  font-size: 16px;
  margin: 10px 0;
}
.payOrder .steps .logo img {
  width: 100px;
}
.payOrder .steps .step {
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex: 50%;
  text-align: center;
}

.payOrder .steps .step .item {
  border: 1px solid black;
  border-radius: 50%;
  line-height: 20px;
  width: 20px;
  margin: auto;
  background-color: cornflowerblue;
  color: white;
  border: 1px cornflowerblue solid;
}
.payOrder .steps .step .item:hover {
  cursor: pointer;
}
.payOrder .steps .step .line {
  height: 2px;
  width: 100px;
  background-color: rgb(40, 47, 134);
}
/* 地址栏 */
/* 触发BFC抵消float带来的内容塌陷 */
.payOrder .adddiv {
  overflow: auto;
}
.payOrder .adddiv .adds .address {
  width: 20%;
  float: left;
  border: 2px solid black;
  margin: 5px 5px 0 0;
  padding: 5px;
  height: 80px;
}
.payOrder .adddiv .adds .address .zone {
  display: flex;
}
.payOrder .adddiv .adds .address .zone span:nth-of-type(1) {
  width: 175px;
  white-space: nowrap;
  overflow: hidden;
  display: block;
  text-overflow: ellipsis;
}
.payOrder .adddiv .adds .address:hover {
  cursor: pointer !important;
  border: 2px solid cornflowerblue !important;
}
.payOrder .adddiv .adds .address .fulladd span:nth-of-type(1) {
  white-space: nowrap;
  overflow: hidden;
  width: 225px;
  text-overflow: ellipsis;
  display: block;
}

.payOrder .adddiv .adds .address .choiced {
  display: flex;
  align-content: center;
  justify-content: space-between;
}
.payOrder .adddiv .adds .address .choiced img {
  width: 20px;
}
.payOrder .adddiv .adds .address .choiced span:hover {
  color: red;
}
.payOrder .adddiv .adds .address .choiced span:active {
  color: gray;
}
/* 确认订单信息 */
.payOrder .checkOrder .formHeard ul {
  list-style: none;
  display: flex;
  justify-content: space-around;
  font-size: 16px;
}
.payOrder .checkOrder .formHeard ul li {
  float: left;
  width: 200px;
  text-align: center;
}
.payOrder .checkOrder .formHeard ul .first {
  width: 400px;
}
.payOrder .checkOrder .products {
  background-color: rgb(242, 247, 255);
  margin: 5px 0;
  padding: 5px 0;
}
.payOrder .checkOrder .products .productInfo {
  display: flex;
  justify-content: space-around;
}
.payOrder .checkOrder .products .productInfo .dec {
  display: flex;
  width: 400px;
}
.payOrder .checkOrder .products .productInfo .dec .image {
  width: 80px;
  height: 80px;
}
.payOrder .checkOrder .products .productInfo .dec img {
  width: 80px;
}
.payOrder .checkOrder .products .productInfo .dec .productDec {
  width: 320px;
  height: 40px;
  line-height: 20px;
  font-size: 12px;
  /* white-space: nowrap; */
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  /* 设置伸缩盒子的子元素排列方式--从上到下垂直排列 */
  -webkit-box-orient: vertical;
  /* 显示的行 */
  -webkit-line-clamp: 2;
  margin-left: 5px;
}
.payOrder .checkOrder .products .productInfo .attribute {
  width: 200px;
  text-align: center;
}
.payOrder .checkOrder .products .productInfo .price {
  width: 200px;
  text-align: center;
}
.payOrder .checkOrder .products .productInfo .num {
  width: 200px;
  text-align: center;
}
.payOrder .checkOrder .products .productInfo .sum {
  width: 200px;
  text-align: center;
}
/* 确认信息 */
.payOrder .checkOrder .finalCheck {
  float: right;
  margin: 20px 0;
  /* width:400px; */
  text-align: right;
}
.payOrder .checkOrder .finalCheck .totalInfo {
  padding: 5px;
  border: 2px cornflowerblue solid;
  margin-bottom: 20px;
}
.payOrder .checkOrder .finalCheck .sum .chara,
.payOrder .checkOrder .finalCheck .sum .digtal {
  font-size: 25px;
}
.payOrder .checkOrder .finalCheck .sum .digtal {
  color: cornflowerblue;
}
.payOrder .checkOrder .finalCheck .check button {
  width: 100px;
  height: 30px;
  line-height: 30px;
  font-size: 16px;
}
.payOrder .checkOrder .finalCheck .check button:nth-child(2) {
  margin-left: 10px;
}
.payOrder .dialog-footer .el-button {
  width: 70px;
  height: 35px;
}
</style>
