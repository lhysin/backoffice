<template>
  <div>
    <the-navbar />
    <div class="content">
      <h1>TheLayout</h1>
      <router-view :key="$route.fullPath" />
    </div>
    <the-footer />
  </div>
</template>

<script>

import TheNavbar from '@/components/layout/TheHeader.vue';
import TheFooter from '@/components/layout/TheFooter.vue';

export default {
  name: 'TheLayout',
  components: {
    'the-navbar': TheNavbar,
    'the-footer': TheFooter,
  },
};
</script>
<style scoped>
.content {
  background-color: antiquewhite;
}
</style>

