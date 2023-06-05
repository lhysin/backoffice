import { Store } from 'vuex';
import { createStore } from 'vuex-extensions'
import createPersistedState from 'vuex-persistedstate';

const modulesFiles = import.meta.glob('@/store/modules/**/*.js', { eager: true });

const modules = Object.keys(modulesFiles).reduce((modules, modulePath) => {

  const regex = /^\/src\/store\/modules\//; // 대체할 부분의 정규식 패턴
  const moduleName = modulePath
    .replace(regex, '') // '/src/index/modules/' 부분을 제거
    .replace('.js', '') // 파일 확장자인 '.js'를 제거
    .replace(/\//g, '/'); // 슬래시를 대체

  const module = modulesFiles[modulePath];
  modules[moduleName] = 'default' in module ? module.default : module;

  return modules;
}, {});

Object.keys(modules).forEach(module => {
  modules[module].namespaced = true;
});

// @/store/modules/auth/loginStore.js => auth/loginState
const store = createStore(Store, {
  modules: modules,
  plugins: [
    createPersistedState({
      storage: window.localStorage,

      // https://stackoverflow.com/questions/55319006/making-only-one-module-persistent-with-vuex-persistedstate
      paths: ['auth/tokenStore'],
    }),
  ],
});

export default store;
