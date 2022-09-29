<template>
    <div class="me">
        <div class="top">
            <topBar></topBar>
        </div>
        <el-container>
            <!-- 头部导航栏 -->
            <el-header style="padding:0px">

                <el-menu :default-active="activeIndex2" class="el-menu-demo" mode="horizontal" @select="handleSelect"
                    background-color="#246EDC" text-color="#fff" active-text-color="#ffd04b"
                    style="text-align:center; padding-left: 130px;">
                    <el-menu-item index="-1"  style="font-size:large;width: 70;" @click="goBack">
                      <i > 返回</i>
                    </el-menu-item>
                    <el-menu-item index="0" style="font-size:large">我的天狗</el-menu-item>
                    <el-menu-item index="1">首页</el-menu-item>
                    <!-- <el-menu-item index="2">账号管理</el-menu-item> -->
                    <!-- <el-menu-item index="3" disabled>消息中心</el-menu-item> -->
                </el-menu>
            </el-header>

            <el-container style="padding-left: 100px;">
                <!-- 侧边 -->
                <el-aside width="250px">

                    <el-col :span="24">
                        <el-menu :default-active="curPath"  class="el-menu-vertical-demo" @open="handleOpen"
                            @close="handleClose" @select="handSelect" background-color="" text-color="#000000" active-text-color="#409eff"
                            router>
                            <div class="menu">
                                <el-menu-item index="/me/order">
                                    <i class="el-icon-menu"></i>
                                    <span slot="title">我的订单</span>
                                </el-menu-item>
                                <el-menu-item index="/cart">
                                    <i class="el-icon-menu"></i>
                                    <span slot="title">我的购物车</span>
                                </el-menu-item>
                                <el-menu-item index="/me/collect"
                                >
                                    <i class="el-icon-document"></i>
                                    <span slot="title">我的收藏</span>
                                </el-menu-item>
                                <el-menu-item index="/me/mypay">
                                    <i class="el-icon-setting"></i>
                                    <span slot="title">我的购买</span>
                                </el-menu-item>
                                <!-- <el-menu-item index="5">
                                    <i class="el-icon-setting"></i>
                                    <span slot="title">我的足迹</span>
                                </el-menu-item>
                                <el-menu-item index="6">
                                    <i class="el-icon-setting"></i>
                                    <span slot="title">我评价的</span>
                                </el-menu-item> -->
                            </div>
                        </el-menu>
                    </el-col>
                </el-aside>
                <el-main>
                    <router-view></router-view>
                </el-main>

            </el-container>
        </el-container>



    </div>
</template>

<script scoped>
import topBar from '@/components/TopBar.vue';
export default {
    name: "",
    components: { topBar },
    data() {
        return {
            userId: "",
            activeIndex2: '1',
            user: {
                avatar:
                    "https://wwc.alicdn.com/avatar/getAvatar.do?userNick=tb487578808&_input_charset=UTF-8&width=80&height=80&type=sns",
                username: "天狗用户001",
                userId:10000
            },
            curPath:'/me/order'
        };
    },
    created() {
        this.loginEd()
        this.userId = this.$cookies.get('token')||10001;
        this.user.userId = this.userId
    },
    methods: {
        goBack() {
            history.back(1)
        },
        loginEd(){
            if(!this.$cookies.isKey('token')){
                this.$router.push('/frontlogin')
            }
        },
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        handSelect(key, keyPath) {
            this.curPath = key
            // console.log(key, keyPath);
        },
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        toOrderPage() {

        },
        toCartPage(user) {
            this.$router.push({
                path: "/cart",
                query: {
                    userId: user.userId,
                }
            })
        },
        toCollectPage(user) {
            this.$router.push({
                path: "/me/collect",
                query: {
                    userId: user.userId,
                }
            })
        },
        toEvaluate() {

        },
    },

}
</script>

<style>
.me .el-header {
    /* background-color: #B3C0D1; */
    color: #333;
    text-align: center;
    line-height: 60px;
}

.me .menu {
    text-align: left;
    margin-left: 50px;
}

.me .el-aside {
    background-color: white;
    color: #333;
    text-align: center;
    line-height: 10px;
    margin-top: 20px;
    height: 700px;
}

.m .el-main {
    /* background-color:; */
    color: #333;
    text-align: center;
    line-height: auto;
    margin-top: 20px;
    padding: 10px;
}

.me .mt-ml-c1 .mt-ml-shim {
    margin-bottom: -26px;
    margin-left: 10px;
    width: 726px;
    *position: relative;
}

.me .top-info {
    height: auto;
    display: block;
    /* background-color: lightblue; */
    border: 1px solid #e4eaee;
    margin-bottom: 20 px;
    /* font-size: 12px; */
    color: #4b4b4b;
    position: relative;
    font-family: tahoma, arial, 'Hiragino Sans GB', 'Microsoft YaHei', \5b8b\4f53, sans-serif;
}


.me .info-top {
    height: 60px;
    background-color: #D3DCE6;

}

.me .info {
    float: left;
    width: 35%;
    padding-top: 3px;
}

.me .avatar {
    float: left;
    display: block;
    width: 52px;
    height: 52px;
    margin-right: 13px;
    margin-left: 12px;
    position: relative;
    border: 2px solid #FFF;
    border-radius: 50%;
    overflow: hidden;
}

.me .name {
    margin-top: 6px;
    display: block;
    color: #000;
}

.me .address {
    float: left;
    width: 65%;
    height: 60px;
    text-align: center;
    /* padding-top: px; */
}

.me .address ul {
    float: left;
    width: 61%;
    margin-right: -1px;
    margin-top: 9px;
    padding-top: 7px;


}

.me .address li {
    width: 40%;
    display: inline;
    float: right;
}

.me .info-bottom {
    overflow: hidden;
}

.me .info-bottom ul {
    height: 52px;
    list-style: none;
    padding-left: 5px;
}

.me .info-bottom li {
    width: 20%;
    float: left;
}

.me .info-bottom a {
    zoom: 1;
    padding: 17px 0;
    display: block;
    color: #4b4b4b;
    text-align: center;
}
</style>

