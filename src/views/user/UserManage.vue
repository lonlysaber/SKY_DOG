<template>
    <div class="content-box">
        <div class="container">
            <!-- 根据条件搜索客户 -->
            <el-row>
                <el-input class="input" placeholder="请输入编号" v-model="searchID" clearable>
                </el-input>
                <el-button @click="queryuserById(searchID)">搜索编号</el-button>
                &nbsp;&nbsp;
                <el-input class="input" placeholder="请输入账号" v-model="searchName" clearable>
                </el-input>
                <el-button @click="queryuserByName(searchName)">搜索账号</el-button>
                &nbsp;&nbsp;
                <el-input class="input" placeholder="请输入手机号" v-model="searchPhone" clearable>
                </el-input>
                <el-button @click="queryuserByPhone(searchPhone)">搜索手机号</el-button>
            </el-row>
            <!-- 搜索结果弹窗 -->
            <el-dialog title="搜索结果" :visible.sync="dialogSearchVisible" :modal-append-to-body='false'>
                <el-table :data="searchuser" border highlight-current-row
                    :header-cell-style="{ 'text-align': 'center' }" :cell-style="{ 'text-align': 'center' }"
                    style="width: 100%; margin-top: 8px; text-align: center">
                    <el-table-column prop="userId" label="用户ID" width="80">
                    </el-table-column>
                    <el-table-column prop="userName" label="账号" width="80">
                    </el-table-column>
                    <el-table-column prop="nick" label="昵称">
                    </el-table-column>
                    <el-table-column prop="phone" label="手机">
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="150">
                        <template slot-scope="scope">
                            <!-- 修改客户信息 -->
                            <el-button @click="updatauser(scope.row)" size="small">修改</el-button>&nbsp;
                            <el-dialog title="修改信息" :visible.sync="dialogVisible" width="30%"
                                :modal-append-to-body='false'>
                                <el-form ref="updateform" :model="update_user" label-width="80px">
                                    <el-form-item label="用户账号">
                                        <el-input v-model="update_user.userName"></el-input>
                                    </el-form-item>
                                    <el-form-item label="手机号">
                                        <el-input v-model="update_user.phone"></el-input>
                                    </el-form-item>
                                    <el-form-item label="昵称">
                                        <el-input v-model="update_user.nick"></el-input>
                                    </el-form-item>
                                    <el-form-item label="用户密码">
                                        <el-input v-model="update_user.password" show-password></el-input>
                                    </el-form-item>
                                </el-form>
                                <div slot="footer" class="dialog-footer">
                                    <el-button @click="dialogVisible = false">取 消</el-button>
                                    <el-button type="primary" @click="updateConfirm()">确 定</el-button>
                                </div>
                            </el-dialog>
                            <!-- 删除 -->
                            <el-button @click="deluser(scope.row)" type="danger" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-dialog>
            <!-- 客户信息 -->
            <el-table :data="userData" border
                highlight-current-row :header-cell-style="{ 'text-align': 'center' }"
                :cell-style="{ 'text-align': 'center' }" style="width: 100%; margin-top: 8px; text-align: center">
                <el-table-column prop="userId" label="用户ID" width="180">
                </el-table-column>
                <el-table-column prop="userName" label="账号" width="180">
                </el-table-column>
                <el-table-column prop="nick" label="昵称">
                </el-table-column>
                <el-table-column prop="phone" label="手机">
                </el-table-column>
                <el-table-column prop="gender" label="性别">
                </el-table-column>
                <el-table-column fixed="right" label="查询" width="160">
                    <template slot-scope="scope">
                        <!-- 查询用户商品 -->
                        <el-button @click="queryMyProduct(scope.row)" type="text" size="small">售卖商品</el-button>
                        <el-dialog title="用户商品" :visible.sync="dialogTableVisible" 
                        :modal-append-to-body='false' >
                            <el-table :data="myProductData">
                                <el-table-column property="productId" label="商品编号" width="80"></el-table-column>
                                <el-table-column property="categoryName" label="商品种类" width="80"></el-table-column>
                                <el-table-column property="productName" label="商品名称" width="160"></el-table-column>
                                <el-table-column property="productPrice" label="价格" width="80"></el-table-column>
                                <el-table-column property="productStatus" label="状态" width="80"></el-table-column>
                                <el-table-column property="productDec" label="商品描述" width="160"></el-table-column>
                                <el-table-column property="productDetail" label="商品细节" width="360"></el-table-column>
                            </el-table>
                            <el-table :data="myProductData">

                            </el-table>
                        </el-dialog>
                        <!-- 查询用户订单 -->
                        <el-button @click="queryMyOreder(scope.row)" type="text" size="small">订单</el-button>
                        <el-dialog title="用户订单" :visible.sync="dialogTableVisible1" :modal-append-to-body='false'>
                            <el-table :data="orderData">
                                <el-table-column property="orderId" label="订单编号"></el-table-column>
                                <el-table-column property="productId" label="商品编号"></el-table-column>
                                <el-table-column property="userId" label="用户编号"></el-table-column>
                                <el-table-column property="productCount" label="商品数量"></el-table-column>
                                <el-table-column property="productDto.productPrice" label="单价"></el-table-column>
                                <el-table-column property="createTime" label="订单时间"></el-table-column>
                                <el-table-column property="orderStatus" label="订单状态"></el-table-column>
                            </el-table>
                        </el-dialog>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="150">
                    <template slot-scope="scope">
                        <!-- 修改客户信息 -->
                        <el-button @click="updatauser(scope.row)" size="small">修改</el-button>&nbsp;
                        <el-dialog title="修改信息" :visible.sync="dialogVisible" width="30%" :modal-append-to-body='false'>
                            <el-form ref="updateform" :model="update_user" label-width="80px">
                                <el-form-item label="用户账号">
                                    <el-input v-model="update_user.userName"></el-input>
                                </el-form-item>
                                <el-form-item label="手机号">
                                    <el-input v-model="update_user.phone"></el-input>
                                </el-form-item>
                                <el-form-item label="昵称">
                                    <el-input v-model="update_user.nick"></el-input>
                                </el-form-item>
                                <el-form-item label="用户密码">
                                    <el-input v-model="update_user.password" show-password></el-input>
                                </el-form-item>
                                <el-form-item label="性别">
                                    <el-input v-model="update_user.gender"></el-input>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer">
                                <el-button @click="dialogVisible = false">取 消</el-button>
                                <el-button type="primary" @click="updateConfirm()">确 定</el-button>
                            </div>
                        </el-dialog>
                        <!-- 删除 -->
                        <el-button @click="deluser(scope.row)" type="danger" size="small">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination 
                @size-change="handlesizeChange"
                @current-change="handleCurrentChange"
                :page-size="pageSize" 
                layout="total, sizes, prev, pager, next, jumper" :total="total">
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
            searchuser: [],
            userData: [],
            myProductData: [],
            orderData: [],
            userId: "",
            total: 0,//总条目数
            pageSize: 10,//每页显示条目个数
            currentPage: 1,//当前页数
            update_user: {
                nick: "",
                userName: "",
                password: "",
                phone: ""
            },
            dialogSearchVisible: false,  //客户查询结果弹出框
            dialogTableVisible: false,  //商品查询弹出框
            dialogTableVisible1: false, //订单查询弹出框
            dialogVisible: false,       //修改表单弹出框
        }
    },
    created() {
        this.getUserData()
    },
    methods: {
        handleCurrentChange(val){
            console.log(val);
            this.currentPage = val
            this.getUserData()
        },
        handlesizeChange(val){
            console.log(val);
            this.pageSize = val
            this.getUserData()
        },
        
        //获取客户信息
        getUserData() {
            this.$axios({
                url: "/user/getUser",//请求的后台接口
                method: "post",
                data:{
                    currentPage:this.currentPage,
                    pageSize:this.pageSize
                }
            }).then(response => {
                console.log(response);
                this.userData = response.data.data.data
                this.total = response.data.data.count
            }).catch(error => {
                //请求失败
            });
        },

        //根据ID搜索客户
        queryuserById() {
            this.$axios({
                url: "/user/queryCondition",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    userId: this.searchID
                }
            }).then(response => {
                console.log(response)
                if (response.data.code == 200) {
                    this.searchuser = response.data.data
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

        //根据账号搜索客户
        queryuserByName() {
            this.$axios({
                url: "/user/queryCondition",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    userName: this.searchName
                }
            }).then(response => {
                if (response.data.data != null) {
                    this.searchuser = response.data.data
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
        queryuserByPhone() {
            this.$axios({
                url: "/user/queryCondition",//请求的后台接口
                method: "get",//get请求方式
                params: {
                    phone: this.searchPhone
                }
            }).then(response => {
                if (response.data.data != null) {
                    this.searchuser = response.data.data
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

        //查询用户商品
        queryMyProduct(user) {
            console.log(user);
            this.userId = user.userId
            console.log();
            this.$axios({
                url: "/product/getMyProduct",//请求的后台接口
                method: "post",
                params:{
                    userId: this.userId
                },
            }).then(response => {
                console.log(response);
                this.myProductData = response.data.data
                this.dialogTableVisible = true
            }).catch(error => {
                console.log(error)
            });
        },

        //查询用户订单
        queryMyOreder(user) {
            this.userId = user.userId
            this.$axios({
                url: "/order/getMyOrder",//请求的后台接口
                method: "post",
                data: {
                    userId: this.userId
                }
            }).then(response => {
                console.log(response);
                this.orderData = response.data.data
                this.dialogTableVisible1 = true
            }).catch(error => {
                console.log(error)
            });
        },

        // 通过弹出框进行数据的提交修改
        updatauser(user) {
            this.update_user = user;
            this.dialogVisible = true;
        },

        //修改提交
        updateConfirm() {
            this.$axios({
                url: "/user/updateClient",
                method: "post",
                data:this.update_user,
                header: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            }).then(res => {
                console.log(res);
                if (res.data.code == 204) {
                    this.dialogVisible = false;
                    this.$message({
                        showClose: true,
                        message: '修改成功！',
                        type: 'success'
                    });
                    this.getUserData()
                }else{
                    this.$message({
                        showClose: true,
                        message: '修改失败！',
                        type: 'success'
                    });
                }
                
            })
        },

        //删除客户
        deluser(user) {
            this.userId = user.userId
            this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                this.$axios.get(`/user/deleteuser/${this.userId}`).then((request => {
                    this.$message({
                        showClose: true,
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getuserData()
                })).catch((error) => {
                    this.$message({
                        showClose: true,
                        message: '删除失败，该用户存在订单或者商品！',
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