<template>
  <div class="home">
    <div class="top">
      <topbar></topbar>
    </div>
    <div class="searchbar">
      <searchbar></searchbar>
    </div>
    <div class="container">
      <!-- 顶部导航栏 -->

      <!-- 分类与个人信息 -->
      <div class="middle">
        <!-- 分类 -->
        <div class="sort">
          <div class="sName">
            <h2>分类</h2>
          </div>
          <div class="sortDetail">
            <div class="sortItem" v-for="item in sorts" :key="item.key">
              <div class="icon"></div>
              <div
                class="itemName"
                v-for="(it, index) in item"
                :key="index"
                v-track="{
                  triggerType: 'click',
                  currentUrl: $route.path,
                  categoryName: sortItem,
                  actionType: 'category-click',
                }"
              >
                <span class="i" v-if="index != 0">/</span>
                <span @click="serchByName(it)" class="name">{{ it }}</span>
              </div>
            </div>
          </div>
        </div>
        <!-- 轮播图 -->
        <div class="carousel">
          <el-carousel trigger="click">
            <el-carousel-item v-for="item in advert" :key="item.key">
              <div @click="gotoDetail(item)" >
                <img :src="item.img.img1" style="margin: auto;" alt="" />

              </div>
            </el-carousel-item>
          </el-carousel>
        </div>
        <!-- 身份信息 -->
        <div class="userInfo">
          <div class="faceInfo" @click="gotoMe(user)">
            <div class="avatar">
              <img src="@/assets/img/user.png" alt="" />
            </div>
            <div class="hello">
              <span>你好!</span>
              <br />
              <span class="username">{{ user.userName }}</span>
            </div>
          </div>
          <div class="orderInfo">
            <div class="item" @click="gotoCart()">
              <div class="num">{{ countNum.cartNum }}</div>
              <div class="dec">购物车</div>
            </div>
            <div class="item" @click="gotoOrder('待收货')">
              <div class="num">{{ countNum.orderTakeNum }}</div>
              <div class="dec">待收货</div>
            </div>
            <div class="item" @click="gotoOrder('待支付')">
              <div class="num">{{ countNum.orderPayNum }}</div>
              <div class="dec">待支付</div>
            </div>
            <!-- <div class="item" @click="gotoOrder('待评价')">
              <div class="num">{{ countNum.orderDesNum }}</div>
              <div class="dec">待评价</div>
            </div> -->
          </div>
          <div class="likeInfo">
            <div class="item" @click="gotoCollect('product')">
              <div class="icon">
                <img src="@/assets/img/collect.png" alt="" />
              </div>
              <div class="dec">宝贝收藏</div>
            </div>
            <div class="item" @click="gotoCollect('shop')">
              <div class="icon">
                <img src="@/assets/img/collectShop.png" alt="" />
              </div>
              <div class="dec">商家收藏</div>
            </div>
            <div class="item" @click="gotoOrder('已完成')">
              <div class="icon">
                <img src="@/assets/img/shop.png" alt="" />
              </div>
              <div class="dec">买过的</div>
            </div>
            <!-- <div class="item">
              <div class="icon">
                <img src="@/assets/img/mark.png" alt="" />
              </div>
              <div class="dec">我的足迹</div>
            </div> -->
          </div>
        </div>
      </div>
      <!-- 个性推荐 -->
      <div class="bottom">
        <h1>猜你喜欢</h1>
        <div class="list">
          <div
            class="product"
            v-for="item in recommend"
            :key="item.key"
            @click="gotoDetail(item)"
            v-track="{
              triggerType: 'click',
              currentUrl: $route.path,
              productId1: productItem.productId,
              categoryName: productItem.categoryName,
              actionType: 'product-click',
            }"
          >
            <el-skeleton style="width: 350px" :loading="loading1" animated>
              <template slot="template">
                <div style="padding: 14px;display: flex;">
                  <el-skeleton-item
                    variant="image"
                    style="width: 100px; height: 100px"
                  />
                
                <div
                  style="
                    display: flex;
                    flex-direction: column;
                    align-items:flex-start;
                    justify-items: space-between;
                    justify-content: space-around;
                    margin-left: 10px;
                    
                  "
                >
                  <el-skeleton-item variant="h3" style="width: 200px;" />
                  <el-skeleton-item variant="text" style="width: 150px;" />
                  <el-skeleton-item variant="text" style="width: 100px;" />
                </div>
              </div>
              </template>
              <template>
                <div class="image">
                  <img :src="item.img.img1" alt="" />
                </div>
                <div class="info">
                  <div class="dec">{{ item.productName }}</div>
                  <div class="price">
                    <span>￥{{ item.productPrice }}</span>
                  </div>
                </div>
              </template>
            </el-skeleton>
          </div>
        </div>
      </div>
      <!-- 商品展示 -->
      <div class="bottom">
        <h1>热门推荐</h1>
        <div class="list">
          <div
            class="product"
            v-for="item in products"
            :key="item.key"
            @click="gotoDetail(item)"
            v-track="{
              triggerType: 'click',
              currentUrl: $route.path,
              productId1: productItem.productId,
              categoryName: productItem.categoryName,
              actionType: 'product-click',
            }"
          >
            <el-skeleton style="width: 350px" :loading="loading2" animated>
              <template slot="template">
                <div style="padding: 14px;display: flex;">
                  <el-skeleton-item
                    variant="image"
                    style="width: 100px; height: 100px"
                  />
                
                <div
                  style="
                    display: flex;
                    flex-direction: column;
                    align-items:flex-start;
                    justify-items: space-between;
                    justify-content: space-around;
                    margin-left: 10px;
                    
                  "
                >
                  <el-skeleton-item variant="h3" style="width: 200px;" />
                  <el-skeleton-item variant="text" style="width: 150px;" />
                  <el-skeleton-item variant="text" style="width: 100px;" />
                </div>
              </div>
              </template>
              <template>
                <div class="image">
                  <img :src="item.img.img1" alt="" />
                </div>
                <div class="info">
                  <div class="dec">{{ item.productName }}</div>
                  <div class="price">
                    <span>￥{{ item.productPrice }}</span>
                  </div>
                </div>
              </template>
            </el-skeleton>
          </div>
        </div>
      </div>
      <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        :total="count"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script >
