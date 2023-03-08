const paths = require('./paths');
const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');
const CaseSensitivePathsPlugin = require('case-sensitive-paths-webpack-plugin');
const ignoredFiles = require('react-dev-utils/ignoredFiles');
const evalSourceMapMiddleware = require('react-dev-utils/evalSourceMapMiddleware');
const errorOverlayMiddleware = require('react-dev-utils/errorOverlayMiddleware');

module.exports = {
    mode: 'development',
    output: {
        publicPath: '/',
        pathinfo: true, // Add /* filename */ comments to generated require()s in the output.
    },
    devtool: 'cheap-module-source-map',
    devServer: {
        port: process.env.WEBPACK_DEV_PORT,
        disableHostCheck: true,
        historyApiFallback: true,
        hot: true,
        open: true,
        openPage: '',
        compress: true, // Enable gzip compression of generated files.
        clientLogLevel: 'none', // Silence WebpackDevServer's own logs since they're generally not useful. It will still show compile warnings and errors with this setting.
        transportMode: 'ws',
        injectClient: false, // Prevent a WS client from getting injected as we're already including webpackHotDevClient
        quiet: false,
        watchOptions: {
            ignored: ignoredFiles(paths.appSrc), // This avoids CPU overload on some systems.
        },
        overlay: false,
        before(app, server) {
            app.use(evalSourceMapMiddleware(server)); // This lets us fetch source contents from webpack for the error overlay
            app.use(errorOverlayMiddleware()); // This lets us open files from the runtime error overlay.
        },
        proxy: {
            context: ['/config', '/assets', '/api'],
            target: `http://localhost:${process.env.PORT}`
        }
    },
    plugins: [
        new ReactRefreshWebpackPlugin({
            overlay: {
                entry: require.resolve('react-dev-utils/webpackHotDevClient'),
                module: require.resolve('react-dev-utils/refreshOverlayInterop'),
                sockIntegration: false,
            },
        }),
        new CaseSensitivePathsPlugin(), // Watcher doesn't work well if you mistype casing in a path so we use a plugin that prints an error when you attempt to do this.
    ]
};
