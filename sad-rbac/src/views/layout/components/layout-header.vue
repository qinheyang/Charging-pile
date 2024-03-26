 <template>
  <div class="header-box">
    <span style="flex:0 0 5%; text-align: left"
          class="collapse-icon">
      <i :class="collapseIcon"
         @click="hadleCollapse"></i> </span>

    <el-breadcrumb separator="/"
                   style="flex: 0 0 89%; text-align: right;">

      <el-breadcrumb-item v-for="(item, index) in breadcrumbList"
                          :key="index">
        <span v-if="index!==0||breadcrumbList.length===1">{{ item.meta.title }}</span>
        <a v-else
           :href="item.path">{{ item.meta.title}}</a>

      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-dropdown trigger="click">
      <span style="flex: 0 0 auto; text-align: right">{{this.loginName}} <i class="el-icon-arrow-down"></i></span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="screen">监控平台</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>
<style lang="scss">
.header-box {
  width: 100%;
  height: 100%;
  border-bottom: 1px solid #bec2c9;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
}
</style>
<script>
import { getCurrentUser } from '@/api/system/login';

export default {
  data () {
    return {
      isCollapse: false,
      collapseIcon: "el-icon-s-fold",
      breadcrumbList: null,
      loginName: ""
    };
  },
  created () {
    this.getBreadcrumbList();
    getCurrentUser().then(resp => {
      this.loginName = resp.data.sysUser.nickName;
    })


  },
  computed: {

  },
  watch: {
    isCollapse (n) {
      n ? this.collapseIcon = "el-icon-s-unfold" : this.collapseIcon = "el-icon-s-fold";
    },
    $route () {
      // if (route.path.startsWith('/redirect/')) {
      //   return
      // }
      this.getBreadcrumbList()
    }

  },
  methods: {
    hadleCollapse () {
      this.isCollapse = !this.isCollapse;
      this.$emit('change-collapse');

    },
    getBreadcrumbList () {
      let matched = this.$route.matched.filter(item => item.meta && item.meta.title);
      const first = matched[0]

      if (!this.isHome(first)) {
        matched = [{ path: '/', meta: { title: '首页' } }].concat(matched)
      }

      this.breadcrumbList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
    },
    isHome (route) {
      const name = route && route.name
      if (!name) {
        return false;
      }
      return name.trim() === 'home';
    },
    logout () {
      this.$router.push({ path: '/login' })
    },
    screen () {
      this.$router.push({ path: '/screen' })
    }
  },
}
</script>
