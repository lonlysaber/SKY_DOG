
import Vue from 'vue'
import VueRouter from 'vue-router'

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err);
}

Vue.use(VueRouter)

// 不上传反盗链
let head = document.getElementsByTagName('head');
let meta = document.createElement('meta');
meta.name = 'referrer';
//根据实际情况修改referrer的值，可选值参考上文
meta.content = 'no-referrer';
head[0].appendChild(meta);

const routes = [
  {
    path:'',
    redirect:'/login'
  },
  {
    
    path: '/',  // 程序启动默认路由
    component: () => import('@/components/common/Whole.vue'),
    meta: { title: '整体页面布局' },

    children: [
      //首页页面
      {
        path:'',
        redirect:'home'
      },
      {
        path: '/home',
        component: () => import('../views/Home.vue'),
        meta: { title: '首页' }
      },

      //用户管理
      {
        path: 'user',
        component: () => import('@/views/user/index.vue'),
        meta: { title: '用户' },
        redirect: '/user/userManage',
        children: [
          {
            path: 'userManage',
            component: () => import('@/views/user/UserManage.vue'),
            meta: { title: '用户管理' },
          },
          {
            path: 'userAdd',
            component: () => import('@/views/user/UserAdd.vue'),
            meta: { title: '用户添加' },
          }
        ]
      },

      //商品管理
      {
        path: 'product',
        component: () => import('@/views/product/Index.vue'),
        meta: { title: '商品' },
        redirect: '/product/manage',  // 该配置是若点击选择一级菜单时，默认选中并跳转到该一级菜单下的第一个二级菜单
        children: [
          {
            path: 'manage',
            component: () => import('@/views/product/ProductManage.vue'),
            meta: { title: '商品管理' },
          },
          {
            path: 'search',
            component: () => import('@/views/product/ProductSearch.vue'),
            meta: { title: '商品管理' },
          },
          {
            path: 'release',
            component: () => import('@/views/product/ProductRelease.vue'),
            meta: { title: '商品发布' },
          }
        ]
      },

      //订单管理
      {
        path: 'order',
        component: () => import('@/views/Order/index.vue'),
        meta: { title: '订单' },
        redirect: '/Order/orderManage',  // 该配置是若点击选择一级菜单时，默认选中并跳转到该一级菜单下的第一个二级菜单
        children: [
          {
            path: 'orderManage',
            component: () => import('@/views/Order/orderManage.vue'),
            meta: { title: '订单管理' },
          },
          {
            path: 'orderRelease',
            component: () => import('@/views/Order/orderRelease.vue'),
            meta: { title: '订单发布' },
          }
        ]
      },

      //员工管理
      {
        path: 'address',
        component: () => import('@/views/address/index.vue'),
        meta: { title: '员工' },
        redirect: '/address/addressManage',  // 该配置是若点击选择一级菜单时，默认选中并跳转到该一级菜单下的第一个二级菜单
        children: [
          {
            path: 'addressManage',
            component: () => import('@/views/address/AddressManage.vue'),
            meta: { title: '员工管理' },
          },
          {
            path: 'addressAdd',
            component: () => import('@/views/address/AddressAdd.vue'),
            meta: { title: '员工添加' },
          },
        ]
      },

    ]
  },
  //员工登录进入后台管理页面
  {
    path: '/login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '登录' }
  },

  //注册
  {
    path: '/regist',
    component: () => import('../views/Regist.vue'),
    meta: { title: '注册' }
  },


]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
