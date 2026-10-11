import "./assets/css/main.css";
import "./assets/css/header_footer.css";
import "./assets/css/login.css";
import "./assets/css/join.css";
import "./assets/css/mypage.css";
import "./assets/css/kakaomap.css";
import "./assets/css/SearchView.css";
import "./assets/css/GlobalStyles.css";
import "./assets/css/SearchDetail.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate"; //새로고침시 데이터 유지를 위한 설정

// Vuetify
import { createVuetify } from "vuetify";
import { aliases, mdi } from "vuetify/iconsets/mdi-svg";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

const app = createApp(App);

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate); //새로고침시 데이터 유지를 위한 설정

const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: "mdi",
    aliases,
    sets: {
      mdi,
    },
  },
});

app.use(pinia);
app.use(router);
app.use(vuetify);
app.mount("#app");
