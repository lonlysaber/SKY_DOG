<template>
    <div class="content-box">
        <div class="container">
            <!-- 客源添加 -->
            <div class="form">
                <el-form ref="ruleForm" :model="user" label-width="100px">
                    <el-form-item label="用户姓名">
                        <el-input v-model="user.userName" placeholder="请输入姓名"></el-input>
                    </el-form-item>
                    <el-form-item label="用户手机号">
                        <el-input v-model="user.userPhone" placeholder="请输入电话号"></el-input>
                    </el-form-item>
                    <el-form-item label="用户密码">
                        <el-input v-model="user.password" placeholder="请输入密码" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="用户昵称">
                        <el-input v-model="user.nick" placeholder="请输入昵称" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="用户">
                        <el-select v-model="user.gender" placeholder="请选择性别" clearable>
                            <el-option label="男" value="男">
                            </el-option>
                            <el-option label="女" value="女">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item class="option" label="上传头像">
                        <el-upload class="upload-demo" action="../../assets/img/" :on-preview="handlePreview"
                            :on-remove="handleRemove" on-progress="upload" :file-list="fileList" list-type="picture">
                            <el-button size="small" type="primary">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                        </el-upload>
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
            user: {
                userName: "",
                password: "",
                phone: "",
                nick:"",
                gender:""
            },
            fileList:[]
        }
    },
    methods: {
        //添加用户
        addClient() {
            this.$axios.post('/user/register', this.user).then((request => {
                console.log(request)
                if (request.data.code == 200) {
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
            this.user.userName = ""
            this.user.userPhone = ""
            this.user.userAccount = ""
            this.user.userPassword = ""
        },
        //重制
        resetForm() {
            //清空输入框
            this.user.userName = ""
            this.user.userPhone = ""
            this.user.userAccount = ""
            this.user.userPassword = ""
        }
    },
}

</script>