<template>
  <div v-loading="loading">
    <el-form :inline="true"
             ref="selectForm"
             :model="params"
             label-width="60px"
             class="list-form">
      <el-form-item prop="menuName"
                    label="菜单名">
        <el-input placeholder="请输入菜单名"
                  v-model="params.menuName">
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   icon="el-icon-search"
                   @click="searchList">搜索</el-button>
        <el-button icon="el-icon-refresh"
                   @click="refresh('selectForm')">重置</el-button>
      </el-form-item>
      <div class="list-btn-box">
        <el-button plain
                   type="primary"
                   icon="el-icon-plus"
                   @click="showDialogForm(undefined)">新增</el-button>

      </div>
    </el-form>
    <el-table :data="tableData"
              stripe
              row-key="menuId"
              lazy
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
              height="490px">

      <el-table-column align="left"
                       prop="menuName"
                       label="菜单名">
      </el-table-column>
      <el-table-column align="center"
                       label="图标">
        <template slot-scope="scope">
          <i :class="scope.row.icon"></i>
        </template>
      </el-table-column>
      <!-- <el-table-column align="center"
                       label="菜单类型">
        <template slot-scope="scope">
          {{menuTypeMap[scope.row.menuType]}}
        </template>
      </el-table-column> -->
      <el-table-column align="center"
                       prop="component"
                       label="组件地址">
      </el-table-column>
      <el-table-column align="center"
                       prop="createTime"
                       label="创建时间"
                       :formatter="formatTime">
      </el-table-column>
      <el-table-column align="center"
                       label="操作">

        <template slot-scope="scope">
          <el-button type="text"
                     icon="el-icon-edit"
                     @click="handleEdit(scope.$index, scope.row)">修改</el-button>
          <el-button type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <menuSave ref='menuSave'
              @refresh-list="refreshList"></menuSave>
  </div>
</template>
<script>
import { listMenu, deleteMenu } from '@/api/system/menu';
import menuSave from './menu-save';
import { formatTime } from '@/utils/elementui-util'
import { handleTree } from '@/utils/tree-util'
export default {
  components: {
    menuSave
  },
  data () {
    return {
      menuTypeMap: {
        'M': "目录",
        'C': "菜单",
        'A': "按钮",
      },
      tableData: [],
      total: null,
      loading: true,
      params: {
        menuName: null,
      },
      menuIds: [],
      deleteDisable: true,

    };
  },
  created () {
    this.searchList();
  },
  methods: {
    formatTime,

    searchList () {
      this.loading = true;
      listMenu(this.params).then(resp => {
        this.tableData = handleTree(resp.data);


        this.$globeValue.loadingDelay(this);
      });
    },
    refresh (formName) {
      this.$refs[formName].resetFields();
    },
    showDialogForm (menuId) {
      this.$refs.menuSave.openDialog(menuId);
    },
    refreshList (menuId) {
      if (menuId === undefined) {
        this.params.pageNum = 1;
      }
      this.searchList();
    },
    handleEdit (index, row) {
      this.showDialogForm(row.menuId);
    },
    handleDelete (index, row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteMenu(row.menuId).then(() => {
          this.$message.success("删除成功");
          this.searchList();
        })
      }).catch(() => {
      });

    },


  },
};
</script>
