
// For supporting browser history callback if navigation library supports this
config.devServer = {
  ...config.devServer, // Merge with other devServer settings
  "historyApiFallback": true,
  "headers": {
    "Access-Control-Allow-Origin": "*",
  }
};
