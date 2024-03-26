import request from "@/utils/request";
const prefix = '/charge/records/'

export function listRecords (params) {
	return request({
		url: prefix + 'list',
		method: 'get',
		params: params
	})
}
export function addRecords (params) {
	return request({
		url: prefix + 'add',
		method: 'post',
		data: params
	})
}
export function updateRecords (params) {
	return request({
		url: prefix + 'update',
		method: 'put',
		data: params
	})
}
export function getRecordsInfo (recordsId) {
	return request({
		url: prefix + recordsId,
		method: 'get',

	})
}
export function deleteRecords (recordsIds) {
	return request({
		url: prefix + `delete/${recordsIds}`,
		method: 'delete',
	})
}


export function exportRecords (params) {
	return request({
		url: prefix + 'export',
		method: 'post',
		data: params
	})
}



export function importRecords (params) {
	return request({
		url: prefix + 'import',
		method: 'post',
		headers: {
			'Content-Type': 'multipart/form-data;'

		},
		data: params
	})
}


export function getRecordsNum () {
	return request({
		url: prefix + 'get-records-num',
		method: 'get',

	})
}

export function getRank () {
	return request({
		url: prefix + 'get-rank',
		method: 'get',

	})
}


export function getBarChart () {
	return request({
		url: prefix + 'get-bar',
		method: 'get',

	})
}


export function getLineChart () {
	return request({
		url: prefix + 'get-line',
		method: 'get',

	})
}
