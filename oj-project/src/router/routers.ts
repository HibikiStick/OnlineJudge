import { RouteRecordRaw } from "vue-router";
import HomeView from "../views/HomeView.vue";
import AboutView from "../views/AboutView.vue";
import AdminView from "../views/AdminView.vue";
import NoAuthView from "../views/noAuthView.vue";
import ACCESS_Enum from "@/access/accessEnum";
import UserLayout from "@/layouts/UserLayout.vue";
import UserLoginView from "@/views/user/UserLoginView.vue";
import UserRegisterView from "@/views/user/UserRegisterView.vue";
import AddQuestionView from "@/views/question/AddQuestionView.vue";
import ManagerQuestionView from "@/views/question/ManagerQuestionView.vue";
import UpdateQuestionView from "@/views/question/UpdateQuestionView.vue";

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/user",
    name: "用户",
    meta: {
      hideInMenu: true,
    },
    component: UserLayout,
    children: [
      {
        path: "/user/login",
        name: "用户登录",
        component: UserLoginView,
      },
      {
        path: "/user/register",
        name: "用户注册",
        component: UserRegisterView,
      },
    ],
  },
  {
    path: "/",
    name: "游览题目",
    component: HomeView,
  },
  {
    path: "/About",
    name: "我的",
    component: AboutView,
  },
  {
    path: "/admin",
    name: "管理员",
    component: AdminView,
    meta: {
      access: ACCESS_Enum.ADMIN,
    },
  },
  {
    path: "/noAuth",
    name: "无权限",
    component: NoAuthView,
    meta: {
      hideInMenu: false,
    },
  },
  {
    path: "/addQuestion",
    name: "新增题目",
    component: AddQuestionView,
    // meta: {
    //   access: ACCESS_Enum.ADMIN,
    // },
  },
  {
    path: "/managerQuestion",
    name: "管理题目",
    component: ManagerQuestionView,
    // meta: {
    //   access: ACCESS_Enum.ADMIN,
    // },
  },
  {
    path: "/update/question",
    name: "编辑题目",
    component: AddQuestionView,
    // meta: {
    //   access: ACCESS_Enum.ADMIN,
    // },
  },
];
//单独分离出router
