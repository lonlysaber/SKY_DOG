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
                { path: 'manage', title: '商品管理', icon: "el-icon-setting" },
                // { path: 'search', title: '商品索引', icon: "el-icon-search" },
                { path: 'release', title: '商品发布', icon: "el-icon-s-promotion" }
            ]
        }
    }
}
</script>