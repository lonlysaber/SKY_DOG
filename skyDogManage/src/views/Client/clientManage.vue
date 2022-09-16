<template>
    <div class="content-box">
        <div class="container">
            <!-- 根据条件搜索客户 -->
            <el-row>
                <el-input class="input" placeholder="请输入编号" v-model="searchID" clearable>
                </el-input>
                <el-button @click="queryClientById(searchID)">搜索编号</el-button>
                &nbsp;&nbsp;
                <el-input class="input" placeholder="请输入姓名" v-model="searchName" clearable>
                </el-input>
                <el-button @click="queryClientByName(searchName)">搜索姓名</el-button>
                &nbsp;&nbsp;
                <el-input class="input" placeholder="请输入手机号" v-model="searchPhone" clearable>
                </el-input>
                <el-button @click="queryClientByPhone(searchPhone)">搜索手机号</el-button>
            </el-row>
            <!-- 搜索结果弹窗 -->
            <el-dialog title="搜索结果" :visible.sync="dialogSearchVisible" :modal-append-to-body='false'>
                <el-table :data="searchClient" border highlight-current-row
                    :header-cell-style="{ 'text-align': 'center' }" :cell-style="{ 'text-align': 'center' }"
                    style="width: 100%; margin-top: 8px; text-align: center">
                    <el-table-column prop="clientId" label="用户ID" width="80">
                    </el-table-column>
                    <el-table-column prop="clientName" label="姓名" width="80">
                    </el-table-column>
                    <el-table-column prop="clientAccount" label="登录名">
                    </el-table-column>
                    <el-table-column prop="clientPhone" label="手机">
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="150">
                        <template slot-scope="scope">
                            <!-- 修改客户信息 -->
                            <el-button @click="updataClient(scope.row)" size="small">修改</el-button>&nbsp;
                            <el-dialog title="修改信息" :visible.sync="dialogVisible" width="30%"
                                :modal-append-to-body='false'>
                                <el-form ref="updateform" :model="update_Client" label-width="80px">
                                    <el-form-item label="用户姓名">
                                        <el-input v-model="update_Client.clientName"></el-input>
                                    </el-form-item>
                                    <el-form-item label="手机号">
                                        <el-input v-model="update_Client.clientPhone"></el-input>
                                    </el-form-item>
                                    <el-form-item label="登录名">
                                        <el-input v-model="update_Client.clientAccount"></el-input>
                                    </el-form-item>
                                    <el-form-item label="用户密码">
                                        <el-input v-model="update_Client.clientPassword" show-password></el-input>
                                    </el-form-item>
                                </el-form>
                                <div slot="footer" class="dialog-footer">
                                    <el-button @click="dialogVisible = false">取 消</el-button>
                                    <el-button type="primary" @click="updateConfirm()">确 定</el-button>
                                </div>
                            </el-dialog>
                            <!-- 删除 -->
                            <el-button @click="delClient(scope.row)" type="danger" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-dialog>
            <!-- 客户信息 -->
            <el-table :data="clientData.slice((currentPage - 1) * pagesize, currentPage * pagesize)" border
                highlight-current-row :header-cell-style="{ 'text-align': 'center' }"
                :cell-style="{ 'text-align': 'center' }" style="width: 100%; margin-top: 8px; text-align: center">
                <el-table-column prop="clientId" label="用户ID" width="180">
                </el-table-column>
                <el-table-column prop="clientName" label="姓名" width="180">
                </el-table-column>
                <el-table-column prop="clientAccount" label="登录名">
                </el-table-column>
                <el-table-column prop="clientPhone" label="手机">
                </el-table-column>
                <el-table-column fixed="right" label="查询" width="110">
                    <template slot-scope="scope">
                        <!-- 查询用户房源 -->
                        <el-button @click="queryMyHouse(scope.row)" type="text" size="small">房源</el-button>
                        <el-dialog title="用户房源" :visible.sync="dialogTableVisible" :modal-append-to-body='false'>
                            <el-table :data="myhouseData">
                                <el-table-column property="houseId" label="房屋编号"></el-table-column>
                                <el-table-column property="city" label="城市"></el-table-column>
                                <el-table-column property="address" label="地址"></el-table-column>
                                <el-table-column property="cellName" label="小区"></el-table-column>
                                <el-table-column property="layoutType" label="户型"></el-table-column>
                                <el-table-column property="houseArea" label="面积" width="40"></el-table-column>
                                <el-table-column property="housePrice" label="价格" width="40"></el-table-column>
                                <el-table-column property="houseOrientation" label="朝向" width="40"></el-table-column>
                            </el-table>
                        </el-dialog>
                        <!-- 查询用户订单 -->
                        <el-button @click="queryPurchase(scope.row)" type="text" size="small">订单</el-button>
                        <el-dialog title="用户订单" :visible.sync="dialogTableVisible1" :modal-append-to-body='false'>
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
                        <!-- 修改客户信息 -->
                        <el-button @click="updataClient(scope.row)" size="small">修改</el-button>&nbsp;
                        <el-dialog title="修改信息" :visible.sync="dialogVisible" width="30%" :modal-append-to-body='false'>
                            <el-form ref="updateform" :model="update_Client" label-width="80px">
                                <el-form-item label="用户姓名">
                                    <el-input v-model="update_Client.clientName"></el-input>
                                </el-form-item>
                                <el-form-item label="手机号">
                                    <el-input v-model="update_Client.clientPhone"></el-input>
                                </el-form-item>
                                <el-form-item label="登录名">
                                    <el-input v-model="update_Client.clientAccount"></el-input>
                                </el-form-item>
                                <el-form-item label="用户密码">
                                    <el-input v-model="update_Client.clientPassword" show-password></el-input>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer">
                                <el-button @click="dialogVisible = false">取 消</el-button>
                                <el-button type="primary" @click="updateConfirm()">确 定</el-button>
                            </div>
                        </el-dialog>
                        <!-- 删除 -->
                        <el-button @click="delClient(scope.row)" type="danger" size="small">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination @size-change="size_change" @current-change="current_change" :current-page="currentPage"
                :page-sizes="[9, 20, 30]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper" :total="clientData.length
                ">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            searchID: "",
            searchName: "",
            searchPhone: "",
            searchClient: [],
            clientData: [],
            myhouseData: [],
            purchaseData: [],
            clientId: "",
            total: 0,//总条目数
            pagesize: 9,//每页显示条目个数
            currentPage: 1,//当前页数
            update_Client: {
                clientAccount: "",
                clientName: "",
                clientPassword: "",
                clientPhone: ""
            },
            dialogSearchVisible: false,  //客户查询结果弹出框
            dialogTableVisible: false,  //房源查询弹出框
            dialogTableVisible1: false, //订单查询弹出框
            dialogVisible: false,       //修改表单弹出框
        }
    },
    created() {
        this.getClientData()
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

        //获取客户信息
        getClientData() {
            this.$axios({
                url: "/client/getClient",//请求的后台接口
                method: "get",//get请求方式
            }).then(response => {
                this.clientData = response.data.data
            }).catch(error => {
                //请求失败
            });
        },

        //根据ID搜索客户
        queryClientById() {
            this.$axios({
                url: "/client/queryCondition",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    clientId: this.searchID
                }
            }).then(response => {
                console.log(response)
                if (response.data.code == 200) {
                    this.searchClient = response.data.data
                    this.dialogSearchVisible = true
                    this.$message({
                        showClose: true,
                        message: '查询成功',
                        type: 'success'
                    });
                } else {
                    this.$message({
                        showClose: true,
                        message: '查询失败！',
                        type: 'error'
                    });
                }
            }).catch(error => {
                console.log(error)
            });
            this.searchID = ""
        },

        //根据姓名搜索客户
        queryClientByName() {
            this.$axios({
                url: "/client/queryCondition",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    clientName: this.searchName
                }
            }).then(response => {
                if (response.data.data != null) {
                    this.searchClient = response.data.data
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
        queryClientByPhone() {
            this.$axios({
                url: "/client/queryCondition",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    clientPhone: this.searchPhone
                }
            }).then(response => {
                if (response.data.data != null) {
                    this.searchClient = response.data.data
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

        //查询用户房源
        queryMyHouse(client) {
            this.clientId = client.clientId
            this.$axios({
                url: "/client/queryMyHouse",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    clientId: this.clientId
                }
            }).then(response => {
                this.myhouseData = response.data.data
                this.dialogTableVisible = true
            }).catch(error => {
                console.log(error)
            });
        },

        //查询用户订单
        queryPurchase(client) {
            this.clientId = client.clientId
            this.$axios({
                url: "/client/queryPurchase",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    clientId: this.clientId
                }
            }).then(response => {
                this.purchaseData = response.data.data
                this.dialogTableVisible1 = true
            }).catch(error => {
                console.log(error)
            });
        },

        // 通过弹出框进行数据的提交修改
        updataClient(client) {
            this.update_Client = client;
            this.dialogVisible = true;
        },

        //修改提交
        updateConfirm() {
            this.$axios({
                url: "/client/updateClient",
                method: "post",
                data:
                    this.update_Client,
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

        //删除客户
        delClient(client) {
            this.clientId = client.clientId
            this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                this.$axios.get(`/client/deleteClient/${this.clientId}`).then((request => {
                    this.$message({
                        showClose: true,
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getClientData()
                })).catch((error) => {
                    this.$message({
                        showClose: true,
                        message: '删除失败，该用户存在订单或者房源！',
                        type: 'warning'
                    });
                })
            }).catch(() => {
                this.$message({
                    showClose: true,
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
    }
}
</script>

<style lang="scss" scoped>
.input {
    width: 200px;
}
</style>