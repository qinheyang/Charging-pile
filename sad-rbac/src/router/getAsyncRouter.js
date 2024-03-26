// 用于处理动态菜单数据，将其转为 route 形式
export function fnAddDynamicMenuRoutes (prefix, menuList = [], routes = []) {
	// 用于保存普通路由数据
	let temp = []
	// 用于保存存在子路由的路由数据
	let route = []
	// 遍历数据
	for (let i = 0; i < menuList.length; i++) {
		// 存在子路由，则递归遍历，并返回数据作为 children 保存
		if (menuList[i].children && menuList[i].children.length > 0) {
			// 获取路由的基本格式
			route = getRoute(prefix, menuList[i])

			// 递归处理子路由数据，并返回，将其作为路由的 children 保存
			route.children = fnAddDynamicMenuRoutes("", menuList[i].children)
			// 保存存在子路由的路由
			routes.push(route)
		} else {
			// 保存普通路由
			temp.push(getRoute(prefix, menuList[i]))
		}
	}
	// 返回路由结果
	return routes.concat(temp)
}

// 返回路由的基本格式
function getRoute (prefix, item) {
	// 路由基本格式
	let route = {
		path: prefix + item.path,
		// 路由所在组件
		// component: (resolve) => require([`@/layout/Index`], resolve),
		component: (resolve) => require([`@/views${item.component == '' || item.component == null ? '/layout/layout' : item.component}`], resolve),
		meta: {
			id: item.menuId,
			type: item.menuType,
			title: item.menuName,
			icon: item.icon,
			isShow: true,
		},
		// 路由的子路由
		children: []
	}
	// 返回 route
	return route
}