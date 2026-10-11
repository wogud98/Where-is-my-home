import { fileURLToPath, URL } from "node:url";
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
    server: {
        proxy: {
            "/api": {
                // front 서버를 spring 서버로 바꿔주기
                // http://localhost/api/board
                target: "http://localhost",
                // /api를 빈칸으로 대체
                rewrite: (path) => path.replace(/^\/api/, ""),
                // 직접 공공데이터를 가져오는 경우 origin 변경
                changeOrigin: true,
            },
            "/data": {
                target: "http://apis.data.go.kr",
                rewrite: (path) => path.replace(/^\/data/, ""),
                changeOrigin: true,
            },
        },
    },
});
