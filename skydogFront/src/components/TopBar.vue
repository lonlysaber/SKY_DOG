<template>
  <div class="TopBar">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-submenu index="1" style="margin-left: 50px">
        <template slot="title">{{ user.userName }}</template>
        <el-menu-item @click="toMePage(user)" index="1-1">
          <!-- <div class="userinfo">
                        <div class="avatar">
                            <img :src="user.avatar" alt="">
                        </div>
                        <div class="router">
                            <p @click="toMePage(user)"> -->
          个人中心
          <!-- </p>
                            <p @click="loginOut">退出登录</p>
                        </div>
                    </div> -->
        </el-menu-item>
        <el-menu-item index="1-1" @click="loginOut">退出登录</el-menu-item>
      </el-submenu>
      <!-- <el-submenu index="2">
        <template slot="title">我的宝贝</template>
        <el-menu-item index="2-1">已买到的宝贝</el-menu-item>
        <el-menu-item index="2-2">我的足迹</el-menu-item>
      </el-submenu> -->
      <el-submenu index="3" style="float: right; margin-right: 50px">
        <template slot="title">收藏夹</template>
        <el-menu-item index="3-1" @click="toCollectPage()"
          >收藏的商品</el-menu-item
        >
        <el-menu-item index="3-2">收藏的商家</el-menu-item>
      </el-submenu>
      <el-menu-item index="4" style="float: right"> 订单管理 </el-menu-item>
    </el-menu>
  </div>
</template>

<script >
import axios from "axios";
export default {
  name: "topbar",
  data() {
    return {
      user: {
        avatar:
          "https://wwc.alicdn.com/avatar/getAvatar.do?userNick=tb487578808&_input_charset=UTF-8&width=80&height=80&type=sns",
        userName: "天狗用户001",
      },
      activeIndex: "1",
      activeIndex2: "1",
    };
  },
  created() {
    this.getUserinfo();
  },
  methods: {
    getUserinfo() {
      axios({
        method: "get",
        url: "/user/queryId/" + this.$cookies.get("token"),
      }).then((res) => {
        // console.log(res)
        this.user = res.data.data.data;
      });
    },
    toMePage(user) {
      console.log(user.username);
      this.$router.push({
        path: "/me",
        query: {
          userName: user.username,
          userId: this.$cookies.get("token"),
        },
      });
    },
    toCollectPage() {
      this.$router.push({
        path: "/me/collect",
      });
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    loginOut() {
      this.$confirm("确认退出登录？")
        .then((_) => {
          this.$router.push("/frontlogin");
        })
        .catch((_) => {});
    },
  },
};
</script>

<style scoped>
.userinfo {
  display: flex;
  align-items: center;
}

.userinfo .avatar img {
  border-radius: 50%;
}

.userinfo .router {
  margin-left: 20px;
}
</style>
