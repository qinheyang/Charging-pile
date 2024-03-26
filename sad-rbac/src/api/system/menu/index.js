import request from "@/utils/request";
const prefix = '/system/menu/'

export function listMenu (params) {
	return request({
		url: prefix + 'list',
		method: 'get',
		params: params
	})
}
export function addMenu (params) {
	return request({
		url: prefix + 'add',
		method: 'post',
		data: params
	})
}
export function updateMenu (params) {
	return request({
		url: prefix + 'update',
		method: 'put',
		data: params
	})
}
export function getMenuInfo (menuId) {
	return request({
		url: prefix + menuId,
		method: 'get',

	})
}
export function deleteMenu (menuIds) {
	return request({
		url: prefix + `delete/${menuIds}`,
		method: 'delete',
	})
}

export function treeSelect () {
	return request({
		url: prefix + 'treeselect',
		method: 'get',
	})
}
