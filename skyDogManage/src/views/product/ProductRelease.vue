<template>
    <div class="content-box">
        <div class="container">
            <div class="form">
                <el-form ref="ruleForm" :model="product" label-width="100px">
                    <el-form-item label="商品名">
                        <el-input v-model="product.productName" placeholder="请输入商品名"></el-input>
                    </el-form-item>
                    <el-form-item label="商品描述">
                        <el-input v-model="product.productDec" placeholder="请输入商品描述"></el-input>
                    </el-form-item>
                    <el-form-item label="价格">
                        <el-input v-model="product.productPrice" placeholder="请输入价格"></el-input>
                    </el-form-item>
                    <el-form-item label="商品种类">
                        <el-select v-model="product.categoryId" placeholder="请选择商品种类">
                            <el-option label="手机" value="10001"></el-option>
                            <el-option label="电脑" value="10002"></el-option>
                            <el-option label="数码" value="10003"></el-option>
                            <el-option label="女装" value="10004"></el-option>
                            <el-option label="女鞋" value="10005"></el-option>
                            <el-option label="男装" value="10006"></el-option>
                            <el-option label="男鞋" value="10007"></el-option>
                            <el-option label="内衣" value="10008"></el-option>
                            <el-option label="奢品" value="10009"></el-option>
                            <el-option label="箱包" value="10010"></el-option>
                            <el-option label="美妆" value="10011"></el-option>
                            <el-option label="饰品" value="10012"></el-option>
                            <el-option label="洗护" value="10013"></el-option>
                            <el-option label="运动" value="10014"></el-option>
                            <el-option label="家装" value="10015"></el-option>
                            <el-option label="电器" value="10016"></el-option>
                            <el-option label="车品" value="10017"></el-option>
                            <el-option label="医药" value="10018"></el-option>
                            <el-option label="保健" value="10019"></el-option>
                            <el-option label="食品" value="10020"></el-option>
                            <el-option label="生鲜" value="10021"></el-option>
                            <el-option label="母婴" value="10022"></el-option>
                            <el-option label="企业礼品" value="10023"></el-option>
                            <el-option label="进口" value="10024"></el-option>
                            <el-option label="百货" value="10025"></el-option>
                            <el-option label="其他" value="10026"></el-option>
                            <el-input placeholder="未找到房型?自己输入房型"></el-input>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="商品细节">
                        <el-input v-model="product.productDetail" placeholder="请输入商品描述"></el-input>
                    </el-form-item>
                    <el-form-item label="商品规格">
                        <el-input v-model="product.productDec" placeholder="请输入商品描述"></el-input>
                    </el-form-item>
                    <el-form-item label="用户">
                        <el-select v-model="product.userId" placeholder="请选择用户" clearable>
                            <el-option v-for="item in userData" :key="item.value" :label="item.userName"
                                :value="item.userId">
                            </el-option>
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
                        <el-button type="primary" @click="addProduct">添加</el-button>
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
            product: {
                userId:"",
                adminId: "",
                categoryId:"",
                productName:"",
                productPrice: "",
                productDec:"",
                productDetail:"",
                productStatus: "在售",
                imageId: 10001,
            },
            userData: [],
            fileList: [],
            img: {
                img1:"",
                img2:"",
                img3:"",
                img4:"",
                img5:"",
            },
        }
    },
    created() {
        this.getUserData()
    },
    methods: {
        //获取用户信息
        getUserData() {
            this.$axios({
                url: "/user/getUser",//请求的后台接口
                method: "post",
                data:{
                    pageSize:30,
                    currentPage:1
                }
            }).then(response => {
                this.userData = response.data.data.data
                console.log(response)
            }).catch(error => {
                //请求失败
            });
        },
        //添加商品
        addProduct() {
            console.log(this.product);
            this.$axios.post('/product/add', this.product).then((request => {
                console.log(request);
                if (request.data.message == "添加成功") {
                    console.log("添加成功！");
                    this.$message({
                        showClose: true,
                        message: '添加商品成功！',
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
            this.product.city = ""
            this.product.address = ""
            this.product.cellName = ""
            this.product.productArea = ""
            this.product.productPrice = ""
            this.product.userId = ""
            this.product.empId = ""
            this.product.productType = ""
            this.product.productOrientation = ""
            this.product.layoutType = ""
        },
        //重制
        resetForm() {
            //清空输入框
            this.product.city = ""
            this.product.address = ""
            this.product.cellName = ""
            this.product.productArea = ""
            this.product.productPrice = ""
            this.product.userId = ""
            this.product.empId = ""
            this.product.productType = ""
            this.product.productOrientation = ""
            this.product.layoutType = ""
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
                this.product.imageId = request.data.data.imgId
                this.addProduct()
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