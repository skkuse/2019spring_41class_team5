module.exports = {
  pwa: {
    name: "Dealistic",
    appleMobileWebAppCapable: "yes",
    appleMobileWebAppStatusBarStye: "black-translucent"
  },
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8083',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
};