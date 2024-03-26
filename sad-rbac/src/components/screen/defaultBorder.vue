<template>
  <diV class="default-border-box"
       ref="markedContent">
    <div class="title">{{ title}}</div>
    <div class="border">
      <slot></slot>
    </div>
  </diV>
</template>


<script>
import Vue from "vue/dist/vue.esm.js";
export default {
  name: "wf-marked-content",
  props: ["content", "title"],
  mounted () {
    this.compile();
  },
  data () {
    return {

    }
  },
  methods: {
    compile () {
      const html = this.content;
      // eslint-disable-next-line no-undef
      const Component = Vue.extend({
        template: `<div> ${html} </div>`,
        data () {
          return {
          };
        },

        methods: {},
      });

      const component = new Component().$mount();

      this.$refs["markedContent"].appendChild(component.$el);
    },
  },
};
</script>
<style scoped>
.border {
  height: 100%;
  width: 100%;
  box-shadow: 0 0 1rem 14px rgb(7 75 107) inset;
  position: absolute;
  top: 13px;
  z-index: 1;
  display: -webkit-flex;
  justify-content: center;
  align-items: center;
}
.title {
  height: 30px;
  font-weight: 900;
  background: #074b6b;
  text-align: center;
  width: 60%;
  border-radius: 50px;
  position: absolute;
  z-index: 2;
  font-size: 15px;
}
.default-border-box {
  display: flex;
  position: relative;
  flex-direction: column;
  align-items: center; /*水平居中*/
}
</style>