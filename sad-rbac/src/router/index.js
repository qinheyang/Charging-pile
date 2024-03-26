import Vue from 'vue'
import Router from 'vue-router'
import login from '@/views/login/login.vue'
import layout from '@/views/layout/layout'
import home from '@/views/home/home.vue'
import screen from '@/views/screen/screen.vue'
// import menu from '@/views/system/menu/menu'
// import user from '@/views/system/user/user.vue'
// import role from '@/views/system/role/role.vue'

Vue.use(Router)
const routes = [
    {
        path: '/',
        component: layout,
        redirect: '/home',
        name: 'home',
        meta: {
            isShow: false
        },
        children: [
            {
                path: 'home',
                name: 'home',
                component: home,
                meta: {

                    title: '首页',
                    icon: "el-icon-s-home",
                    type: "C",
                    isShow: true

                },
            },
        ]
    },


    // {
    //     path: '/system',
    //     component: layout,
    //     meta: {
    //         title: '系统管理'
    //     },
    //     children: [
    //         {
    //             path: 'menu',
    //             component: menu,
    //             meta: {
    //                 title: '菜单管理'
    //             }
    //         },
    //         {
    //             path: 'user',
    //             component: user,
    //             meta: {
    //                 title: '用户管理'
    //             }
    //         }, {
    //             path: 'role',
    //             component: role,
    //             meta: {
    //                 title: '角色管理'
    //             }
    //         }
    //     ]
    // },
    {
        path: '/login',
        component: login,
        meta: {
            isShow: false
        }

    },
    {
        path: '/screen',
        component: screen,
        meta: {
            isShow: false
        }

    },
];
const router = new Router({
    mode: 'history',
    routes
});


export default router;