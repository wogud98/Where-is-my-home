<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth";
const authStore = useAuthStore();
const router = useRouter();

const loginForm = ref({
  userId: "",
  password: "",
});

const login = async () => {
  try {
    await authStore.login(loginForm.value);
    router.push("/");
  } catch (error) {
    console.log("로그인 실패 에러:", error);
    alert("아이디 또는 비밀번호가 일치하지 않습니다.");
  }
};
</script>

<template>
  <div class="login-container">
    <form @submit.prevent="login">
      <router-link to="/" class="logo">뭘방</router-link>
      <div class="d-flex justify-content-center">
        <fieldset>
          <ul id="ul">
            <li>
              <h3>뭘방에 로그인</h3>
            </li>
            <li>
              <input
                type="text"
                placeholder="아이디"
                id="login-id"
                class="fieldset-input"
                v-model="loginForm.userId"
              />
            </li>
            <li>
              <input
                type="text"
                placeholder="비밀번호"
                id="login-pw"
                class="fieldset-input"
                v-model="loginForm.password"
              />
            </li>
            <li>
              <!-- form tag 내부 버튼은 default가 submit type이기에 정보를 submit 시 새로고침 된다 -->
              <button type="submit" id="login-btn" class="login-btn">
                로그인
              </button>
            </li>
            <!-- <li><a href="${pageContext.request.contextPath}/member?action=mvSignin"
            class="a-signin">계정이 없으신가요? 회원가입</a></li> -->
            <li>
              <router-link to="/join" class="a-signin"
                >계정이 없으신가요? 회원가입</router-link
              >
            </li>
            <!-- <li>
              <button
                type="button"
                id="find-password-btn"
                class="find-password-btn"
              >
                비밀번호 찾기
              </button>
            </li> -->
          </ul>
        </fieldset>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
