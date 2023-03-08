const fs = require('fs');
const path = require('path');

const appDirectory = fs.realpathSync(process.cwd());
const resolveApp = relativePath => path.resolve(appDirectory, relativePath);

module.exports = {
    appPath: resolveApp('.'),
    appSrc: resolveApp('src'),
    appIndexTsx: resolveApp('src/index.tsx'),
    appDist: resolveApp('dist'),
    appDistScripts: resolveApp('dist/scripts'),
    appIndexHtml: resolveApp('public/index.html'),
    appPackageJson: resolveApp('package.json'),
    staticAssetsPublicUrl: getStaticAssetsPublicUrl,
    stripEndingSlash: stripEndingSlash,
};

function stripEndingSlash(url) {
    if (url.endsWith('/')) {
        return url.slice(0, -1);
    }
    return url;
}

function getStaticAssetsPublicUrl(isProductionBuild) {
    const env = process.env.STATIC_ASSETS_PUBLIC_URL || '/';
    return isProductionBuild ? env : '/';
}
