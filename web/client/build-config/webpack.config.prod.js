const paths = require('./paths');
const TerserPlugin = require('terser-webpack-plugin');
const InlineChunkHtmlPlugin = require('react-dev-utils/InlineChunkHtmlPlugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');

module.exports = {
    mode: 'production',
    devtool: 'source-map',
    bail: true, // Stop compilation early in production
    output: {
        path: paths.appDist,
        filename: 'scripts/[name].[contenthash:8].js',
        publicPath: paths.staticAssetsPublicUrl(true),
    },
    optimization: {
        minimize: true,
        minimizer: [
            new TerserPlugin({
                terserOptions: {
                    parse: { ecma: 8 },
                    compress: {
                        ecma: 5,
                        warnings: false,
                        comparisons: false,
                        inline: 2,
                    },
                    mangle: { safari10: true },
                    output: {
                        ecma: 5,
                        comments: false,
                        ascii_only: true, // Turned on because emoji and regex is not minified properly using default
                    },
                },
                parallel: true,
                cache: true,
                sourceMap: true,
            }),
            new CssMinimizerPlugin({
                sourceMap: false
            }),
        ],
        // Automatically split vendor and commons https://medium.com/webpack/webpack-4-code-splitting-chunk-graph-and-the-splitchunks-optimization-be739a861366
        splitChunks: {
            chunks: 'all',
            name: 'vendor',
        },
        // Keep the runtime chunk separated to enable long term caching https://twitter.com/wSokra/status/969679223278505985
        runtimeChunk: {
            name: 'runtime',
        }
    },
    plugins: [
        new InlineChunkHtmlPlugin(HtmlWebpackPlugin, [/runtime-.+[.]js/]),
        new MiniCssExtractPlugin({
            filename: 'styles/[name].[contenthash:8].css',
            chunkFilename: 'styles/[name].[contenthash:8].chunk.css',
        }),
    ]
};
