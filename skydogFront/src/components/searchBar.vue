<template>
  <div class="searchBar">
    <!-- logo -->
    <div class="logo">
      <img src="@/assets/img/logo.png" @click="gotoHome" alt="" />
    </div>
    <div
      class="shopName"
      v-if="$route.path == '/productdetail'"
      @click="shopVisible = true"
    >
      <span>{{ shops.userName }}</span>
    </div>
    <div class="search">
      <el-select v-model="select" placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-input v-model="input" placeholder="输入内容搜索"></el-input>
      <el-button
        type="primary"
        @click="toSearch"
        v-track="{
          triggerType: 'click',
          currentUrl: $route.path,
          searchKeyword: input,
          actionType: 'search-click',
        }"
        >搜索</el-button
      >
    </div>
    <el-dialog
      :visible.sync="shopVisible"
      width="80%"
      :before-close="handleClose"
      center
    >
      <el-table :data="products" 
      style="width: 100%" 
      border>
        <el-table-column fixed 
          prop="productName" 
          label="商品名称" 
          width="300">
        </el-table-column>

        <el-table-column 
          prop="productSale" 
          label="销量(件)" 
          width="120">
        </el-table-column>
        <el-table-column 
          prop="productGrade"
          label="评分"
          width="120">
        </el-table-column>
        <el-table-column 
          prop="clickRate" 
          label="点击量" 
          width="120">
        </el-table-column>
        <el-table-column 
        prop="favorRate" 
          label="好评率" 
          width="120">
        </el-table-column>
        <el-table-column
          label="图片" 
          width="120"> 
          <template slot-scope="scope">
            <img :src="scope.row.img.img1" style="width:40px;height:40px;" alt="">
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
              <el-button @click="shopVisible = false">关 闭</el-button>
              <el-button type="danger" v-if="shopCollectEd" @click="delShopCollect">
                取消收藏
              </el-button>
              <el-button type="primary" v-if="!shopCollectEd" @click="shopCollect">
                收藏店铺
              </el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script >
import axios from "axios";
export default {
  name: "searchBar", 
  props: ["sel", "ipt", "shop"],
  data() {
    return {
      options: [
        {
          value: "商品",
          label: "商品",
        },
        {
          value: "商家",
          label: "商家",
        },
      ],
      select: this.sel || "商品",
      input: this.ipt || null,
      shops: "",
      shopVisible: false,
      products: [],
      shopCollectEd:false,
    };
  },
  created() {
    if (this.$route.path == "/productdetail") {
      this.shops = this.shop 

    }
  },
  methods: {
    gotoHome() {
      this.$router.push("/");
    },
    toSearch() {
      console.log(this.select, this.input, this.$route.path);
      let that = this;
      // 子组件传值给父组件
      // 搜索栏 --> 搜索页面
      this.$emit("changeInput", this.input);
      this.$router.push({
        path: "/search",
        query: {
          select: this.select,
          input: this.input,
        },
      });
    },
    // 获取店铺信息
    getShopByID() {
      axios({
        url: "/product/getMyProduct", //请求的后台接口
        method: "post",
        params: {
          userId: this.shop.userId,
        },
      }).then((res) => {
        this.products = res.data.data;
        this.getShopCollected()
      });
    },
    // 查询店铺是否被收藏
    getShopCollected(){
      // axios({
      //   url: "/collect/getMyStore/"+this.$cookies.get('token'), //请求的后台接口
      //   method: "get",
        
      // }).then((res) => {
        
      //   let collectedShop = res.data.data;
      //   console.log(res);
      //   // for(let i = 0;i < collectedShop.length;i++){
      //   //   if(collectedShop[i].product.productDec == this.p)
      //   // }
      // });
    },
    // 关闭弹框
    handleClose(done) {
      done();
    },
    // 删除商家收藏
    delShopCollect(){
      this.shopCollectEd = false
      console.log(this.shop);
      axios({
        url: "/collect/deleteStore/"+this.$cookies.get("token")+'&&'+this.shop.userId, //请求的后台接口
        method: "get",
      }).then((res) => {
        console.log(res);
        
      });
    },
    // 收藏商家
    shopCollect(){
      this.shopCollectEd = true
      console.log(this.shops);
      axios({
        url: "/collect/addStore", //请求的后台接口
        method: "post",
        data: {
          userId:this.$cookies.get("token"),
          sellerId: this.shop.userId,
          collectTime:(new Date()).getTime()
        },
      }).then((res) => {
        if(res.data.code == 202){
          this.$message({
          message:'添加成功',
          type:'success'
        })
        }
        
        console.log(res);

      });
    }
  },
  mounted(){
    this.shops = this.shop
  },
  watch:{
    shop:{
      handler(newValue){
        this.shops = JSON.parse(JSON.stringify(newValue))
      this.getShopByID();

      }
    }
  }
};
</script>

<style>
.searchBar {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  margin: 10px 0 10px 0;
}
.searchBar .logo img {
  width: 100px;
  margin-right: 20px;
}
.searchBar .logo img:hover {
  cursor: pointer;
}
.searchBar .shopName {
  text-align: left;
  margin-right: 20px;
  overflow: hidden;
  line-break: anywhere;
  width: 125px;
  line-height: 20px;
  font-size: 14px;
  white-space: nowrap;
}
.searchBar .shopName:hover {
  color: red;
  cursor: pointer;
}
.searchBar .shopName:active {
  font-size: 14px;
  color: gray;
}
.searchBar .search {
  display: flex;
  /* flex:1; */
  align-items: center;

  border: 1px cornflowerblue solid;
  border-radius: 30px;
  width: 700px;
  height: 42px;
  background-color: #fff;
}
.searchBar .search .el-input--suffix .el-input__inner {
  border: 1px solid #ffffff;
  border-radius: 30px 0 0 30px;
}
.searchBar .search .el-select:hover .el-input__inner {
  border: 1px solid #ffffff;
}
.searchBar .search .el-select:acitve .el-input__inner {
  border: 1px solid #ffffff;
}

.searchBar .search .el-button--primary {
  border-radius: 0 30px 30px 0;
}
.searchBar .search .el-input__inner {
  border: 1px solid #fff;
}
.searchBar .el-table__row .cell{
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
}
</style>
