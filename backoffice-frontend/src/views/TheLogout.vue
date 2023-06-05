<template>
</template>

<script>
import store from '@/store';
import router from '@/router';

export default {
  setup() {
    store.dispatch('auth/loginStore/logout');
    router.push('/login');
  }
};
</script>
