import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);

// const store = new Vuex.Store({
// 	state: {
// 		breadcrumbItems: [],
// 	},

// 	mutations: {
// 		// 在这里定义你的状态变更函数（mutations）
// 		setBreadcrumbItems (state, items) {
// 			state.breadcrumbItems = items;
// 		},
// 	},
// });

const state = {
	dynamicRoutes: []
}

const mutations = {
	DYNAMIC_ROUTES (state, routes) {
		state.dynamicRoutes = routes

	}
}

const actions = {
	dynamicRoutes ({ commit }, routes) {
		commit('DYNAMIC_ROUTES', routes)
	}
}
// const getters = {
// 	getDynamicRoutes (state) {
// 		return function () {
// 			return state.dynamicRoutes;
// 		}
// 	}
// }


const store = new Vuex.Store({
	state,
	mutations,
	actions,


})
export default store;