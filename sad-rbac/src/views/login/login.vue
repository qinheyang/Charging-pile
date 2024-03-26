<template>

  <div ref="vantaRef"
       class="login-body">
    <el-form class="login-container demo-ruleForm"
             :model="loginForm"
             status-icon
             :rules="rules"
             ref="ruleForm"
             label-width="100px">
      <h1 class="login-title">充电桩监控平台</h1>
      <el-form-item prop="userName">
        <img src="@/assets/login/username.png"
             class="login-icon username-icon" />
        <el-input type="text"
                  v-model="loginForm.userName"
                  autocomplete="off"
                  placeholder="用户名"
                  class="input-box"></el-input>
      </el-form-item>
      <el-form-item prop="checkPass">
        <img src="@/assets/login/password.png"
             class="login-icon username-icon" />
        <el-input type="password"
                  v-model="loginForm.password"
                  placeholder="密码"
                  autocomplete="off"
                  class="input-box">
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   @click="submitForm('ruleForm')">
          登 录
        </el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
import { login } from '@/api/system/login';
import { setToken, removeToken } from '@/utils/auth'
import * as THREE from 'three'
import BIRDS from 'vanta/src/vanta.birds'
export default {
  name: "login",
  data () {
    return {
      loginForm: {
        userName: "",
        password: "",

      },
      rules: {
        userName: [{ required: true, message: '请输入昵称', trigger: "blur" }],
        password: [{ required: true, message: '请输入密码', trigger: "blur" }],

      },
    };
  },
  mounted () {
    this.vantaEffect = BIRDS({
      el: this.$refs.vantaRef,
      THREE: THREE
    })
  },
  beforeDestroy () {
    if (this.vantaEffect) {
      this.vantaEffect.destroy()
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const loading = this.$loading({
            lock: true,
            text: '登录中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          removeToken();
          login(this.loginForm).then(resp => {
            console.log(resp);
            setToken(resp.token)
            loading.close()
            this.$router.push({ path: '/' })

          }).catch(() => {
            loading.close()

          })

        } else {
          return false;
        }
      });
    },
    resetForm (formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>
<style scoped lang="scss">
.login-body {
  width: 100%;
  height: 100%;
  // background: url(@/assets /login/background.jpg);
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-container {
  height: 35%;
  width: 23%;
  border-radius: 20px;
  -webkit-backdrop-filter: blur(20px);
  background: #ffffffba;
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
}
/deep/.el-form-item__content {
  margin: 0 !important;
  display: flex;
  flex-direction: row;
  align-items: center;
  .login-icon {
    width: 25px;
    height: 25px;
    margin-right: 10px;
    color: #9a9696;
  }
}
/deep/ .el-input__inner {
  background: transparent;
  padding: 0;
  border: 0;
  border-bottom: 2px solid #9a9696;
  border-radius: 0%;
  font-size: 17px;
  color: #9a9696;
}
/deep/ .el-input__inner::-webkit-input-placeholder {
  color: #9a9696;
}

.login-title {
  color: #000;
  font-size: 28px;
}
.el-button {
  font-weight: 900;
  font-size: 18px;
  width: 80px;
  margin-top: 10px;
}
</style>