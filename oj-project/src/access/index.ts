import router from "@/router";
import store from "@/store";
import ACCESS_Enum from "./accessEnum";
import checkAccess from "./checkAccess";

router.beforeEach(async (to, from, next) => {
  //判断当前用户是否有权限
  if (to.meta?.access === "canAdmin") {
    if (store.state.userMoudel.loginUser?.role !== "admin") {
      next("/noAuth");
      return;
    }
  }
  //判断是否已登录
  const loginUser = store.state.userMoudel.loginUser;
  console.log(loginUser);
  //如果未登录，则进行自动登录
  if (!loginUser || !loginUser.userRole) {
    await store.dispatch("getLoginUser");
  }
  //判断当前跳转路由需要的权限
  const needAccess = (to.meta?.access as string) ?? ACCESS_Enum.NOT_LOGIN;
  if (needAccess !== ACCESS_Enum.NOT_LOGIN) {
    if (!loginUser) {
      next("/user/login?redirect=${to.fullPath}");
      return;
    }
    if (!checkAccess(loginUser, needAccess)) {
      next("/noAuth");
      return;
    }
  }
  next();
});
