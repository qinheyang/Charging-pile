import request from "@/utils/request";
import { parseStrEmpty } from "@/utils/string-util";
const prefix = '/system/user/'

export function listUser (params) {
	return request({
		url: prefix + 'list',
		method: 'get',
		params: params
	})
}
export function addUser (params) {
	return request({
		url: prefix + 'add',
		method: 'post',
		data: params
	})
}

export function updateUser (params) {
	return request({
		url: prefix + 'update',
		method: 'put',
		data: params
	})
}
export function getUserInfo (userId) {
	return request({
		url: prefix + parseStrEmpty(userId),
		method: 'get',

	})
}
export function deleteUser (userIds) {
	return request({
		url: prefix + `delete/${userIds}`,
		method: 'delete',
	})
}
