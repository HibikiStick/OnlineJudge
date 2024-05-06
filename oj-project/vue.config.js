const { defineConfig } = require("@vue/cli-service");
const MonacoWabpackPlugin = require("monaco-editor-webpack-plugin");

module.exports = defineConfig({
  transpileDependencies: true,
  chainWebpack(config) {
    config.plugin("monaco").use(new MonacoWabpackPlugin());
  },
});