import topbar from "@/components/TopBar.vue";
import searchbar from "@/components/searchBar.vue";
import { Message } from "element-ui";
import axios from "axios";

export default {
  name: "",
  components: {
    topbar,
    searchbar,
  },
  data() {
    return {
      sorts: [
        ["女装", "内衣", "奢品"],
        ["女鞋", "男鞋", "箱包"],
        ["美妆", "饰品", "洗护"],
        ["男装", "运动", "百货"],
        ["手机", "数码", "企业礼品"],
        ["家装", "电器", "车品"],
        ["食品", "生鲜", "母婴"],
        ["医药", "保健", "进口"],
      ],
      advert: [
        // "https://img.alicdn.com/imgextra/i3/6000000004152/O1CN01NBqTd31gXfF629F6d_!!6000000004152-2-octopus.png",
        // "https://img.alicdn.com/imgextra/i4/6000000000466/O1CN01JrDvz61FJTLKlOknL_!!6000000000466-2-octopus.png",
        // "https://img.alicdn.com/imgextra/i4/6000000001997/O1CN01h6Pl301QcfimQACBD_!!6000000001997-0-octopus.jpg",
        // "https://img.alicdn.com/imgextra/i1/6000000002861/O1CN01F7NW371X0NvBVP99i_!!6000000002861-0-octopus.jpg",
        // "https://img.alicdn.com/imgextra/i1/6000000004570/O1CN01B0jaUp1jd6nWcaZzU_!!6000000004570-2-octopus.png",
      
      ],
      user: {
        avatar:
          "https://wwc.alicdn.com/avatar/getAvatar.do?userNick=tb487578808&_input_charset=UTF-8&width=80&height=80&type=sns",
        userName: "天狗用户001",
      },
      countNum: {
        cartNum: 0,
        orderTakeNum: 0,
        orderPayNum: 0,
        orderDesNum: 0,
      },
      products: [
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
      ],
      num: 1,
      loading1: true,
      loading2: true,
      hotWord: "食品",
      sortItem: "女装",
      productItem: "",
      recommend: [
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
        {
          img: "https://gw.alicdn.com/bao/uploaded/i1/179917267/O1CN016Xkm9223YKxfqgeOC_!!179917267.jpg_300x300q90.jpg_.webp",
          dec: "数据线收纳神器魔术贴扎带理线器电脑束线带桌面电线走线固定绑带",
          price: "5.8",
        },
      ],
      currentPage: 1,
      pageSize: 30,
      count: 0,
    };
  },
  created() {
    // console.log(this.$cookies.isKey('token'))
    this.getUserRecommend();
    this.getProducts();
    this.getUser();
  },
  methods: {
    // 获取用户信息
    getUser() {
      axios({
        method: "get",
        url: "/user/queryId/" + this.$cookies.get("token"),
      }).then((res) => {
        this.user = res.data.data.data;

        this.getCartNum();
        this.getOrderSum();
      });
    },
    // 统计购物车数量
    getCartNum() {
      axios({
        method: "get",
        url: "/cart/getMyCart/" + this.$cookies.get("token"),
      }).then((res) => {
        this.countNum.cartNum = res.data.data.length;
      });
    },
    // 统计订单数量
    getOrderSum() {
      axios({
        method: "post",
        url: "/order/getMyOrder/",
        data: {
          userId: this.$cookies.get("token"),
        },
      }).then((res) => {
        // this.user.cartNum = res.data.data.data.length
        // console.log(res)

        this.orderCount(res.data.data);
      });
    },
    orderCount(arr) {
      arr.forEach((item) => {
        if (item.orderStatus == "待支付") {
          this.countNum.orderPayNum++;
        } else if (item.orderStatus == "待收货") {
          this.countNum.orderTakeNum++;
        } else if (item.orderStatus == "待评价") {
          this.countNum.orderDesNum++;
        } else {
          // console.log(item.orderStatus);
        }
      });
    },
    // 热门推荐分页
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getProducts();
    },
    // 主页商品信息
    getProducts() {
      axios({
        method: "post",
        url: "/recommend/hotRec/",
        data: {
          currentPage: this.currentPage,
          // keyword: this.hotWord,
          pageSize: this.pageSize,
        },
      }).then((res) => {
        this.products = res.data.data.data;
        this.count = res.data.data.count;
        console.log(res);
        this.getAdvate();
        this.loading2 = false;
      });
    },
    // 主页个性推荐
    getUserRecommend() {
      axios({
        method: "get",
        url: "/user/getUserRecommend/" + this.$cookies.get("token"),
      }).then((res) => {
        // console.log(res)
        this.loading1 = false;
        this.recommend =res.data.data.data
      });
    },
    // 轮播图加载
    getAdvate(){
      for(let i = 0;i < 5;i++){
        this.advert.push(this.products[i])
      }
    },
    // 点击分类搜索
    serchByName(it) {
      this.sortItem = it;
      this.$router.push({
        path: "search",
        query: {
          input: it,
        },
      });
    },
    // 跳转详情页
    gotoDetail(p) {
      if (this.$cookies.isKey("token")) {
        // console.log(p)
        this.productItem = p;
        this.$router.push({
          path: "/productdetail",
          query: {
            productId: p.productId,
          },
        });
      } else {
        this.$message({
          message: "请先登录",
          type: "error",
        });

        this.$router.push("/frontlogin");
      }
      // console.log(item)
    },
    // 跳转个人页面
    gotoMe(user) {
      this.$router.push({
        path: "/me",
        query: {
          userName: user.username,
          userId: this.$cookies.get("token"),
        },
      });
    },
    // 跳转购物车页面
    gotoCart(user) {
      this.$router.push("/cart");
    },
    // 跳转订单页面
    gotoOrder(name) {
      this.$router.push({
        path: "/me/order",
        query: {
          activeName: name,
        },
      });
    },
    // 跳转收藏页面
    gotoCollect(name) {
      this.$router.push({
        path: "/me/collect",
        query: {
          activeName: name,
        },
      });
    },
  },
};
</script>

