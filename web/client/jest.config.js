/* eslint-disable @typescript-eslint/no-var-requires */
const preset = require('@social-network/jest-config/jest-preset');

module.exports = {
    ...preset,
    transform: {
        ...preset.transform,
        "^.+\\.(js|jsx|ts|tsx)$": "babel-jest",
    },
    collectCoverageFrom: [
        'src/**/*.{js,jsx,ts,tsx}',
        '!src/**/*.d.ts'
    ],
    moduleNameMapper: {
        ...preset.moduleNameMapper,
        '^.+\\.module\\.(css|sass|scss)$': 'identity-obj-proxy'
    },
    cacheDirectory: '.cache/jest',
};
