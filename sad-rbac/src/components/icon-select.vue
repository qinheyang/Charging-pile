<template>
  <div style="width:100%;height:40px;"
       class="chooseIcons">
    <el-popover placement="bottom"
                width="450px"
                trigger="manual"
                v-model="visible">
      <span slot="reference"
            style="display:inline-block;width:100%;height:40px;line-height:40px;">
        <el-input :prefix-icon="userChooseIcon"
                  type="text"
                  :value="userChooseIcon"
                  readonly
                  @click.native="visible = !visible" />

      </span>
      <div class="iconList">
        <i v-for="item in iconList"
           :key="item"
           :class="item"
           @click="setIcon(item)"
           style="font-size:20px"></i>
      </div>
    </el-popover>
  </div>
</template>

<script>
import { icons } from '@/utils/elementui-util'

export default {
  created () {
    this.iconList = icons;
  },
  data () {
    return {

      icons,
      chooseIcons: undefined,
      iconList: undefined,
      userChooseIcon: undefined,
      visible: false,
    }
  },

  methods: {
    setIcon (icon) {
      this.userChooseIcon = icon;//将i的样式设为选中的样式el-icon-xxx
      this.visible = false;
      this.$emit("getIcon", icon)
    },
    clearIcon () {
      this.userChooseIcon = undefined;
    },
    setDefaultValue (icon) {
      this.userChooseIcon = icon;
    }
  }

}
</script>

<style  lang="scss">
.el-input__icon {
  color: #000000 !important;
}
.iconList {
  width: 400px;
  height: 300px;
  overflow-y: scroll; //y轴显示滚动条
  overflow-x: hidden; //x轴溢出隐藏
  display: flex;
  //justify-content: space-around;
  flex-wrap: wrap; //一定要换行
  i {
    display: inline-block;
    width: 60px;
    height: 45px;
    color: #000000;
    font-size: 20px;
    border: 1px solid #e6e6e6;
    border-radius: 4px;
    cursor: pointer;
    text-align: center;
    line-height: 45px;
    margin: 5px;
    &:hover {
      color: #000000;
      border-color: #000000;
    }
  }
}
</style>
