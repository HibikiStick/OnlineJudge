<template>
  <div id="userLogin">
    <h2 style="margin-bottom: 16px">User Login</h2>
    <a-form
      :model="form"
      style="max-width: 480px; margin: 0 auto"
      :auto-label-width="true"
      @submit="handleSubmit"
    >
      <a-form-item field="userAccount" label="用户">
        <a-input v-model="form.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item field="userPassword" label="密码">
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit">提交</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import UserController from "../../controller/UserController";
import { reactive } from "vue";
import message from "@arco-design/web-vue/es/message";
import store from "../../store";
import { useRouter } from "vue-router";

const router = useRouter();

const form = reactive({
  userAccount: "",
  userPassword: "",
});
const handleSubmit = async () => {
  const res = await UserController.Login(form);
  //登陆成功后跳转主页
  if (res.data.code === 0) {
    alert("登陆成功！" + JSON.stringify(res.data));
    router.push({ path: "/", replace: true });
    store.dispatch("getLoginUser");
    console.log(store.state, "--------------");
  } else {
    message.error("登陆失败", res.message);
  }
};
</script>

<style scoped></style>
