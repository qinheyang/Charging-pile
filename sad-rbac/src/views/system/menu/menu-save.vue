<template>
  <el-dialog :title="dialogFormTitle"
             :visible.sync="dialogFormVisible"
             class="save-form-div"
             :close-on-click-modal="$globeValue.clickModalClose"
             @closed=closed()>
    <el-form v-loading='loading'
             :model="saveForm"
             :rules="rules"
             ref="saveForm"
             label-width="100px"
             size="medium">
      <el-form-item prop="menuName"
                    label="菜单名">
        <el-input placeholder="请输入菜单名"
                  v-model="saveForm.menuName"
                  maxlength=50
                  auto-complete="off">
        </el-input>
      </el-form-item>
      <el-form-item prop="parentName"
                    label="上级菜单">
        <tree-select ref="treeSelect"
                     :data="menuTree"
                     :treeSelectProps="treeSelectPorps"
                     @getSelectTreeData="getSelectTreeData">
        </tree-select>
      </el-form-item>
      <el-form-item prop="menuType"
                    label="菜单类型">
        <el-radio-group v-model="saveForm.menuType">
          <el-radio v-for=" (m,i) in menuTypeArray"
                    :key="i"
                    :label="m.value">{{m.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-show="saveForm.menuType !=menuTypeArray[2].value"
                    prop="icon"
                    label="图标">
        <icon-select ref="iconSelect"
                     @getIcon="getIcon"></icon-select>
      </el-form-item>
      <el-form-item prop="path"
                    label="路由地址"
                    v-show="saveForm.menuType !=menuTypeArray[2].value">
        <el-input placeholder="请输入路由地址"
                  v-model="saveForm.path"
                  maxlength=200
                  auto-complete="off" />
      </el-form-item>
      <el-form-item prop="isFrame"
                    label="是否是外链"
                    v-show="saveForm.menuType !=menuTypeArray[2].value">
        <el-radio-group v-model="saveForm.isFrame">
          <el-radio v-for=" (m,i) in isFrameArray"
                    :key="i"
                    :label="m.value">{{m.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="component"
                    label="组件地址"
                    v-show="saveForm.menuType ==menuTypeArray[1].value">
        <el-input placeholder="请输入路由地址"
                  v-model="saveForm.component"
                  maxlength=200
                  auto-complete="off" />
      </el-form-item>

    </el-form>
    <div slot="footer"
         class="dialog-footer">
      <el-button type="primary"
                 @click="submitForm('saveForm')">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { handleTree } from '@/utils/tree-util';
import { updateMenu, addMenu, getMenuInfo, listMenu } from '@/api/system/menu';
import { icons } from "@/utils/elementui-util";
import treeSelect from "@/components/tree-select";
import { isEmpty } from "@/utils/string-util"
import { listToMap } from "@/utils/map-util"
import iconSelect from '@/components/icon-select.vue';


export default ({
  components:
  {

    treeSelect,
    iconSelect
  },
  watch: {
    "saveForm.menuType" (n) {
      if (n == this.menuTypeArray[2].value) {
        this.saveForm.icon = undefined;
        this.saveForm.path = undefined;
        this.saveForm.component = undefined;
        this.saveForm.icon = undefined;
        this.rules = this.buttonRules;
      } else if (n == this.menuTypeArray[0].value) {
        this.saveForm.component = undefined;
        this.rules = this.menuRules;
      } else {
        this.rules = this.catalogueRules;
      }
    }
  },
  data () {

    return {
      icons,
      treeSelectPorps: {
        id: 'menuId',
        label: 'menuName',
        children: 'children'
      },
      menuTree: [],
      treeMap: undefined,
      saveForm: {
        menuId: undefined,
        menuName: undefined,
        parentId: undefined,
        menuType: 'M',
        icon: undefined,
        path: undefined,
        isFrame: undefined,
        component: undefined

      },
      menuTypeArray: [
        { label: '目录', value: 'M' },
        { label: '菜单', value: 'C' },
        { label: '按钮', value: 'A' },
      ],
      isFrameArray: [
        { label: '是', value: "0" },
        { label: '否', value: "1" },

      ],

      loading: false,
      dialogFormVisible: false,
      dialogFormTitle: undefined,
      menuRules: {
        menuName: [
          { required: true, message: '请输入菜单名', trigger: 'blur' },
        ],

        menuType: [
          { required: true, message: '请选择菜单类型', trigger: 'change' },

        ],
        path: [{
          required: true, message: '请输入路由地址', trigger: 'blur'
        }],
        isFrame: [
          { required: true, message: '请选择是否为外部链接', trigger: 'change' }
        ]
      },
      catalogueRules: {
        menuName: [
          { required: true, message: '请输入菜单名', trigger: 'blur' },
        ],
        menuType: [
          { required: true, message: '请选择菜单类型', trigger: 'change' },

        ],
        path: [{
          required: true, message: '请输入路由地址', trigger: 'blur'
        }]
        ,
        isFrame: [
          { required: true, message: '请选择是否为外部链接', trigger: 'change' }
        ]
      },
      buttonRules: {
        menuName: [
          { required: true, message: '请输入菜单名', trigger: 'blur' },
        ],
        menuType: [
          { required: true, message: '请选择菜单类型', trigger: 'change' },

        ],

      },

      rules: {

      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.saveForm.menuId === undefined) {
            addMenu(this.saveForm).then(() => {
              this.cancel();
              this.$emit('refresh-list');
              this.$message.success("保存成功");
            })

          } else {
            updateMenu(this.saveForm).then(() => {
              this.cancel();
              this.$emit('refresh-list');
              this.$message.success("保存成功");
            })
          }
        }
      });
    },
    resetForm (formName) {
      this.saveForm = {
        menuId: undefined,
        menuName: undefined,
        parentId: undefined,
        menuType: 'M',
        icon: undefined,
        path: undefined,
        isFrame: undefined,
        component: undefined
      };
      this.$refs[formName].resetFields();
      this.$refs.treeSelect.clearSelectTree();
      this.$refs.iconSelect.clearIcon();


    },
    openDialog (menuId) {
      this.rules = this.menuRules;
      listMenu().then((resp) => {
        this.menuTree = handleTree(resp.data);
        this.treeMap = resp.data;
      })
      if (menuId === undefined) {
        this.dialogFormTitle = '菜单新增';

      } else {
        this.dialogFormTitle = '菜单修改';
        getMenuInfo(menuId).then((resp) => {
          this.saveForm = resp.data;
          if (!isEmpty(this.saveForm.parentId)) {
            this.treeMap = listToMap('menuId', this.treeMap);
            var data = {
              menuId: this.saveForm.parentId,
              menuName: this.treeMap[this.saveForm.parentId].menuName
            }
            this.$refs.treeSelect.setDefaultValue(data);
          }
          if (!isEmpty(this.saveForm.icon))
            this.$refs.iconSelect.setDefaultValue(this.saveForm.icon);
        })
      }
      this.dialogFormVisible = true;

    },

    cancel () {
      this.dialogFormVisible = false;
    },
    closed () {
      this.resetForm('saveForm');
    },
    getSelectTreeData (data) {
      this.saveForm.parentId = data.id;
      console.log(this.saveForm.parentId);
    },
    getIcon (icon) {
      this.saveForm.icon = icon;
    },



  }
})
</script>

