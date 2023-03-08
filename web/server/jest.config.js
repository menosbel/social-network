/* eslint-disable @typescript-eslint/no-var-requires */
const preset = require('@social-network/jest-config/jest-preset');

module.exports = {
    ...preset,
    transform: {
        ...preset.transform,
        "^.+\\.(js|ts)$": "babel-jest",
    },
    collectCoverageFrom: [
        'src/**/*.{js,ts}',
        '!src/**/*.d.ts'
    ],
    moduleNameMapper: {
        ...preset.moduleNameMapper,
    },
    cacheDirectory: '.cache/jest',
    watchPlugins: [],
    testEnvironment: 'node'
};
