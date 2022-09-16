<template>
    <div class="top">
        <div class="user">
            <div class="user-avator">
                <img src="@/assets/img/admin.jpeg" />
            </div>
            <!-- 用户名下拉菜单 -->
            <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                <span class="el-dropdown-link">{{ showUsername }}<i class="el-icon-caret-bottom"></i></span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="Info">个人信息</el-dropdown-item>
                    <el-dropdown-item command="house">个人房源</el-dropdown-item>
                    <el-dropdown-item command="order">个人订单</el-dropdown-item>
                    <el-dropdown-item command="add">发布房源</el-dropdown-item>
                    <el-dropdown-item command="Update">修改密码</el-dropdown-item>
                    <el-dropdown-item command="loginout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <!-- 个人信息弹出框 -->
        <el-dialog title="个人信息" :visible.sync="dialogTableVisible" :modal-append-to-body='false'>
            <el-descriptions title="个人信息" direction="vertical" :column="4" border>
                <el-descriptions-item label="姓名">{{ Client.clientName }}</el-descriptions-item>
                <el-descriptions-item label="手机号">{{ Client.clientPhone }}</el-descriptions-item>
                <el-descriptions-item label="登录名" :span="2">{{ Client.clientAccount }}</el-descriptions-item>
                <el-descriptions-item label="编号">
                    <el-tag size="small">{{ Client.clientId }}</el-tag>
                </el-descriptions-item>
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
        <!-- 用户房源 -->
        <el-dialog title="用户房源" :visible.sync="dialogHouseVisible" :modal-append-to-body='false'>
            <el-table :data="myhouseData">
                <el-table-column property="houseId" label="房屋编号"></el-table-column>
                <el-table-column property="city" label="城市"></el-table-column>
                <el-table-column property="address" label="地址"></el-table-column>
                <el-table-column property="cellName" label="小区"></el-table-column>
                <el-table-column property="layoutType" label="户型"></el-table-column>
                <el-table-column property="houseArea" label="面积"></el-table-column>
                <el-table-column property="housePrice" label="价格"></el-table-column>
                <el-table-column property="houseOrientation" label="朝向"></el-table-column>
                <el-table-column fixed="right" label="操作">
                    <template slot-scope="scope">
                        <el-button @click="delHouse(scope.row)" type="danger">下架</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <!-- 用户订单 -->
        <el-dialog title="用户订单" :visible.sync="dialogOrderVisible" :modal-append-to-body='false'>
            <el-table :data="purchaseData">
                <el-table-column property="orderId" label="订单编号"></el-table-column>
                <el-table-column property="houseId" label="房源编号"></el-table-column>
                <el-table-column property="clientId" label="用户编号"></el-table-column>
                <el-table-column property="empId" label="员工编号"></el-table-column>
                <el-table-column property="salePrice" label="成交额"></el-table-column>
                <el-table-column property="buildTime" label="订单时间"></el-table-column>
                <el-table-column property="orderStatus" label="订单状态"></el-table-column>
                <el-table-column fixed="right" label="操作">
                    <template slot-scope="scope">
                        <el-button @click="orderStatusUpdata(scope.row)" size="small">处理</el-button>
                        <el-dialog title="请选择操作" :visible.sync="dialogUpdataVisible" width="20%"
                            :modal-append-to-body='false'>
                            <el-button type="primary" @click="payment">支付订单</el-button>
                            <el-button type="danger" @click="cancel">取消订单</el-button>
                        </el-dialog>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <!-- 发布房源 -->
        <el-dialog title="发布房源" :visible.sync="dialogAddVisible" :modal-append-to-body='false'>
            <el-form ref="ruleForm" :model="house" label-width="100px">
                <el-form-item label="城市">
                    <el-input v-model="house.city" placeholder="请输入城市"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="house.address" placeholder="请输入地址"></el-input>
                </el-form-item>
                <el-form-item label="小区">
                    <el-input v-model="house.cellName" placeholder="请输入小区"></el-input>
                </el-form-item>
                <el-form-item label="面积（㎡）">
                    <el-input v-model="house.houseArea" placeholder="请输入面积"></el-input>
                </el-form-item>
                <el-form-item label="价格（万元）">
                    <el-input v-model="house.housePrice" placeholder="请输入价格"></el-input>
                </el-form-item>
                <el-form-item label="户型">
                    <el-select v-model="house.layoutType" placeholder="请选择户型">
                        <el-option label="1室1厅1厨1卫" value="1室1厅1厨1卫"></el-option>
                        <el-option label="2室1厅1厨1卫" value="2室1厅1厨1卫"></el-option>
                        <el-option label="3室1厅1厨1卫" value="3室1厅1厨1卫"></el-option>
                        <el-option label="3室1厅1厨2卫" value="3室1厅1厨2卫"></el-option>
                        <el-option label="3室2厅1厨2卫" value="3室2厅1厨2卫"></el-option>
                        <el-option label="4室1厅1厨1卫" value="4室1厅1厨1卫"></el-option>
                        <el-option label="4室2厅1厨1卫" value="4室2厅1厨1卫"></el-option>
                        <el-option label="4室2厅1厨2卫" value="4室2厅1厨2卫"></el-option>
                        <el-option label="4室2厅1厨3卫" value="4室2厅1厨3卫"></el-option>
                        <el-option label="5室1厅1厨1卫" value="5室1厅1厨1卫"></el-option>
                        <el-option label="5室2厅1厨1卫" value="5室2厅1厨1卫"></el-option>
                        <el-option label="5室2厅1厨2卫" value="5室2厅1厨2卫"></el-option>
                        <el-option label="5室3厅1厨3卫" value="5室3厅1厨3卫"></el-option>
                        <el-input v-model="house.layoutType" placeholder="未找到房型?自己输入房型"></el-input>
                    </el-select>
                </el-form-item>
                <el-form-item label="员工">
                    <el-select v-model="house.empId" placeholder="请选择员工">
                        <el-option v-for="item in empData" :key="item.value" :label="item.empName" :value="item.empId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="类型">
                    <el-radio-group v-model="house.houseType">
                        <el-radio label="新房"></el-radio>
                        <el-radio label="二手房"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="朝向">
                    <el-select v-model="house.houseOrientation" placeholder="请选择房源朝向">
                        <el-option label="南" value="南"></el-option>
                        <el-option label="东" value="东"></el-option>
                        <el-option label="北" value="北"></el-option>
                        <el-option label="西" value="西"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item class="option" label="图片">
                    <el-upload class="upload-demo" action="../../assets/img/" :on-preview="handlePreview"
                        :on-remove="handleRemove" :on-progress="upload" :file-list="fileList" list-type="picture">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
                </el-form-item>
                <el-form-item class="option">
                    <el-button type="primary" @click="submitAdd">添加</el-button>
                    <el-button @click="resetForm">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>

