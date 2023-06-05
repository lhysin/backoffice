<template>
  <div>
    <h2>Users</h2>

    <div>
      <table>
        <thead>
        <tr>
          <th>userId</th>
          <th>userName</th>
          <th>userEmail</th>
          <th>userContactNumber</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in userList" :key="user.userId">
          <td>{{ user.userId }}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.contactNumber }}</td>
        </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>

<script>
import {computed, onMounted} from 'vue';
import {useStore} from 'vuex';

export default {
  setup() {

    const store = useStore();
    const userList = computed(() =>
      store.getters['user/userStore/getUserList'],
    )

    onMounted(() => {
      store.dispatch('user/userStore/findAllWithoutAdmin');
    });

    return {
      userList,
    };
  }
};
</script>
