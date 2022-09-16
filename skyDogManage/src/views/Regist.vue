<template>
  <div class="regist">
    <div class="registArea">
      <el-form ref="registForm" :model="register" :rules="rules" label-position="left" label-width="auto" show-message>
        <span class="regist-title" align="center">欢迎您的注册</span>
        <div style="margin-top: 5px"></div>
        <el-form-item prop="registeraccount" label="用户名">
          <el-col :span="22">
            <el-input v-model="register.registeraccount" placeholder="请输入用户名"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item id="password" prop="registerpassword" label="密码">
          <el-col :span="22">
            <el-input v-model="register.registerpassword" show-password placeholder="请输入密码"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item prop="registername" label="名字">
          <el-col :span="22">
            <el-input v-model="register.registername" placeholder="请输入你的名字"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item prop="registerphone" label="号码">
          <el-col :span="22">
            <el-input v-model="register.registerphone" placeholder="请输入你的号码"></el-input>
          </el-col>
        </el-form-item>
        <div style="margin: 16px">
          <el-row>
            <el-button type="primary" icon="el-icon-upload" @click="registSuccess()">确定注册</el-button>
            <el-button type="primary" icon="el-icon-upload" @click="gotoLogin()">返回登录</el-button>
          </el-row>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      register: {
        registeraccount: '',
        registername: '',
        registerpassword: '',
        registerphone: '',
      },
      client: {
        clientAccount: '',
        clientName: '',
        clientPassword: '',
        clientPhone: '',
      },
      rules: {
        registerpassword: [
          { required: true, message: '密码不可为空', trigger: 'blur' }
        ],
        registername: [
          { required: true, message: '名字不可为空', trigger: 'blur' }
        ],
        registerphone: [
          { required: true, message: '号码不可为空', trigger: 'blur' }
        ],
      },

    };
  },
  created() {

  },
  methods: {
    registSuccess() {

      this.$axios({
        url: "client/register",
        method: "post",
        data:
        {
          clientAccount: this.register.registeraccount,
          clientName: this.register.registername,
          clientPassword: this.register.registerpassword,
          clientPhone: this.register.registerphone,
        }
        ,
        header: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      }).then(res => {
        console.log(res)
        this.$message({
          showClose: true,
          message: '恭喜你，注册成功！',
          type: 'success'
        });
      }).catch(err => {
        console.log(error);
      })
    },
    gotoLogin() {
      this.$router.replace("/Login");  //跳转进入后台接口
    }
  }
}
</script>

<style scoped>
.regist {
  width: 100%;
  height: 100vh;
  background-image: url("../assets/img/图片.jpeg");
  overflow: hidden;
}

.regist-title {
  text-align: center;
  /* margin: 0 auto 60px auto; */
  display: block;
  color: #66CD00;
  font-size: 30px;
  font-weight: bold;

}

.registArea {
  margin: 200px auto;
  width: 27%;
  box-shadow: 0 8px 8px rgba(255, 255, 255, 0.2);
  background-color: white;
  height: 350px;
  border-radius: 15px;
  padding: 15px;
}
</style>