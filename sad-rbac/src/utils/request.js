import axios from "axios";
import { getToken } from "./auth";
import { Message } from 'element-ui'
const request = axios.create({
	// withCredentials: true,
	baseURL: process.env.VUE_APP_BASE_API + process.env.VUE_APP_BASE_URL + "/",
	timeout: 15000
})

request.interceptors.request.use(config => {
	config.headers['Content-Type'] = config.headers['Content-Type'] ? config.headers['Content-Type'] : 'application/json;charset=utf-8';

	config.headers['token'] = getToken();  // 设置请求头
	return config;
}, error => {
	return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
	response => {
		let res = response.data;
		if (res.code !== 200) {
			Message({
				dangerouslyUseHTMLString: true,
				message: res.msg,
				duration: 5 * 1000,
				type: 'error'
			})
			return Promise.reject(new Error(res.msg || 'Error'))
		} else {

			return res
		}
	},
	error => {
		console.log('err' + error) // for debug
		let { message } = error;
		if (message == "Network Error") {
			message = "后端接口连接异常";
		} else if (message.includes("timeout")) {
			message = "系统接口请求超时";
		} else if (message.includes("Request failed with status code")) {
			message = "系统接口" + message.substr(message.length - 3) + "异常";
		}
		Message({
			dangerouslyUseHTMLString: true,
			message: error.message,
			duration: 5 * 1000,
			type: 'error'
		})
		return Promise.reject(error)
	}
)


export default request
