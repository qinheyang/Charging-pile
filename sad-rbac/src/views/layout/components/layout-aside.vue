<template>
  <el-aside width="auto">

    <el-menu :router=" true"
             class="el-menu-vertical"
             background-color=" rgb(48, 56, 86)"
             text-color="#fff"
             active-text-color="#ffd046"
             :collapse="isCollapse">
      <div class="home_tltle">
        <img height="50px"
             width="50px"
             src="@/assets/logo.png">

        <h1 v-show="!isCollapse">充电桩监控平台</h1>

      </div>
      <tree-menu :data="menuList"></tree-menu>
    </el-menu>
  </el-aside>
</template>
<style scoped>
.home_tltle {
  justify-content: center;
  align-items: center;
  display: flex;

  text-align: center;
  color: #fff;
  margin: 0;
  height: 50px;
  width: 100%;
  background-color: rgb(48, 56, 86);
}
</style>
<script>
import treeMenu from "@/views/layout/components/tree-menu.vue";

export default {
  components: {
    treeMenu,
  },
  computed: {
    menuList () {
      var menus = []
      for (let item of this.$router.options.routes) {
        if (item.redirect) {
          menus.push(item.children[0])
        } else {
          menus.push(item)
        }
      }
      menus.push(...this.$store.state.dynamicRoutes);
      return menus;
    }
  },
  props: ['isCollapse'],
  data () {
    return {
    }
  }

}
</script>