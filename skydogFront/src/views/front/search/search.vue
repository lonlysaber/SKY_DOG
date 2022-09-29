<template>
  <div class="inner">
    <TopBar />
    <SearchBar :sel="select" :ipt="input" @changeInput="hand" />
    <!-- 筛选 -->
    <div class="sift">
      <el-tabs type="card" v-model="sort" @tab-click="handleClick">
        <el-tab-pane name="0">
          <span slot="label"> 综合排序</span>
        </el-tab-pane>
        <el-tab-pane name="2">
          <span slot="label"> 销量</span>
        </el-tab-pane>
        <el-tab-pane name="4">
          <span slot="label">评分</span>
        </el-tab-pane>
        <el-tab-pane name="6">
          <span slot="label"> 价格</span>
        </el-tab-pane>
      </el-tabs>
      <div class="price">
        <el-input placeholder="￥" v-model="lowPrice" clearable> </el-input>
        ——
        <el-input placeholder="￥" v-model="highPrice" clearable> </el-input>
        <el-button type="primary" round @click="getProduct">确定</el-button>
      </div>
    </div>
    <!-- 商品内容 -->
    <div class="main">
      <div
        class="product"
        v-for="p in products"
        :key="p.productId"
        @click="gotoDetail(p)"
        v-track="{
          triggerType: 'click',
          currentUrl: $route.path,
          productId1: p.productId,
          categoryName: p.categoryName,
          actionType: 'product-click',
        }"
      >
        <el-skeleton style="width: 180px" :loading="loading" animated>
          <template slot="template">
            <el-skeleton-item
              variant="image"
              style="width: 175px; height: 175px"
            />
            <div style="padding: 14px">
              <div style="display: flex; justify-content: space-between">
                <el-skeleton-item variant="h3" style="width: 40%" />
                <el-skeleton-item variant="h3" style="width: 40%" />
              </div>
              
              <div
                style="
                  display: flex;
                  align-items: flex-start;
                  flex-direction: column;
                  justify-items: space-between;
                  justify-content: space-around;
                  height: 50px;
                "
              >
                <el-skeleton-item variant="text" style="margin-right: 16px" />
                <el-skeleton-item variant="text" style="margin-right: 16px" />

                <el-skeleton-item variant="text" style="width: 40%" />
              </div>
            </div>
          </template>
          <template>
            <img :src="p.img.img1" />
            <div class="content">
              <div class="row1">
                <div class="price">
                  <span>￥</span>
                  <span>{{ p.productPrice }}</span>
                </div>
                <div class="pay">{{ 200 + "+" }}付款</div>
              </div>
              <div class="row2">
                <div class="dec">
                  {{ p.productName }}
                </div>
              </div>
            </div>
          </template>
        </el-skeleton>
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
</template>

<script >
import SearchBar from "@/components/searchBar.vue";
import TopBar from "@/components/TopBar.vue";
import axios from "axios";
export default {
  name: "",
  data() {
    return {
      lowPrice: "",
      highPrice: "",
      activeName: "综合排序",
      products: [
        { productName: "", productPrice: "", img: { img1: "" } },
        { productName: "", productPrice: "", img: { img1: "" } },
        { productName: "", productPrice: "", img: { img1: "" } },
        { productName: "", productPrice: "", img: { img1: "" } },
        { productName: "", productPrice: "", img: { img1: "" } },
      ],
      currentPage: 1,
      pageSize: 30,
      select: "商品",
      input: "",
      count: 0,
      sort: 0,
      loading: true,
    };
  },
  created() {
    // console.log(this.$route.query.select)
    // console.log(this.$route.query.input)
    this.select = this.$route.query.select;
    this.input = this.$route.query.input;

    this.getProduct();
    // this.$on("changeInput",data=>console.log(data))
  },
  Mounted() {
    // this.$on("changeInput",data=>console.log(data))
    // this.getProduct()
  },
  methods: {
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getProduct();
    },
    hand(data) {
      this.input = data;
      this.getProduct();
    },

    handleClick(tab, event) {
      console.log(tab, event, this.sort);

      this.getProduct();
    },
    getProduct() {
      axios({
        method: "post",
        url: "/product/search",
        data: {
          lowPrice: this.lowPrice,
          highPrice: this.highPrice,
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          keyword: this.input,
          sort: this.sort,
        },
      }).then((res) => {
        // console.log(res);
        this.products = res.data.data.data;
        this.count = res.data.data.count;
        this.loading = false;
      });
    },
    gotoDetail(p) {
      if (this.$cookies.isKey("token")) {
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
  },
  components: { SearchBar, TopBar },
};
</script>

<style scoped>
.inner {
  background-color: rgb(255, 255, 255);
}
.sift {
  width: 80%;
  margin: auto;
  padding-left: 60px;
}
.sift .el-input__inner {
  width: 100px;
}
.sift .price {
  width: 400px;
  position: relative;
  left: 300px;
  height: -100px;
  margin-top: -55px;
}
.sift .el-input {
  width: 100px;
}
.sift .el-button.is-round {
  margin-left: 10px;
}
.main {
  width: 80%;
  margin: auto;
  margin-top: 20px;
  overflow: auto;
}
.main .product {
  width: 180px;
  height: 280px;
  margin-left: 30px;
  float: left;

  border: 2px #fdfdfd solid;
}
.main .product:hover {
  border: 2px #409eff solid;
}
.main .product img {
  width: 100%;
  height: 180px;
}
.main .product .content {
  padding-top: 6px;
}
.main .product .content .row1 .price {
  color: brown;
  font-size: 16px;
  float: left;
  height: 22px;
  line-height: 22px;
}
.main .product .content .row1 {
  width: 100%;
  height: 22px;
}
.main .product .content .row1 .pay {
  float: right;
  font-size: 12px;
  color: #888888;
  line-height: 22px;
}
.main .product .content .row2 {
  width: 100%;
  height: 36px;
}
.main .product .content .row2 .dec {
  float: left;
  font-size: 14px;
  height: 36px;
  margin-top: 6px;
  /* 1.溢出隐藏 */
  overflow: hidden;
  /* 2.用省略号来代替超出文本 */
  text-overflow: ellipsis;
  /* 3.设置盒子属性为-webkit-box  必须的 */
  display: -webkit-box;
  /* 4.-webkit-line-clamp 设置为2，表示超出2行的部分显示省略号，如果设置为3，那么就是超出3行部分显示省略号 */
  -webkit-line-clamp: 2;
  /* 5.字面意思：单词破坏：破坏英文单词的整体性，在英文单词还没有在一行完全展示时就换行  即一个单词可能会被分成两行展示 */
  word-break: break-all;
  /* 6.盒子实现多行显示的必要条件，文字是垂直展示，即文字是多行展示的情况下使用 */
  -webkit-box-orient: vertical;
}
</style>
