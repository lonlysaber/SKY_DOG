<template>
  <div class="productDetail">
    <div class="top">
      <div class="topbar">
        <topbar></topbar>
      </div>
      <div class="searchbar">
        <searchbar :shop="user"></searchbar>
      </div>
    </div>
    <div class="middle">
      <div class="productInfo">
        <!-- 左侧图片轮播图 -->
        <div class="carousel">
          <el-carousel trigger="click" :autoplay="false">
            <el-carousel-item
              v-for="item in product.img"
              :key="item.key"
              align="middle"
            >
              <img
                :src="item"
                onerror="javascript:this.src='src/assets/logo.png';this.onerror=null"
                alt=""
              />
            </el-carousel-item>
          </el-carousel>
        </div>
        <!-- 右侧商品信息 -->
        <div class="detail">
          <div class="head">
            <h3>{{ product.productName }}</h3>
          </div>
          <hr />
          <div class="price">
            <div class="name">
              <span>价格</span>
            </div>
            <div class="pri">
              <h2>￥{{ product.productPrice }}</h2>
            </div>
          </div>
          <hr />
          <div class="scales">
            <div class="name">
              <span>规格</span>
            </div>
            <div class="scale">
              <template v-if="product.productScale">
                <div
                  class="span"
                  v-for="item in product.scales"
                  :key="item.key"
                >
                  <span @click="clickScale(item, $event)">{{ item }}</span>

                  <span v-if="checkEdScale == item" style="color: red">●</span>
                </div>
              </template>
              <template v-else>
                <span>无</span>
              </template>
            </div>
          </div>
          <hr />
          <div class="features">
            <div class="name">
              <span>特征</span>
            </div>
            <div class="feature">
              <template v-if="product.productFeature">
                <div
                  class="span"
                  v-for="item in product.features"
                  :key="item.key"
                >
                  <span @click="clickFeature(item, $event)">{{ item }}</span>
                  <span v-if="checkEdFeature == item" style="color: red"
                    >●</span
                  >
                </div>
              </template>
              <template v-else>
                <span>无</span>
              </template>
            </div>
          </div>
          <hr />
          <div class="stepnum">
            <div class="name">
              <span>数量</span>
            </div>
            <div class="num">
              <el-input-number
                v-model="num"
                @change="handleChange"
                :min="1"
                :max="99"
              >
              </el-input-number>
            </div>
          </div>
          <hr />
          <div class="subBtn">
            <div class="buy">
              <button @click="buy"
              
            >立即购买</button>
            </div>
            <div class="addCart">
              <button @click="addCart"
              v-track="{
              triggerType: 'click',
              currentUrl: $route.path,
              productId5: product.productId,
              categoryName: product.categoryName,
              actionType: 'cart-click',
            }"
              >加入购物车</button>
            </div>
            <div class="gotoCart">
              <button @click="gotoCart">查看购物车</button>
            </div>
            <div class="collect" @click="collectProduct"
            v-track="{
              triggerType: 'click',
              currentUrl: $route.path,
              productId4: product.productId,
              categoryName: product.categoryName,
              actionType: 'collect-click',
            }"
            >
              <img v-if="!collectEd" src="@/assets/img/collect.png" alt="" title="收藏商品">
              <img v-else src="@/assets/img/collected.png" alt="" title="已收藏">
              
            </div>
          </div>
          <el-dialog
            :visible.sync="collectVisible"
            width="30%"
            :before-close="handleClose"
            center
          >
            <span>商品已收藏</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="collectVisible = false">关 闭</el-button>
              <el-button type="danger" @click="deleteCollect">
                取消收藏
              </el-button>
            </span>
          </el-dialog>
          <el-dialog
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose"
            center
          >
            <span>添加购物车成功</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">关 闭</el-button>
              <el-button type="primary" @click="gotoCart">
                查看购物车
              </el-button>
            </span>
          </el-dialog>
        </div>
        <div class="outList" v-if="product.productStatus=='下架'">
          <img src="@/assets/img/outList.png" alt="">
        </div>
      </div>
      <hr />
      <div class="detailDescript">
        <h4>商品介绍</h4>
        <div class="descript">
          <template v-if="true">
            <div
              class="desitem"
              v-for="(value, key) in product.productDetail"
              :key="value.key"
            >
              <span>{{ key }}：{{ value }}</span>
            </div>
          </template>
          <template v-else>
            <span>暂无商品介绍</span>
          </template>
        </div>
      </div>
      <div class="bigImg">
        <h4>商品大图</h4>
        <div class="imgs">
          <div class="imgItem" 
          v-for="item in product.img" 
          :key="item.key">
            <img :src="item"
              onerror="this.style.display='none';this.onerror=null"
              alt=""
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script >
import topbar from "@/components/TopBar.vue";
import searchbar from "@/components/searchBar.vue";
import axios from "axios";

