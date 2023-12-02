const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

/**
 * @type {import('webpack').Configuration}
 */
module.exports = {
	mode: 'development',
	entry: './src/scripts/index.js',
	output: {
		path: path.resolve(__dirname, '.bamb'),
		filename: 'bundle.js',
	},
	module: {
		rules: [
			{
				test: /\.js$/,
				exclude: /node_modules/,
				use: {
					loader: 'babel-loader',
				},
			},
		],
	},
	plugins: [
		new HtmlWebpackPlugin({
			template: './src/pages/toc.html',
		}),
	],
};