module.exports = {
    transform: {
        "^(?!.*\\.(js|jsx|ts|tsx|json)$)": 'jest-transform-stub',
        '^.+\\.(js)$': 'babel-jest',
        '\\.(ts)$': 'ts-jest'
    },
    moduleNameMapper: {
        '^.+\\.(css|styl|less|sass|scss|png|jpg|ttf|woff|woff2)$': 'jest-transform-stub',
    },
    moduleFileExtensions: [
        'web.js',
        'js',
        'web.ts',
        'ts',
        'web.tsx',
        'tsx',
        'json',
        'web.jsx',
        'jsx',
        'node'
    ],
    testRegex: '(/__tests__/(.*)\\.(test|spec))\\.(ts|js|tsx|jsx)$',
    testPathIgnorePatterns: [
        '\\.snap$',
        '<rootDir>/node_modules/'
    ],
    cacheDirectory: '.cache/jest',
    watchPlugins: [
        'jest-watch-typeahead/filename',
        'jest-watch-typeahead/testname'
    ],
    globals: {
        '__TEST__': true,
    },
    setupFilesAfterEnv: ['jest-extended'],
};
