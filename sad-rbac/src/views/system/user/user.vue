<template>
  <div v-loading="loading">
    <el-form :inline="true"
             ref="selectForm"
             :model="params"
             label-width="60px"
             class="list-form">
      <el-form-item prop="userName"
                    label="用户名">
        <el-input placeholder="请输入用户名"
                  v-model="params.userName">
        </el-input>
      </el-form-item>
      <el-form-item prop="nickName"
                    label="昵称">
        <el-input placeholder="请输入昵称"
                  v-model="params.nickName">
        </el-input>
      </el-form-item>
      <el-form-item prop="phone"
                    label="电话">
        <el-input placeholder="请输入电话"
                  v-model="params.phone">
        </el-input>
      </el-form-item>
      <el-form-item prop="email"
                    label="邮箱">
        <el-input placeholder="请输入邮箱"
                  v-model="params.email">
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
                       label="用户编号"
                       width="100"></el-table-column>
      <el-table-column align="center"
                       prop="userName"
                       label="用户名">
      </el-table-column>
      <el-table-column align="center"
                       prop="nickName"
                       label="昵称">
      </el-table-column>
      <el-table-column align="center"
                       prop="phone"
                       label="电话">
      </el-table-column>
      <el-table-column align="center"
                       prop="email"
                       label="邮箱">
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
    <userSave ref='userSave'
              @refresh-list="refreshList"></userSave>
  </div>
</template>

<style scoped lang="scss">
.el-pagination {
  margin: 15px;
}
</style>

<script>
import { listUser, deleteUser } from '@/api/system/user';
import userSave from './user-save.vue';
import { formatTime } from '@/utils/elementui-util';
export default {
  components: {
    userSave
  },
  data () {
    return {
      tableData: null,
      total: null,
      loading: true,
      params: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        nickName: null,
        phone: null,
        email: null
      },
      userIds: [],
      deleteDisable: true,
    }
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
      listUser(this.params).then(resp => {
        this.tableData = resp.data.records;
        this.total = resp.data.total;
        this.$globeValue.loadingDelay(this);
      });
    },
    refresh (formName) {
      this.$refs[formName].resetFields();
    },
    showDialogForm (userId) {
      this.$refs.userSave.openDialog(userId);
    },
    refreshList (userId) {
      if (userId === undefined) {
        this.params.pageNum = 1;
      }
      this.searchList();
    },
    handleEdit (index, row) {
      this.showDialogForm(row.userId);
    },
    handleDelete (index, row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(row.userId).then(() => {
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
        deleteUser(this.userIds).then(() => {
          this.$message.success("删除成功");
          this.searchList();
        })
      }).catch(() => {
      });

    },
    handleSelectionChange (val) {
      this.userIds = val.map(item => item.userId);
      console.log(this.userIds)
      if (val.length != 0) {
        this.deleteDisable = false;
      } else {
        this.deleteDisable = true;
      }
    },

  }
}
</script>