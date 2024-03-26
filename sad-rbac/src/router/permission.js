import router from '@/router/index'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import { getRouters } from '@/api/system/login';
import { fnAddDynamicMenuRoutes } from '@/router/getAsyncRouter'
import store from '@/store';
NProgress.configure({
	easing: 'ease', // 动画方式
	speed: 500, // 递增进度条的速度
	showSpinner: false, // 是否显示加载ico
	trickleSpeed: 200, // 自动递增间隔
	minimum: 0.3 // 初始化时的最小百分比
})

// router.beforeEach((to, from, next) => {

// 	NProgress.start();

// 	const whiteList = ['/login', '/register', '/404'];
// 	const token = getToken();
// 	// if (                               ) {
// 	// 	if (token) {
// 	// 		next();
// 	// 	} else {
// 	// 		next(`/login`);
// 	// 	}
// 	// } else {
// 	if (whiteList.indexOf(to.path) !== -1) {
// 		next()
// 	} else {
// 		if (token) {
// 			next(`/?redirect=${to.path}`)
// 		} else {
// 			next(`/login?redirect=${to.path}`)
// 		}
// 	}
// 	// }
// });
function inWhiteList (toPath) {
	NProgress.start();
	const whiteList = ['/login', '/register', '/404'];
	const path = whiteList.find((value) => {
		// 使用正则匹配
		const reg = new RegExp('^' + value)
		return reg.test(toPath)
	})
	return !!path
}
router.beforeEach((to, from, next) => {
	const token = getToken();
	if (inWhiteList(to.path)) {
		next()
	} else {
		//用户已登录
		if (token) {
			var menuTree = localStorage.getItem("menus");


			if (store.state.dynamicRoutes.length == 0) {
				getRouters().then(resp => {
					menuTree = resp.data;
					let menuRouter = fnAddDynamicMenuRoutes("/", resp.data);
					store.dispatch('dynamicRoutes', menuRouter).then(() => {
						for (let x of store.state.dynamicRoutes) {
							router.addRoute(x)
						}
					})
					localStorage.setItem("menus", menuTree);
				})
				next()
			} else {

				next()
			}

		} else {
			next(`/login`)
		}
	}
});
router.afterEach(() => {
	// 在即将进入新的页面组件前，关闭掉进度条

	NProgress.done()


})
// function inWhiteList (toPath) {

// 	const path = whiteList.find((value) => {
// 		// 使用正则匹配
// 		const reg = new RegExp('^' + value)
// 		return reg.test(toPath)
// 	})
// 	return !!path
// }