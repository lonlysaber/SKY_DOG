<template>
    <div class="content-box">
        <div class="container">
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
                    <el-form-item label="面积（㎡）">
                        <el-input v-model="house.houseArea" placeholder="请输入面积"></el-input>
                    </el-form-item>
                    <el-form-item label="价格(万元)">
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
            </div>
        </div>
    </div>

</template>

<script>
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
                houseStatus: "在售",
                imageId: 10001,
                layoutType: "",
            },
            clientData: [],
            empData: [],
            fileList: [],
            img: {
                img1: "",
                img2: "",
                img3: "",
                img4: "",
                img5: "",
            },
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

        //添加房源
        addHouse() {
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
            var address = "../../assets/img/admin.jpeg"
            // this.img.img1 = address + this.fileList[0].name
            this.img.img1 = this.fileList[0].url
            if (this.fileList.length > 1) {
                // this.img.img2 = address + this.fileList[1].name
                this.img.img2 = this.fileList[1].url
                if (this.fileList.length > 2) {
                    // this.img.img3 = address + this.fileList[2].name
                    this.img.img3 = this.fileList[2].url
                    if (this.fileList.length > 3) {
                        // this.img.img4 = address + this.fileList[3].name
                        this.img.img4 = this.fileList[3].url
                        if (this.fileList.length > 3) {
                            // this.img.img5 = address + this.fileList[4].name
                            this.img.img5 = this.fileList[4].url
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
option {
    margin-left: 50px;
}
</style>