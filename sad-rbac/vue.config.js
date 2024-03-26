const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  pages: {
    index: {
      entry: 'src/main.js', // 入口文件
      title: '充电桩监控平台'
    }
  },
  transpileDependencies: true,

  configureWebpack: {
    devtool: 'source-map', // 配置本地调试
    module: {}
  },
  devServer: {
    host: '0.0.0.0',
    // https:true,
    port: 8080,
    client: {
      webSocketURL: 'ws://0.0.0.0:8080/ws',
    },
    headers: {
      'Access-Control-Allow-Origin': '*',
    }
  }
})
