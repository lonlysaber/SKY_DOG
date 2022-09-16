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
                { path: 'clientManage', title: '用户管理' , icon: "el-icon-setting"},
                { path: 'clientAdd', title: '用户添加' , icon: "el-icon-s-promotion" }
            ]
        }
    }
}
</script>

<!-- Admin1    adminManage -->