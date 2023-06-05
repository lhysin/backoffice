import axios from "@/plugins/axios.js";
import store from "@/store/index.js";

const state = {
  userList: []
};

const mutations = {
  setUserList (state, userList) {
    return state.userList = userList;
  },
};

const getters = {
  getUserList (state) {
    return state.userList;
  },
};

const actions = {
  findAllWithoutAdmin(context) {
    return new Promise((resolve, reject) => {
      axios.get('/api/v1/users')
        .then(res => {
        store.commit('user/userStore/setUserList', res.data);
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
