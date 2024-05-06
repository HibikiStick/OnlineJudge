import ACCESS_Enum from "../access/accessEnum";

const local = "http://localhost:8081";
const axios = require("axios");
axios.defaults.withCredentials = true;

const getLoginUserVo: any = async () => {
  const res = await axios
    .get(local + "/user/get/login", {
      parmas: {
        user_login: ACCESS_Enum.NOT_LOGIN,
      },
    })
    .then(function (res: any) {
      console.log(res);
      return res;
    })
    .catch(function (err: any) {
      alert(err);
      return err;
    });
  return res;
};
const Login: any = async (from: any) => {
  const res = await axios
    .post(local + "/user/login", from)
    .then(function (res: any) {
      console.log(res);
      return res;
    })
    .catch(function (err: any) {
      alert(err);
      return err;
    });
  return res;
};

export default { getLoginUserVo, Login };
