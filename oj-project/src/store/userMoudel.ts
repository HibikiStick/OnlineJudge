//state 保存的用户信息
import ACCESS_Enum from "@/access/accessEnum";
import { StoreOptions } from "vuex";
import UserController from "@/controller/UserController";

export default {
  namespace: true,
  state: () => ({
    loginUser: {
      userName: "未登录",
    },
  }),

  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },

  actions: {
    async getLoginUser({ commit, state }, payload) {
      const res = await UserController.getLoginUserVo();
      if (res.data.code === 0) {
        commit("updateUser", res.data);
      } else {
        commit("updateUser", {
          ...state.loginUser,
          userRole: ACCESS_Enum.NOT_LOGIN,
        });
      }
    },
  },
} as StoreOptions<any>;
