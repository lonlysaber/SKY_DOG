<template>
    <div class="cart">
        <div class="top">
            <topBar></topBar>
        </div>
        <div class="search">
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" background-color="#246EDC"
                text-color="#fff" active-text-color="#ffd04b"
                style="text-align:center; padding-left: 15px; padding-right:20px">
                <el-menu-item style="font-size:small" @click="goBack">
                    <i class="el-icon-back"> 返回</i>
                </el-menu-item>

                <el-menu-item index="0" style="font-size:large">我的购物车</el-menu-item>

                <el-input class="input" v-model="input" placeholder="输入内容搜索">
                </el-input>

                <el-button type="primary" @click="toSearch">搜索</el-button>
            </el-menu>
        </div>

        <div class="product">

            <!-- <el-empty description="购物车为空"></el-empty> -->

            <div class="title">
                <span class="title-left">购物车(全部{{cartDetail.length}})</span>

                <div class="check">
                    <span class="title-right">已选商品 ￥&nbsp;<span class="dollar">{{total}}</span> 元</span>
                    <el-button type="primary" @click="toOrderPage()">结算</el-button>
                </div>
            </div>


            <div class="myproduct">
                <el-table ref="multipleTable" :data="cartDetail" tooltip-effect="dark"
                    style="width: 100%;text-align: center;" @selection-change="handleSelectionChange">

                    <el-table-column type="selection" width="45">
                    </el-table-column>

                    <el-table-column label="商品图片" width="110">
                        <div class="stuff" slot-scope="scope">
                            <img :src="scope.row.productDto.img.img1" alt="图片加载失败" @click="toProductDetail(scope.row)">
                        </div>
                    </el-table-column>

                    <el-table-column label="商品信息" width="330">
                        <div class="dec" slot-scope="scope">
                            <span @click="toProductDetail(scope.row)">￥{{scope.row.productDto.productName}}</span>
                        </div>
                    </el-table-column>

                    <el-table-column label="规格" width="130">
                        <div class="scale" slot-scope="scope">
                            <span v-if="scope.row.productDto.productScale">{{scope.row.productDto.productScale}}</span>
                            <span v-else>无</span>
                        </div>
                    </el-table-column>

                    <el-table-column label="单价" width="110">
                        <template slot-scope="scope">
                            <span>￥{{scope.row.productDto.productPrice}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column label="数量">
                        <template slot-scope="scope">
                            <el-input-number v-model="scope.row.productCount" @change="handleChange(scope.row)" :min="1"
                                :max="99" size="mini">
                            </el-input-number>
                        </template>
                    </el-table-column>

                    <el-table-column label="金额" width="100">
                        <div class="money" slot-scope="scope">
                            <span>￥{{parseInt(scope.row.productCount)*
                            parseInt(scope.row.productDto.productPrice)}}</span>
                        </div>
                    </el-table-column>

                    <el-table-column prop="" label="操作" width="110">
                        <div class="operation" slot-scope="scope">
                            <p @click="addCollect(scope.row)">移入收藏</p>
                            <p @click="deleteProduct(scope.row)">删除商品</p>
                        </div>
                    </el-table-column>

                </el-table>

            </div>

        </div>

    </div>
</template>

<script>
import topBar from '@/components/TopBar.vue';
import searchBar from '@/components/searchBar.vue';

export default {
    name: "",
    components: { topBar, searchBar },
    data() {
        return {
            userId: '10001',
            productId: '',
            username: "",
            input: "",
            activeIndex: '1',
            cartNum: '',
            num: 1,
            money: '',
            total: 0,
            multipleSelection: [],
            cartDetail: [
            ],
        }
    },
    created() {
        this.userId = this.$cookies.get('token');
        this.getCart();
    },
    methods: {

        // 返回上一页
        goBack() {
            history.back(1)
        },
        //跳转订单页
        toOrderPage() {
            if (this.multipleSelection.length == 0) {
                this.$message({
                    message: '请至少选择一个商品',
                    type: 'error'
                })
            } else {
                this.$router.push({
                    path: '/payorder',
                    query: {
                        checkOrder: JSON.stringify(this.multipleSelection) ,
                    }
                });
            }
        },

        //跳转详情页
        toProductDetail(value) {
            console.log(value);
            this.$router.push({
                path: '/productdetail',
                query: {
                    userId: value.userId,
                    productId: value.productDto.productId
                }
            });
        },

        //搜索
        toSearch() {

        },

        toCheck(value) {
            console.log(this.multipleSelection);
            let res = value.map((item) => {
                // console.log(this.getMoney(item));
                return this.total += parseInt(this.getMoney(item));
            });
        },
        // 计算金额
        getMoney(value) {
            // console.log(value);
            this.money = value.productCount * value.productDto.productPrice;
            // console.log(this.money);
            return this.money;
        },

        //添加收藏
        addCollect(value) {

            this.$axios({
                url: "/collect/add",
                method: "post",
                data: {
                    userId: value.userId,
                    productId: value.productDto.productId
                }
            }).then(res => {
                console.log(res);
            });
        },

        //删除商品
        deleteProduct(value) {
            let _this = this;
            this.$confirm('此操作将永久删除该数据, 是否继续?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                console.log(value);
                _this.$axios({
                    url: "/cart/delete/" + value.cartId,
                    method: "get",
                }).then(res => {
                    console.log(res);
                })
            });


            this.getCart();

        },

        // 获取购物车
        getCart() {
            this.$axios({
                url: "/cart/getMyCart/" + this.userId,
                method: "get",
            }).then((res) => {
                console.log(res.data.data);
                this.cartDetail = res.data.data;
            });
        },

        //数量改变
        handleChange(value) {
            console.log(value);
            this.getMoney(value);
            this.total = 0
            this.toCheck(this.multipleSelection)
        },

        toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
            } else {

                this.$refs.multipleTable.clearSelection();

            }
        },

        handleSelectionChange(val) {
            this.total = 0;
            this.multipleSelection = val;
            console.log(this.multipleSelection);
            this.toCheck(this.multipleSelection)
        },

    }
}
</script>

