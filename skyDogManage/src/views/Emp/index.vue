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
                { path: 'empManage', title: '员工管理', icon: "el-icon-setting" },
                { path: 'empAdd', title: '员工添加', icon: "el-icon-s-promotion" },
                { path: 'rankList', title: '员工排行', icon: "el-icon-s-data" }
            ]
        }
    }
}
</script>

<!-- Admin1    adminManage -->