import request from "@/utils/request";
const prefix = '/repair/report/'

export function listReport (params) {
	return request({
		url: prefix + 'list',
		method: 'get',
		params: params
	})
}
export function addReport (params) {
	return request({
		url: prefix + 'add',
		method: 'post',
		data: params
	})
}
export function updateReport (params) {
	return request({
		url: prefix + 'update',
		method: 'put',
		data: params
	})
}
export function getReportInfo (reportId) {
	return request({
		url: prefix + reportId,
		method: 'get',

	})
}
export function deleteReport (reportIds) {
	return request({
		url: prefix + `delete/${reportIds}`,
		method: 'delete',
	})
}
export function exportReport (params) {
	return request({
		url: prefix + 'export',
		method: 'post',
		data: params
	})
}
