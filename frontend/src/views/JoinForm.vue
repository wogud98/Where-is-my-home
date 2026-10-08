<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth";

const authStore = useAuthStore();
const router = useRouter();
const joinForm = ref({
  userId: "",
  password: "",
  name: "",
});

const join = async () => {
  try {
    const response = await authStore.join(joinForm.value);
    console.log(response);
    router.push("/login");
  } catch (error) {
    console.log("회원가입 실패 에러:", error);
    alert("회원가입 실패");
  }
};
</script>

<template>
  <div class="login-container">
    <router-link to="/" class="logo">뭘방</router-link>
    <form @submit.prevent="join">
      <div class="d-flex justify-content-center">
        <fieldset>
          <ul>
            <li>
              <h3>뭘방에 회원가입</h3>
            </li>
            <li>
              <input
                type="text"
                placeholder="이름"
                class="fieldset-input"
                v-model="joinForm.name"
              />
            </li>
            <li>
              <input
                type="text"
                placeholder="아이디"
                class="fieldset-input"
                v-model="joinForm.userId"
              />
            </li>
            <li>
              <input
                type="text"
                placeholder="비밀번호"
                class="fieldset-input"
                v-model="joinForm.password"
              />
            </li>

            <!--
        <li>
					<button class="id-duplicate-check-btn">중복 확인</button>
        </li>
        <li>
          <input type="text" placeholder="비밀번호 확인" class="fieldset-input" />
        </li>
        -->

            <!-- 관심 매물 시작 -->
            <!-- <li>
            <h4 class="h4-interests">관심 매물</h4> <input type="text" placeholder="1순위" class="fieldset-input"
              name="interestFirst" style="margin-bottom: 20px; margin-top: 20px" /> <input type="text" placeholder="2순위"
              class="fieldset-input" name="interestSecond" />
          </li> -->
            <!-- 관심 매물 끝 -->
            <li>
              <button type="submit" class="signin-btn">회원가입</button>
            </li>
          </ul>
        </fieldset>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
