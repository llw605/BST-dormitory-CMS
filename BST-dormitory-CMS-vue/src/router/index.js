import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */
import universityRouter from './modules/university'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [{
  path: '/redirect',
  component: Layout,
  hidden: true,
  children: [{
    path: '/redirect/:path(.*)',
    component: () => import('@/views/redirect/index')
  }]
},
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  }, {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [{
      path: 'index',
      component: () => import('@/views/profile/index'),
      name: 'Profile',
      meta: {
        title: 'profile',
        icon: 'user',
        noCache: true
      }
    }]
  },
  {
    path: '/dormitory_static_form',
    component: () => import('@/views/form/stu-dormitory-form'),
    hidden: true,
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [

  {
    path: '/',
    component: Layout,
    redirect: '/index',
    meta: {},
    children: [{
      path: 'index',
      component: () => import('@/views/home/index'),
      name: 'Index',
      meta: {
        title: '首页',
        icon: 'el-icon-s-home',
        affix: true
      }
    }]
  },
  {
    path: '/announcement',
    component: Layout,
    redirect: '/announcement/index',
    meta: {
      roles: ['admin']
    },
    children: [{
      path: 'index',
      component: () => import('@/views/announcement/index'),
      name: 'Index',
      meta: {
        title: '发布公告',
        icon: 'el-icon-s-promotion',
        affix: true
      }
    }]
  },
  {
    path: '/user',
    component: Layout,
    meta: {
      icon: 'el-icon-user-solid',
      roles: ['admin']
    },
    children: [{
      path: '/user/manage',
      component: () => import('@/views/user/manage'),
      name: 'Manage',
      meta: {
        title: '后台用户'
      }
    }]
  },
  {
    path: '/univer',
    component: Layout,
    meta: {
      title: '大学管理',
      icon: 'el-icon-edit',
      roles: ['admin']
    },
    children: [
      {
        path: 'manage',
        component: () => import('@/views/university/manage'),
        name: 'BuildingManage',
        meta: {
          title: '学校管理'
        }
      },
      {
        path: 'building',
        component: () => import('@/views/university/building'),
        name: 'University',
        meta: {
          title: '宿舍楼管理'
        }
      }
    ]
  },
  {
    path: '/form',
    component: Layout,
    meta: {
      icon: 'el-icon-tickets',
      roles: ['admin']
    },
    children: [{
      path: 'manage',
      component: () => import('@/views/form/manage'),
      name: 'FormManage',
      meta: {
        title: '表单管理'
      }
    }]
  },
  universityRouter,
  // 404 page must be placed at the end !!!
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
]

const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
