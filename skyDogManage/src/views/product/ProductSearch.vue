<template>
    <div class="content-box">
        <div class="container">
            <!-- 区间索引 -->
            <div class="section">
                <el-input v-model="minPrice" class="input" placeholder="请输入最低价格(万元)"></el-input>
                <el-input v-model="maxPrice" class="input" placeholder="请输入最高价格(万元)"></el-input>
                &nbsp;&nbsp;
                <el-button type="primary" @click="queryByPrice">价格区间索引</el-button>
            </div>
            <br>
            <div class="section">
                <el-input v-model="minHouseArea" class="input" placeholder="请输入最小面积（㎡）"></el-input>
                <el-input v-model="maxHouseArea" class="input" placeholder="请输入最大面积（㎡）"></el-input>
                &nbsp;&nbsp;
                <el-button type="primary" @click="queryByArea">面积区间索引</el-button>
            </div>
            <br>
            <!-- 房源索引 -->
            <div class="form">
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
                    <el-form-item label="户型">
                        <el-input v-model="house.layoutType" placeholder="请输入户型"></el-input>
                    </el-form-item>
                    <el-form-item label="用户">
                        <el-select v-model="house.clientId" placeholder="请选择用户" clearable>
                            <el-option v-for="item in clientData" :key="item.value" :label="item.clientName"
                                :value="item.clientId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="员工">
                        <el-select v-model="house.empId" placeholder="请选择员工" clearable>
                            <el-option v-for="item in empData" :key="item.value" :label="item.empName"
                                :value="item.empId">
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
                    <el-form-item class="option">
                        <el-button type="primary" @click="searchHouse">搜索</el-button>
                        <el-button @click="resetForm">重置</el-button>
                    </el-form-item>
                </el-form>
                <!-- 搜索结果弹窗 -->
                <el-dialog title="搜索结果" :visible.sync="dialogSearchVisible" :modal-append-to-body='false'>
                    <el-table :data="searchOrder" border highlight-current-row
                        :header-cell-style="{ 'text-align': 'center' }" :cell-style="{ 'text-align': 'center' }"
                        style="width: 100%; margin-top: 8px; text-align: center">
                        <el-table-column prop="houseId" label="房源编号" width="90">
                        </el-table-column>
                        <el-table-column prop="clientId" label="客户编号" width="90">
                        </el-table-column>
                        <el-table-column prop="city" label="城市" width="90">
                        </el-table-column>
                        <el-table-column prop="address" label="地址" width="120">
                        </el-table-column>
                        <el-table-column prop="cellName" label="小区" width="160">
                        </el-table-column>
                        <el-table-column prop="houseArea" label="面积（㎡）" width="70">
                        </el-table-column>
                        <el-table-column prop="housePrice" label="价格(万元)" width="100">
                        </el-table-column>
                        <el-table-column prop="layoutType" label="户型" width="120">
                        </el-table-column>
                        <el-table-column prop="houseType" label="房源类型" width="120">
                        </el-table-column>
                        <el-table-column prop="houseOrientation" label="朝向" width="100">
                        </el-table-column>
                        <el-table-column prop="houseStatus" label="状态" width="80" sortable>
                        </el-table-column>
                    </el-table>
                </el-dialog>
            </div>
        </div>
    </div>

</template>

<script>
export default {
    data() {
        return {
            searchOrder: [],
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
                imageId: null,
                layoutType: "",
            },
            maxPrice: "",
            minPrice: "",
            minHouseArea: "",
            maxHouseArea: "",

            clientData: [],
            empData: [],
            dialogSearchVisible: false,
        }
    },
    created() {
        this.getClientData()
        this.getEmpData()
    },
    methods: {
        //获取用户信息
        getClientData() {
            this.$axios({
                url: "/client/getClient",//请求的后台接口
                method: "get",//get请求方式
            }).then(response => {
                this.clientData = response.data.data
                console.log(this.clientData)
            }).catch(error => {
                //请求失败
            });
        },

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

        //价格区间搜索
        queryByPrice() {
            console.log(this.maxPrice)
            console.log(this.minPrice)
            this.$axios.get(`/house/queryByPrice/${this.minPrice}&&${this.maxPrice}`).then((request => {
                console.log(request.data.data);
                this.searchOrder = request.data.data
                console.log(this.searchOrder);
                this.dialogSearchVisible = true
                this.$message({
                    showClose: true,
                    message: '查询成功',
                    type: 'success'
                });
            })).catch((error) => {
                console.log(error);
            })
            this.maxPrice = "",
                this.minPrice = ""
        },

        //面积区间搜索
        queryByArea() {
            console.log(this.maxHouseArea)
            console.log(this.minHouseArea)
            this.$axios.get(`/house/queryByArea/${this.minHouseArea}&&${this.maxHouseArea}`).then((request => {
                console.log(request.data.data);
                this.searchOrder = request.data.data
                console.log(this.searchOrder);
                this.dialogSearchVisible = true
                this.$message({
                    showClose: true,
                    message: '查询成功',
                    type: 'success'
                });
            })).catch((error) => {
                console.log(error);
            })
            this.maxHouseArea = "",
                this.minHouseArea = ""
        },

        //搜索房源
        searchHouse() {
            console.log(this.house);
            this.$axios.post('/house/queryCondition', this.house).then((request => {
                console.log(request.data.data)
                this.searchOrder = request.data.data
                console.log(this.searchOrder)
                this.dialogSearchVisible = true
                this.$message({
                    showClose: true,
                    message: '查询成功',
                    type: 'success'
                });
            })).catch((error) => {
                console.log(error);
            })
            //清空输入框
            this.house.city = ""
            this.house.address = ""
            this.house.cellName = ""
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
            this.house.clientId = ""
            this.house.empId = ""
            this.house.houseType = ""
            this.house.houseOrientation = ""
            this.house.layoutType = ""
        }
    }
}
</script>

<style lang="scss" scoped>
option {
    margin-left: 50px;
}

.section {
    margin-left: 100px;
}

.input {
    width: 200px;
}
</style>