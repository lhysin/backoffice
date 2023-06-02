const state = {
  accessToken: null,
  refreshToken: null,
};

const mutations = {

  saveToken (state, payload) {
    state.accessToken = payload.accessToken;
    state.refreshToken = payload.refreshToken;
  },

  removeToken (state, payload) {
    state.accessToken = null;
    state.refreshToken = null;
  },
};

const getters = {
  hasAccessToken (state) {
    return !!state.accessToken;
  },

  getAccessToken (state) {
    return state.accessToken;
  },

  getRefreshToken (state) {
    return state.refreshToken;
  },
};

const actions = {
};

export default {
  state,
  getters,
  actions,
  mutations,
};
