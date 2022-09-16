<template>
  <div>
    <el-card>
      <el-row :gutter="35" style="margin: 0 auto; margin-bottom: 20px">
        <el-col :span="6">
          <el-card
            shadow="never"
            style="border-top: 4px solid rgb(58, 160, 255)"
          >
            <div class="colorCardsBody">
              <span>房源发布量<i class="el-icon-d-arrow-right"></i></span>
              <p>{{ houseCount }}</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="never" style="border-top: 4px solid red">
            <div class="colorCardsBody">
              <span>系统用户量<i class="el-icon-d-arrow-right"></i></span>
              <p>{{ clientCount }}</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card
            shadow="never"
            style="border-top: 4px solid rgb(250, 211, 55)"
          >
            <div class="colorCardsBody">
              <span>达成订单数<i class="el-icon-d-arrow-right"></i></span>
              <p>{{ turnoverCount[0] }}</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card
            shadow="never"
            style="border-top: 4px solid rgb(78, 203, 115)"
          >
            <div class="colorCardsBody">
              <span>季度营业额<i class="el-icon-d-arrow-right"></i></span>
              <p>{{ turnoverCount[1] }}万</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="35" style="margin: 0 auto; margin-bottom: 20px">
        <el-col :span="24">
          <div style="padding: 28px 40px 0 40px" class="dataOverview">
            <div class="overview-list-item">
              <p class="overview-list-text">房源新增</p>
              <p class="overview-list-count">7</p>
              <!-- <p :class="setColor(-10.52)">-10.52%</p> -->
            </div>
            <div class="overview-list-item">
              <p class="overview-list-text">用户新增</p>
              <p class="overview-list-count">20</p>
              <!-- <p :class="setColor(+15.0)">+15.00%</p> -->
            </div>
            <div class="overview-list-item">
              <p class="overview-list-text">员工新增</p>
              <p class="overview-list-count">1</p>
              <!-- <p :class="setColor(+20)">+20%</p> -->
            </div>
            <div class="overview-list-item">
              <p class="overview-list-text">订单新增</p>
              <p class="overview-list-count">5</p>
              <!-- <p :class="setColor(-6.5)">-6.5%</p> -->
            </div>
            <div class="overview-list-item">
              <p class="overview-list-text">订单完成</p>
              <p class="overview-list-count">3</p>
              <!-- <p :class="setColor(72.22)">72.22%</p> -->
            </div>
            <div class="overview-list-item">
              <p class="overview-list-text">订单取消</p>
              <p class="overview-list-count">0</p>
              <!-- <p :class="setColor(+0.0)">-0.00%</p> -->
            </div>
            <div class="overview-list-item">
              <p class="overview-list-text">收入</p>
              <p class="overview-list-count">9.27</p>
              <!-- <p :class="setColor(+3.96)">+3.96%</p> -->
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <el-card style="margin-top: 30px">
      <div slot="header">
        <span class="clearfix">统计图表</span>
      </div>

      <div style="display: flex">
        <el-row :gutter="35" style="margin: 0 auto; margin-bottom: 20px">
          <div id="LineECharts" style="width: 600px; height: 600px"></div>
        </el-row>
        <el-row :gutter="35" style="margin: 0 auto; margin-bottom: 20px">
          <div
            id="SectorECharts"
            style="width: 600px; height: 600px; margin-top: 50px"
          ></div>
        </el-row>
      </div>
      <div style="display: flex">
        <el-row :gutter="35" style="margin: 0 auto; margin-bottom: 20px">
          <div
            id="RoundECharts"
            style="width: 600px; height: 600px; margin-top: 50px"
          ></div>
        </el-row>
        <el-row :gutter="35" style="margin: 0 auto; margin-bottom: 20px">
          <div
            id="BarECharts"
            style="width: 600px; height: 600px; margin-top: 25px"
          ></div>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { ECharts } from "echarts";
