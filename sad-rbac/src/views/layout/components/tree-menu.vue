<template>
  <div>
    <template v-for="(m, i) in data">
      <el-submenu :index="m.meta.id+''"
                  :key="i"
                  v-if="m.meta.type === 'M' &&  m.meta.isShow==true">
        <template slot="title">
          <i :class="m.meta.icon"></i>
          <span slot="title">{{ m.meta.title }}</span>
        </template>
        <tree-menu :data="m.children"
                   :parentPath="m.path"> </tree-menu>
      </el-submenu>
      <el-menu-item :index="parentPath+'/'+m.path"
                    :key="i"
                    v-else-if=" m.meta.type === 'C' &&  m.meta.isShow==true">
        <i :class="m.meta.icon"></i>
        <span slot="title"> {{
       m.meta.title
      }}</span></el-menu-item>
    </template>
  </div>
</template>
<style lang="scss">
/*隐藏文字*/
.el-menu--collapse .el-submenu__title span {
  display: none;
}
/*隐藏 > */
.el-menu--collapse .el-submenu__title .el-submenu__icon-arrow {
  display: none;
}
</style>

<script>
export default {
  props: {
    data: {
      type: Array,

    },
    parentPath: {
      type: String,
      default: ""
    }
  },
  name: "treeMenu",
  data () {
    return {
      //   datas: [{
      //     path: "/system",
      //     meta: {
      //       title: "系统管理",
      //       type: "M",
      //       icon: "",
      //       id: "1"
      //     },
      //     children: [{
      //       path: "user",
      //       meta: {
      //         title: "用户管理",
      //         type: "C",
      //         icon: "",
      //         id: "2"
      //       },
      //     }]
      //   }]
    };
  },
  created () { },
};
</script>