<style>
.cart .el-menu-demo {
    display: flex;
}

.cart .search .el-icon-back {
    font-size: 15px;
    height: fit-content;
    color: white;
    padding-right: 20px;
    margin-bottom: 2px;
}

.cart .search .el-input {
    margin-left: 350px;
    width: 600px;
    align-items: center;
}


.cart .search .el-input .el-input__inner {
    margin-top: 10px;
    align-items: center;
    border: 1px cornflowerblue solid;
    border-radius: 30px;
    width: 600px;
    height: 42px;
    background-color: #FFF;

}

.cart .search .el-button {
    margin: 10px 0px 0px 30px;
    border-radius: 25px;
    width: 100px;
    height: 42px;
}

.cart .product {
    width: 80%;
    height: auto;
    /* display:flboxex; */
    margin: 0px auto;
    margin-top: 80px;
    border-radius: 25px;
    background-color: aliceblue;


}

.cart .title {
    overflow: hidden;
    font-size: 12px;
    position: relative;
    height: 72px;
    width: 96%;
    padding: 0 18px;
    border-bottom: 2px solid #e6e6e6;
    margin-right: 0px;

}

.cart .title-left {
    float: left;
    overflow: hidden;
    color: #000;
    font-size: 20px;
    font-weight: 600;
    height: 72px;
    line-height: 72px;

}

.cart .title-right {
    color: #000;
    font-size: 15px;
    font-weight: 600;
    height: 72px;
    line-height: 72px;

}

.cart .dollar {
    font-family: Verdana, Arial;
    padding-left: 2px;
    font-weight: 500;
    font-size: 22px;
    color: #FF5000;
}

.cart .check {
    float: right;
}

.cart .title .el-button {
    margin: 18px 0px 0px 30px;
    border-radius: 25px;
    width: 100px;
    height: 42px;
}

.cart .myproduct {
    height: auto;
    display: flex;
    margin-top: 2px;
    background-color: aliceblue;
}

.cart .myproduct .dec:hover {
    color: #ff4442;
    cursor: pointer;
}

.cart .myproduct .money {
    font-family: Verdana, Arial;
    font-weight: 100;
    font-size: 14px;
    color: #FF5000;
}

.cart .myproduct .operation p:hover {
    color: #ff4442;
    cursor: pointer;
}

.cart .myproduct .stuff {
    display: flex;
    height: 150px;
}

.cart .myproduct .stuff img {
    /* float: left; */
    margin-top: 30px;
    width: 100px;
    height: 100px;
    display: block;
    border-radius: 5px;
}

.cart .myproduct .stuff img:hover {
    cursor: pointer;
}

.cart .stuff .title {
    margin-top: 35px;
    word-break: normal;
    width: 250px;
    border-color: white;
    word-break: break-all;
}

.cart .el-table .cell {
    text-align: center;
    box-sizing: border-box;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: normal;
    word-break: break-all;
    line-height: 23px;
    padding-left: 10px;
    padding-right: 10px;
}

.cart .count {
    display: flex;

}
</style>
