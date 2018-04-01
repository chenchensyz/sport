var webpack = require('webpack');
module.exports = {

  entry:{
    "app":'./router.js'
  },
  output: {
    // path: __dirname,
    filename: '[name].js',
    // publicPath: '/',
    chunkFilename: '[name].chunk.js'
  },

  module: {
    loaders: [
      { test: /\.js$/, exclude: /node_modules/, loader: 'babel-loader?presets[]=es2015&presets[]=react' },
      {test:/\.css$/,loader: 'style-loader!css-loader'},
      {test: /\.less$/,loaders: ['style','css?-minimize','postcss','less']},
      { test: /\.scss$/, loader: 'style!css!sass'},
      { test: /\.(jpg|png)$/, loader: "url-loader?limit=8192&name=images/[hash:8].[name].[ext]"}
      ]
  },
  plugins: [
    new webpack.optimize.UglifyJsPlugin()
  //   // new webpack.optimize.UglifyJsPlugin({
  //   //   compress: {
  //   //     warnings: false
  //   //   }
  //   // })
  ]
  // console.log(plugins)
}
