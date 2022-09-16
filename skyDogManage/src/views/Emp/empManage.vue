<template>
    <div class="content-box">
        <div class="container">
            <!-- 根据条件搜索员工 -->
            <el-row>
                <el-input class="input" placeholder="请输入编号" v-model="searchID" clearable>
                </el-input>
                <el-button @click="queryEmpById(searchID)">搜索编号</el-button>
                &nbsp;&nbsp;
                <el-input class="input" placeholder="请输入姓名" v-model="searchName" clearable>
                </el-input>
                <el-button @click="queryEmpByName(searchName)">搜索姓名</el-button>
                &nbsp;&nbsp;
                <el-input class="input" placeholder="请输入手机号" v-model="searchPhone" clearable>
                </el-input>
                <el-button @click="queryEmpByPhone(searchPhone)">搜索手机号</el-button>
            </el-row>
            <!-- 搜索结果弹窗 -->
            <el-dialog title="搜索结果" :visible.sync="dialogSearchVisible" :modal-append-to-body='false'>
                <el-table :data="searchEmp" border highlight-current-row :header-cell-style="{ 'text-align': 'center' }"
                    :cell-style="{ 'text-align': 'center' }" style="width: 100%; margin-top: 8px; text-align: center">
                    <el-table-column prop="adminId" label="管理员ID">
                    </el-table-column>
                    <el-table-column prop="empId" label="员工ID">
                    </el-table-column>
                    <el-table-column prop="empName" label="姓名">
                    </el-table-column>
                    <el-table-column prop="empAccount" label="登录名">
                    </el-table-column>
                    <el-table-column prop="empPhone" label="电话">
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="150">
                        <template slot-scope="scope">
                            <!-- 修改信息弹窗 -->
                            <el-button @click="updataEmp(scope.row)" size="small">修改</el-button>&nbsp;
                            <el-dialog title="表单弹框" :visible.sync="dialogVisible" width="40%"
                                :modal-append-to-body='false'>
                                <el-form ref="updateform" :model="update_emp" label-width="100px">
                                    <el-form-item label="员工姓名">
                                        <el-input v-model="update_emp.empName"></el-input>
                                    </el-form-item>
                                    <el-form-item label="手机号">
                                        <el-input v-model="update_emp.empPhone"></el-input>
                                    </el-form-item>
                                    <el-form-item label="登录名">
                                        <el-input v-model="update_emp.empAccount"></el-input>
                                    </el-form-item>
                                    <el-form-item label="员工密码">
                                        <el-input v-model="update_emp.empPassword" show-password></el-input>
                                    </el-form-item>
                                    <el-form-item label="员工入职时间">
                                        <el-input v-model="update_emp.hireDate"></el-input>
                                    </el-form-item>
                                </el-form>
                                <div slot="footer" class="dialog-footer">
                                    <el-button @click="dialogVisible = false">取 消</el-button>
                                    <el-button type="primary" @click="updateConfirm()">确 定</el-button>
                                </div>
                            </el-dialog>
                            <el-button @click="delEmp(scope.row)" type="danger" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-dialog>
            <!-- 员工信息 -->
            <el-table :data="empData.slice((currentPage - 1) * pagesize, currentPage * pagesize)" border
                highlight-current-row :header-cell-style="{ 'text-align': 'center' }"
                :cell-style="{ 'text-align': 'center' }" style="width: 100%; margin-top: 8px; text-align: center"
                :default-sort="{ prop: 'date', order: 'descending' }">
                <el-table-column prop="adminId" label="管理员ID">
                </el-table-column>
                <el-table-column prop="empId" label="员工ID">
                </el-table-column>
                <el-table-column prop="empName" label="姓名">
                </el-table-column>
                <el-table-column prop="empAccount" label="登录名">
                </el-table-column>
                <el-table-column prop="empPhone" label="电话">
                </el-table-column>
                <el-table-column prop="hireDate" label="入职日期" sortable>
                </el-table-column>
                <el-table-column fixed="right" label="查询" width="110">
                    <template slot-scope="scope">
                        <!-- 查询员工负责房源 -->
                        <el-button @click="queryMyManageHouse(scope.row)" type="text" size="small">房源</el-button>
                        <el-dialog title="员工负责房源" :visible.sync="dialogTableVisible" :modal-append-to-body='false'>
                            <el-table :data="houseData">
                                <el-table-column property="houseId" label="房屋编号"></el-table-column>
                                <el-table-column property="city" label="城市"></el-table-column>
                                <el-table-column property="address" label="地址"></el-table-column>
                                <el-table-column property="cellName" label="小区"></el-table-column>
                                <el-table-column property="layoutType" label="户型"></el-table-column>
                                <el-table-column property="houseArea" label="面积"></el-table-column>
                                <el-table-column property="housePrice" label="价格"></el-table-column>
                                <el-table-column property="houseOrientation" label="朝向"></el-table-column>
                            </el-table>
                        </el-dialog>
                        <!-- 查询员工负责订单 -->
                        <el-button @click="queryPurchase(scope.row)" type="text" size="small">订单</el-button>
                        <el-dialog title="员工负责订单" :visible.sync="dialogTableVisible1" :modal-append-to-body='false'>
                            <el-table :data="purchaseData">
                                <el-table-column property="orderId" label="订单编号"></el-table-column>
                                <el-table-column property="houseId" label="房源编号"></el-table-column>
                                <el-table-column property="clientId" label="用户编号"></el-table-column>
                                <el-table-column property="empId" label="员工编号"></el-table-column>
                                <el-table-column property="salePrice" label="成交额"></el-table-column>
                                <el-table-column property="buildTime" label="订单时间"></el-table-column>
                                <el-table-column property="orderStatus" label="订单状态"></el-table-column>
                            </el-table>
                        </el-dialog>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="150">
                    <template slot-scope="scope">
                        <!-- 修改信息弹窗 -->
                        <el-button @click="updataEmp(scope.row)" size="small">修改</el-button>&nbsp;
                        <el-dialog title="表单弹框" :visible.sync="dialogVisible" width="40%" :modal-append-to-body='false'>
                            <el-form ref="updateform" :model="update_emp" label-width="100px">
                                <el-form-item label="员工姓名">
                                    <el-input v-model="update_emp.empName"></el-input>
                                </el-form-item>
                                <el-form-item label="手机号">
                                    <el-input v-model="update_emp.empPhone"></el-input>
                                </el-form-item>
                                <el-form-item label="登录名">
                                    <el-input v-model="update_emp.empAccount"></el-input>
                                </el-form-item>
                                <el-form-item label="员工密码">
                                    <el-input v-model="update_emp.empPassword" show-password></el-input>
                                </el-form-item>
                                <el-form-item label="员工入职时间">
                                    <el-input v-model="update_emp.hireDate"></el-input>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer">
                                <el-button @click="dialogVisible = false">取 消</el-button>
                                <el-button type="primary" @click="updateConfirm()">确 定</el-button>
                            </div>
                        </el-dialog>
                        <el-button @click="delEmp(scope.row)" type="danger" size="small">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination @size-change="size_change" @current-change="current_change" :current-page="currentPage"
                :page-sizes="[9, 20, 30]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper" :total="empData.length
                ">
            </el-pagination>
        </div>
    </div>
