<template>
    <div>
        <div>最近消费曲线</div>
        <div>
            <el-row :gutter="35" style="margin: 0 auto; margin-bottom: 20px;">
                <div id="LineECharts" style="width: 880px; height: 540px"></div>
            </el-row>
        </div>

    </div>
</template>

<script>
    export default {
        data() {
            return {
                order: [1, 2, 5, 4,5,4,5,3,2],
                turnover: [200, 1500.5, 3052.99, 568.5,255.5,7653.5,450,178,180],
            }
        },
        methods: {
             // 接收数据渲染图表
            drawLine(order, turnover) {
                let myChart = this.$echarts.init(document.getElementById("LineECharts"));
                myChart.setOption({
                    tooltip: {
                        trigger: "axis",
                        axisPointer: {
                            type: "cross",
                            crossStyle: {
                            color: "#999",
                            },
                        },
                    },
                    toolbox: {
                        feature: {
                            dataView: { show: true, readOnly: false },
                            magicType: { show: true, type: ["line", "bar"] },
                            restore: { show: true },
                            saveAsImage: { show: true },
                        },
                    },
                    legend: {
                        data: ["消费额", "商品数"],
                    },
                    xAxis: [
                        {
                            type: "category",
                            data: ["3月","4月", "5月","6月", "7月", "8月", "9月"],
                            axisPointer: {
                            type: "shadow",
                        },
                    },
                    ],
                    yAxis: [
                        {
                            type: "value",
                            name: "消费额",
                            min: 0,
                            max: 10000,
                            interval: 1000,
                            axisLabel: {
                                formatter: "{value} 元",
                            },
                        },
                        {
                            type: "value",
                            name: "商品数",
                            min: 0,
                            max: 20,
                            interval: 2,
                            axisLabel: {
                                formatter: "{value} 笔",
                            },
                        },
                    ],
                    series: [
                    {
                        name: "消费额",
                        type: "bar",
                        tooltip: {
                            valueFormatter: function (value) {
                                return value + "元";
                            },
                        },
                        //接收消费额数据
                        data: this.turnover,
                        //柱状图颜色变化
                        itemStyle: {
                            normal: {
                                color: function (params) {
                                    let colorList = [
                                        // 每根颜色顺序
                                        "rgba(225, 133, 255, 0.5)",
                                        "rgba(45, 211, 193, 0.5)",
                                        "rgba(121, 173, 245, 0.5)",
                                        "rgba(252, 223, 86, 0.5)",
                                        "rgb(230, 127, 43,0.5)",
                                        "rgb(154, 22, 255,0.5)",
                                        "rgb(255, 22, 22,0.5)",
                                        "rgb(33, 137, 147,0.5)",
                                        "rgb(109, 147, 33,0.5)",
                                    ];
                                    return colorList[params.dataIndex];
                                },
                            },
                        },
                    },
                    {
                        name: "商品数",
                        type: "line",
                        yAxisIndex: 1,
                        tooltip: {
                        valueFormatter: function (value) {
                            return value + "笔";
                        },
                        },
                        //接收已完成订单数据
                        data: this.order,
                    },
                    ],
                });
                //窗口适应大小
                window.onresize = () => {
                    myChart.resize();
                };
            },
        },
        mounted() {
            this.drawLine(this.order, this.turnover)
        },
        created() {
        }
    }
</script>

<style scoped>
.cl{
    color: rgb(154, 22, 255);
}
</style>