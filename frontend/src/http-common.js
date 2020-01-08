import axios from 'axios';
import Cookies from 'js-cookie';

const AXIOS = axios.create({
  baseURL: 'http://localhost:8081/api',
})

AXIOS.interceptors.request.use(
  (config) => {
    let token = Cookies.get('AUTH');

    if (token) {
      config.headers['Authorization'] = `Bearer ${ token }`;
    }

    return config;
  }, 

  (error) => {
    return Promise.reject(error);
  }
);

export default AXIOS;