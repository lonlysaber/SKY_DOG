<template>
    <div>
        <div class="content-box">
            <div class="container" style="height:900px">
                <div class="titletop">商品评价榜单</div>
                <div class="rankLeft">
                    <div class=resultheader>
                        <div class=resulttitle>
                            
                            <div class="titlebottom">商品好评榜<el-dropdown size="mini" @command="handleCommand">
                                <el-button type="el-dropdown-link">
                                    选择季度({{qua1}})<i class="el-icon-arrow-down el-icon--right"></i>
                                </el-button>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item command="1">一季度</el-dropdown-item>
                                    <el-dropdown-item command="2">二季度</el-dropdown-item>
                                    <el-dropdown-item command="3">三季度</el-dropdown-item>
                                    <el-dropdown-item command="4">四季度</el-dropdown-item>
                                    <el-dropdown-item command="0">全部</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown></div>
                            
                        </div>
                    </div>
                    <div class="mainbox">
                        <ul class="list">
                            <li class="item" v-for="(r, index) in rankList" :key="r.empId">
                                <div class="mingci" :class="'paiming' + index"><span>{{ index + 1 }}</span></div>
                                <div class="mingz">
                                    <h3 class="resultname"><span>{{ r.empName }}</span>
                                    </h3>
                                    <div class="line" :style="{ width: r.commissionTotal / 200 + 'px' }"></div>
                                </div>
                                <div class="number">
                                    <span>{{ r.commissionTotal }}</span>元
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="rankRight">
                    <div class=resultheader>
                        <div class=resulttitle>
                            
                            <div class="titlebottom">订单达成榜<el-dropdown size="mini" @command="handle">
                                <el-button type="el-dropdown-link">
                                    选择季度({{qua2}})<i class="el-icon-arrow-down el-icon--right"></i>
                                </el-button>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item command="1">一季度</el-dropdown-item>
                                    <el-dropdown-item command="2">二季度</el-dropdown-item>
                                    <el-dropdown-item command="3">三季度</el-dropdown-item>
                                    <el-dropdown-item command="4">四季度</el-dropdown-item>
                                    <el-dropdown-item command="0">全部</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown></div>
                            
                        </div>
                    </div>
                    <div class="mainbox">
                        <ul class="list">
                            <li class="item" v-for="(r, index) in rankListByOrder" :key="r.empId">
                                <div class="mingci" :class="'paiming' + index"><span>{{ index + 1 }}</span></div>
                                <div class="mingz">
                                    <h3 class="resultname"><span>{{ r.empName }}</span>
                                    </h3>
                                    <div class="line" :style="{ width: r.orderTotal * 40 + 'px' }"></div>
                                </div>
                                <div class="numberRight">
                                    <span>{{ r.orderTotal }}</span>单
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            rankList: [],
            rankListByOrder: [],
            type: 1,
            quarter: 0,
            qua1:"全年",
            qua2:"全年",
        }
    },
    methods: {
        getRankList(quarter=0,type=1) {
            this.$axios({
                url: "/achievementRankList",
                method: "get",
                params: {
                    quarter: quarter,
                    type: type
                }
            }).then(response => {
                console.log(response)
                this.rankList = response.data.data
            }).catch(error => {
                console.log(error)
            });
        },
        getRankListOrderTotal(quarter=0,type=2) {
            this.$axios({
                url: "/achievementRankList",
                method: "get",
                params: {
                    quarter: quarter,
                    type: type
                }
            }).then(response => {
                console.log(response)
                this.rankListByOrder = response.data.data
            }).catch(error => {
                console.log(error)
            });
        },
        handleCommand(command) {
            if(command == 0){
                this.qua1="全部"
            }else{
                this.qua1=command+"季度"
            }
            this.getRankList(command)
        },
        handle(command) {
            if(command == 0){
                this.qua2="全部"
            }else{
                this.qua2=command+"季度"
            }
            this.getRankListOrderTotal(command)
        }
    },
    mounted() {
        this.getRankList(),
        this.getRankListOrderTotal()
    },
}
</script>

<style lang="scss" scoped>
/* 结果页面 */
.rankLeft {
    width: 48%;
    float: left;
}

.rankRight {
    width: 48%;
    float: right;
}

.resultheader {
    /* 结果头部 */
    //  position: absolute;
    width: 100%;
    border: 10px solid #3989FA;
    background-color: #3989FA;
    border-radius: 32px 32px 0px 0px;
}


.resulttitle {
    top: 30px;
    text-align: center;
}

.titletop {
    font-size: 42px;
    font-weight: 700;
    letter-spacing: 13px;
    font-family: "华文彩云";
    color: rgb(114, 150, 194);
    width: 100%;
    text-align: center;
}

.titlebottom {
    font-size: 32px;
    font-weight: 500;
    letter-spacing: 10px;
    font-family: "华文琥珀";
    color: rgb(96, 230, 185);
}

.mainbox {
    /* 主要排行容器 */
    width: 100%;
    display: flex;
    border: 10px solid #4D38B3;
    border-radius: 0px 0px 32px 32px;
    background-color: #e6e3ed;
}

.list {
    width: 100%;
    height: 100%;
    list-style: none;
    margin: 0;
    padding: 0;
}

ul {
    list-style: none;
    display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 40px;
}

li {
    width: 450px;
    height: 80px;
    font-size: 20px;
    display: flex;
    align-items: center;
    padding: 0 0;
}

.mingci {
    color: rgb(5, 5, 5);
    background-color: #7191d7;
    background-size: contain;
    width: 50px;
    height: 50px;
    text-align: center;
    box-sizing: border-box;
    padding: 16px 0px;
    margin-right: 5px;
}

.paiming0 {
    /* 重设第一名样式 */
    color: rgb(255, 255, 255);
    background-color: #cded2c;
}

.paiming1 {
    /* 重设第二名样式 */
    color: rgb(255, 255, 255);
    background-color: #cad4d2;
}

.paiming2 {
    /* 重设第三名样式 */
    color: rgb(255, 255, 255);
    background-color: #a6b073;
}

.resultname {
    /* 名字左对齐 */
    text-align: left;
}

.line {
    /* 排名条 */
    height: 10px;
    background-color: blue;
    border-radius: 0 5px 5px 0;
}

.number {
    position: absolute;
    left: 42%;
}
.numberRight {
    position: absolute;
    left: 92%;
}
</style>