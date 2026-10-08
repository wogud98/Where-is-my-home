<script setup>
import { computed } from "vue";

//props 정의 (받아옴)
const props = defineProps({ currentPage: Number, totalPage: Number });

//pageChange 이벤트 정의, emit을 통해 부모컴포넌트로 이벤트 발생시킴
const emit = defineEmits(["pageChange"]);
// 페이지네비게이션 크기 : 한번에 보여지는 수
const navigationSize = parseInt(import.meta.env.VITE_ARTICLE_NAVIGATION_SIZE);

//startPage, endPage, endRange : 페이지 범위 계산에 사용
const startPage = computed(() => {
    return (
        parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1
    );
});
const endPage = computed(() => {
    let lastPage =
        parseInt((props.currentPage - 1) / navigationSize) * navigationSize +
        navigationSize;
    return props.totalPage < lastPage ? props.totalPage : lastPage;
});
const endRange = computed(() => {
    return (
        parseInt((props.totalPage - 1) / navigationSize) * navigationSize <
        props.currentPage
    );
});

// 시작, 끝 페이지까지의 범위 생성 함수
function range(start, end) {
    const list = [];
    for (let i = start; i <= end; i++) list.push(i);
    return list;
}

// 페이지 변경될 때 호출되는 메서드,
function onPageChange(pg) {
    emit("pageChange", pg);
}
</script>

<template>
    <div class="row">
        <ul class="pagination pagination-sm justify-content-center">
            <li class="page-item">
                <a class="page-link custom-page-link" @click="onPageChange(1)"
                    >&laquo;</a
                >
            </li>
            <li class="page-item">
                <a
                    class="page-link custom-page-link"
                    @click="onPageChange(startPage == 1 ? 1 : startPage - 1)"
                >
                    <span aria-hidden="true"> &lt;</span></a
                >
            </li>
            <template v-for="pg in range(startPage, endPage)" :key="pg">
                <li
                    :class="
                        currentPage === pg ? 'page-item active' : 'page-item'
                    "
                >
                    <a
                        class="page-link custom-page-link"
                        @click="onPageChange(pg)"
                        >{{ pg }}</a
                    >
                </li>
            </template>
            <li class="page-item">
                <a
                    class="page-link custom-page-link"
                    @click="onPageChange(endRange ? totalPage : endPage + 1)"
                >
                    <span aria-hidden="true"> &gt;</span></a
                >
            </li>
            <li class="page-item">
                <a
                    class="page-link custom-page-link"
                    @click="onPageChange(totalPage)"
                    >&raquo;</a
                >
            </li>
        </ul>
    </div>
</template>

<style scoped>
a {
    cursor: pointer;
}
.custom-page-link {
    color: rgb(38, 15, 215); /* Change this to the desired color */
}
.custom-page-link:hover {
    text-decoration: underline;
}
</style>
