<template>
    <div class="orderVue">
        <div class="content">
            <el-tabs v-model="activeName" @tab-click="handleClick" stretch>
                <el-tab-pane name="所有订单">
                    <span slot="label"> 所有订单 </span>
                </el-tab-pane>
                <el-tab-pane name="待支付">
                    <span slot="label"> 待支付 </span>
                </el-tab-pane>
                <el-tab-pane name="待发货">
                    <span slot="label"> 待发货 </span>
                </el-tab-pane>
                <el-tab-pane name="待收货">
                    <span slot="label"> 待收货 </span>
                </el-tab-pane>
                <el-tab-pane name="待评价">
                    <span slot="label"> 待评价 </span>
                </el-tab-pane>
                <el-tab-pane name="finished">
                    <span slot="label"> 已完成 </span>
                </el-tab-pane>
                <el-tab-pane name="已删除">
                    <span slot="label"><i class="el-icon-delete-solid"></i> 订单回收站 </span>
                </el-tab-pane>
            </el-tabs>
            <div class="orderTar">
                <div style="width:40%">
                    宝贝
                </div>
                <div>
                    单价
                </div>
                <div>
                    数量
                </div>
                <div>
                    商品操作
                </div>
                <div>
                    实付款
                </div>
                <div>
                    交易状态
                </div>
                <div>
                    交易操作
                </div>
            </div>
            <div class="product" v-for="o in orderList" :key="o.orderId"
            :style="o.orderStatus !='已收货' ?'border: 2px #daf3ff solid;':'border: 2px #f1f1f1 solid;'">
                <div class="header" :style="o.orderStatus !='已收货' ?'background-color: #eaf8ff;':'background-color: #f1f1f1;'">
                    <div style="font-weight: 600;">
                        {{o.createTime}}
                    </div>
                    <div>
                        订单号：{{o.orderId}}
                    </div>
                </div>
                <div class="main">
                    <div style="width:40%">
                        <img :src="o.productDto.img.img1" alt="">
                        <div style="float: right;width: 60%;overflow: hidden;
                        margin-right: 20px;
                            text-overflow: ellipsis;
                            display: -webkit-box;
                            -webkit-line-clamp: 2;
                            word-break: break-all;
                            -webkit-box-orient: vertical;" >
                            <a href="">{{o.productDto.productName}}</a>
                        </div>
                    </div>
                    <div>
                        ￥{{o.productDto.productPrice}}
                    </div>
                    <div>
                        {{o.productCount}}
                    </div>
                    <div>
                        <a href="#">申请售后<br/></a>
                        <a href="#">投诉商家</a>
                    </div>
                    <div>
                        实付款
                    </div>
                    <div>
                        {{o.orderStatus}}
                        <!-- <a href="#">交易成功<br/></a>
                        <a href="#">订单详情<br/></a>
                        <a href="#">查看物流</a> -->
                    </div>
                    <div>
                        <el-button type="primary" size="mini" v-if="o.orderStatus !='已收货'">确认收货</el-button>
                        <el-button size="mini" v-if="o.orderStatus =='已收货'">评价</el-button><br/>
                        <a href="#" v-if="o.orderStatus =='已收货'">申请开票</a>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script >
import axios from 'axios';
export default {
    data() {
      return {
        activeName: '所有订单',
        orderList:[]
      };
    },
    methods: {
      handleClick(tab, event) {
        this.getMyOrder()
        console.log(tab, event);
      },
      getMyOrder(){
        const order = {
            userId:this.$cookies.get("token"),
            orderStatus:this.activeName == "所有订单"?null:this.activeName
        }
        axios({
            method: 'post',
            url: '/order/getMyOrder',
            data:order
        })
        .then(res =>{
            // console.log(res);
            this.orderList = res.data.data
        });
      }
    },
    created() {
        this.activeName = this.$route.query.activeName || '所有订单'
        this.getMyOrder()
    },
}
</script>

<style scoped>
.orderVue{
    width: 90%;
}
.orderVue .content{
    width: 100%;
}
.orderVue .content span{
    font-weight: 600;
    font-size: 16px;
}
.orderVue .content .orderTar{
    width: 100%;
    background-color: #f5f5f5;
    overflow: hidden;
}
.orderVue .content .product{
    border: 2px #daf3ff solid;
    overflow: hidden;
    margin-top: 20px;
}
.orderVue .content .product:hover{
    border: 2px #409eff solid !important;
}
.orderVue .content .orderTar div
{
    float: left;
    width: 10%;
    line-height: 36px;
    font-size: 12px;
}
.orderVue .content .product .main div
{
    float: left;
    width: 10%;
    line-height: 20px;
    font-size: 12px;
}
.orderVue .content .product .header{
    overflow: hidden;
    height: 40px;
    background-color: #eaf8ff;
}
.orderVue .content .product .header div{
    float: left;
    margin-left: 10px;
    font-size: 12px;
    line-height: 40px;
}
.orderVue .content .product .main{
    padding: 10px;
    overflow: hidden;
    border-top: 2px #daf3ff solid;
}
.orderVue .content .product .main img{
    width: 80px;
    height: 80px;
}
.orderVue .content .product .main a{
    text-decoration:none;
    color:#000;
}
.orderVue .content .product .main a:hover{
    color: #ff4442;
}
</style>
