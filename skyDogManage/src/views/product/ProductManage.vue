<template>
    <div class="content-box">
        <div class="container">
            <!-- 根据ID搜索商品 -->
            <el-row>
                <el-input class="input" placeholder="请输入关键词" v-model="keyword" clearable>
                </el-input>
                <el-button @click="search(keyword)">搜索</el-button>
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
                    :filters="[{ text: '下架', value: '下架' }, { text: '在售', value: '在售' }]"
                    filter-placement="bottom-end">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.productStatus === '下架' ? 'primary' : 'success'" disable-transitions>
                            {{ scope.row.productStatus }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="productDetail" label="商品细节" width="200">
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
                                <el-form-item label="商品名">
                                    <el-input type="textarea" v-model="update_Product.productName"></el-input>
                                </el-form-item>
                                <el-form-item label="商品描述">
                                    <el-input type="textarea" v-model="update_Product.productDec"></el-input>
                                </el-form-item>
                                <el-form-item label="价格">
                                    <el-input v-model="update_Product.productPrice"></el-input>
                                </el-form-item>
                                <el-form-item label="商品细节">
                                    <el-input v-model="update_Product.productDetail" type="textarea"></el-input>
                                </el-form-item>
                                <el-form-item label="商品种类">
                                    <el-select v-model="update_Product.categoryId" placeholder="请选择商品种类">
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
            keyword: "",
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
            if(this.keyword == ""){
                this.getProductData()
            }else{
                this.search()
            }
            
        },
        handlesizeChange(val){
            console.log(val);
            this.pageSize = val
            if(this.keyword == ""){
                this.getProductData()
            }else{
                this.search()
            }
        },
        //获取商品信息
        getProductData() {
            this.$axios({
                url: "/product/getProduct",//请求的后台接口
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
        search() {
            this.dialogSearchVisible = true
            console.log(this.keyword)
            this.$axios({
                url: "/product/search",//请求的后台接口
                method: "post",//get请求方式
                data:{
                    keyword:this.keyword,
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
                data:this.update_Product,
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