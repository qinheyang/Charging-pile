<template>
  <div v-loading="loading">
    <el-form :inline="true"
             ref="selectForm"
             :model="params"
             label-width="80px"
             class="list-form">
      <el-form-item prop="pileCode"
                    label="编号">
        <el-input placeholder="请输入充电桩编号"
                  v-model="params.pileCode">
        </el-input>
      </el-form-item>

      <el-form-item prop="speed"
                    label="充电速度">
        <el-input placeholder="请输入充电速度"
                  v-model="params.speed">
        </el-input>
      </el-form-item>
      <el-form-item prop="outputPower"
                    label="输出功率">
        <el-input placeholder="请输入最大输出功率"
                  v-model="params.outputPower">
        </el-input>
      </el-form-item>
      <el-form-item prop="pileSate"
                    label="状态">
        <el-select v-model="params.pileState"
                   style="width:92%"
                   clearable
                   placeholder="请选择充电桩状态">
          <el-option v-for="item in pileSateOption"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="requirement"
                    label="连接要求">
        <el-input placeholder="请输入连接要求"
                  v-model="params.requirement">
        </el-input>
      </el-form-item>

      <el-form-item prop="price"
                    label="价格">
        <el-input placeholder="请输入价格"
                  type="number"
                  v-model="params.price">
        </el-input>
      </el-form-item>
      <el-form-item prop="installTime"
                    label="安装时间">
        <el-date-picker v-model="installTimes"
                        type="datetimerange"
                        range-separator="至"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
        </el-date-picker>
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
              height="460px">
      <el-table-column align="center"
                       type="selection"></el-table-column>
      <el-table-column align="center"
                       prop="pileCode"
                       label="充电桩编号">
      </el-table-column>
      <el-table-column align="center"
                       prop="speed"
                       label="充电速度">
      </el-table-column>
      <el-table-column align="center"
                       prop="outputPower"
                       label="最大输出功率">
      </el-table-column>
      <el-table-column align="center"
                       width="50"
                       label="状态">
        <template slot-scope="scope">
          {{sateMap[scope.row.pileState]}}
        </template>
      </el-table-column>
      <el-table-column align="center"
                       prop="requirement"
                       label="连接要求">
      </el-table-column>
      <el-table-column align="center"
                       prop="price"
                       label="价格(元)">
      </el-table-column>
      <el-table-column align="center"
                       prop="installTime"
                       label="安装时间"
                       :formatter="formatTime">
      </el-table-column>
      <el-table-column align="center"
                       prop="createTime"
                       label="创建时间"
                       :formatter="formatTime">
      </el-table-column>
      <el-table-column align="center"
                       width="200"
                       label="操作">

        <template slot-scope="scope">

          <el-button type="text"
                     icon="el-icon-edit"
                     @click="handleEdit(scope.$index, scope.row)">修改</el-button>
          <el-button type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          <el-button type="text"
                     icon="el-icon-tickets"
                     @click="handleRepair(scope.$index, scope.row)">维修记录</el-button>
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
    <pileSave ref='pileSave'
              @refresh-list="refreshList"></pileSave>
    <repairList ref='repairList'></repairList>
  </div>
</template>
<script>
import { listPile, deletePile, exportPile } from '@/api/charge/pile';
import pileSave from './pile-save';
import repairList from './repair-list';
import { formatTime } from '@/utils/elementui-util'

export default {
  components: {
    pileSave,
    repairList
  },
  data () {
    return {
      pileSateOption: [
        { label: '正常', value: "0" },
        { label: '故障', value: "1" },
      ],
      sateMap: {
        '0': "正常",
        '1': "故障"
      },
      installTimes: [],
      tableData: null,
      table: null,
      total: null,
      loading: true,
      params: {
        pageNum: 1,
        pageSize: 10,
        pileCode: null,
        pileState: null,
        speed: null,
        outputPower: null,
        requirement: null,
        beginInstallTime: null,
        endInstallTime: null,


      },
      pileIds: [],
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
      this.params.beginInstallTime = this.installTimes[0];
      this.params.endInstallTime = this.installTimes[1];
      this.loading = true;
      listPile(this.params).then(resp => {
        this.tableData = resp.data.records;
        this.total = resp.data.total;
        this.$globeValue.loadingDelay(this);
      });
    },
    refresh (formName) {
      this.installTimes = []
      this.params.beginInstallTime = null;
      this.params.endInstallTime = null;
      this.$refs[formName].resetFields();
    },
    showDialogForm (pileId) {
      this.$refs.pileSave.openDialog(pileId);
    },
    refreshList (pileId) {
      if (pileId === undefined) {
        this.params.pageNum = 1;
      }
      this.searchList();
    },
    handleEdit (index, row) {
      this.showDialogForm(row.pileId);
    },
    handleDelete (index, row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deletePile(row.pileId).then(() => {
          this.$message.success("删除成功");
          this.searchList();
        })
      }).catch(() => {
      });

    },
    handleRepair (index, row) {
      this.$refs.repairList.openDialog(row.pileCode);

    },
    deleteByIds () {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deletePile(this.pileIds).then(() => {
          this.$message.success("删除成功");
          this.searchList();
        })
      }).catch(() => {
      });

    },
    handleSelectionChange (val) {
      this.pileIds = val.map(item => item.pileId);
      if (val.length != 0) {
        this.deleteDisable = false;
      } else {
        this.deleteDisable = true;
      }
    },
    exportExecl () {

      exportPile(this.params).then((resp) => {
        let url = process.env.VUE_APP_BASE_API + process.env.VUE_APP_BASE_URL + process.env.VUE_APP_BASE_PROFILE + process.env.VUE_APP_BASE_DOWNLOAD + "/" + resp.msg;
        window.open(url, "_self")
        console.log(resp)
      });
    }


  },
};
</script>
