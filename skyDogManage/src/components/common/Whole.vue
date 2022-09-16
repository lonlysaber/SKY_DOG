<template>
    <div class="wrapper">
        <!-- 页面头部部分 -->
        <div class="header">
            <div class="logo">房产中介管理</div>
            <!-- 水平一级菜单 -->
            <div style="float:left;">
                <el-menu mode="horizontal" text-color="#000000" active-text-color="#3989fa" :default-active="toIndex"
                    @select="handleSelect">
                    <el-menu-item v-for="(item, index) in itemList" :index="item.path" :key="index">
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </el-menu>
            </div>
            <!-- 用户区域 -->
            <div class="header-right">
                <div class="header-user-con">
                    <div class="user-avator">
                        <img src="@/assets/img/admin.jpeg" />
                    </div>
                    <!-- 用户名下拉菜单 -->
                    <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                        <span class="el-dropdown-link">{{ showUsername }}<i class="el-icon-caret-bottom"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="Info">个人信息</el-dropdown-item>
                            <el-dropdown-item command="Update">修改密码</el-dropdown-item>
                            <el-dropdown-item command="loginout">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
        </div>
        <!-- 个人信息弹出框 -->
        <el-dialog title="个人信息" :visible.sync="dialogTableVisible" :modal-append-to-body='false'>
            <el-descriptions title="个人信息" direction="vertical" :column="4" border>
                <el-descriptions-item label="姓名">{{ Emp.empName }}</el-descriptions-item>
                <el-descriptions-item label="手机号">{{ Emp.empPhone }}</el-descriptions-item>
                <el-descriptions-item label="登录名" :span="2">{{ Emp.empAccount }}</el-descriptions-item>
                <el-descriptions-item label="管理">
                    <el-tag size="small">{{ Emp.adminId }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="入职时间">{{ Emp.hireDate }}</el-descriptions-item>
            </el-descriptions>
        </el-dialog>
        <!-- 修改密码弹出框 -->
        <el-dialog title="修改密码" :visible.sync="dialogInputVisible" width="40%" :modal-append-to-body='false'>
            <el-form ref="updateform" label-width="100px">
                <el-form-item label="旧密码">
                    <el-input v-model="oldPassword" show-password></el-input>
                </el-form-item>
                <el-form-item label="新密码">
                    <el-input v-model="accPassword" show-password></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogInputVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateConfirm()">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 页面左侧二级菜单栏，和主体内容区域部分 -->
        <el-main>
            <router-view></router-view>
        </el-main>

    </div>
</template>
 
<script>
import Cookies from "js-cookie"
export default {
    data() {
        return {
            itemList: [    // 水平一级菜单栏的菜单
                { path: '/home', title: '首页' },
                { path: '/client', title: '用户' },
                { path: '/product', title: '商品' },
                { path: '/order', title: '订单' },
                { path: '/emp', title: '地址' },
            ],
            empID: "",            //员工编号
            accPassword: "",      //新密码
            oldPassword: "",      //旧密码
            Emp: {
                empAccount: "",
                empName: "",
                empPassword: "",
                empPhone: "",
                hireDate: ""
            },
            dialogInputVisible: false, //修改密码弹出框
            dialogTableVisible: false //员工个人信息弹出框
        }
    },
    computed: {
        toIndex() {  // 根据路径绑定到对应的一级菜单，防止页面刷新重新跳回第一个
            return '/' + this.$route.path.split('/')[1];
        },
        showUsername() {   //获取用户名
            return Cookies.get('username')
        },
    },
    created() {
        this.empID = Cookies.get('empId')
    },
    methods: {
        handleSelect(path) {  // 切换菜单栏
            this.$router.push({
                path: path
            });
        },
        handleCommand(command) {  // 用户名下拉菜单选择事件
            //退出登录
            if (command == 'loginout') {
                this.$confirm('此操作将退出登录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$router.push({
                        path: '/Login'
                    });
                    this.$message({
                        showClose: true,
                        type: 'success',
                        message: '退出成功!'
                    });
                }).catch(() => {
                    this.$message({
                        showClose: true,
                        type: 'info',
                        message: '已取消退出'
                    });
                });
            }
            //修改密码
            if (command == 'Update') {
                console.log(this.empID)
                this.dialogInputVisible = true
            }
            //个人信息
            if (command == 'Info') {
                console.log(this.empID)
                this.$axios({
                    url: "/emp/getEmpById",//请求的后台接口
                    method: "get",//get请求方式
                    params: {
                        empId: this.empID
                    }
                }).then(response => {
                    console.log(response)
                    this.Emp = response.data.data
                    this.dialogTableVisible = true
                }).catch(error => {
                    console.log(error)
                });
            }
        },

        //提交修改
        updateConfirm() {
            this.$axios({
                url: "/emp/updatePwd",
                method: "get",
                params:
                {
                    accPassword: this.accPassword,
                    empId: parseInt(this.empID),
                    oldPassword: this.oldPassword,
                },
                header: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            }).then(res => {
                console.log(res)
                if (res.data.message === "成功") {
                    this.dialogInputVisible = false;
                    this.$message({
                        showClose: true,
                        message: '修改成功！',
                        type: 'success'
                    });
                };
                if (res.data.message === "失败") {
                    this.dialogInputVisible = false;
                    this.$message({
                        showClose: true,
                        message: '修改失败，密码错误',
                        type: 'error'
                    });
                };
            })
            this.accPassword = "";
            this.oldPassword = ""
        },
    }
}
</script>
 
<style scoped>
.wrapper {
    width: 100%;
    height: 100%;
    background: #f0f0f0;
}

.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
}

.header .logo {
    float: left;
    margin-left: 60px;
    margin-top: 17.5px;
    height: 29px;
    width: 160px;
    vertical-align: middle;
}

/* --------------- 用户头像区域的样式 ---------------- */
.header-right {
    float: right;
    padding-right: 50px;
}

.header-user-con {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 70px;
}

.user-avator {
    margin-left: 20px;
}

.user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}

.user-name {
    margin-left: 10px;
}

.el-dropdown-link {
    cursor: pointer;
}

.el-dropdown-menu__item {
    text-align: center;
}

/* --------------- 水平一级菜单栏的样式--------------------- */
.el-menu.el-menu--horizontal {
    border-bottom: none !important;
    float: left;
    margin-left: 50px;
    background: transparent;
}

.el-menu--horizontal>.el-menu-item.is-active {
    /* border-bottom: 2px solid #3989fa;
  color: #3989fa; */
    font-weight: bold;
}

.el-menu--horizontal>.el-menu-item {
    font-size: 16px;
    margin: 0 15px;
}
</style>