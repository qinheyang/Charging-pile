<template>
  <div class="layout-container">
    <el-container>
      <layout-aside :isCollapse="isCollapse"></layout-aside>
      <el-container>
        <el-header>
          <layout-header @change-collapse="changeCollapse(collapse)"></layout-header>
        </el-header>
        <el-main>
          <layout-main></layout-main>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<style  lang="scss">
.layout-container {
  height: 100%;
  .el-container {
    height: 100%;
  }
}
.el-aside {
  overflow-y: hidden;

  ul {
    overflow-y: hidden;
    overflow-x: hidden;
  }
}
.el-menu {
  height: 100%;
}
.el-header {
  border-bottom: 1px solid #bec2c9;
}
.el-menu-vertical:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.collapse-icon i {
  font-size: 25px;
}
.el-main {
  overflow-y: hidden;
}
</style>
<script>
import layoutMain from "@/views/layout/components/layout-main.vue";
import layoutAside from './components/layout-aside.vue';
import layoutHeader from './components/layout-header.vue';
export default {
  components: {
    layoutAside,
    layoutMain,
    layoutHeader
  },
  data () {
    return {
      isCollapse: false,


    };
  },

  methods: {
    changeCollapse () {
      this.isCollapse = !this.isCollapse;
    }
  }

};
</script>