<style>
* {
  box-sizing: border-box;
}

.home {
  background-color: rgb(234, 232, 235);
}
/* 页面主体 */
.home .container {
  font-size: 14px;
  background-color: #fff;
  margin: 5px 100px 5px 100px;
  /* height: 100vh; */
  border-radius: 18px;
}
/* 中间内容 */
.home .middle {
  display: flex;
}
/* 分类 */
.home .middle .sort {
  background-color: #f7f9fa;
  border-radius: 12px;
  margin: 24px 0 0px 24px;
  padding: 0 24px 0px 24px;
  height: 314px;
  white-space: nowrap;
  width: 230px;
}
.home .middle .sort .sName h2 {
  font-size: 18px;
  color: #111;
  letter-spacing: 0;
  line-height: 24px;
  /* float: left; */
  text-align: left;
}
.home .middle .sort .sortItem {
  display: flex;
  margin-top: 10px;
}
.home .middle .sort .sortItem .i {
  margin: 0 14px 0 14px;
}
.home .middle .sort .sortItem span {
  margin: 24px 0px 24px 0px;
}
.home .middle .sort .sortItem .name:hover {
  color: cornflowerblue;
  cursor: pointer;
}
/* 轮播图 */
.home .middle .carousel {
  width: 564px;
  height: 314px;
  overflow: hidden;
  border-radius: 12px;
  margin: 24px;
  display: block;
  visibility: visible;
  background-color: #f7f9fa
}
.home .middle .carousel img {
  /* width: 564px; */
  height: 314px;
  display: block;
  /* border-radius: 12px 0 0 12px; */
}
.home .middle .carousel img:hover{
  cursor: pointer;
}
.home .el-carousel__container {
  position: relative;
  height: 314px;
}
/* 身份信息 */
.home .middle .userInfo {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  margin: 24px;
}
.home .middle .faceInfo {
  display: flex;
}
.home .middle .faceInfo:hover {
  cursor: pointer;
}
.home .middle .faceInfo .avatar {
  margin-right: 20px;
  width: 50px;
}
.home .middle .faceInfo img {
  width: 100%;
  border-radius: 50px;
  margin-right: 20px;
}
.home .middle .faceInfo .hello {
  text-align: left;
}
.home .middle .faceInfo .hello:hover {
  color: cornflowerblue;
}
.home .middle .userInfo .orderInfo {
  display: flex;
  justify-content: space-around;
}
.home .middle .userInfo .orderInfo .item:hover {
  cursor: pointer;
}

