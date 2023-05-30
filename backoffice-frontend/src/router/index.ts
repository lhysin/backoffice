import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import store from '@/store';

const routes: Array<RouteRecordRaw> = [
    // 로그인 페이지
    {
        path: '/login',
        component: () => import('@/views/Login.vue')
    },
    // 인증이 필요한 페이지
    {
        path: '/dashboard',
        component: () => import('@/views/DashBoard.vue'),
        meta: { requiresAuth: true }
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    const toLogin = to.path === '/login';
    const toRoot = to.path === '/';
    const isLogin = store.getters['loginUserModule/isLogin'];

    if (!isLogin && !toLogin) {
        // 로그인하지 않은 상태에서 로그인 페이지가 아닌 다른 페이지로 접근 시 로그인 페이지로 이동
        next('/login');
    } else if (isLogin && (toRoot || toLogin)) {
        // 로그인한 상태에서 '/' 또는 '/login'으로 접근 시 대시보드로 이동
        next('/dashboard');
    } else {
        // 그 외의 경우는 다음 단계로 진행
        next();
    }
});

export default router;