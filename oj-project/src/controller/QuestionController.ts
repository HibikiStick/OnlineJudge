const local = "http://localhost:8081";
const axios = require("axios");
axios.defaults.withCredentials = true;

const addQuestion: any = async (form: any) => {
  const res = await axios
    .post(local + "/quest/add", form)
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

export default { addQuestion };
