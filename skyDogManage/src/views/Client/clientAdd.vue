<template>
    <div class="content-box">
        <div class="container">
            <!-- 客源添加 -->
            <div class="form">
                <el-form ref="ruleForm" :model="client" label-width="100px">
                    <el-form-item label="用户姓名">
                        <el-input v-model="client.clientName" placeholder="请输入姓名"></el-input>
                    </el-form-item>
                    <el-form-item label="用户手机号">
                        <el-input v-model="client.clientPhone" placeholder="请输入电话号"></el-input>
                    </el-form-item>
                    <el-form-item label="用户账号名">
                        <el-input v-model="client.clientAccount" placeholder="请输入账号名"></el-input>
                    </el-form-item>
                    <el-form-item label="用户密码">
                        <el-input v-model="client.clientPassword" placeholder="请输入密码" show-password></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="addClient">添加</el-button>
                        <el-button @click="resetForm">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            client: {
                clientAccount: "",
                clientName: "",
                clientPassword: "",
                clientPhone: ""
            }
        }
    },
    methods: {
        //添加用户
        addClient() {
            this.$axios.post('/client/addClient', this.client).then((request => {
                console.log(request)
                if (request.data.message == "添加成功") {
                    console.log("添加成功！");
                    this.$message({
                        showClose: true,
                        message: '添加用户成功！',
                        type: 'success'
                    });
                }
                else {
                    this.$message({
                        showClose: true,
                        message: '添加失败!',
                        type: 'error'
                    });
                }
                console.log(request.data.message)
            })).catch((error) => {
                console.log(error);
            })
            //清空输入框
            this.client.clientName = ""
            this.client.clientPhone = ""
            this.client.clientAccount = ""
            this.client.clientPassword = ""
        },
        //重制
        resetForm() {
            //清空输入框
            this.client.clientName = ""
            this.client.clientPhone = ""
            this.client.clientAccount = ""
            this.client.clientPassword = ""
        }
    },
}

</script>