</template>

<script>
import Cookies from "js-cookie"
export default {
    data() {
        return {
            searchID: "",
            searchName: "",
            searchPhone: "",
            searchEmp: [],
            empData: [],
            houseData: [],
            purchaseData: [],
            empId: "",
            total: 0,//总条目数
            pagesize: 9,//每页显示条目个数
            currentPage: 1,//当前页数
            update_emp: {
                empAccount: "",
                empName: "",
                empPassword: "",
                empPhone: "",
                hireDate: ""
            },
            Admin: "",  //管理员权限判断
            dialogSearchVisible: false,  //员工查询结果弹出框
            dialogTableVisible: false,  //房源查询弹出框
            dialogTableVisible1: false, //订单查询弹出框
            dialogVisible: false, //修改表单弹出框
        }
    },
    created() {
        this.getEmpData()
    },
    methods: {
        //监听 pagesize 改变的事件
        size_change(newSize) {
            this.pagesize = newSize
        },
        //监听 页码值 改变的事件
        current_change(newPage) {
            this.currentPage = newPage
        },
        
        // 日期转换
		  formateDate(emp) {
			  for (var data in emp){
				  // 数据拆分
				   let arr = emp[data].hireDate.split('T');
				   let d = arr[0];
				   emp[data].hireDate=d;
				   // 将对象重新赋值
				   this.empData = emp 
			  }
		  },

        //获取客户信息
        getEmpData() {
            this.$axios({
                url: "/emp/getEmp",//请求的后台接口
                method: "get",//get请求方式
            }).then(response => {
                console.log(response)
                this.empData = response.data.data
                console.log(this.empData)
                this.formateDate(this.empData)
            }).catch(error => {
                //请求失败
            });
        },

        //根据ID搜索客户
        queryEmpById() {
            console.log(this.searchID)
            this.$axios({
                url: "/emp/queryCondition",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    empId: this.searchID
                }
            }).then(response => {
                if (response.data.data != null) {
                    console.log(response)
                    this.searchEmp = response.data.data
                    console.log(this.searchEmp)
                    this.dialogSearchVisible = true
                    this.$message({
                        showClose: true,
                        message: '查询成功',
                        type: 'success'
                    });
                } else {
                    this.$message.error('查询失败');
                }
            }).catch(error => {
                console.log(error)
            });
            this.searchID = ""
        },

        //根据姓名搜索客户
        queryEmpByName() {
            console.log(this.searchName)
            this.$axios({
                url: "/emp/queryCondition",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    empName: this.searchName
                }
            }).then(response => {
                if (response.data.data != null) {
                    console.log(response)
                    this.searchEmp = response.data.data
                    console.log(this.searchEmp)
                    this.dialogSearchVisible = true
                    this.$message({
                        showClose: true,
                        message: '查询成功',
                        type: 'success'
                    });
                } else {
                    this.$message.error('查询失败');
                }
            }).catch(error => {
                console.log(error)
            });
            this.searchName = ""
        },

        //根据手机号搜索客户
        queryEmpByPhone() {
            console.log(this.searchPhone)
            this.$axios({
                url: "/emp/queryCondition",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    empPhone: this.searchPhone
                }
            }).then(response => {
                if (response.data.data != null) {
                    console.log(response)
                    this.searchEmp = response.data.data
                    console.log(this.searchEmp)
                    this.dialogSearchVisible = true
                    this.$message({
                        showClose: true,
                        message: '查询成功',
                        type: 'success'
                    });
                } else {
                    this.$message.error('查询失败');
                }
            }).catch(error => {
                console.log(error)
            });
            this.searchPhone = ""
        },

        //查询员工负责房源
        queryMyManageHouse(emp) {
            this.empId = emp.empId
            console.log(this.empId)
            this.$axios({
                url: "/emp/queryMyManageHouse",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    empId: this.empId
                }
            }).then(response => {
                this.houseData = response.data.data
                console.log(this.houseData)
                this.dialogTableVisible = true
            }).catch(error => {
                console.log(error)
            });
        },

        //查询员工负责订单
        queryPurchase(emp) {
            this.empId = emp.empId
            console.log(this.empId)
            this.$axios({
                url: "/emp/queryMyManageOrder",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    empId: this.empId
                }
            }).then(response => {
                this.purchaseData = response.data.data
                console.log(this.purchaseData)
                this.dialogTableVisible1 = true
            }).catch(error => {
                console.log(error)
            });
        },

        // 通过弹出框进行数据的提交修改
        updataEmp(emp) {
            this.Admin = Cookies.get('adminId')
            if (this.Admin == 10001) {
                this.update_emp = emp;
                console.log(this.update_emp);
                this.dialogVisible = true;
            }
            else {
                this.$message({
                    showClose: true,
                    message: '对不起，您没有管理权限',
                    type: 'error'
                });
            }
        },

        //提交修改
        updateConfirm() {
            this.$axios({
                url: "/emp/updateEmp",
                method: "post",
                data:
                    this.update_emp,
                header: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            }).then(res => {
                if (res.data != null) {
                    this.dialogVisible = false;
                };
                this.$message({
                    showClose: true,
                    message: '修改成功！',
                    type: 'success'
                });
            })
        },

        //删除员工
        delEmp(emp) {
            this.Admin = Cookies.get('adminId')
            console.log(this.Admin);
            if (this.Admin == 10001) {
                this.empId = emp.empId
                console.log(this.empId)
                this.$confirm('此操作将永久删除该员工, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    this.$axios.get(`/emp/deleteEmp/${this.empId}`).then((request => {
                        console.log("删除成功！");
                        this.getEmpData()
                    })).catch((error) => {
                        console.log(error);
                    })
                    this.$message({
                        showClose: true,
                        type: 'success',
                        message: '删除成功!'
                    });
                }).catch(() => {
                    this.$message({
                        showClose: true,
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
            else {
                this.$message({
                    showClose: true,
                    message: '对不起，您没有管理权限',
                    type: 'error'
                });
            }

        },
    }
}
</script>

<style lang="scss" scoped>
.input {
    width: 200px;
}
</style>