<script setup>
import {
  CaretBottom,
  Crop,
  EditPen,
  Management,
  Promotion,
  SwitchButton,
  User,
  UserFilled,
  MagicStick,
  DArrowLeft,
  DArrowRight,
  DataLine
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import { userInfoService } from "@/api/user";
import { useUserInfoStore } from "@/stores/userInfo";
import { useRouter, useRoute } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import { useTokenStore } from "@/stores/token";
import { useTabsStore } from '@/stores/tabs'
import { watch, ref } from 'vue';
import frgema from './darw/frgema.vue';

const userInfoStore = useUserInfoStore();
const isCollapse = ref(true)

const getUserInfo = async () => {
  const result = await userInfoService();
  userInfoStore.setUserInfo(result.data)
}
getUserInfo()

// tabs逻辑
const TabsStore = useTabsStore()
let hashTabs = {
  '文章分类': '/article/category',
  '文章管理': '/article/manage',
  '灵感创作': '/ai/gemini',
  '画板绘制': '/darw/frgema'
}

const router = useRouter();
watch(() => TabsStore.currentTab, (newVal, oldVal) => {
  if (hashTabs[newVal]) {
    router.push(hashTabs[newVal])
  }
})

// 退出登录
const tokenStore = useTokenStore();
const handleCommand = (command) => {
  if (command === 'logout') {
    ElMessageBox.confirm(
      '你确认要退出吗？',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }
    ).then(
      async () => {
        // clear data in pinia
        userInfoStore.removeUserInfo()
        tokenStore.removeToken()
        ElMessage.success("退出成功")
        await router.push('/login')
      }
    )
  } else {
    router.push('/user/' + command)
  }
}

// iframe显示逻辑
const isNowPage = ref(false)
const isVisited = ref(false)
const routerPath = useRoute()
watch(
  () => routerPath.path,
  (value) => {
    if (value === '/darw/frgema') {
      isVisited.value = true
      isNowPage.value = true
    } else {
      isNowPage.value = false
    }
  },
  {
    immediate: true
  }
)

</script>

<template>
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside>
      <div class="el-aside__logo" v-if="!isCollapse"></div>
      <div v-else class="el-aside__logo1"></div>
      <el-menu :collapse="isCollapse" active-text-color="#ffd04b" background-color="rgb(0, 21, 41)" text-color="#fff"
        router>
        <el-menu-item index="/article/category" @click="TabsStore.addTab('文章分类')">
          <el-icon>
            <Management />
          </el-icon> <template #title>文章分类</template>
        </el-menu-item>
        <el-menu-item index="/article/manage" @click="TabsStore.addTab('文章管理')">
          <el-icon>
            <Promotion />
          </el-icon>
          <template #title>文章管理</template>
        </el-menu-item>
        <el-menu-item index="/ai/gemini" @click="TabsStore.addTab('灵感创作')">
          <el-icon>
            <MagicStick />
          </el-icon>
          <template #title>灵感创作</template>
        </el-menu-item>

        <el-menu-item index="/darw/frgema" @click="TabsStore.addTab('画板绘制')">
          <el-icon><DataLine /></el-icon>
          <template #title>画板绘制</template>
        </el-menu-item>

        <el-sub-menu>
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>个人中心</span>
          </template>
          <el-menu-item index="/user/info">
            <el-icon>
              <User />
            </el-icon>
            <template #title>基本资料</template>
          </el-menu-item>
          <el-menu-item index="/user/avatar">
            <el-icon>
              <Crop />
            </el-icon>
            <template #title>更换头像</template>
          </el-menu-item>
          <el-menu-item index="/user/resetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <template #title>修改密码</template>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div style="display: flex; align-items: center; justify-content: space-between; user-select: none;">
          <div
            style="border: 4px solid #ccc; margin-right: 10px; padding: 2px; background-color: #fff; border-radius: 50%; cursor: pointer;">
            <el-icon @click="isCollapse = !isCollapse" v-show="isCollapse">
              <DArrowRight />
            </el-icon>
            <el-icon @click="isCollapse = !isCollapse" v-show="!isCollapse">
              <DArrowLeft />
            </el-icon>
          </div>

          <div>捉刀客：<strong>{{ userInfoStore.userInfo.nickname }}</strong></div>
        </div>

        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown__box">
            <el-avatar :src="userInfoStore.userInfo.userPic ? userInfoStore.userInfo.userPic : avatar" />
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
              <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
              <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
              <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>

      <!-- Tabs区域 -->
      <el-tabs v-model="TabsStore.currentTab" type="card" class="demo-tabs" closable @tab-remove="TabsStore.removeTab">
        <el-tab-pane v-for="item in TabsStore.tabs" :key="item" :label="item" :name="item" />
      </el-tabs>
      <el-main>
        <frgema v-if="isVisited" v-show="isNowPage" />
        <router-view v-slot="{ Component }">
          <keep-alive :include="['frgema', 'gemini']">
            <component :is="Component" />
          </keep-alive>
        </router-view>
      </el-main>
      <!-- 底部区域 -->
      <el-footer>文思泉涌 © 文官执笔 ，不欺百姓</el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;
  display: flex;

  .el-aside {
    width: auto;
    background-color: rgb(0, 21, 41);

    &__logo {
      height: 120px;
      background: url('@/assets/logoone.png') no-repeat center / 120px auto;
    }

    &__logo1 {
      height: 120px;
      background: url('@/assets/logosmall.png') no-repeat center / 36px auto;
    }

    .el-menu {
      border-right: none;
      background-color: rgb(0, 21, 41);
      color: #000;
    }

    .el-menu-item:hover {
      background-color: rgb(36, 73, 255);
    }
  }

  .el-container {

    .el-header {
      background-color: rgb(255, 255, 255);
      display: flex;
      align-items: center;
      justify-content: space-between;

      .el-dropdown__box {
        display: flex;
        align-items: center;

        .el-icon {
          color: rgb(255, 255, 255);
          margin-left: 10px;
        }

        &:active,
        &:focus {
          outline: none;
        }
      }
    }

    .el-footer {
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
      color: #666;
    }
  }

}
</style>
