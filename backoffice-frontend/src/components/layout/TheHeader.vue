<template>
  <div class="header">
    <h1>TheHeader</h1>
    <router-link to="/">Home</router-link> / <router-link to="/users">Users</router-link> / <router-link to="/user-action-statistics">UserActionStatistics</router-link>
    <br>
    <router-link to="/logout">logout</router-link>
  </div>
</template>

<style scoped>
.header {
  text-align: center;
  background-color: lightgray;
}
</style>
<script setup>
</script>
