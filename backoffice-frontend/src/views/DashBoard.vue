<template>
  <div>
    <h2>DashBoard</h2>

    <button @click="me">me</button>

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
        store.getters['auth/loginStore/getLoginUser'],
        null,
        4
      )
    )

    const me = () => {
      store.dispatch('auth/loginStore/me');
    }

    onMounted(() => {
      me();
    });

    return {
      loginUser,
      me
    };
  }
};
</script>
