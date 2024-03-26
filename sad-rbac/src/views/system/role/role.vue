<template>
  <div v-loading="loading">
    <el-form :inline="true"
             ref="selectForm"
             :model="params"
             label-width="60px"
             class="list-form">
      <el-form-item prop="roleName"
                    label="角色名">
        <el-input placeholder="请输入角色名"
                  v-model="params.roleName">
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
        <el-button plain
                   type="danger"
                   icon="el-icon-delete"
                   :disabled="deleteDisable"
                   @click="deleteByIds()">批量删除</el-button>

      </div>
    </el-form>
    <el-table :data="tableData"
              stripe
              @selection-change="handleSelectionChange"
              height="490px">
      <el-table-column align="center"
                       type="selection"
                       fixed></el-table-column>
      <el-table-column type="index"
                       align="center"
                       label="角色编号"
                       width="100"></el-table-column>
      <el-table-column align="center"
                       prop="roleName"
                       label="角色名">
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
    <el-pagination background
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :current-page="params.pageNum"
                   :page-sizes="[10, 20, 30, 40]"
                   :page-size="params.pageSize"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>
    <roleSave ref='roleSave'
              @refresh-list="refreshList"></roleSave>
  </div>
</template>
<script>
import { listRole, deleteRole } from '@/api/system/role';
import roleSave from './role-save';
import { formatTime } from '@/utils/elementui-util'
export default {
  components: {
    roleSave
  },
  data () {
    return {
      tableData: null,
      total: null,
      loading: true,
      params: {
        pageNum: 1,
        pageSize: 10,
        roleName: null,
      },
      roleIds: [],
      deleteDisable: true,

    };
  },
  created () {
    this.searchList();
  },
  methods: {
    formatTime,
    handleSizeChange (val) {
      this.params.pageSize = val;
      this.searchList();

    },
    handleCurrentChange (val) {
      this.params.pageNum = val;
      this.searchList();
    },
    searchList () {
      this.loading = true;
      listRole(this.params).then(resp => {
        this.tableData = resp.data.records;
        this.total = resp.data.total;
        this.$globeValue.loadingDelay(this);
      });
    },
    refresh (formName) {
      this.$refs[formName].resetFields();
    },
    showDialogForm (roleId) {
      this.$refs.roleSave.openDialog(roleId);
    },
    refreshList (roleId) {
      if (roleId === undefined) {
        this.params.pageNum = 1;
      }
      this.searchList();
    },
    handleEdit (index, row) {
      this.showDialogForm(row.roleId);
    },
    handleDelete (index, row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteRole(row.roleId).then(() => {
          this.$message.success("删除成功");
          this.searchList();
        })
      }).catch(() => {
      });

    },
    deleteByIds () {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteRole(this.roleIds).then(() => {
          this.$message.success("删除成功");
          this.searchList();
        })
      }).catch(() => {
      });

    },
    handleSelectionChange (val) {
      this.roleIds = val.map(item => item.roleId);
      console.log(this.roleIds)
      if (val.length != 0) {
        this.deleteDisable = false;
      } else {
        this.deleteDisable = true;
      }
    },
  },
};
</script>
