import Cookies from "js-cookie";
import router from '@/router';
import AXIOS from '../http-common'

export const actions = {
    userSignIn({ commit }, payload) {
        debugger;
        commit ('setLoading', true);

        const data = {
            username: payload.username,
            password: payload.password,
        };

        AXIOS.post('/auth', data)
            .then((response) => {
                Cookies.set('AUTH', response.data.accessToken);
                commit('setError', null);

                router.push({ name: 'BillsList' });
            })
            .catch(() => {
                commit('setError', 'Something went wrong. Please try again.');
            })
            .finally(() => commit('setLoading', false));
    },
    userSignOut ({commit}) {
        AXIOS.post('/public/users/logout')
            .then(res => {
                commit ('setLoading', false);
                commit ('setError', null);

                commit ('clearAuth');
                // EventBus.$emit('authenticated', 'User not authenticated');
                router.push('/');
            })
            .catch(error => {
                commit('setError', error.message);
                commit('setLoading', false);
            });
    },
}