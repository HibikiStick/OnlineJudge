import ACCESS_Enum from "./accessEnum";

/**
 * 判断有无权限
 * @param loginUser 用户
 * @param needAccess 需要的权限
 */
const checkAccess = (loginUser: any, needAccess = ACCESS_Enum.NOT_LOGIN) => {
  const loginUserAccess = loginUser?.userRole ?? ACCESS_Enum.NOT_LOGIN;
  if (needAccess === ACCESS_Enum.NOT_LOGIN) {
    return true;
  }
  if (needAccess === ACCESS_Enum.USER) {
    if (loginUserAccess === ACCESS_Enum.NOT_LOGIN) {
      return false;
    }
  }
  if (needAccess === ACCESS_Enum.ADMIN) {
    if (loginUserAccess !== ACCESS_Enum.ADMIN) {
      return false;
    }
  }
  return true;
};

export default checkAccess;
