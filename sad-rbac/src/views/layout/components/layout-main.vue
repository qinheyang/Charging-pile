<template>
  <keep-alive>
    <router-view :key="key"></router-view>
  </keep-alive>
</template>
<style lang="scss" scoped>
</style>
<script>
export default {
  computed: {
    key () {
      return this.$route.path
    }
  },
  data () {
    return {
      url: "/login",
    };
  },
};
</script>
