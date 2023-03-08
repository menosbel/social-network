module.exports = {
    root: true,
    extends: [
        'plugin:react/recommended',
        '@social-network'
    ],
    'plugins': ['react'],
    'parserOptions': {
        'ecmaFeatures': {
            'jsx': true
        },
    },
    'settings': {
        'react': {
            'pragma': 'React',
            'version': 'detect'
        }
    },
    'rules': {
        'react/prop-types': 'off',
        'react/display-name': 'off'
    }
};
