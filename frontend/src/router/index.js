import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginForm from "../views/LoginForm.vue";
import JoinForm from "../views/JoinForm.vue";
import MyPageForm from "../views/MyPageForm.vue";
import SearchView from "@/views/SearchView.vue";
import SearchList from "@/components/SearchView/SearchList.vue";
import SearchDetailView from "../views/SearchDetailView.vue";
import { useAuthStore } from "../stores/auth";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        //HomeView
        {
            path: "/",
            name: "home",
            component: HomeView,
        },
        // {
        //   path: '/about',
        //   name: 'about',
        //   // route level code-splitting
        //   // this generates a separate chunk (About.[hash].js) for this route
        //   // which is lazy-loaded when the route is visited.
        //   component: () => import('../views/AboutView.vue')
        // }

        // Member
        //로그인 페이지
        {
            path: "/login",
            name: "login",
            component: LoginForm,
        },
        //회원가입 페이지
        {
            path: "/join",
            name: "join",
            component: JoinForm,
        },
        //회원정보 페이지
        {
            path: "/mypage",
            name: "mypage",
            component: MyPageForm,
        },

        // SearchView
        {
            path: "/house",
            component: SearchView,
            redirect: "/house/list",
            children: [
                //SearchList
                {
                    path: "/house",
                    component: SearchView,
                    redirect: "/house/list",
                    children: [
                        //SearchList
                        {
                            path: "list",
                            name: "list",
                            component: SearchList,
                        },
                    ],
                },
                //SearchDetailView
                {
                    path: "/detail/:ac/:lat/:lng",
                    name: "detail",
                    component: SearchDetailView,
                    meta: { requiresAuth: true },
                },
            ],
        },

        //board
        {
            path: "/board",
            name: "board",
            meta: { requiresAuth: true },
            // component: TheBoardView,
            // route level code-splitting
            // this generates a separate chunk (About.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import("../views/TheBoardView.vue"),
            redirect: { name: "article-list" },
            children: [
                {
                    path: "list",
                    name: "article-list",
                    component: () =>
                        import("@/components/boards/BoardList.vue"),
                },
                {
                    path: "view/:articleno",
                    name: "article-view",
                    component: () =>
                        import("@/components/boards/BoardDetail.vue"),
                },
                {
                    path: "write",
                    name: "article-write",
                    component: () =>
                        import("@/components/boards/BoardWrite.vue"),
                },
                {
                    path: "modify/:articleno",
                    name: "article-modify",
                    component: () =>
                        import("@/components/boards/BoardModify.vue"),
                },
            ],
        },
    ],
});

//로그인, 또는 관리자 권한이 필요한 경우 제어 ( next()의 경우, 원래 이동하려 했던 라우트로 이동 )
router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();
    //로그인 필요
    if (to.matched.some((record) => record.meta.requiresAuth)) {
        if (!authStore.token) {
            alert("로그인 권한 필요");
            next({ path: "/login" }); // 인증되지 않은 경우, 로그인 페이지로 리다이렉트
        } else next();
    } else {
        next(); // 인증이 필요하지 않은 경우 해당 경로로 이동
    }
});

export default router;
