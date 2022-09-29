import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'

const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import("@/views/front/home/home.vue")
  },
  {
    path: '/frontlogin',
    name: 'frontLogin',
    component: () => import("@/views/front/login/login.vue")
  },
  {
    path: '/me',
    name: 'me',
    component: () => import("@/views/front/me/me.vue"),
    children:
      [
          {
          path:'',
          redirect:'order'
        },
        {
          path: "collect",
          name: "collect",
          component: () => import("@/views/front/collect/collect.vue")
        },
        {
          path: "order",
          name: "order",
          component: () => import("@/views/front/order/order.vue")
          
        },
        {
          path: "mypay",
          name: "mypay",
          component: () => import("@/views/front/me/myPay.vue")
          
        },
      ]
  },
  {
    path:"/cart",
    name:"cart",
    component:()=> import("@/views/front/cart/cart.vue")
  },


  {
    path: '/productdetail',
    name: 'productDetail',
    component: () => import('@/views/front/product/productDetail.vue')
  },
  {
    path: '/search',
    name: 'search',
    component: () => import('@/views/front/search/search.vue')
  },
  {
    path: '/payorder',
    name: 'payOrder',
    component: () => import('@/views/front/order/payOrder.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
let startTime = Date.now()
let currentTime
router.beforeEach((to, from, next) => {

  if(to,from){
	 currentTime = Date.now() 
	 let date = currentTime
	 console.log('用户由 ', from.path, ' 页面 跳转到 ', to.path, ' 页面，在 ', from.path, ' 页面停留了 ', currentTime - startTime, '毫秒。转换成秒数约为：', parseInt((currentTime - startTime) / 1000))
	 let params = {
	 		  // 日期
        date:Date.now(),
        // 用户id
        userId:localStorage.getItem('userId') || null,
        // 页面地址path
        currentUrl:from.path || null,
        // 当前点击时间
        actionTime:Date.now(),
        // 上一次点击时间
        // preTime:preTime,
        // 页面停留时间
        duration:currentTime - startTime,
        searchKeyword:null,
        // 点击商品id
        productId1:null,
        // 商品分类
        categoryName:null, 
        // 下单订单编号
        orderId1:null,
        // 下单商品id
        productId2:null,
        // 支付订单编号
        orderId2:null,
        // 支付商品id
        productId3: null,
        // 订单状态
        orderStatus:null,
        // 收藏id
        collectId:null,
        // 收藏商品id
        productId4: null,
        // 评论id
        evaluateId:null,
        // 发货地址
        startAddress:null,
        // 收货地址
        endAddress:null,
        // 购物车id
        cartId:null,
        // 购物车商品id
        productId5:null,
	 }
   console.log(params)
   startTime = Date.now()
   axios({
    method:'post',
    url:'/spark/saveEventTrack',
    data:params
  })  
	 next()
  }
 
});

export default router
