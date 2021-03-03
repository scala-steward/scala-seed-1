const { merge } = require('webpack-merge');
const webpack = require("webpack"),
    //merge = require("webpack-merge"),
    UglifyJsPlugin = require("uglifyjs-webpack-plugin"),
    path = require("path"),
    CopyWebpackPlugin = require("copy-webpack-plugin");
const HtmlWebpackPlugin = require('html-webpack-plugin');

const topsrcdir = path.resolve(__dirname, "../src/main")
const Paths = {
    ProjectName: "ui",
    Assets: path.resolve(topsrcdir, "resources"),
    Scala: path.resolve(topsrcdir, "scala"),
    Public: path.resolve(__dirname, "../public"),
}

function libraryOutput(dest, isProd, publicPath) {
    const hash = isProd && isProd === true ? "[chunkhash]" : "[hash]"
    return {
        name: "ExampleApp",
        output: {
            publicPath,
            path: dest,
            filename: `[name].${hash}.js`,
            library: "[name]",
            libraryTarget: "var",
            chunkFilename: `[name].${hash}.js`,
        },
        optimization: {
            runtimeChunk: "single",
            splitChunks: {
                cacheGroups: {
                    vendor: {
                        test: /[\\/]node_modules[\\/]/,
                        name: "vendors",
                        chunks: "initial"
                    }
                }
            }
        }
    };
}

const indexHTMLPlugin = [
    new HtmlWebpackPlugin({
        title: "scalajs-reaction Examples App",
        filename: "index.html",
        template: path.join(Paths.Public, "index.html"),
    }),
]

const finalStyleLoaders = [
    {loader: "style-loader"},
    // may want to replace with types-for-css-modules-loader
    {loader: "css-loader", options: {modules: true, importLoaders: 1}},
    {
        loader: "postcss-loader",
        options: {
            ident: "postcss",
            plugins: loader => [
                require("postcss-import")({root: loader.resourcePath}),
                require("postcss-mixins")(),
                require("postcss-cssnext")({
                    //https://github.com/MoOx/postcss-cssnext/blob/master/src/features.js
                    features: {
                        customProperties: false,
                        applyRule: false,
                        calc: false
                    }
                }),
                require("postcss-reporter")({clearMessages: true})
            ]
        }
    }
];

// in dev mode, we create a "dist" directory
const devServer = {
    //publicPath: "dist/",
    historyApiFallback: {
        rewrites: [
            {
                // match everything or our SPA
                from: "/^.$/",
                // server to the root
                to: "/"
            }
        ]
    },
    contentBase: "dist",
    compress: true,
    hot: true,
    open: true,
    host: "0.0.0.0",
    disableHostCheck: true,
    https: false,
    watchContentBase: true,
    headers: {
        "Access-Control-Allow-Origin": "*"
    }
};

// scalapath: relative path from topdir to scala output .js file
const common = (scalapath, isProd) => ({
    entry: {
        Scala: scalapath
    },
    target: "web",
    resolve: {
        // If using symlinks in node_modules, you need this false so webpack does
        // *not* use the symlink's resolved absolute path as the directory hierarchy.
        symlinks: false,
        extensions: [".ts", ".tsx", ".js", ".jsx", ".json", "*"],
        alias: {
            Assets: Paths.Assets,
            Public: Paths.Public,
            Examples: Paths.Scala,
            JSExamples: Paths.Assets,
            BuildSettings: isProd
                ? path.resolve(Paths.Assets, "config.production")
                : path.resolve(Paths.Assets, "config.development")
        }
    },
    module: {
        rules: [
            // {
            //     // Load css, convert to js object, also load via stylesheet.
            //     test: /\.css(\.js)?$/,
            //     use: finalStyleLoaders
            // },
            {
                test: /\.(css)$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(ttf|eot|woff|woff2|png|glb|svg)$/,
                use: 'file-loader'
            },
            // {
            //     // Order is important for this loader, run after babel but before other css loaders. Why not postcss-js?
            //     test: /\.css\.js?$/,
            //     use: [{loader: "css-js-loader"}]
            // },
            {
                // babel only transpiles, run typecheck separately via npm run typecheck
                test: /\.(ts|js)x?$/, // picks up pure .js and .jsx
                exclude: /node_modules/,
                include: [/examples\/src\/main\/resources/],
                loader: require.resolve("babel-loader"),
                options: {
                    cacheDirectory: true
                }
            },
            {
                test: /\.(png|jpg|gif)$/,
                use: ["url-loader"]
            },
            {
                test: /\.md$/,
                use: "raw-loader"
            }
        ]
    },
    devServer: devServer
});

function copies(dest) {
    return [{from: "public/*.html", to: dest,
        //flatten: true
    }];
}

const dev = {
    devtool: "source-map",
    mode: "development"
};

const prod = {
    // skip source-map
    mode: "production"
};

module.exports = function (env, argv) {
    const isProd = env && env.BUILD_KIND && env.BUILD_KIND === "production";
    const scalapath = path.join(__dirname, "../target/scala-2.13/Scala.js");
    const dist = path.join(__dirname, "dist");
    const staticAssets = copies(dist);
    const output = libraryOutput(dist, isProd);
    const globals = nodeEnv => ({
        "process.env": {NODE_ENV: JSON.stringify(nodeEnv || "development")}
    });
    const copyplugin = new CopyWebpackPlugin({
        patterns: staticAssets
    });
    // const prodCopy = new CopyWebpackPlugin({
    //     patterns: [{
    //         from: "dist/*",
    //         to: path.join(__dirname, "website/scalajs-reaction/static/demo"),
    //         //flatten: true
    //     }]
    // });

    console.log("isProd: ", isProd);
    console.log("scalapath: ", scalapath);

    if (isProd) {
        const g = globals("production");
        console.log("Production build");
        console.log("globals: ", g);
        return merge(output, common(scalapath, isProd), prod, {
            plugins: [
                ...indexHTMLPlugin,
                new webpack.DefinePlugin(g),
                copyplugin, // must be befor prodCopy
                //prodCopy
            ]
        });
    } else {
        const g = globals();
        console.log("Development build");
        console.log("globals: ", g);
        return merge(output, common(scalapath, isProd), dev, {
            plugins: [
                ...indexHTMLPlugin,
                new webpack.HotModuleReplacementPlugin(),
                new webpack.DefinePlugin(g),
                copyplugin,
                //prodCopy
            ]
        });
    }
};