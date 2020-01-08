export const getters = {
    isAuth(state) {
      return state.isAuth;
    },
    user(state) {
      return state.user;
    },
    error(state) {
      return state.error;
    },
    loading(state) {
      return state.loading;
    },
    billsList(state) {
      return state.billsList;
    }
}