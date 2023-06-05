import { createRouter, createWebHistory } from 'vue-router';
import store from '@/store';
import { useStore } from 'vuex';
import layoutRouter from "@/router/layoutRouter.js";

const routes = [
  {
    path: '/login',
    name : 'TheLogin',
    component: () => import('@/views/TheLogin.vue'),
    meta: {
      isPublic : true
    }
  },  {
    path: '/logout',
    name : 'TheLogout',
    component: () => import('@/views/TheLogout.vue'),
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

  // store reset
  useStore().reset({
    modules: {
      'auth/tokenStore': {
        self: false,
      },
      'auth/loginStore': {
        self: false,
      },
    },
  });

  if (isPublic && hasAccessToken) {
    next('/');
  } else if (!isPublic && !hasAccessToken) {
    next('/login');
  } else {
    next();
  }
});

const layoutRoute = {
  path: '/',
  name: 'Layout',
  component: () => import('@/components/layout/TheLayout.vue'),
  children: layoutRouter,
};

router.addRoute(layoutRoute);

export default router;
