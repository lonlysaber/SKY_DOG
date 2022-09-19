<template>
    <div class="content-box">
        <div class="container">
            <!-- 根据ID搜索商品 -->
            <el-row>
                <el-input class="input" placeholder="请输入编号" v-model="searchID" clearable>
                </el-input>
                <el-button @click="queryProductById(searchID)">搜索</el-button>
                <el-dialog :visible.sync="dialogSearchVisible" :modal-append-to-body='false'>
                    <el-descriptions title="查询结果" direction="vertical" :column="12" border >
                        <el-descriptions-item label="商品编号" :span="4" >{{ searchProduct.productId }}</el-descriptions-item>
                        <el-descriptions-item label="商品名" :span="6">{{ searchProduct.productName }}</el-descriptions-item>
                        <el-descriptions-item label="评分" :span="2">{{ searchProduct.productGrade }}</el-descriptions-item>
                        <el-descriptions-item label="用户编号" :span="4">{{ searchProduct.userId }}</el-descriptions-item>
                        <el-descriptions-item label="商品描述" :span="6">{{ searchProduct.productDec }}</el-descriptions-item>
                        <el-descriptions-item label="商品种类" :span="2">{{ searchProduct.categoryName }}</el-descriptions-item>
                        <el-descriptions-item label="价格" :span="2">{{ searchProduct.productPrice }}</el-descriptions-item>
                        <el-descriptions-item label="状态" :span="2">{{ searchProduct.productStatus }}</el-descriptions-item>
                        <el-descriptions-item label="销量" :span="2">{{ searchProduct.productSale }}</el-descriptions-item>
                    </el-descriptions>
                </el-dialog>
            </el-row>
            <!-- 商品信息 -->
            <el-table :data="productData"  border
                highlight-current-row :header-cell-style="{ 'text-align': 'center' }"
                :cell-style="{ 'text-align': 'center' }" style="width: 100%; margin-top: 8px; text-align: center">
                <el-table-column prop="productId" label="商品编号" width="90">
                </el-table-column>
                <el-table-column prop="userId" label="用户编号" width="90">
                </el-table-column>
                <el-table-column prop="categoryName" label="商品种类" width="90">
                </el-table-column>
                <el-table-column prop="productName" label="商品名" width="200">
                </el-table-column>
                <el-table-column prop="productDec" label="商品描述" width="200">
                </el-table-column>
                <el-table-column prop="productPrice" label="价格" width="90" sortable>
                </el-table-column>
                <el-table-column prop="productStatus" label="状态" width="80"
                    :filters="[{ text: '下架', value: '下架' }, { text: '在售', value: '在售' }]" :filter-method="filterTag"
                    filter-placement="bottom-end">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.productStatus === '下架' ? 'primary' : 'success'" disable-transitions>
                            {{ scope.row.productStatus }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="productSale" label="销量" width="90">
                </el-table-column>
                <el-table-column prop="productGrade" label="评分" width="90">
                </el-table-column>
                
                <el-table-column fixed="right" label="操作" width="225">
                    <template slot-scope="scope">
                        <!-- 查看商品图片 -->
                        <el-button @click="getProductImg(scope.row)" size="small">查看</el-button>
                        <el-dialog title="商品图片" :visible.sync="dialogImgVisible" :modal-append-to-body='false'>
                            <div class="imgArea">
                                <div class="block">
                                    <el-carousel trigger="click" height="400px">
                                        <el-carousel-item v-for="(item,index) in productImg" :key="index">
                                            <img :src="item" alt="" />
                                        </el-carousel-item>
                                    </el-carousel>
                                </div>
                            </div>
                        </el-dialog>
                        <!-- 修改商品信息 -->
                        <el-button @click="updataProduct(scope.row)" size="small">修改</el-button>&nbsp;
                        <el-dialog title="修改信息" :visible.sync="dialogVisible" width="30%" :modal-append-to-body='false'>
                            <el-form ref="updateform" :model="update_Product" label-width="80px">
                                <el-form-item label="商品种类">
                                    <el-input v-model="update_Product.city"></el-input>
                                </el-form-item>
                                <el-form-item label="商品名">
                                    <el-input v-model="update_Product.address"></el-input>
                                </el-form-item>
                                <el-form-item label="评分">
                                    <el-input v-model="update_Product.cellName"></el-input>
                                </el-form-item>
                                <el-form-item label="商品描述">
                                    <el-input v-model="update_Product.ProductArea"></el-input>
                                </el-form-item>
                                <el-form-item label="价格">
                                    <el-input v-model="update_Product.ProductPrice"></el-input>
                                </el-form-item>
                                <el-form-item label="销量">
                                    <el-input v-model="update_Product.layoutType"></el-input>
                                </el-form-item>
                                <el-form-item label="类型">
                                    <el-radio-group v-model="update_Product.ProductType">
                                        <el-radio label="新房"></el-radio>
                                        <el-radio label="二手房"></el-radio>
                                    </el-radio-group>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer">
                                <el-button @click="dialogVisible = false">取 消</el-button>
                                <el-button type="primary" @click="updateConfirm()">确 定</el-button>
                            </div>
                        </el-dialog>
                        <!-- 删除商品信息 -->
                        <el-button @click="delProduct(scope.row)" type="danger" size="small">删除</el-button>
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
            searchProduct: {
                address: "",
                adminId: "",
                cellName: "",
                city: "",
                clientId: "",
                empId: "",
                productArea: "",
                productId: "",
                productOrientation: "",
                productPrice: "",
                productType: "",
                image: null,
                layoutType: "",
            },
            update_Product: "",   //修改商品
            productImg: [],   //查看商品图片
            productData: [],
            productID: "",
            product: {
                address: "",
                adminId: "",
                cellName: "",
                city: "",
                clientId: "",
                empId: "",
                productArea: "",
                productId: "",
                productOrientation: "",
                productPrice: "",
                productType: "",
                image: null,
                layoutType: "",
            },
            currentPage:1,
            pageSize:10,
            total: 0,//总条目数
            dialogSearchVisible: false,  //商品查询结果弹出框
            dialogVisible: false,       //修改表单弹出框
            dialogImgVisible: false,     //商品图片弹出框
        }
    },
    created() {
        //自动获取商品信息
        this.getProductData()
    },
    methods: {
        //监听 页码值 改变的事件
        handleCurrentChange(val){
            console.log(val);
            this.currentPage = val
            this.getProductData()
        },
        handlesizeChange(val){
            console.log(val);
            this.pageSize = val
            this.getProductData()
        },
        //获取商品信息
        getProductData() {
            this.$axios({
                url: "/product/getMyProduct",//请求的后台接口
                method: "post",//get请求方式
                data:{
                    currentPage:this.currentPage,
                    pageSize:this.pageSize
                }
            }).then(response => {
                console.log(response)
                this.productData = response.data.data.data
                this.total = response.data.data.count
            }).catch(error => {
                //请求失败
            });
        },

        //根据ID搜索商品
        queryProductById() {
            this.dialogSearchVisible = true
            console.log(this.searchID)
            this.$axios.get(`/product/queryById/${this.searchID}`)
            .then((request => {
                console.log(request.data.data);
                this.searchProduct = request.data.data
                console.log(this.searchProduct);
                this.dialogSearchVisible = true
                this.$message({
                    showClose: true,
                    message: '查询成功',
                    type: 'success'
                });
            })).catch((error) => {
                console.log(error);
            })
            this.searchID = ""
        },

        //查看商品图片
        getProductImg(product) {
            this.productImg = product.img
            console.log(product);
            console.log(this.productImg);
            this.dialogImgVisible = true;
            delete this.productImg.imgId
            delete this.productImg.productId
            delete this.productImg.productName
        },

        //修改商品
        updataProduct(product) {
            this.update_Product = product;
            console.log(this.update_Product);
            this.dialogVisible = true;
        },

        //修改提交
        updateConfirm() {
            this.$axios({
                url: "/product/update",
                method: "post",
                data:
                    this.update_Product,
                header: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            }).then(res => {
                if (res.data != null) {
                    this.dialogVisible = false;
                };
                console.log(res)
                this.$message({
                    showClose: true,
                    message: '修改成功！',
                    type: 'success'
                });
            })
        },

        //删除商品
        delProduct(product) {
            this.productID = product.productId
            console.log(product)
            console.log(product.productId)
            this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                this.$axios.get(`/product/delete/${this.productID}`).then((request => {
                    console.log(request.data.message)
                    if (request.data.code === 204) {
                        this.$message({
                            showClose: true,
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getproductData()
                    } else {
                        this.$message({
                            showClose: true,
                            message: '删除失败！',
                            type: 'warning'
                        });
                    }
                })).catch((error) => {
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

<style>
.input {
    width: 200px;
}
.container .cell{
        /* 1.溢出隐藏 */
        overflow: hidden;
    /* 2.用省略号来代替超出文本 */
    text-overflow: ellipsis;
    /* 3.设置盒子属性为-webkit-box  必须的 */
    display: -webkit-box;
    /* 4.-webkit-line-clamp 设置为2，表示超出2行的部分显示省略号，如果设置为3，那么就是超出3行部分显示省略号 */
    -webkit-line-clamp: 2;
    /* 5.字面意思：单词破坏：破坏英文单词的整体性，在英文单词还没有在一行完全展示时就换行  即一个单词可能会被分成两行展示 */
    word-break: break-all;
    /* 6.盒子实现多行显示的必要条件，文字是垂直展示，即文字是多行展示的情况下使用 */
    -webkit-box-orient: vertical;
}
</style>