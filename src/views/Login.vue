<template>
  <div class="login">
    <div class="title">
      天狗商城后台管理
    </div>
    <div class="loginArea">
      <el-form ref="loginForm" :model="user" :rules="rules" label-position="left" label-width="auto" show-message>
        <span class="login-title">欢迎登录</span>
        <div style="margin-top: 5px"></div>
        <el-form-item prop="username" label="用户名">
          <el-col :span="22">
            <el-input v-model="user.username" placeholder="请输入用户名"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item id="password" prop="password" label="密码">
          <el-col :span="22">
            <el-input v-model="user.password" show-password placeholder="请输入密码"></el-input>
          </el-col>
        </el-form-item>
        <div style="margin: 16px">
          <el-row>
            <el-button type="primary" icon="el-icon-upload" @click="doRegist()">用户注册</el-button>
            <el-button type="primary" icon="el-icon-upload" @click="doClientLogin()">用户登录</el-button>
          </el-row>
        </div>
      </el-form>
    </div>
  </div>
</template>
<script>
import Cookies from "js-cookie"
export default {
  data() {
    return {
      userData: [],
      empPassword: '',
      empAccount: '',
      clientPassword: '',
      clientAccount: '',
      user: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          { required: true, message: '账号不可为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不可为空', trigger: 'blur' }
        ]
      },

    };
  },
  created() {

  },
  methods: {
    //注册
    doRegist() {
      this.$router.replace("/regist");
    },

    //登录
    doClientLogin() {
      this.$axios({
        url: "/admin/login",
        method: "post",
        data: {
          adminAcc: this.user.username,
          adminPwd: this.user.password
        },
        header: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      }).then(res => {
        console.log(res);
        this.userData = res.data.data;
        Cookies.set('adminId', (this.userData.adminId))
        Cookies.set('adminName', (this.userData.adminName))
        console.log(res.data.message)
        console.log(this.userData.adminName);
        if (res.data.code === 200) {
          this.$router.push("/home");  //跳转进入后台接口
          this.$message({
            message: '登录成功',
            type: 'success'
          });
        } else {

        }
      }).catch(
        error => {
          //请求失败
        }
      )

    }

  }
};
</script>
 
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h5 {
  text-align: left;
  color: gray;

}

h3 {
  text-align: left;
}

.login {
  width: 100%;
  height: 100vh;
  background-image: url("../assets/img/图片.jpeg");
  overflow: hidden;
  background-size: 100%;
  background-repeat: no-repeat;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #747bef;
  font-size: 30px;
  font-weight: bold;
}

.loginArea {
  margin-top: 50px;
  /* margin: 200px auto; */
  margin-left: 36%;
  width: 27%;
  box-shadow: 0 8px 8px rgba(255, 255, 255, 0.2);
  background-color: white;
  height: 250px;
  border-radius: 15px;
  padding: 15px;
}

.title {
  font-size: 50px;
  color: rgb(255, 255, 255);
  text-align: center;
  margin-top: 120px;
  border-radius: 20px;
  text-shadow: 0px 0px 10px black;
}
</style>