<script setup>
import { computed } from "vue";
import { useAuthStore } from "@/stores/auth";
import router from "../router";
const authStore = useAuthStore();
const memberInfo = computed(() => authStore.user);

const deleteUser = async () => {
  try {
    const response = await authStore.deleteUser(authStore.user.userId);
    console.log(response);
    authStore.logout();
    router.push("/");
  } catch (error) {
    console.log("회원삭제 실패 에러:", error);
    alert("회원 삭제 실패");
  }
};

function changePassword() {}
</script>

<template>
  <h1 style="color: aliceblue">내 정보 조회</h1>
  <div class="d-flex justify-content-center">
    <table class="table">
      <thead class="table-dark">
        <tr>
          <th class="table-title">마이페이지</th>
          <th>내용</th>
          <th>비고</th>
        </tr>
      </thead>
      <tbody>
        <tr></tr>
        <tr>
          <td class="main-td">이름</td>
          <td class="first-td">{{ memberInfo.name }}</td>
          <td class="second-td"></td>
        </tr>
        <tr>
          <td class="main-td">아이디</td>
          <td class="first-td">{{ memberInfo.userId }}</td>
          <td class="second-td"></td>
        </tr>
      </tbody>
    </table>
  </div>
  <div class="button-container">
    <button class="bg-danger text-white" @click="deleteUser">회원탈퇴</button>
  </div>
</template>

<style scoped></style>
