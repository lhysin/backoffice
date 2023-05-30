<template>
  <div>
    <h2>Login</h2>
    <form @submit.prevent="login">
      <input type="text" v-model="username" placeholder="Username" required>
      <input type="password" v-model="password" placeholder="Password" required>
      <button type="submit">Log in</button>
    </form>
  </div>
</template>

<script>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {
  setup() {
    const username = reactive('admin');
    const password = reactive('admin');

    const router = useRouter();
    const store = useStore();

    const login = () => {
      store.dispatch('loginUserModule/login', {
            userId: 'admin',
            password: 'admin'
          }
      ).then(res => {
        router.push("/dashboard");
      })
    };

    return {
      username,
      password,
      login
    };
  }
};
</script>