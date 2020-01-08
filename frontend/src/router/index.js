import Vue from 'vue';
import Router from 'vue-router';
import Cookies from 'js-cookie';

import Home from '@/components/Home';
import Login from '@/components/Login';
import Register from '@/components/Register';
import BillsList from '@/components/bills/BillsList';
import SaveBill from '@/components/bills/SaveBill';
import UpdateBill from '@/components/bills/UpdateBill'
import Charts from '@/components/bills/Charts';

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
    },
    {
      path: '/bills',
      name: 'BillsList',
      component: BillsList,
      meta: { 
        requiresAuth: true
      }
    },
    {
      path: '/charts',
      name: 'Charts',
      component: Charts,
      meta: { 
        requiresAuth: true
      }
    },
    {
      path: '/save',
      name: 'SaveBill',
      component: SaveBill,
      meta: { 
        requiresAuth: true
      }
    },
    {
      path: '/update',
      name: 'UpdateBill',
      component: UpdateBill,
      props: true,
      meta: { 
        requiresAuth: true
      }
    }
  ]
});

router.beforeEach((to, from, next) => {
  if(to.matched.some(record => record.meta.requiresAuth)) {
    if (Cookies.get('AUTH') !== undefined) {
      next();
      return;
    }
    next('/login');
  } else {
    next();
  }
})

export default router;