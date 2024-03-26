import request from "@/utils/request";
const prefix = '/system/role/'

export function listRole (params) {
	return request({
		url: prefix + 'list',
		method: 'get',
		params: params
	})
}
export function addRole (params) {
	return request({
		url: prefix + 'add',
		method: 'post',
		data: params
	})
}
export function updateRole (params) {
	return request({
		url: prefix + 'update',
		method: 'put',
		data: params
	})
}
export function getRoleInfo (roleId) {
	return request({
		url: prefix + roleId,
		method: 'get',

	})
}
export function deleteRole (roleIds) {
	return request({
		url: prefix + `delete/${roleIds}`,
		method: 'delete',
	})
}