export default {
  name: "Home", // 首页
  data() {
    return {
      houseCount: "",
      clientCount: "",
      orderCount: "",
      turnoverCount: [],
      quarter: "",
    };
  },

  created() {
    this.getHouseCount();
    this.getQuarter();
    this.getClientCount();
    this.getTurnoverStatistic();
  },
  methods: {
    // setColor(dataVal) {
    //   if (dataVal > 0) {
    //     return "overview-list-trend-up";
    //   } else {
    //     return "overview-list-trend-down";
    //   }
    // },

    //获取当前季度
    getQuarter() {
      var date = new Date();
      var month = date.getMonth() + 1;
      if (month < 4) {
        this.quarter = 1;
      } else if (month > 4 && month < 7) {
        this.quarter = 2;
      } else if (month > 7 && month < 10) {
        this.quarter = 3;
      } else if (month > 10 && month < 12) {
        this.quarter = 4;
      }
    },

    //获取房源数量
    getHouseCount() {
      this.$axios({
        url: "/statistic/queryCount", //请求的后台接口
        method: "get", //get请求方式
      })
        .then((res) => {
          // console.log(res)
          this.houseCount = res.data.data;
        })
        .catch((error) => {
          //请求失败
        });
    },

    //获取客源数量
    getClientCount() {
      this.$axios({
        url: "/statistic/queryClientCount", //请求的后台接口
        method: "get", //get请求方式
      })
        .then((res) => {
          // console.log(res)
          this.clientCount = res.data.data;
        })
        .catch((error) => {
          //请求失败
        });
    },

    //根据季度获取营业额
    getTurnoverStatistic() {
      this.$axios({
        url: "/statistic/turnoverStatistic/" + this.quarter, //请求的后台接口
        method: "get", //get请求方式
      })
        .then((res) => {
          // console.log(res)
          this.turnoverCount = res.data.data;
        })
        .catch((error) => {
          //请求失败
        });
    },

    //获取折线柱状图数据
    getLineChartsData() {
      this.$axios({
        url: "/statistic/lineChartDate", //请求的后台接口
        method: "get", //get请求方式
      })
        .then((res) => {
          // console.log(res);
          var data = res.data.data;
          //   console.log(data);
          // console.log(data.order);
          var order = data.order;
          var turnover = data.turnover;
          this.drawLine(order, turnover);
          // console.log(data);
        })
        .catch((error) => {
          //请求失败
        });
    },

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
          data: ["营业额", "订单达成数"],
        },
        xAxis: [
          {
            type: "category",
            data: ["1月-3月", "4月-6月", "7月-9月", "10月-12月"],
            axisPointer: {
              type: "shadow",
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "营业额",
            min: 0,
            max: 500,
            interval: 50,
            axisLabel: {
              formatter: "{value} 万",
            },
          },
          {
            type: "value",
            name: "订单达成数",
            min: 0,
            max: 25,
            interval: 5,
            axisLabel: {
              formatter: "{value} 笔",
            },
          },
        ],
        series: [
          {
            name: "营业额",
            type: "bar",
            tooltip: {
              valueFormatter: function (value) {
                return value + "万";
              },
            },
            //接收营业额数据
            data: turnover,
            //柱状图颜色变化
            itemStyle: {
              normal: {
                color: function (params) {
                  let colorList = [
                    // 每根颜色顺序
                    "rgba(225, 133, 255, 0.39)",
                    "rgba(45, 211, 193, 0.39)",
                    "rgba(121, 173, 245, 0.39)",
                    "rgba(252, 223, 86, 0.39)",
                  ];
                  return colorList[params.dataIndex];
                },
              },
            },
          },
          {
            name: "订单达成数",
            type: "line",
            yAxisIndex: 1,
            tooltip: {
              valueFormatter: function (value) {
                return value + "笔";
              },
            },
            //接收已完成订单数据
            data: order,
          },
        ],
      });
      //窗口适应大小
      window.onresize = () => {
        myChart.resize();
      };
    },

    //获取房源价格比例统计数据
    houseStatistic() {
      this.$axios({
        url: "/statistic/houseStatistic", //请求的后台接口
        method: "get", //get请求方式
      })
        .then((res) => {
          var data = res.data.data;
          //   console.log(data);
          this.drawSector1(data);
        })
        .catch((error) => {
          //请求失败
        });
    },

    //接收数据绘制扇形图
    drawSector1(data) {
      //console.log(data);
      let myChart = this.$echarts.init(
        document.getElementById("SectorECharts")
      );
      // 绘制图表
      myChart.setOption({
        title: {
          text: "房源比例",
          subtext: "价格占比",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "价格区间",
            type: "pie",
            radius: "55%",
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      });
      window.onresize = () => {
        myChart.resize();
      };
    },

    //获取系统角色统计信息
    clientStatistic() {
      this.$axios({
        url: "/statistic/clientStatistic",
        method: "get",
      })
        .then((res) => {
          var data = res.data.data;
          this.drawSector2(data);
          console.log(data);
        })
        .catch((error) => {
          //请求失败
        });
    },

    //绘制角色比例扇形图
    drawSector2(data) {
      let myChart = this.$echarts.init(document.getElementById("RoundECharts"));
      // 绘制图表
      myChart.setOption({
        title: {
          text: "系统用户统计",
          subtext: "人员比例",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          top: "15%",
          left: "center",
        },
        series: [
          {
            name: "类型",
            type: "pie",
            radius: ["35%", "60%"],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: "#fff",
              borderWidth: 2,
            },
            label: {
              show: false,
              position: "center",
            },
            emphasis: {
              label: {
                show: true,
                fontSize: "40",
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: data,
          },
        ],
      });
      window.onresize = () => {
        myChart.resize();
      };
    },

    // 获取已完成的订单数据
    orderStatistic() {
      this.$axios({
        url: "/statistic/getOrderTotal",
        method: "get",
      })
        .then((res) => {
          var data = res.data.data;
          this.drawBar(data);
          console.log(data);
        })
        .catch((error) => {
          //请求失败
        });
    },

    //接收数据绘制柱状图
    drawBar(data) {
      let myChart = this.$echarts.init(document.getElementById("BarECharts"));
      // 绘制图表
      myChart.setOption({
        title: {
          text: "系统订单统计",
          subtext: "状态区间",
          left: "center",
        },
        xAxis: {
          type: "category",
          data: ["已完成", "待支付", "取消"],
        },
        yAxis: {
          type: "value",
          name: "订单达成数",
          min: 0,
          max: 25,
          interval: 5,
          axisLabel: {
            formatter: "{value} 笔",
          },
        },
        tooltip: {
          trigger: "item",
        },

        series: [
          {
            name: "数量",
            type: "bar",
            itemStyle: {
              normal: {
                color: function (params) {
                  var colorList = ["#C1232B", "#B5C334", "#FCCE10"];
                  return colorList[params.dataIndex];
                },
              },
            },
            barWidth: 70, //柱子宽度
            data: data,
          },
        ],
      });
      window.onresize = () => {
        myChart.resize();
      };
    },
  },
  mounted() {
    this.getLineChartsData();
    this.drawLine();
    this.houseStatistic();
    this.drawSector1();
    this.clientStatistic();
    this.drawSector2();
    this.orderStatistic();
    this.drawBar();
  },
};
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}

