<template>
  <div id="userLayout">
    <a-layout style="height: 400px; min-height: 100vh">
      <a-layout-header class="header">
        <a-space>
          <img class="logo" />
          <div>online jage</div>
        </a-space>
      </a-layout-header>
      <a-layout-content class="content">
        <userLoginView style="margin-top: 200px" />
      </a-layout-content>
      <a-layout-footer class="footer">底部栏</a-layout-footer>
    </a-layout>
  </div>
</template>

<script>
import userLoginView from "../views/user/UserLoginView.vue";

export default {
  components: {
    userLoginView,
  },
};
</script>

<style scoped>
#userLayout {
  text-align: center;
  background: url("../assets/mount.jpg") 0% 0% /100% 100%;
}
/* 导航栏样式 */
#userLayout .header {
}
#userLayout .logo {
  width: 64px;
  height: 64px;
}
/* 中层 */
#userLayout .content {
  padding: 20px;
  padding-top: 64px;
}
/* 底部栏 */
#userLayout .footer {
  background-color: aqua;
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
