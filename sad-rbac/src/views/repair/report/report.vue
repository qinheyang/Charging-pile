<template>
  <div v-loading="loading">
    <el-form :inline="true"
             ref="selectForm"
             :model="params"
             label-width="70px"
             class="list-form">
      <el-form-item prop="reportCode"
                    label="编号">
        <el-input placeholder="请输入充电桩编号"
                  v-model="params.pileCode">
        </el-input>
      </el-form-item>
      <el-form-item prop="faultType"
                    label="故障类型">
        <el-select v-model="params.faultType"
                   clearable
                   placeholder="请选择故障类型"
                   style="width: 92%;">
          <el-option v-for="item in faultTypeList"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="repaired"
                    label="维护状态">
        <el-select v-model="params.repaired"
                   clearable
                   placeholder="请选择维护状态"
                   style="width: 92%;">
          <el-option v-for="item in repairedList"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
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
        <el-button plain
                   type="warning"
                   icon="el-icon-download"
                   @click="exportExecl()">导出</el-button>
      </div>
    </el-form>
    <el-table :data="tableData"
              stripe
              @selection-change="handleSelectionChange"
              height="490px">
      <el-table-column align="center"
                       type="selection">
      </el-table-column>
      <el-table-column align="center"
                       prop="pileCode"
                       label="故障充电桩编号">
      </el-table-column>
      <el-table-column align="center"
                       label="故障类型">
        <template slot-scope="scope">
          {{typeMap[scope.row.faultType]}}
        </template>

      </el-table-column>
      <!-- <el-table-column align="center"
                       prop="pileCode"
                       label="故障充电桩编号">
      </el-table-column> -->
      <el-table-column align="center"
                       label="是否维修">
        <template slot-scope="scope">
          {{repairMap[scope.row.repaired]}}
        </template>
      </el-table-column>

      <el-table-column align="center"
                       prop="createTime"
                       label="故障上报时间"
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
    <reportSave ref='reportSave'
                @refresh-list="refreshList"></reportSave>
  </div>
</template>
<script>
import { listReport, deleteReport, exportReport } from '@/api/repair/report';
import { getCurrentUser } from '@/api/system/login';
import reportSave from './report-save';
import { formatTime } from '@/utils/elementui-util'

export default {
  components: {
    reportSave
  },
  data () {
    return {
      repairMap: {
        '0': "未维修",
        '1': "已维修"
      },
      repairedList: [
        { value: "0", label: "未维修" },
        { value: "1", label: "已维修" },
      ],
      faultTypeList: [{ value: "0", label: "插头损坏" },
      { value: "1", label: "线路断裂" },
      { value: "2", label: "接触器故障" },
      { value: "3", label: "电源失效" },
      { value: "4", label: "其它" }
      ],
      typeMap: {
        "0": "插头损坏",
        "1": "线路断裂",
        "2": "接触器故障",
        "3": "电源失效",
        "4": "其它"
      },
      tableData: null,
      table: null,
      total: null,
      loading: true,
      params: {
        pageNum: 1,
        pageSize: 10,
        carNumber: null,
        createBy: null,
        pileCode: null,
      },
      reportIds: [],
      deleteDisable: true,


    };
  },
  created () {
    getCurrentUser().then(resp => {
      this.params.createBy = resp.data.sysUser.userName;
      this.searchList();
    })

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
      listReport(this.params).then(resp => {
        this.tableData = resp.data.records;
        this.total = resp.data.total;
        this.$globeValue.loadingDelay(this);
      });
    },
    refresh (formName) {
      this.$refs[formName].resetFields();
    },
    showDialogForm (reportId) {
      this.$refs.reportSave.openDialog(reportId);
    },
    refreshList (reportId) {
      if (reportId === undefined) {
        this.params.pageNum = 1;
      }
      this.searchList();
    },
    handleEdit (index, row) {
      this.showDialogForm(row.reportId);
    },
    handleDelete (index, row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteReport(row.reportId).then(() => {
          this.$message.success("删除成功");
          this.searchList();
        })
      }).catch(() => {
      });

    },
    exportExecl () {
      exportReport(this.params).then((resp) => {
        let url = process.env.VUE_APP_BASE_API + process.env.VUE_APP_BASE_URL + process.env.VUE_APP_BASE_PROFILE + process.env.VUE_APP_BASE_DOWNLOAD + "/" + resp.msg;
        window.open(url, "_self")
      });
    },
    deleteByIds () {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteReport(this.reportIds).then(() => {
          this.$message.success("删除成功");
          this.searchList();
        })
      }).catch(() => {
      });

    },
    handleSelectionChange (val) {
      this.reportIds = val.map(item => item.reportId);
      console.log(this.reportIds)
      if (val.length != 0) {
        this.deleteDisable = false;
      } else {
        this.deleteDisable = true;
      }
    },


  },
};
</script>
