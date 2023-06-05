<template>
  <div>
    <h2>user-action-statistic</h2>

    <div>
      <table>
        <thead>
        <tr>
          <th>userId</th>
          <th>actionType</th>
          <th>count</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="userAction in userActionList" :key="userAction.userId">
          <td>{{ userAction.userId }}</td>
          <td>{{ userAction.actionType }}</td>
          <td>{{ userAction.count }}</td>
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
    const userActionList = computed(() =>
      store.getters['analytics/userActionStatisticsStore/getUserActionList'],
    )

    onMounted(() => {
      store.dispatch('analytics/userActionStatisticsStore/findUserActionStatistics');
    });

    return {
      userActionList,
    };
  }
};
</script>
