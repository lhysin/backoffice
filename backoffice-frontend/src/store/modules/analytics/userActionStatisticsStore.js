import axios from "@/plugins/axios.js";
import store from "@/store/index.js";

const state = {
  userActionList: []
};

const mutations = {
  setUserActionList (state, userActionList) {
    return state.userActionList = userActionList;
  },
};

const getters = {
  getUserActionList (state) {
    return state.userActionList;
  },
};

const actions = {
  findUserActionStatistics(context) {
    return new Promise((resolve, reject) => {
      axios.get('/api/v1/analytics/user-action-statistic')
        .then(res => {
        store.commit('analytics/userActionStatisticsStore/setUserActionList', res.data);
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
