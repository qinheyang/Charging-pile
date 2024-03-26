module.exports = {
    "env": {
        node:true,

    },
    "extends": [
        "eslint:recommended",
        "plugin:vue/essential"
    ],
    "parserOptions": {
        parser: '@babel/eslint-parser'
    },
    "plugins": [
        "vue"
    ],
    "rules": {
        "vue/multi-word-component-names":"off",
    }
};