export default {
  components: {
    topbar,
    searchbar,
  },
  name: "",
  data() {
    return {
      productId: "",
      product: {
        productId: "10003",
        productName:
          "PINKPINEAPPLE粉红菠萝女装短外套女春秋新款2022年学生开衫夹克上衣女士风衣外套短款 PPA芥末黄 165/88A（L）",
        productPrice: "279",
        img: {
          img1: "https://img10.360buyimg.com/n5/s450x450_jfs/t1/101838/18/31132/98953/62d65f90E37aaf946/143951520738aee4.jpg!cc_450x450.avif",
          img2: "https://img10.360buyimg.com/n5/s450x450_jfs/t1/101838/18/31132/98953/62d65f90E37aaf946/143951520738aee4.jpg!cc_450x450.avif",
          img3: "https://img10.360buyimg.com/n5/s450x450_jfs/t1/101838/18/31132/98953/62d65f90E37aaf946/143951520738aee4.jpg!cc_450x450.avif",
          img4: "https://img10.360buyimg.com/n5/s450x450_jfs/t1/101838/18/31132/98953/62d65f90E37aaf946/143951520738aee4.jpg!cc_450x450.avif",
          img5: "https://img10.360buyimg.com/n5/s450x450_jfs/t1/101838/18/31132/98953/62d65f90E37aaf946/143951520738aee4.jpg!cc_450x450.avif",
        },
      },
      scales: ["1m", "2m", "3m", "4m", "5m"],
      features: ["红色", "黑色", "蓝色", "绿色", "灰色"],
      num: 1,
      descript: {},
      checkEdScale: "",
      checkEdFeature: "",
      dialogVisible: false,
      collectVisible:false,
      collectEd:false,
      cartId:'',
      collectId:'',
      user:{},
    };
  },
  created() {
    console.log(this.$route.query.productId)
    this.productId = this.$route.query.productId || "10004";
    this.getProductById();
    
  },
  methods: {
    // 删除图片信息
    deleteImg(key) {
      console.log(key);
      eval(`delete this.product.img.${key}`);
      // console.log(this.product,this.product.img)
    },
    // 查询商品对应的店铺信息
    getUserById(id){
      axios({
        method:'get',
        url:'/user/queryId/'+id
      }).then(res=>{
        this.user = res.data.data.data
      })
    },
    // 查询商品信息
    getProductById() {
      axios({
        method: "get",
        url: "/product/queryById/" + this.productId,
      }).then((res) => {
        // console.log(res);
        this.product = res.data.data;
        this.product.productDetail = JSON.parse(this.product.productDetail);
        delete this.product.img.imgId;
        delete this.product.img.productId;
        delete this.product.img.productName;
        this.queryCollect();
        this.getUserById(res.data.data.userId)
      });
    },
    // 商品信息改变
    handleChange(value) {
      // console.log(value);
    },
    // 商品规格改变
    clickScale(scale, e) {
      // console.log(scale,e);
      // e.target.style.color='red'
      if (this.checkEdScale == scale) {
        this.checkEdScale = "";
      } else {
        this.checkEdScale = scale;
      }
    },
    // 商品特征改变
    clickFeature(feature, e) {
      // console.log(feature,e);
      // e.target.style.color='red'
      if (this.checkEdFeature == feature) {
        this.checkEdFeature = "";
      } else {
        this.checkEdFeature = feature;
      }
    },
    // 点击购买
    buy() {
      let scale = this.checkEdScale || this.scales[0];
      let feature = this.checkEdFeature || this.features[0];
      this.product.num = this.num || 1;
      let products = [];
      products.push(this.product);
      console.log(products);

      this.$router.push({
        path: "payOrder",
        query: {
          products: JSON.stringify(products) ,
          scale: scale,
          feature: feature,
          num: this.num,
        },
      });
      
      // console.log(scale,feature)
    },
    // 加入购物车
    addCart() {
      let scale = this.checkEdScale || this.scales[0];
      let feacture = this.checkEdFeature || this.features[0];
      // console.log(scale,feacture)
      axios({
        method: "post",
        url: "/cart/add",
        data: {
          joinTime: new Date().getTime(),
          productCount: this.num,
          productId: this.product.productId,
          scaleId: null,
          userId: this.$cookies.get("token"),
        },
      }).then((res) => {
        console.log(res);
        this.cartId = res.data.data
        this.dialogVisible = true;
      });
    },
    // 关闭弹框
    handleClose(done) {
      done();
    },
    // 跳转到购物车
    gotoCart() {
      this.dialogVisible = false;
      this.$router.push({
        path: "/cart",
        query: {
          userId: this.$cookies.get("token"),
        },
      });
    },
    // 收藏商品
    collectProduct(){
      if(this.collectEd == false){
        
        axios({
        method:'post',
        url:'/collect/add',
        data:{
          userId:this.$cookies.get('token'),
          productId:this.product.productId,
          collectTime:(new Date()).getTime()
        }
      }).then(res=>{
        // 收藏成功
        console.log(res)
        if(res.data.code == 202){
          this.collectEd = true
          this.collectId = res.data.data
          this.$message({
            message: "收藏成功",
            type: "success",
          });
        }
        if(res.data.code == 203){
          this.collectEd = true
          
        }
      })
      }else{
        this.collectVisible = true
      }
      
    },
    // 删除收藏
    deleteCollect(){
      this.collectEd = false
      axios({
        method:'get',
        url:'/collect/delete/'+this.$cookies.get('token')+'&&'+this.product.productId,
        
        
      }).then(res=>{
        this.$message({
            message: "该商品已取消收藏",
            type: "success",
          });
        // console.log(res)
        this.collectVisible = false
      })
    },
    // 查询收藏情况
    queryCollect(){
      axios({
        method:'get',
        url:'/collect/getMyCollect/'+this.$cookies.get('token')
      }).then(res=>{
        let arr = res.data.data
        for(let i=0;i<arr.length;i++){
          // console.log(arr[i].productId,this.product.productId)
          if(arr[i].productId == this.product.productId){
            this.collectEd = true;
          }
        }
      })
    }
  },
};
</script>

