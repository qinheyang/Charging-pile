<template>
  <el-select style="width: 100%;"
             ref="selectTree"
             v-model="selectData.id"
             clearable
             @clear="clearSelectTree"
             placeholder="请选择">
    <el-option class="setstyle"
               :label="selectData.label"
               :value="selectData.id">
      <el-tree ref="tree"
               :row-key="defaultProps.id"
               :node-key="defaultProps.id"
               :default-expand-all="true"
               :expand-on-click-node="false"
               :current-node-key="selectData.id"
               :data="data"
               :props="defaultProps"
               @node-click="handleNodeClick"
               :highlight-current="true"></el-tree>
    </el-option>
  </el-select>
</template>

<script>

export default {
  name: 'treeSelect',
  props: ['treeSelectProps', 'data'],
  data () {
    return {
      defaultProps: {
        id: this.treeSelectProps.id ? this.treeSelectProps.id : 'id',
        label: this.treeSelectProps.label ? this.treeSelectProps.label : 'label',
        children: this.treeSelectProps.children ? this.treeSelectProps.children : 'children',
      },
      selectData: {
        id: undefined,
        label: undefined
      }

    }
  },

  methods: {
    clearSelectTree () {
      this.selectData.id = undefined;
      this.selectData.label = undefined;
      this.$emit('getSelectTreeData', this.selectData)

    },
    handleNodeClick (data) {
      this.selectData.id = data[this.defaultProps.id];
      this.selectData.label = data[this.defaultProps.label];
      this.$emit('getSelectTreeData', this.selectData)
      this.$refs.selectTree.blur();
    },
    setDefaultValue (data) {


      this.selectData.id = data[this.defaultProps.id];
      this.$nextTick(() => {
        this.$refs.tree.setCurrentKey(this.selectData.id)
        console.log()

      })
      this.selectData.label = data[this.defaultProps.label];

    }
  }


}

</script>

<style lang="scss" scoped>
.setstyle {
  height: 100%;
  padding: 0 !important;
  margin: 0;
  overflow: auto;
  cursor: default !important;
}
</style>