<template>
  <a-row
    id="globalHeader"
    class="grid-demo"
    style="box-shadow: #eee 1px 1px 5px"
    align="center"
    :wrap="false"
  >
    <a-col flex="auto">
      <a-menu
        mode="horizontal"
        :default-selected-keys="selectedKey"
        @menu-item-click="doMenuClick"
      >
        <a-menu-item
          key="0"
          :style="{ padding: 0, marginRight: '38px' }"
          disabled
        >
          <div
            :style="{
              width: '80px',
              height: '30px',
              borderRadius: '2px',
              background: 'var(--color-fill-3)',
              cursor: 'text',
            }"
          />
        </a-menu-item>
        <a-menu-item v-for="item in visbleRoute" :key="item.path">{{
          item.name
        }}</a-menu-item>
      </a-menu>
    </a-col>
    <a-col flex="100px">
      <div>
        {{ Store.state.userMoudel?.loginUser?.data?.userName ?? "未登录" }}
      </div>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import { routes } from "../router/routers";
import { useRouter } from "vue-router";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import store from "../store";
import checkAccess from "../access/checkAccess";
import ACCESS_Enum from "../access/accessEnum";

const router = useRouter();
const selectedKey = ref(["/"]);
const Store = useStore();
const loginUser = Store.state.userMoudel.loginUser;

const visbleRoute = computed(() => {
  return routes.filter((item, index) => {
    // todo 根据权限过滤菜单
    if (item.meta?.hideInMenu) {
      return false;
    }
    if (
      !checkAccess(
        Store.state.userMoudel.loginUser,
        item.meta?.access as string
      )
    ) {
      return false;
    }
    return true;
  });
});

router.afterEach((to, from, failure) => {
  selectedKey.value = [to.path];
});
const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};
</script>

<style scoped></style>
