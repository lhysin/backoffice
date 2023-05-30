import { createStore, createLogger } from 'vuex';
import loginUserModule from '@/store/modules/loginUserModule.ts';

interface RootState {
    // 루트 상태
}

const store = createStore<RootState>({
    modules: {
        loginUserModule,
        // 다른 모듈들도 추가로 등록할 수 있습니다.
    },
    plugins: [createLogger()] // 개발 시에만 사용하는 로거 플러그인
});

// loginUserModule 상태 변경 시 로컬 스토리지에 저장
store.watch(
    (state, getters) => state.loginUserModule,
    (newState) => {
        localStorage.setItem('loginUserModule', JSON.stringify(newState));
    },
    { deep: true }
);

// 초기화 시 로컬 스토리지에서 loginUserModule 상태 복원
const storedState = localStorage.getItem('loginUserModule');
if (storedState) {
    store.commit('loginUserModule/initializeState', JSON.parse(storedState));
}
export default store;