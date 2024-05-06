<template>
  <div id="app">
    <template v-if="route.path.startsWith('/user')">
      <router-view></router-view>
    </template>
    <template v-else>
      <basiclayouts></basiclayouts>
    </template>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from "vue";
import { useRoute } from "vue-router";
import Basiclayouts from "./layouts/BasicLayout.vue";

const route = useRoute();

//全局初始化函数
const doInit = () => {
  console.log("hello world");
};

onMounted(() => {
  doInit();
});
</script>

<style>
#app {
}
</style>
