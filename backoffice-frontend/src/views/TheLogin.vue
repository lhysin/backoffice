<template>
  <div>
    <h2>Login</h2>
    <h4>active MODE : {{ env.MODE }}</h4>
    <h4>active VITE_BACKOFFICE_BACKEND_HOST : {{ env.VITE_BACKOFFICE_BACKEND_HOST }}</h4>
    <form @submit.prevent="login">
      <input type="text" v-model="userId" placeholder="Username" required>
      <input type="password" v-model="password" placeholder="Password" required>
      <button type="submit">Log in</button>
    </form>
  </div>
</template>

<script>
import {computed, reactive} from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {
  setup() {
    const userId = reactive('admin');
    const password = reactive('admin');

    const router = useRouter();
    const store = useStore();

    const env = computed(() => JSON.parse(JSON.stringify(import.meta.env)));

    const login = () => {
      store.dispatch('auth/loginStore/login', {
            userId: userId,
            password: password
          }
      ).then(() => {
        router.push("/");
      })
    };

    return {
      userId,
      password,
      login,
      env
    };
  }
};
</script>
