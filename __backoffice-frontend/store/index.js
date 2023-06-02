import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import AuthToken from '@/store/modules/auth/token.js';
import AuthLogin from '@/store/modules/auth/login.js';



const store = createStore({
  modules: {
    'AUTH_TOKEN' : AuthToken,
    'AUTH_LOGIN' : AuthLogin
  },
  plugins: [
    createPersistedState({
      storage: window.localStorage,

      // https://stackoverflow.com/questions/55319006/making-only-one-module-persistent-with-vuex-persistedstate
      paths: ['AUTH_TOKEN'],
    }),
  ],
});

export default store;
