<template>
  <div id="basicLayout">
    <a-layout style="height: 400px; min-height: 100vh">
      <a-layout-header class="header">
        <GlobalHeader></GlobalHeader>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">底部栏</a-layout-footer>
    </a-layout>
  </div>
</template>

<script>
import GlobalHeader from "../components/GlobalHeader.vue";

export default {
  components: {
    GlobalHeader,
  },
};
</script>

<style scoped>
#basicLayout {
}
/* 导航栏样式 */
#basicLayout .header {
  margin-bottom: 16px;
}
/* 中层 */
#basicLayout .content {
  /* background: #afcd53; */
  margin-bottom: 16px;
  padding: 20px;
}
/* 底部栏 */
#basicLayout .footer {
  background: #8f82bc;
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
