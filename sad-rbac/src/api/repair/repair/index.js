import request from "@/utils/request";
const prefix = '/repair/repair/'

export function addRepair (params) {
	return request({
		url: prefix + 'add',
		method: 'post',
		data: params
	})
}


export function listRepair (params) {
	return request({
		url: prefix + 'list',
		method: 'get',
		params: params
	})
}

export function exportRepair (params) {
	return request({
		url: prefix + 'export',
		method: 'post',
		data: params
	})
}