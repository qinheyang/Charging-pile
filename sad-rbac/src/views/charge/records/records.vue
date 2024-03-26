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
      <el-form-item prop="carNumber"
                    label="车牌号">
        <el-input placeholder="请输入充电车牌号"
                  v-model="params.carNumber">
        </el-input>
      </el-form-item>
      <el-form-item prop="carType"
                    label="车牌类型">
        <el-select v-model="params.carType"
                   style="width:92%"
                   clearable
                   placeholder="请选择车辆类型">
          <el-option v-for="item in carTypeList"
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
      <div style="display: flex">
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
        <el-upload style="margin-left:0.9%"
                   action="#"
                   :http-request="uploadExecl"
                   accept=".xlsx"
                   :show-file-list="false">
          <el-button plain
                     type="info"
                     icon="el-icon-upload2"> 导入 </el-button></el-upload>

      </div>
    </el-form>
    <el-table :data="tableData"
              stripe
              @selection-change="handleSelectionChange"
              height="490px">
      <el-table-column align="center"
                       type="selection"></el-table-column>
      <el-table-column align="center"
                       prop="pileCode"
                       label="充电桩编号">
      </el-table-column>

      <el-table-column align="center"
                       prop="carNumber"
                       label="车牌号">
      </el-table-column>
      <el-table-column align="center"
                       label="车辆类型">
        <template slot-scope="scope">
          {{carTypeMap[scope.row.carType]}}
        </template>
      </el-table-column>
      <el-table-column align="center"
                       prop="beginTime"
                       label="充电开始时间"
                       :formatter="formatTime">
      </el-table-column>
      <el-table-column align="center"
                       prop="endTime"
                       label="充电结束时间"
                       :formatter="formatTime">
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
    <recordsSave ref='recordsSave'
                 @refresh-list="refreshList"></recordsSave>
  </div>
</template>
<script>
import { listRecords, deleteRecords, exportRecords, importRecords } from '@/api/charge/records';
import recordsSave from './records-save';
import { formatTime } from '@/utils/elementui-util'

export default {
  components: {
    recordsSave
  },
  data () {
    return {
      sateMap: {
        '0': "正常",
        '1': "故障"
      },
      carTypeMap: {
        '0': "私家车",
        '1': "出租车",
        '2': "公交车",
      },
      carTypeList: [
        { label: "私家车", value: "0" },
        { label: "出租车", value: "1" },
        { label: "公交车", value: "2" }
      ],
      tableData: null,
      table: null,
      total: null,
      loading: true,
      params: {
        pageNum: 1,
        pageSize: 10,
        carNumber: null,
      },
      recordsIds: [],
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
      listRecords(this.params).then(resp => {
        this.tableData = resp.data.records;
        this.total = resp.data.total;
        this.$globeValue.loadingDelay(this);
      });
    },
    refresh (formName) {
      this.$refs[formName].resetFields();
    },
    showDialogForm (recordsId) {
      this.$refs.recordsSave.openDialog(recordsId);
    },
    refreshList (recordsId) {
      if (recordsId === undefined) {
        this.params.pageNum = 1;
      }
      this.searchList();
    },
    handleEdit (index, row) {
      this.showDialogForm(row.recordsId);
    },
    handleDelete (index, row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteRecords(row.recordsId).then(() => {
          this.$message.success("删除成功");
          this.searchList();
        })
      }).catch(() => {
      });

    },
    exportExecl () {
      exportRecords(this.params).then((resp) => {
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
        deleteRecords(this.recordsIds).then(() => {
          this.$message.success("删除成功");
          this.searchList();
        })
      }).catch(() => {
      });

    },
    handleSelectionChange (val) {
      this.recordsIds = val.map(item => item.recordsId);
      console.log(this.recordsIds)
      if (val.length != 0) {
        this.deleteDisable = false;
      } else {
        this.deleteDisable = true;
      }
    },

    uploadExecl (params) {
      const file = params.file;
      const fileSuffix = file.name.substring(file.name.lastIndexOf("."));
      if (!/\.(xlsx)$/.test(fileSuffix)) {
        this.$message({
          message: "文件类型要求：xlsx",
          type: "error",
        });
        return false;
      }
      const formData = new FormData();
      formData.append("file", file);
      importRecords(formData).then((res) => {
        if (res.code == 200) {
          this.$message.success(res.msg);
          this.searchList();
        }
      });
    }


  },
};
</script>