.home .middle .userInfo .likeInfo {
  display: flex;
  justify-content: space-around;
}
.home .middle .userInfo .likeInfo .item {
  width: 70px;
}
.home .middle .userInfo .likeInfo .item:hover {
  cursor: pointer;
}
.home .middle .userInfo .likeInfo .item img {
  width: 25px;
}
/* 商品展示 */
.home .bottom {
  margin: 24px;
}
.home .bottom .list {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
}
.home .bottom .list .product {
  margin: 10px;
}
.home .bottom .list .product > div:first-child {
  display: flex;
  flex: 0 0 31%;
  /* width: 31%; */

  text-align: left;
  background-color: #f7f9fa;
  padding: 10px;
  margin: 12px 0 12px 0;
  border-radius: 15px;
  cursor: pointer;
}
.home .bottom .product img {
  width: 100px;
  height: 100px;
}
.home .bottom .product .info {
  margin-left: 12px;
  display: flex;
  flex-direction: column;
  /* justify-content: space-around; */
}
.home .bottom .product .info .dec {
  font-size: 14px;
  height: 55px;
  margin-top: 6px;
  /* 1.溢出隐藏 */
  overflow: hidden;
  /* 2.用省略号来代替超出文本 */
  text-overflow: ellipsis;
  /* 3.设置盒子属性为-webkit-box  必须的 */
  display: -webkit-box;
  /* 4.-webkit-line-clamp 设置为2，表示超出2行的部分显示省略号，如果设置为3，那么就是超出3行部分显示省略号 */
  -webkit-line-clamp: 3;
  /* 5.字面意思：单词破坏：破坏英文单词的整体性，在英文单词还没有在一行完全展示时就换行  即一个单词可能会被分成两行展示 */
  word-break: break-all;
  /* 6.盒子实现多行显示的必要条件，文字是垂直展示，即文字是多行展示的情况下使用 */
  -webkit-box-orient: vertical;
}
.home .bottom .product .info .price span {
  font-size: 18px;
  color: cornflowerblue;
}
</style>
