<template>
    <div class="content-box">
        <div class="container">
            <el-form ref="insertform" :model="insert_Emp" label-width="80px">
                <el-form-item label="入职时间">
                    <div class="block">
                        <span class="demonstration"></span>
                        <el-date-picker v-model="insert_Emp.hireDate" type="datetime" placeholder="选择日期时间">
                        </el-date-picker>
                    </div>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="insert_Emp.empName" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="登录名">
                    <el-input v-model="insert_Emp.empAccount" placeholder="请输入账户名"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="insert_Emp.empPhone" placeholder="请输入电话号"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="insert_Emp.empPassword" placeholder="请输入密码" show-password></el-input>
                </el-form-item>
                <el-form-item label="权限" label-width="100px">
                    <el-select v-model="insert_Emp.adminId" clearable> 
                        <el-option label="管理员权限" value="10001"></el-option>
                        <el-option label="员工权限" value="10002"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item class="option">
                    <el-button type="primary" @click="addEmp">添加</el-button>
                    <el-button @click="resetForm">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import Cookies from "js-cookie"
export default {
    data() {
        return {
            insert_Emp: {
                adminId: "",
                clientId: "",
                empAccount: "",
                empId: "",
                empName: "",
                empPassword: "",
                empPhone: "",
                hireDate: ""
            },
            Admin: ""  //管理员权限判断
        };
    },
    created() {

    },
    methods: {
        //添加员工
        addEmp() {
            this.Admin = Cookies.get('adminId')
            console.log(this.Admin);
            if (this.Admin == 10001) {
                console.log(this.insert_Emp);
                this.insert_Emp.hireDate = this.formatting(this.insert_Emp.hireDate);
                this.$axios.post('/emp/addEmp', this.insert_Emp).then((request => {
                    if (request.data.message == "添加成功") {
                        console.log("添加成功！");
                        this.$message({
                            showClose: true,
                            message: '添加员工成功！',
                            type: 'success'
                        });
                    }
                    else {
                        this.$message({
                            showClose: true,
                            message: '添加失败！',
                            type: 'error'
                        });
                    }
                    console.log(request)
                })).catch((error) => {
                    console.log(error);
                })
                // //清空输入框
                this.insert_Emp.hireDate = ""
                this.insert_Emp.empName = ""
                this.insert_Emp.empAccount = ""
                this.insert_Emp.empPhone = ""
                this.insert_Emp.empPassword = ""
                this.insert_Emp.adminId = ""
            }
            else {
                this.$message({
                    showClose: true,
                    message: '对不起，您没有管理权限',
                    type: 'error'
                });
            }
        },
        //重制
        resetForm() {
            //清空输入框
            this.insert_Emp.hireDate = ""
            this.insert_Emp.empName = ""
            this.insert_Emp.empAccount = ""
            this.insert_Emp.empPhone = ""
            this.insert_Emp.empPassword = ""
            this.insert_Emp.adminId = ""
        },

        formatting(time) {
            var time = new Date(time);
            var y = time.getFullYear();
            var m = time.getMonth() + 1;
            var d = time.getDate();
            var h = time.getHours();
            var mm = time.getMinutes();
            var s = time.getSeconds();
            return (
                y +
                "-" +
                this.repair0(m) +
                "-" +
                this.repair0(d) +
                " " +
                this.repair0(h) +
                ":" +
                this.repair0(mm) +
                ":" +
                this.repair0(s)
            );
        },
        repair0(m) {
            return m < 10 ? '0' + m : m
        },

    },
};
</script>

<style lang="scss" scoped>
</style>