<style>
* {
  margin: 0;
  padding: 0;
}
/* 主体页面 */
.productDetail {
  background-color: rgb(234, 232, 235);
  height: 100%;
  padding-bottom: 10px;
}
/* 中间部分 */
.productDetail .middle {
  margin: 0 100px;
  background-color: #fff;
  border-radius: 18px;
  padding-bottom: 10px;
}
.productDetail .middle > hr {
  margin: 0 24px 10px 24px;
}
.productDetail .productInfo {
  display: flex;
}
/* 左侧轮播图 */
.productDetail .carousel {
  border: 0.5px solid rgba(0, 0, 0, 0.05);
  width: 330px;
  height: 314px;
  overflow: hidden;
  margin: 24px;
  display: block;
  visibility: visible;
}
.productDetail .carousel img {
  width: 100%;
  width: 100%;
  height: 100%;
  display: block;
}
.productDetail .el-carousel__container {
  text-align: center;
  position: relative;
  height: 314px;
}
.productDetail .carousel ul {
  width: 100%;
}
/* 右侧商品信息 */
.productDetail .detail {
  margin: 24px;
  flex-grow: 1;
}
.productDetail .detail hr {
  margin: 15px 0;
}
.productDetail .detail .head {
  line-height: 20px;
  /* font-size: 12px; */
  /* white-space: nowrap; */
  overflow: hidden;
  /* text-overflow: ellipsis; */
  display: -webkit-box;
  /* 设置伸缩盒子的子元素排列方式--从上到下垂直排列 */
  -webkit-box-orient: vertical;
  /* 显示的行 */
  -webkit-line-clamp: 3;
  margin-left: 5px;
}
.productDetail .detail .price {
  display: flex;
  /* color: cornflowerblue; */

  text-align: left;
}
.productDetail .detail .price .name {
  flex: 30%;
}
.productDetail .detail .price .pri {
  flex: 70%;
}
.productDetail .detail .scales {
  display: flex;
  margin: 10px 0;
}
.productDetail .detail .scales .name {
  text-align: left;
  flex: 30%;
}
.productDetail .detail .scales .scale {
  display: flex;
  flex: 70%;
  justify-content: space-between;
  /* max-width: 400px; */
  flex-wrap: wrap;
  align-content: flex-start;
}
.productDetail .detail .scales .scale .span {
  flex: 20%;
  text-align: left;
}
.productDetail .detail .scales .scale span:hover {
  cursor: pointer;
}
.productDetail .detail .features {
  display: flex;
  margin: 10px 0;
}
.productDetail .detail .features .name {
  text-align: left;
  flex: 30%;
}
.productDetail .detail .features .feature {
  display: flex;
  flex: 70%;
  /* max-width: 400px; */
  justify-content: space-between;
  flex-wrap: wrap;
  align-content: flex-start;
}
.productDetail .detail .features .feature .span {
  flex: 20%;
  text-align: left;
}
.productDetail .detail .features .feature span:hover {
  cursor: pointer;
}
.productDetail .detail .stepnum {
  display: flex;
  /* margin: 10px 0; */
  text-align: left;
}
.productDetail .detail .stepnum .name {
  flex: 30%;
}
.productDetail .detail .stepnum .num {
  flex: 70%;
}
.productDetail .detail .subBtn {
  display: flex;
  justify-content: space-around;
  margin: 10px 0;
}
.productDetail .detail .subBtn button {
  width: 200px;
  height: 50px;
  line-height: 50px;
  font-size: 16px;
}
.productDetail .detail .subBtn .buy button {
  background-color: aliceblue;
  border: 1px solid cornflowerblue;
}
.productDetail .detail .subBtn .buy button:active {
  border: 1px gray;
}
.productDetail .detail .subBtn .addCart button {
  background-color: cornflowerblue;
  border: 1px white solid;
  color: white;
}
.productDetail .detail .subBtn .addCart button:active {
  border: 1px black;
}
.productDetail .detail .subBtn .collect{
  height:50px;
  width: 50px;
  flex-grow: 0;
  flex-shrink: 0;
}
.productDetail .detail .subBtn .collect img{
  height:50px;
}
.productDetail .detail .subBtn .collect img:active{
  height:47px;

}
/* 下架商品遮罩 */
.productDetail .middle .productInfo .outList{
  position: absolute;
  z-index: 9999;
  right: 120px;
}
.productDetail .middle .productInfo .outList img{
  width:825px;
}
/* 商品详细描述 */
.productDetail .middle .detailDescript {
  text-align: left;
  margin: 0 24px 24px 24px;
}
.productDetail .middle .detailDescript .descript {
  display: flex;
  flex-wrap: wrap;
}
.productDetail .middle .detailDescript .descript .desitem {
  flex: 0 0 25%;
  font-size: 10px;
  height: 19px;
  margin: 0px;
  /* 1.溢出隐藏 */
  overflow: hidden;
  /* 2.用省略号来代替超出文本 */
  text-overflow: ellipsis;
  /* 3.设置盒子属性为-webkit-box  必须的 */
  display: -webkit-box;
  /* 4.-webkit-line-clamp 设置为2，表示超出2行的部分显示省略号，如果设置为3，那么就是超出3行部分显示省略号 */
  -webkit-line-clamp: 1;
  /* 5.字面意思：单词破坏：破坏英文单词的整体性，在英文单词还没有在一行完全展示时就换行  即一个单词可能会被分成两行展示 */
  word-break: break-all;
  /* 6.盒子实现多行显示的必要条件，文字是垂直展示，即文字是多行展示的情况下使用 */
  -webkit-box-orient: vertical;
}
/* 商品大图展示 */
.productDetail .middle .bigImg{
  text-align: left;
  margin: 24px;
}
.productDetail .middle .bigImg img{
  width:100%;
}
</style>
