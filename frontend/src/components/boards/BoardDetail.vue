<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;

const article = ref({});

onMounted(() => {
    getArticle();
});

const getArticle = () => {
    // const { articleno } = route.params;
    detailArticle(
        articleno,
        ({ data }) => {
            article.value = data;
        },
        (error) => {
            console.error(error);
        }
    );
};

function moveList() {
    router.push({ name: "article-list" });
}

function moveModify() {
    router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
    deleteArticle(
        articleno,
        (response) => {
            if (response.status == 200) moveList();
        },
        (error) => {
            console.error(error);
        }
    );
}
</script>

<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="sky">글보기</mark>
                </h2>
            </div>
            <div class="col-lg-10 text-start" style="background-color: white">
                <div class="row my-2">
                    <h2 class="px-5" style="color: black">
                        {{ article.articleNo }}. {{ article.subject }}
                    </h2>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="clearfix align-content-center">
                            <img
                                class="avatar me-2 float-md-start bg-light p-2"
                                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                            />
                            <p>
                                <span class="fw-bold"></span> <br />
                                <span class="fw-light" style="color: black">
                                    {{ article.registerTime }} / 조회 :
                                    {{ article.hit }}
                                </span>
                            </p>
                        </div>
                    </div>
                    <div class="col-md-4 align-self-center text-end"></div>
                    <div class="divider mb-3"></div>
                    <div style="color: black; font-size: larger">
                        {{ article.content }}
                    </div>
                    <div class="divider mt-3 mb-3"></div>
                    <div class="d-flex justify-content-end">
                        <button
                            type="button"
                            class="btn btn-outline-primary mb-3"
                            @click="moveList"
                            style="color: black"
                        >
                            글목록
                        </button>
                        <button
                            type="button"
                            class="btn btn-outline-primary mb-3 ms-1"
                            @click="moveModify"
                            style="color: black"
                        >
                            글수정
                        </button>
                        <button
                            type="button"
                            class="btn btn-outline-primary mb-3 ms-1"
                            @click="onDeleteArticle"
                            style="color: black"
                        >
                            글삭제
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
