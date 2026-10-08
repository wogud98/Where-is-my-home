import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { jwtDecode } from "jwt-decode";

// pinia의 defineStore의 설정 정보를 재설정 해준 것인가?
export const useAuthStore = defineStore(
  "auth",
  () => {
    //login User의 정보
    const user = ref({
      userId: "",
      name: "",
    });
    const token = ref(""); //jwt 토큰 정보

    const login = async (loginForm) => {
      const { data } = await axios.post(`/api/user/login`, loginForm);
      console.log("로그인 요청 후 응답 데이터:", data);

      token.value = data.token; // 토큰 정보 저장

      const decoded = jwtDecode(data.token); // 토큰에서 유저정보 추출하여 유저정보 저장
      console.log("디코딩된 토큰 정보 : ", decoded);
      user.value.userId = decoded.userId;
      user.value.name = decoded.name;
    };

    const logout = () => {
      clearUser();
    };

    const clearUser = () => {
      user.value.userId = "";
      user.value.name = "";
      token.value = "";
    };

    const join = async (joinForm) => {
      console.log("회원가입 요청 전 회원가입 정보 데이터:", joinForm);
      return await axios.post(`/api/user/join`, joinForm);
    };

    const deleteUser = async (userId) => {
      console.log("회원정보 삭제 전 회원 아이디", userId);
      return await axios.delete(`/api/user/delete/${userId}`);
    };

    return { user, token, login, logout, clearUser, join, deleteUser };
  },

  //새로고침시 데이터 유지를 위한 설정(localStorage에 저장해서 불러오는 방식)
  {
    persist: true,
  }
  //sessionStorage 사용하고 싶은 경우
  // {
  //   persist: {
  //     storage: sessionStorage,
  //   },
  // }
);
