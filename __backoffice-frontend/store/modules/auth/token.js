const state = {
  accessToken: null,
  refreshToken: null,
};

const mutations = {

  init (state) {
    state.accessToken = null;
    state.refreshToken = null;
  },

  saveToken (state, payload) {
    state.accessToken = payload.accessToken;
    state.refreshToken = payload.refreshToken;
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
