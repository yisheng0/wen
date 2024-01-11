import './assets/main.scss'

import { createApp } from 'vue'

// element   中文包
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/dist/locale/zh-cn.js'

// router
import router from '@/router'

// pinia   pinia-persistedstate-plugin
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'

import App from './App.vue'

const app = createApp(App)
const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist);
app.use(pinia);
app.use(router);
app.use(ElementPlus, { locale })
app.mount('#app')
