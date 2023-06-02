import axios from 'axios';
import router from '@/router';
import store from '@/store';

const config = {
  baseURL: import.meta.env.BACKOFFICE_BACKEND_HOST,
  timeout: 300 * 1000, // Timeout
  withCredentials: true, // Check cross-site Access-Control
  headers: {
    common: {
      'Access-Control-Allow-Origin': '*'
    },
    post: {
      'Content-Type': 'application/json',
    },
  },
};

const instance = axios.create(config);

instance.interceptors.request.use(
  config => {
    const accessToken = store.getters[''];
    if (accessToken) {
      config.headers['Authorization'] = `Bearer ${accessToken}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
  response => {
    return response;
  },
  async error => {
    if (error.response) {
      const status = error.response.status;
      if (status === 401) {
        const refreshToken = store.state.loginUserModule.refreshToken;
        if (refreshToken) {
          try {
            // refresh 토큰을 서버로 보내서 새로운 토큰 생성
            const refreshResponse = await axios.post('/refresh', { refreshToken });

            // 새로운 토큰을 Vuex에 저장합니다.
            store.commit('loginUserModule/saveToken', {
              accessToken: refreshResponse.data.accessToken,
              refreshToken: refreshResponse.data.refreshToken
            });

            // 원래 요청을 그대로 복구하여 진행
            return instance(error.config);
          } catch (refreshError) {
            // refresh 토큰이 만료된 경우, 로그인 페이지로 이동합니다.
            store.commit('loginUserModule/removeToken');
            router.push('/login');
          }
        } else {
          // 토큰이 만료된 경우, 로그인 페이지로 이동
          router.push('/login');
        }
      } else if (status === 403) {
        // 권한이 부족한 경우, 얼럿을 표시
        alert('권한이 부족합니다.');
      }
    }
    return Promise.reject(error);
  }
);

export default instance;
