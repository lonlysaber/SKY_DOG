<template>
  <div class="frontLogin">
    <div class="window">
      <el-tabs v-model="activeName" @tab-click="handleClick" stretch>
        <el-tab-pane label="密码登录" name="first">
          <el-form ref="form" :rules="rules" :model="form" label-width="80px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" show-password></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit(form)">登录</el-button>
              <el-button @click="resetForm('form')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="second">
          <el-form
            ref="digestform"
            :rules="rules"
            :model="digestform"
            label-width="80px"
          >
            <el-form-item label="用户名" prop="username">
              <el-input v-model="digestform.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input
                maxlength="4"
                v-model="digestform.password"
                show-password
              ></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPsd">
              <el-input
                maxlength="4"
                v-model="digestform.checkPsd"
                show-password
              ></el-input>
            </el-form-item>
            <el-form-item style="margin-left: 0px">
              <el-button type="primary" @click="onDigest(digestform)"
                >注册</el-button
              >
              <el-button @click="resetForm('digestform')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script >
import { Message } from "element-ui";
import axios from "axios";

export default {
  name: "",
  data() {
    return {
      activeName: "first",
      form: {
        username: "",
        password: "",
      },
      digestform: {
        username: "",
        password: "",
        checkPsd: "",
      },
      user: {
        userId: "",
        username: "",
        password: "",
        phone: "",
      },
      checkKey: "",
      time: 60,
      checkEd: false,

      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
        ],
        checkPsd: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (value === "") {
                callback(new Error("请再次输入密码"));
              } else if (value !== this.digestform.password) {
                callback(new Error("两次输入密码不一致!"));
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.$cookies.remove("token");
    localStorage.removeItem("userId");
  },
  methods: {
    onSubmit(form) {
      axios({
        method: "post",
        url: "/user/login",
        data: {
          userName: form.username,
          password: form.password,
        },
      }).then((res) => {
        this.user = res.data.data;
        console.log(this.user);
        if (res.data.code == 200) {
          this.$message({
            message: "登录成功",
            type: "success",
          });
          this.setCookie(this.user.userId);
          localStorage.setItem("userId", this.user.userId);
          this.$router.push("/");
        }
        if (res.data.code == 201) {
          this.$message({
            message: "账号或密码错误",
            type: "error",
          });
          return;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleClick(tab, event) {
      // console.log(tab, event);
    },
    // 写cookie
    setCookie(id) {
      this.$cookies.set("token", id);

      console.log(this.$cookies.keys());
    },
    // 防抖
    throttle(cd, time = 1000) {
      var t = null;
      return function () {
        if (t) return;
        t = setTimeout(() => {
          cd.call(this);
          t = null;
        }, time);
      };
    },

    onDigest(form) {
      this.setCookie();
      if (form.username == "" || form.password == "" || form.checkPsd == "") {
        this.$message({
          message: "请输入用户名和密码",
          type: "error",
        });
        return;
      }
      if (form.password != form.checkPsd) {
        this.$message({
          message: "两次输入的密码不一致",
          type: "error",
        });
        return;
      }
      if (form.password == form.checkPsd) {
        axios({
          method:'post',
          url:'/user/register',
          data:{
            username:form.username,
            password:form.password
          }
        }).then(res=>{
          this.$message({
            message: "注册成功",
            type: "success",
          });
          this.activeName = 'first'
        })
      }
    },
  },
};
</script>

<style>
* {
  padding: 0;
  margin: 0;
}
.frontLogin {
  height: 100vh;
  background: url(@/assets/img/frontLogin.jpg);
  background-repeat: no-repeat;
  background-size: 100%;
}
.frontLogin .window {
  padding: 30px 10px;
  background-color: #fff;
  border: 1px gray solid;
  border-radius: 12px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.frontLogin .el-form-item:last-child .el-form-item__content{
  margin-left:0 !important;
}
.window .el-tabs {
  width: 500px;
  background-color: #fff;
}
.window .el-input--suffix .el-input__inner {
  border-radius: 0;
}
::v-deep .el-tabs__nav-scroll {
  width: 30% !important;
  margin: 0 auto !important;
}
.window
  .el-tab-pane:nth-child(2)
  .el-form
  .el-form-item:nth-child(2)
  .el-form-item__content {
  /* border:1px red solid; */
  display: flex;
}
.window .frontLogin .el-button.el-button--primary {
  float: left;
  border-radius: 0;
}
.window .checkKey {
  background-color: black;
  color: #fff;
  margin: 0 5px;
  width: 90px;
}
</style>
