<template>
    <div>

        <!-- 一级菜单下面所拥有的二级菜单 -->
        <el-aside>
            <SideMenu :itemList='itemList'></SideMenu>
        </el-aside>
        <!-- 以及二级菜单所对应的页面 -->
        <el-main>
            <router-view></router-view>
        </el-main>

    </div>
</template>
 
<script>
import SideMenu from '@/components/sidemenu/SideMenu';
export default {
    components: {
        SideMenu
    },
    data() {
        return {
            itemList: [
                { path: 'orderManage', title: '订单管理', icon: "el-icon-setting" },
                { path: 'orderRelease', title: '订单发布', icon: "el-icon-s-promotion" }
            ]
        }
    }
}
</script>

<!-- Order1   orderManage     orderRelease-->