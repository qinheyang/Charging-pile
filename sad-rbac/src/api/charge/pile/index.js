import request from "@/utils/request";
const prefix = '/charge/pile/'

export function listPile (params) {
	return request({
		url: prefix + 'list',
		method: 'get',
		params: params
	})
}
export function addPile (params) {
	return request({
		url: prefix + 'add',
		method: 'post',
		data: params
	})
}
export function updatePile (params) {
	return request({
		url: prefix + 'update',
		method: 'put',
		data: params
	})
}
export function getPileInfo (pileId) {
	return request({
		url: prefix + pileId,
		method: 'get',

	})
}
export function deletePile (pileIds) {
	return request({
		url: prefix + `delete/${pileIds}`,
		method: 'delete',
	})
}



export function getPiles (params) {
	return request({
		url: prefix + 'get-piles',
		method: 'get',
		params: params
	})
}


export function exportPile (params) {
	return request({
		url: prefix + 'export',
		method: 'post',
		data: params
	})
}

export function getPileNum () {
	return request({
		url: prefix + 'get-pile-num',
		method: 'get',
	})
}

export function getRingNum () {
	return request({
		url: prefix + 'get-ring-num',
		method: 'get',
	})
}

export function getPileMap () {
	return request({
		url: prefix + 'get-pile-map',
		method: 'get',
	})
}