import { createRouter, createWebHistory } from 'vue-router';
import store from '@/store';

const routes = [
  // 공개 페이지
  {
    path: '/login',
    name : 'Login',
    component: () => import('@/views/TheLogin.vue'),
    meta: {
      isPublic : true
    }
  },

  {
    path: '/',
    name: 'DashBoard',
    component: () => import('@/views/DashBoard.vue'),
  },

  {
    path: '/:pathMatch(.*)*',
    name: 'ErrorView',
    component: () => import('@/components/NotFound.vue'),
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  const hasAccessToken = store.getters['auth/tokenStore/hasAccessToken'];
  const isPublic = to.meta.isPublic;

  if (isPublic && hasAccessToken) {
    next('/');
  } else if (!isPublic && !hasAccessToken) {
    next('/login');
  } else {
    next();
  }
});

export default router;
