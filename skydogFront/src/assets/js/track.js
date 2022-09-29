import Vue from 'vue'
import axios from 'axios'
import qs from 'qs'
let category= {}
let product ={}
let search = {}
let buy = []
let pay = {}
let collect =  {}
let evaluate = {}
let address = {}
let cart = {}
// 自定义埋点指令
Vue.directive('track', {
  update: (el, binding, vnode) => {
    if (binding.value.actionType == 'category-click') {
      //这里参数是根据自己业务可以自己定义
      category = {
        // 日期
        date:Date.now(),
        // 用户id
		    userId:localStorage.getItem('userId') || null,
        // 页面地址path
		    currentUrl:binding.value.currentUrl || null,
		    // 当前点击时间
		    actionTime:Date.now(),

        // 页面停留时间
        duration:null,
        // 搜索关键词
        searchKeyword:binding.value.searchKeyword || null,
        // 点击商品id
        productId1:binding.value.productId1 || null,
        // 商品分类
        categoryName:binding.value.categoryName || null,
        // 下单订单编号
        orderId1:binding.value.orderId1 || null,
        // 下单商品id
        productId2:binding.value.productId2 || null,
        // 支付订单编号
        orderId2:binding.value.orderId2 || null,
        // 支付商品id
        productId3:binding.value.productId3 || null,
        // 订单状态
        orderStatus:binding.value.orderStatus || null,
        // 收藏id
        collectId:binding.value.collectId || null,
        // 收藏商品id
        productId4:binding.value.productId4 || null,
        // 评论id
        evaluateId:binding.value.evaluateId || null,
        // 发货地址
        startAddress:binding.value.startAddress || null,
        // 收货地址
        endAddress:binding.value.endAddress || null,
        // 购物车id
        cartId:binding.value.cartId || null,
        // 购物车商品id
        productId5:binding.value.productId5 || null,
      }
    }
    if (binding.value.actionType == 'product-click') {
      //这里参数是根据自己业务可以自己定义
      product = {
        // 日期
        date:Date.now(),
        // 用户id
		    userId:localStorage.getItem('userId') || null,
        // 页面地址path
		    currentUrl:binding.value.currentUrl || null,
		    // 当前点击时间
		    actionTime:Date.now(),

        // 页面停留时间
        duration:null,
        // 搜索关键词
        searchKeyword:binding.value.searchKeyword || null,
        // 点击商品id
        productId1:binding.value.productId1 || null,
        // 商品分类
        categoryName:binding.value.categoryName || null,
        // 下单订单编号
        orderId1:binding.value.orderId1 || null,
        // 下单商品id
        productId2:binding.value.productId2 || null,
        // 支付订单编号
        orderId2:binding.value.orderId2 || null,
        // 支付商品id
        productId3:binding.value.productId3 || null,
        // 订单状态
        orderStatus:binding.value.orderStatus || null,
        // 收藏id
        collectId:binding.value.collectId || null,
        // 收藏商品id
        productId4:binding.value.productId4 || null,
        // 评论id
        evaluateId:binding.value.evaluateId || null,
        // 发货地址
        startAddress:binding.value.startAddress || null,
        // 收货地址
        endAddress:binding.value.endAddress || null,
        // 购物车id
        cartId:binding.value.cartId || null,
        // 购物车商品id
        productId5:binding.value.productId5 || null,
      }
    }
    if (binding.value.actionType == 'search-click') {
      //这里参数是根据自己业务可以自己定义
      search = {
        // 日期
        date:Date.now(),
        // 用户id
		    userId:localStorage.getItem('userId') || null,
        // 页面地址path
		    currentUrl:binding.value.currentUrl || null,
		    // 当前点击时间
		    actionTime:Date.now(),

        // 页面停留时间
        duration:null,
        // 搜索关键词
        searchKeyword:binding.value.searchKeyword || null,
        // 点击商品id
        productId1:binding.value.productId1 || null,
        // 商品分类
        categoryName:binding.value.categoryName || null,
        // 下单订单编号
        orderId1:binding.value.orderId1 || null,
        // 下单商品id
        productId2:binding.value.productId2 || null,
        // 支付订单编号
        orderId2:binding.value.orderId2 || null,
        // 支付商品id
        productId3:binding.value.productId3 || null,
        // 订单状态
        orderStatus:binding.value.orderStatus || null,
        // 收藏id
        collectId:binding.value.collectId || null,
        // 收藏商品id
        productId4:binding.value.productId4 || null,
        // 评论id
        evaluateId:binding.value.evaluateId || null,
        // 发货地址
        startAddress:binding.value.startAddress || null,
        // 收货地址
        endAddress:binding.value.endAddress || null,
        // 购物车id
        cartId:binding.value.cartId || null,
        // 购物车商品id
        productId5:binding.value.productId5 || null,
      }
    }
    if (binding.value.actionType == 'buy-click') {
      //这里参数是根据自己业务可以自己定义
      buy = binding.value.productId2
    }
    if (binding.value.actionType == 'pay-click') {
      //这里参数是根据自己业务可以自己定义
      pay = {
        // 日期
        date:Date.now(),
        // 用户id
		    userId:localStorage.getItem('userId') || null,
        // 页面地址path
		    currentUrl:binding.value.currentUrl || null,
		    // 当前点击时间
		    actionTime:Date.now(),

        // 页面停留时间
        duration:null,
        // 搜索关键词
        searchKeyword:binding.value.searchKeyword || null,
        // 点击商品id
        productId1:binding.value.productId1 || null,
        // 商品分类
        categoryName:binding.value.categoryName || null,
        // 下单订单编号
        orderId1:binding.value.orderId1 || null,
        // 下单商品id
        productId2:binding.value.productId2 || null,
        // 支付订单编号
        orderId2:binding.value.orderId2 || null,
        // 支付商品id
        productId3:binding.value.productId3 || null,
        // 订单状态
        orderStatus:binding.value.orderStatus || null,
        // 收藏id
        collectId:binding.value.collectId || null,
        // 收藏商品id
        productId4:binding.value.productId4 || null,
        // 评论id
        evaluateId:binding.value.evaluateId || null,
        // 发货地址
        startAddress:binding.value.startAddress || null,
        // 收货地址
        endAddress:binding.value.endAddress || null,
        // 购物车id
        cartId:binding.value.cartId || null,
        // 购物车商品id
        productId5:binding.value.productId5 || null,
      }
    }
    if (binding.value.actionType == 'collect-click') {
      //这里参数是根据自己业务可以自己定义
      collect = {
        // 日期
        date:Date.now(),
        // 用户id
		    userId:localStorage.getItem('userId') || null,
        // 页面地址path
		    currentUrl:binding.value.currentUrl || null,
		    // 当前点击时间
		    actionTime:Date.now(),

        // 页面停留时间
        duration:null,
        // 搜索关键词
        searchKeyword:binding.value.searchKeyword || null,
        // 点击商品id
        productId1:binding.value.productId1 || null,
        // 商品分类
        categoryName:binding.value.categoryName || null,
        // 下单订单编号
        orderId1:binding.value.orderId1 || null,
        // 下单商品id
        productId2:binding.value.productId2 || null,
        // 支付订单编号
        orderId2:binding.value.orderId2 || null,
        // 支付商品id
        productId3:binding.value.productId3 || null,
        // 订单状态
        orderStatus:binding.value.orderStatus || null,
        // 收藏id
        collectId:binding.value.collectId || null,
        // 收藏商品id
        productId4:binding.value.productId4 || null,
        // 评论id
        evaluateId:binding.value.evaluateId || null,
        // 发货地址
        startAddress:binding.value.startAddress || null,
        // 收货地址
        endAddress:binding.value.endAddress || null,
        // 购物车id
        cartId:binding.value.cartId || null,
        // 购物车商品id
        productId5:binding.value.productId5 || null,
      }
    }
    if (binding.value.actionType == 'evaluate-click') {
      //这里参数是根据自己业务可以自己定义
      evaluate = {
        // 日期
        date:Date.now(),
        // 用户id
		    userId:localStorage.getItem('userId') || null,
        // 页面地址path
		    currentUrl:binding.value.currentUrl || null,
		    // 当前点击时间
		    actionTime:Date.now(),

        // 页面停留时间
        duration:null,
        // 搜索关键词
        searchKeyword:binding.value.searchKeyword || null,
        // 点击商品id
        productId1:binding.value.productId1 || null,
        // 商品分类
        categoryName:binding.value.categoryName || null,
        // 下单订单编号
        orderId1:binding.value.orderId1 || null,
        // 下单商品id
        productId2:binding.value.productId2 || null,
        // 支付订单编号
        orderId2:binding.value.orderId2 || null,
        // 支付商品id
        productId3:binding.value.productId3 || null,
        // 订单状态
        orderStatus:binding.value.orderStatus || null,
        // 收藏id
        collectId:binding.value.collectId || null,
        // 收藏商品id
        productId4:binding.value.productId4 || null,
        // 评论id
        evaluateId:binding.value.evaluateId || null,
        // 发货地址
        startAddress:binding.value.startAddress || null,
        // 收货地址
        endAddress:binding.value.endAddress || null,
        // 购物车id
        cartId:binding.value.cartId || null,
        // 购物车商品id
        productId5:binding.value.productId5 || null,
      }
    }
    if (binding.value.actionType == 'address-click') {
      //这里参数是根据自己业务可以自己定义
      address = {
        // 日期
        date:Date.now(),
        // 用户id
		    userId:localStorage.getItem('userId') || null,
        // 页面地址path
		    currentUrl:binding.value.currentUrl || null,
		    // 当前点击时间
		    actionTime:Date.now(),
        // 页面停留时间
        duration:null,
        // 搜索关键词
        searchKeyword:binding.value.searchKeyword || null,
        // 点击商品id
        productId1:binding.value.productId1 || null,
        // 商品分类
        categoryName:binding.value.categoryName || null,
        // 下单订单编号
        orderId1:binding.value.orderId1 || null,
        // 下单商品id
        productId2:binding.value.productId2 || null,
        // 支付订单编号
        orderId2:binding.value.orderId2 || null,
        // 支付商品id
        productId3:binding.value.productId3 || null,
        // 订单状态
        orderStatus:binding.value.orderStatus || null,
        // 收藏id
        collectId:binding.value.collectId || null,
        // 收藏商品id
        productId4:binding.value.productId4 || null,
        // 评论id
        evaluateId:binding.value.evaluateId || null,
        // 发货地址
        startAddress:binding.value.startAddress || null,
        // 收货地址
        endAddress:binding.value.endAddress || null,
        // 购物车id
        cartId:binding.value.cartId || null,
        // 购物车商品id
        productId5:binding.value.productId5 || null,
      }
    }
    if (binding.value.actionType == 'cart-click') {
      //这里参数是根据自己业务可以自己定义
      cart = {
        // 日期
        date:Date.now(),
        // 用户id
		    userId:localStorage.getItem('userId') || null,
        // 页面地址path
		    currentUrl:binding.value.currentUrl || null,
		    // 当前点击时间
		    actionTime:Date.now(),

        // 页面停留时间
        duration:null,
        // 搜索关键词
        searchKeyword:binding.value.searchKeyword || null,
        // 点击商品id
        productId1:binding.value.productId1 || null,
        // 商品分类
        categoryName:binding.value.categoryName || null,
        // 下单订单编号
        orderId1:binding.value.orderId1 || null,
        // 下单商品id
        productId2:binding.value.productId2 || null,
        // 支付订单编号
        orderId2:binding.value.orderId2 || null,
        // 支付商品id
        productId3:binding.value.productId3 || null,
        // 订单状态
        orderStatus:binding.value.orderStatus || null,
        // 收藏id
        collectId:binding.value.collectId || null,
        // 收藏商品id
        productId4:binding.value.productId4 || null,
        // 评论id
        evaluateId:binding.value.evaluateId || null,
        // 发货地址
        startAddress:binding.value.startAddress || null,
        // 收货地址
        endAddress:binding.value.endAddress || null,
        // 购物车id
        cartId:binding.value.cartId || null,
        // 购物车商品id
        productId5:binding.value.productId5 || null,
      }
    }
 },
 bind:(el,binding,vnode)=>{
	 if (binding.value.triggerType === 'click'){
	    //如果是click类型，监听click事件
	    el.addEventListener('click', (event) => {
	 		//调用后台接口保存数据
       if(binding.value.actionType == 'category-click'){
        console.log(category)
        axios({
          method:'post',
          url:'/spark/saveEventTrack',
          data:category
        })
      }
       if(binding.value.actionType == 'product-click'){
        console.log(product)
        if(product=={})return
         axios({
           method:'post',
           url:'/spark/saveEventTrack',
           data:product
         })
       }
       if(binding.value.actionType == 'search-click'){
        console.log(search)
        if(search == {})return
        axios({
          method:'post',
          url:'/spark/saveEventTrack',
          data:search
        })
       }
       if(binding.value.actionType == 'buy-click'){
        console.log(buy)
        // 下单操作传递的是数组，需要遍历操作
        for(let item of buy){
          let cur = {
            // 日期
            date:Date.now(),
            // 用户id
            userId:localStorage.getItem('userId') || null,
            // 页面地址path
            currentUrl:binding.value.currentUrl || null,
            // 当前点击时间
            actionTime:Date.now(),
    
            // 页面停留时间
            duration:null,
            // 搜索关键词
            searchKeyword:binding.value.searchKeyword || null,
            // 点击商品id
            productId1:binding.value.productId1 || null,
            // 商品分类
            categoryName:item.categoryName || null,
            // 下单订单编号
            orderId1:binding.value.orderId1 || null,
            // 下单商品id
            productId2:item.productId || null,
            // 支付订单编号
            orderId2:binding.value.orderId2 || null,
            // 支付商品id
            productId3:binding.value.productId3 || null,
            // 订单状态
            orderStatus:binding.value.orderStatus || null,
            // 收藏id
            collectId:binding.value.collectId || null,
            // 收藏商品id
            productId4:binding.value.productId4 || null,
            // 评论id
            evaluateId:binding.value.evaluateId || null,
            // 发货地址
            startAddress:binding.value.startAddress || null,
            // 收货地址
            endAddress:binding.value.endAddress || null,
            // 购物车id
            cartId:binding.value.cartId || null,
            // 购物车商品id
            productId5:binding.value.productId5 || null,
          }
          console.log(cur)
          axios({
            method:'post',
            url:'/spark/saveEventTrack',
            data:cur
          })
        }
       }
       if(binding.value.actionType == 'pay-click'){
        console.log(pay)
        axios({
          method:'post',
          url:'/spark/saveEventTrack',
          data:pay
        })
       }
       if(binding.value.actionType == 'collect-click'){
        console.log(collect)
        axios({
          method:'post',
          url:'/spark/saveEventTrack',
          data:collect
        })
       }
       if(binding.value.actionType == 'evaluate-click'){
        console.log(evaluate)
        axios({
          method:'post',
          url:'/spark/saveEventTrack',
          data:evaluate
        })
       }
       if(binding.value.actionType == 'cart-click'){
        console.log(cart)
        axios({
          method:'post',
          url:'/spark/saveEventTrack',
          data:cart
        })
       }
       
	     }, false)
	 }
 }
})
