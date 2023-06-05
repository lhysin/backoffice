const routes = [
  {
    path: '/',
    component: () => import('@/views/DashBoard.vue'),
  },
  {
    path: '/users',
    component: () => import('@/views/user/TheUser.vue'),
  },
  {
    path: '/user-action-statistics',
    component: () => import('@/views/analytics/UserActionStatistics.vue'),
  },
];

export default routes;
