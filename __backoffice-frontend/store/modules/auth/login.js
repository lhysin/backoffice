import axios from "@/plugins/axios.js";

const state = {
  loginUser: {},
};


const mutations = {
  saveMe (state, loginUser) {
    state.loginUser = loginUser;
  },
};

const getters = {
  getLoginUser (state) {
    return state.loginUser;
  },
};

const actions = {
  login(context, params) {
    return new Promise((resolve, reject) => {
      axios.post('/api/v1/auth/login', {
        userId: params.userId,
        password: params.password
      }).then(res => {
        context.commit('saveToken', {
          accessToken: res.data.accessToken,
          refreshToken: res.data.refreshToken,
        });
        resolve({});
      }).catch(err => {
        reject(err);
      });
    });
  },

  logout(context) {
    context.commit('removeToken');
  },

  me(context) {
    return new Promise((resolve, reject) => {
      axios.get('/api/v1/auth/me').then(res => {
        context.commit('saveMe', {
          loginUser: res.data
        });
        resolve({});
      }).catch(err => {
        reject(err);
      });
    });
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
