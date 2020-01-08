export const mutations = {
  setIsAuth(state, payload) {
    state.isAuth = payload;
  },
  setUser(state, payload) {
    state.user = payload;
  },
  setError(state, payload) {
    state.error = payload;
  },
  setLoading(state, payload) {
    state.loading = payload;
  },
  setBillsList(state, payload) {
    const bills = payload.map((el) => {
      const status = el.status ? 'Paid' : 'Unpaid';

      return {
        id: el.id,
        price: el.price,
        name: el.name,
        due: el.due,
        status: status,
        creationDate: el.creationDate,
        details: el.details,
      };
    })
    state.billsList = bills;
  },
}