<script>
import Cookies from "js-cookie"
export default {
    data() {
        return {
            house: {
                address: "",
                adminId: "",
                cellName: "",
                city: "",
                clientId: "",
                empId: "",
                houseArea: "",
                houseId: "",
                houseOrientation: "",
                housePrice: "",
                houseType: "",
                imageId: '',
                layoutType: "",
                houseStatus: "在售",
            },
            empData: [],  //员工信息
            fileList: [],
            img: {
                img1: "",
                img2: "",
                img3: "",
                img4: "",
                img5: "",
            },
            Updata_order: "",   //处理订单
            submitOrder: "",
            purchaseData: [],
            myhouseData: [],   //房源信息
            Client: {
                clientAccount: "",
                clientName: "",
                clientPassword: "",
                clientPhone: ""
            },
            clientID: "",
            houseID: "",
            accPassword: "",      //新密码
            oldPassword: "",      //旧密码
            dialogInputVisible: false, //修改密码弹出框
            dialogTableVisible: false,//员工个人信息弹出框
            dialogHouseVisible: false,  //客户房源信息弹出框
            dialogOrderVisible: false,  //客户订单信息弹出框
            dialogUpdataVisible: false, //订单处理弹出框
            dialogAddVisible: false,    //发布房源弹出框
        }
    },

    created() {
        this.clientID = Cookies.get('clientId')
        this.getEmpData()
    },

    computed: {
        showUsername() {   //获取用户名
            return Cookies.get('clientName')
        },
    },

    methods: {
        //获取员工信息
        getEmpData() {
            this.$axios({
                url: "/emp/getEmp",//请求的后台接口
                method: "get",//get请求方式
            }).then(response => {
                this.empData = response.data.data
                console.log(this.empData)
            }).catch(error => {
                //请求失败
            });
        },

        // 用户名下拉菜单选择事件
        handleCommand(command) {
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
                console.log(this.clientID)
                this.dialogInputVisible = true
            }
            //个人信息
            if (command == 'Info') {
                console.log(this.clientID)
                this.$axios({
                    url: "/client/getClientById",//请求的后台接口
                    method: "get",//get请求方式
                    params: {
                        clientId: this.clientID
                    }
                }).then(response => {
                    console.log(response)
                    this.Client = response.data.data
                    this.dialogTableVisible = true
                }).catch(error => {
                    console.log(error)
                });
            }
            //查询房源
            if (command == 'house') {
                console.log(this.clientID)
                this.$axios({
                    url: "/client/queryMyHouse",//请求的后台接口
                    method: "get",//get请求方式
                    params: {
                        clientId: this.clientID
                    }
                }).then(response => {
                    this.myhouseData = response.data.data
                    this.dialogHouseVisible = true
                }).catch(error => {
                    console.log(error)
                });
            }
            //查询订单
            if (command == 'order') {
                console.log(this.clientID)
                this.$axios({
                    url: "/client/queryPurchase",//请求的后台接口
                    method: "get",//get请求方式
                    params: {
                        clientId: this.clientID
                    }
                }).then(response => {
                    this.purchaseData = response.data.data
                    this.dialogOrderVisible = true
                }).catch(error => {
                    console.log(error)
                });
            }
            //添加订单
            if (command == 'add') {
                console.log(this.clientID)
                this.dialogAddVisible = true
            }
        },

        //下架房源
        delHouse(house) {
            this.houseID = house.houseId
            console.log(this.houseID)
            this.$confirm('此操作将永久删除该房源, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                this.$axios.get(`/house/delete/${this.houseID}`).then((request => {
                    console.log(request)
                    this.$message({
                        showClose: true,
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.$router.go(0);
                })).catch((error) => {
                    this.$message({
                        showClose: true,
                        message: '删除失败，该房源存在订单！',
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

        //处理订单
        orderStatusUpdata(order) {
            this.Updata_order = order
            console.log(this.Updata_order)
            this.dialogUpdataVisible = true;
        },

        //支付订单
        payment() {
            console.log(this.Updata_order)
            this.$confirm('是否支付该订单?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.Updata_order.orderStatus = "已完成"
                this.$axios({
                    url: "/order/update",
                    method: "post",
                    data:
                        this.Updata_order,
                    header: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }).then((res) => {
                    this.$router.go(0);
                }).catch((err) => { });
                this.$message({
                    showClose: true,
                    type: 'success',
                    message: '支付成功!'
                });
            }).catch(() => {
                this.$message({
                    showClose: true,
                    type: 'info',
                    message: '已取消支付'
                });
            });
        },

        //取消订单
        cancel() {
            console.log(this.Updata_order.orderId)
            console.log(this.Updata_order.houseId)
            this.$confirm('此操作将取消该订单, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios({
                    url: "/order/delete/" + this.Updata_order.orderId,
                    method: "get",
                }).then((res) => {
                    this.$axios.get(`/house/changeStatusById/${this.Updata_order.houseId}`).then((request => {
                    })).catch((error) => {
                    })
                    this.$router.go(0);
                }).catch((err) => { });
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
        },

        //提交修改
        updateConfirm() {
            this.$axios({
                url: "/client/updatePwd",
                method: "get",
                params:
                {
                    accPassword: this.accPassword,
                    clientId: parseInt(this.clientID),
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

        //添加房源
        addHouse() {
            this.house.clientId = this.clientID
            console.log(this.house.imageId);
            console.log(this.house);
            this.$axios.post('/house/add', this.house).then((request => {
                console.log(request);
                if (request.data.message == "添加成功") {
                    console.log("添加成功！");
                    this.$message({
                        showClose: true,
                        message: '添加房源成功！',
                        type: 'success'
                    });
                    this.$router.go(0);
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
            //清空输入框
            this.house.city = ""
            this.house.address = ""
            this.house.cellName = ""
            this.house.houseArea = ""
            this.house.housePrice = ""
            this.house.clientId = ""
            this.house.empId = ""
            this.house.houseType = ""
            this.house.houseOrientation = ""
            this.house.layoutType = ""
        },
        //重制
        resetForm() {
            //清空输入框
            this.house.city = ""
            this.house.address = ""
            this.house.cellName = ""
            this.house.houseArea = ""
            this.house.housePrice = ""
            this.house.clientId = ""
            this.house.empId = ""
            this.house.houseType = ""
            this.house.houseOrientation = ""
            this.house.layoutType = ""
        },
        upload(event, file, fileList) {
            console.log('event');
            console.log(event);
            console.log(file);
            console.log(this.fileList);
            const img = {
                name: file.name,
                url: file.url
            }
            this.fileList.push(img)
        },
        //提交添加
        submitAdd() {
            // var address = "../../assets/img/admin.jpeg"
            var address = "http://localhost/img/"
            this.img.img1 = address + this.fileList[0].name
            // this.img.img1 = this.fileList[0].url
            if (this.fileList.length > 1) {
                this.img.img2 = address + this.fileList[1].name
                // this.img.img2 = this.fileList[1].url
                if (this.fileList.length > 2) {
                    this.img.img3 = address + this.fileList[2].name
                    // this.img.img3 = this.fileList[2].url
                    if (this.fileList.length > 3) {
                        this.img.img4 = address + this.fileList[3].name
                        // this.img.img4 = this.fileList[3].url
                        if (this.fileList.length > 3) {
                            this.img.img5 = address + this.fileList[4].name
                            // this.img.img5 = this.fileList[4].url
                        }
                    }
                }
            }
            console.log(this.img);

            this.$axios.post('/img/add', this.img).then(request => {
                console.log(request);
                this.house.imageId = request.data.data.imgId
                this.addHouse()
            }).catch((error) => {
                console.log(error)
            })
        },
        submitUpload() {
            this.$refs.upload.submit();
        },
        handleRemove(file, fileList) {
            this.fileList = this.fileList.filter((f) => {
                return f.url != file.url
            })
        },
        handlePreview(file) {
            console.log(file);
        }
    }
}
</script>

<style lang="scss" scoped>
.top {
    height: 80px;
    width: 10%;
    margin-top: 20px;
    float: right;
}

.user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}
</style>