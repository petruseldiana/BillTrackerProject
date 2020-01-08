<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
      <div class="container">
        <router-link class="navbar-brand js-scroll-trigger" to="/">Bill tracker</router-link>
        <div v-if="isAuthenticated">
          <router-link class="nav-link js-scroll-trigger" to="/bills">List</router-link>
        </div>
        <div v-if="isAuthenticated">
          <router-link class="nav-link js-scroll-trigger" to="/charts">Graph</router-link>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="navbar-nav ml-auto">
            <li v-if="isAuthenticated" class="nav-item mr-4">
              <span class="nav-link">Welcome, {{ user.username }}</span>
            </li>
            <li class="nav-item">
              <router-link class="nav-link js-scroll-trigger" to="/">Home</router-link>
            </li>
            <li v-if="!isAuthenticated" class="nav-item">
              <router-link class="nav-link js-scroll-trigger" to="login">Login</router-link>
            </li>
            <li v-if="isAuthenticated" class="nav-item">              
              <router-link @click.native="logout()" class="nav-link js-scroll-trigger" to="/">Logout</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <div class="masthead">
      <div class="container d-flex h-100 align-items-center justify-content-center">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie';
import AXIOS from './http-common';

export default {
  name: 'app',
  mounted() {
    if (Cookies.get('AUTH') !== undefined) {
      AXIOS.get('/getCurrentUser')
        .then((response) =>{
          this.$store.commit('setIsAuth', true);
          this.$store.commit('setUser', { id: response.data.id, username: response.data.name });
        })
        .catch((error) => {
          this.$store.commit('setIsAuth', false);
          Cookies.remove('AUTH');
        });
    }
  },
  computed: {
    isAuthenticated() {
      return this.$store.getters.isAuth;
    },
    user() {
      return this.$store.getters.user;
    },
  },
  methods: {
    logout() {
      AXIOS.post(`/logout`)
        .then((response) => {
          this.$store.commit('setIsAuth', false);
          Cookies.remove('AUTH');
          this.$router.push("/");
        })
        .catch((error) => console.log(error));
    },
  },
}
</script>

<style lang="scss">
@import './assets/css/global.scss';

#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
