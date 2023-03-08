module.exports = {
  root: true,
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
  ],
  'plugins': ['@typescript-eslint','import-quotes'],
  'env': {
    'browser': true,
    'jasmine': true,
    'jest': true
  },
  'globals': {
    'DEV': 'readonly'
  },
  'parserOptions':  {
    'ecmaVersion':  2018,
    'sourceType':  'module',
  },
  'rules': {
    'semi': 'off',
    'prettier/prettier': 'off',
    'object-curly-spacing': ['error', 'always'],
    '@typescript-eslint/semi': ['error'],
    '@typescript-eslint/no-explicit-any': 'off',
    '@typescript-eslint/no-use-before-define': 'off',
    '@typescript-eslint/explicit-function-return-type': 'off',
    '@typescript-eslint/explicit-member-accessibility': ['error', { 'accessibility': 'no-public' }],
    '@typescript-eslint/prefer-interface': 'off',
    '@typescript-eslint/no-non-null-assertion': 'off',
    '@typescript-eslint/no-empty-interface': 'off',
    '@typescript-eslint/no-var-requires': 'off',
    '@typescript-eslint/explicit-module-boundary-types': 'off',
    "@typescript-eslint/ban-ts-ignore": "off",
    "@typescript-eslint/ban-ts-comment": "off",
    "@typescript-eslint/no-empty-function": "off",
    "@typescript-eslint/no-unused-vars": ['warn', {
      'vars': 'all',
      'args': 'none',
      'ignoreRestSiblings': false,
    }],
    '@typescript-eslint/indent': ['error', 4, {
      'FunctionDeclaration': {
        'parameters': 'first',
      },
      'FunctionExpression': {
        'parameters': 'first',
      },
      'SwitchCase': 1,
    }],
    'no-undef': 'off',
    'no-extend-native': 'off',
    'require-atomic-updates': 'off',
    'import-quotes/import-quotes': ['error', 'single']
  },
  'parser': '@typescript-eslint/parser'
};
