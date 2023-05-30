import { Module } from 'vuex';
import axios from '@/plugins/axios.ts';

interface LoginState {
    accessToken: string | null;
    refreshToken: string | null;
    isLogin: boolean;
}

interface TokenRes {
    accessToken: string;
    refreshToken: string;
}

interface UserRes {
    userId: string;
    name: string;
    email: string;
    contactNumber: string;
}

const loginUserModule: Module<LoginState, any> = {
    namespaced: true,

    state: {
        accessToken: null,
        refreshToken: null,
        isLogin: false,
        loginUser: {},
    },

    mutations: {
        initializeState(state, storedState) {
            Object.assign(state, storedState);
        },
        saveToken(state, { accessToken, refreshToken }: TokenRes) {
            state.accessToken = accessToken;
            state.refreshToken = refreshToken;
            state.isLogin = true;
        },
        removeToken(state) {
            state.accessToken = null;
            state.refreshToken = null;
            state.isLogin = false;
        },
        saveMe(state, loginUser : UserRes) {
            state.loginUser = loginUser;
        }
    },

    getters: {
        isLogin(state) {
            return state.isLogin;
        },
        loginUser(state) {
            return state.loginUser;
        }
    },

    actions: {
        login(context, params) {
            return new Promise((resolve, reject) => {
                axios.post('/api/v1/auth/login',{
                    userId : params.userId,
                    password : params.password
                }).then(res => {
                    context.commit('saveToken', {
                        accessToken : res.data.accessToken,
                        refreshToken : res.data.refreshToken,
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
                        loginUser : res.data
                    });
                    resolve({});
                }).catch(err => {
                    reject(err);
                });
            });
        }

    }
};

export default loginUserModule;