/* 卡片标题字体的样式 */
.clearfix {
  padding-left: 5px;
  font-size: 22px;
  font-weight: bold;
}

/* 四个彩色卡片的相关样式 */
.colorCardsBody {
  padding: 18px 40px;
}

.colorCardsBody span {
  font-size: 16px;
  color: #515a6e;
}

.colorCardsBody p {
  font-family: Roboto-Medium !important;
  padding-top: 15px;
  font-size: 18px;
  font-weight: 800;
}

/* 数据总览区域的整体样式设置 */
.dataOverview {
  padding: 0 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 数据总览里的每一条信息项 */
.overview-list-item {
  padding: 0 25px;
  border-left: 1px solid rgba(220, 222, 226, 0.5);
  flex: 1;
}

.overview-list-item:first-child {
  border: none;
}

/* 数据总览里每条信息项的文本部分 */
.overview-list-text {
  color: #515a6e;
  font-size: 14px;
  margin-bottom: 24px;
}

.overview-list-count {
  color: #17233d;
  font-weight: 700;
  margin-bottom: 16px;
  font-size: 16px;
}

.overview-list-trend-up {
  font-size: 14px;
  color: blue;
}

.overview-list-trend-down {
  font-size: 14px;
  color: red;
}

.programmaticBatchBtn {
  /* 程序化批量的按钮样式 */
  float: right;
  padding: 4px 11px 3px 11px;
  margin-left: 20px;
  margin-right: 50px;
  font-size: 14px;
  color: rgb(255, 255, 255);
  background-color: rgb(0, 182, 151);
}

.programmaticBatchBtn img {
  /* 程序化批量按钮上的动图标 */
  width: 24px;
  display: inline-block;
  vertical-align: middle;
  /* 让该图标在 Y 轴上居中 */
}

.search-form {
  /* 日期筛选控件的样式 */
  display: inline;
  margin-left: 20px;
  margin-right: 8px;
  float: right;
}

.search-form span,
.search-form-2 span {
  /* 筛选控件的前面字体样式 */
  margin-right: 10px;
  font-weight: bold;
}

.search-form-2 {
  /* “效果总览” 卡片里的筛选控件定位设置 */
  margin-top: 30px;
  margin-bottom: -40px;
  margin-left: 30px;
}

.blankContent {
  /* TOP 创意卡片底部空白部分，为了和 TOP 广告底部对齐 */
  width: auto;
  height: 40px;
}
</style>
