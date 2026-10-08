<script setup>
import { computed } from "vue";
import { useAuthStore } from "@/stores/auth";

const authStore = useAuthStore();
const isLogin = computed(() => !!authStore.token); //!!의 경우 해당 정보가 truthy하다면 true를 반환
const memberId = computed(() => authStore.user.userId);

const logout = () => {
  if (!confirm("로그아웃 하시겠습니까?")) return;
  authStore.logout();
};
</script>

<template>
  <header>
    <div class="div-header">
      <nav class="nav-header" style="margin-right: 50vw">
        <router-link to="/" class="header-link" style="color: white">
          <img
            :width="100"
            aspect-ratio="1/1"
            cover
            src="@/assets/img/Roomlogo.png"
            alt="Room Logo"
        /></router-link>
      </nav>
      <nav class="nav-header">
        <ul>
          <li>
            <router-link to="/" class="header-link" style="color: black"
              >HOME</router-link
            >
          </li>
          <li>
            <router-link to="/board" class="header-link" style="color: black"
              >게시판</router-link
            >
          </li>
          <template v-if="!isLogin">
            <li>
              <router-link to="/login" class="header-link" style="color: black"
                >로그인</router-link
              >
            </li>
            <li>
              <router-link to="/join" class="header-link" style="color: black"
                >회원가입</router-link
              >
            </li>
          </template>
          <template v-else>
            <li style="color: black">{{ memberId }}님 로그인</li>
            <li>
              <a
                href=""
                @click="logout"
                class="header-link"
                style="color: black"
                >로그아웃</a
              >
            </li>
            <li>
              <router-link to="/mypage" class="header-link" style="color: black"
                >마이페이지</router-link
              >
            </li>
          </template>
        </ul>
      </nav>
    </div>
  </header>
</template>

<style scoped></style>
