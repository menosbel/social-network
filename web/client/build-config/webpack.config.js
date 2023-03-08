const path = require('path');
require('dotenv').config({ path: path.resolve(path.join(process.cwd(), '../'), '.env') });
const paths = require('./paths');
const { merge } = require('webpack-merge');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const HtmlWebPackPlugin = require('html-webpack-plugin');
const ModuleNotFoundPlugin = require('react-dev-utils/ModuleNotFoundPlugin');
const webpack = require('webpack');
const ESLintPlugin = require('eslint-webpack-plugin');
const ModuleScopePlugin = require('react-dev-utils/ModuleScopePlugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

const commonConfig = (webpackEnv) => {
    const isEnvProduction = webpackEnv === 'prod';

    return {
        entry: {
            app: [paths.appIndexTsx],
        },
        resolve: {
            extensions: ['.js', '.jsx', 'mjs', '.ts', '.tsx', '.css', 'json'],
            modules: [
                'node_modules',
                paths.appSrc,
            ],
            plugins: [
                new ModuleScopePlugin(paths.appSrc, [
                    paths.appPackageJson,
                    require.resolve('react-dev-utils/refreshOverlayInterop'),
                ]),
            ]
        },
        module: {
            rules: [
                {
                    // 'oneOf' match one of the following loaders or fall back to the 'file' loader
                    oneOf: [
                        {
                            test: [/\.bmp$/, /\.gif$/, /\.jpe?g$/, /\.png$/],
                            loader: require.resolve('url-loader'),
                            options: {
                                name: 'assets/[name].[contenthash:8].[ext]',
                                limit: 10000 // embed as data url assets smaller than 10000 bytes to avoid requests
                            },
                        },
                        {
                            test: /\.(js|jsx|mjs|ts|tsx)$/,
                            exclude: /node_modules/,
                            loader: 'babel-loader',
                            options: {
                                plugins: [
                                    !isEnvProduction && require.resolve('react-refresh/babel')
                                ].filter(Boolean),
                                cacheDirectory: true, //It enables caching results in ./node_modules/.cache/babel-loader/ directory for faster rebuilds.
                                cacheCompression: false,
                                compact: isEnvProduction,
                            }
                        },
                        {
                            test: /\.css$/,
                            use: [
                                isEnvProduction && { loader: MiniCssExtractPlugin.loader },
                                !isEnvProduction && { loader: 'style-loader' },
                                { loader: 'css-loader' },
                            ].filter(Boolean)
                        },
                        {
                            loader: require.resolve('file-loader'),
                            exclude: [/\.(js|mjs|jsx|ts|tsx)$/, /\.html$/, /\.json$/],
                            options: {
                                name: 'assets/[name].[hash:8].[ext]',
                            }
                        },
                        // Make sure to add the new loader(s) before the 'file' loader.
                    ]
                }
            ]
        },
        plugins: [
            new ForkTsCheckerWebpackPlugin(),
            new CleanWebpackPlugin(),
            new HtmlWebPackPlugin(Object.assign({},
                {
                    template: paths.appIndexHtml,
                    filename: './index.html',
                    inject: true,
                    templateParameters: {
                        staticAssetsPublicUrl: paths.stripEndingSlash(paths.staticAssetsPublicUrl(isEnvProduction)),
                    }
                },
                isEnvProduction ? {
                    minify: {
                        removeComments: true,
                        collapseWhitespace: true,
                        removeRedundantAttributes: true,
                        useShortDoctype: true,
                        removeEmptyAttributes: true,
                        removeStyleLinkTypeAttributes: true,
                        keepClosingSlash: true,
                        minifyJS: true,
                        minifyCSS: false,
                        minifyURLs: true,
                    },
                } : undefined
            )),
            new ModuleNotFoundPlugin(paths.appPath), // This gives some necessary context to module not found errors, such as the requesting resource.
            new webpack.DefinePlugin({
                DEV: !isEnvProduction,
                // It is absolutely essential that NODE_ENV is set to production during a production build. Otherwise React will be compiled in the very slow development mode.
                'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV || 'development')
            }),
            new ESLintPlugin({
                // Plugin options
                extensions: ['js', 'jsx', 'mjs', 'ts', 'tsx'],
                formatter: require.resolve('react-dev-utils/eslintFormatter'),
                eslintPath: require.resolve('eslint'),
                context: paths.appSrc,
                // ESLint class options
                cwd: paths.appPath,
                resolvePluginsRelativeTo: __dirname,
            }),
        ],
    };
};

module.exports = ({ env }) => {
    const envConfig = require(`./webpack.config.${env}.js`);
    return merge(commonConfig(env), envConfig);
};
