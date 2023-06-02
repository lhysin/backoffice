<template>
  <div>
    <h2>DashBoard</h2>

    <button @click="logout">logout</button>

    <h4>/api/v1/auth/me</h4>
    <pre>{{ loginUser }}</pre>

  </div>
</template>

<script>
import {computed, onMounted} from 'vue';
import {useStore} from 'vuex';
import {useRouter} from 'vue-router';

export default {
  setup() {

    const store = useStore();
    const router = useRouter();
    const loginUser = computed(() =>
      JSON.stringify(
        store.getters['loginUserModule/loginUser'],
        null,
        4
      )
    )

    onMounted(() => {
      store.dispatch('loginUserModule/me');
    });

    const logout = () => {
      store.dispatch('loginUserModule/logout');
      router.push('/login');
    }

    return {
      loginUser,
      logout
    };
  }
};
</script>
