import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/client/HomeView.vue'
import StoreView from '../views/client/StoreView.vue'
import DetailView from '../views/client/DetailView.vue'
import LoginView from '../views/client/LoginView.vue'
import RegisterView from '../views/client/RegisterView.vue'
import OrderView from '../views/client/OrderVỉew.vue'
import NotFoundComponent from '@/components/client/NotFoundCompoment.vue'
import ClientLayoutView from '../views/client/LayoutView.vue'

// Admin imports
import AdminLayoutView from '../views/admin/LayoutView.vue'
import AdminLoginView from '../views/admin/LoginView.vue'
import AdminMainView from '../views/admin/MainView.vue'
import AdminDashbroadView from '../views/admin/DashbroadView.vue'
import AdminRegisterView from '../views/admin/RegisterView.vue'
import AdminManagerView from '../views/admin/ManagerView.vue'
import CategoryView from '../views/admin/category/Index.vue'
import ProductView from '../views/admin/product/Index.vue'
import ProductFormView from '../views/admin/product/Form.vue'
import AdminFormView from '../views/admin/FormView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: ClientLayoutView,
      children: [
        { path: '', name: 'home', component: HomeView },
        { path: 'store', name: 'store', component: StoreView },
        { path: 'detail/:id', name: 'detail', component: DetailView },
        { path: 'login', name: 'login', component: LoginView },
        { path: 'register', name: 'register', component: RegisterView },
        { path: 'order', name: 'order', component: OrderView },
        { path: ':pathMatch(.*)*', name: 'NotFound', component: NotFoundComponent }
      ]
    },
    {
      path: '/admin',
      redirect: '/admin/login',
    },
    {
      path: '/admin',
      component: AdminLayoutView,
      children: [
        { path: 'dashbroad', name: 'admin-dashbroad', component: AdminDashbroadView },
        { path: 'main', name: 'admin-main', component: AdminMainView },
        { path: 'manager', name: 'admin-manager', component: AdminManagerView },
        { path: 'category', name: 'admin-category', component: CategoryView },
        { path: 'product', name: 'admin-product', component: ProductView },
        { path: 'form-product', name: 'admin-form-product', component: ProductFormView },
        { path: 'form', name: 'admin-form', component: AdminFormView }
      ]
    },
    { path: '/admin/login', name: 'admin-login', component: AdminLoginView },
    { path: '/admin/register', name: 'admin-register', component: AdminRegisterView }
  ]
})

export default router
