<script setup>
import { ref, computed, watch, onMounted } from "vue";
import { useRoute } from "vue-router";
import VPageNavigation from "@/components/common/VPageNavigation.vue";
import VKaKaoMapRoad from "../components/common/VKaKaoMapRoad.vue";
import { useHouseStore } from "@/stores/house";

const houseStore = useHouseStore();
const route = useRoute();

const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const currLng = route.params.lng;
const currLat = route.params.lat;

const aptParams = ref({
    pgno: currentPage.value,
    spp: VITE_ARTICLE_LIST_SIZE,
    aptCode: route.params.ac,
});

onMounted(() => {
    //const currLng = route.params.lng;
    //const currLat = route.params.lat;
    console.log("currLNG:", currLng);
    console.log("currLNG:", currLat);
});

const onPageChange = (val) => {
    console.log(val + "번 페이지로 이동 준비 끝!!!");
    currentPage.value = val;
    aptParams.value.pgno = val;
    houseStore.getRealEstates(aptParams.value);
};

// const aptCode = ref(route.params.ac);

houseStore.getRealEstates(aptParams.value);

const aptDealInfos = computed(() => houseStore.realEstates);

watch(
    () => aptDealInfos,
    () => {
        currentPage.value = houseStore.currentPage;
        totalPage.value = houseStore.totalPage;
    },
    { deep: true }
);

console.log("aptDealInfos", aptDealInfos); // computed는 사용하기 전까지 값이 담겨있지 않다.

const squareMeterToPyung = (squareMeter) => {
    const pyungConversionFactor = 3.305785;
    const pyungValue = squareMeter / pyungConversionFactor;
    const squareMeterValue = pyungValue * pyungConversionFactor;
    return [pyungValue.toFixed(2), squareMeterValue.toFixed(2)];
};
const formatPrice = (price) => {
    // 쉼표 제거 후 문자열을 숫자로 변환
    price = parseFloat(price.replace(/,/g, ""));

    const unit = 10000; // 1억
    const billion = Math.floor(price / unit);
    const million = Math.floor(price % unit); // 만원 단위로 표시

    if (billion > 0) {
        return `${billion}억${million > 0 ? ` ${million}만` : ""}`;
    } else {
        return `${million}만원`;
    }
};
</script>

<template>
    <div class="body" id="detail-body">
        <div class="row">
            <div
                class="col-lg-6 col-md-12 d-flex justify-content-center"
                id="leftOption"
            >
                <div class="card">
                    <!-- <img
                        src="../../src/assets/img/house.jpg"
                        class="card-img-top"
                        alt="house"
                    /> -->
                    <VKaKaoMapRoad
                        :currLng="currLng"
                        :currLat="currLat"
                    ></VKaKaoMapRoad>
                </div>
            </div>

            <div
                class="col-lg-6 col-md-12"
                id="rightOption"
                style="justify-content: flex-end"
            >
                <div id="right-title">
                    <h5
                        class="cd-title"
                        style="font-size: 2.5rem;
}"
                    >
                        {{ aptDealInfos[0]["매물 이름"] }}
                    </h5>
                    <p class="cd-title" style="font-size: 1rem">
                        도로명 주소 : {{ aptDealInfos[0]["도로명 주소"] }}
                    </p>
                </div>
                <table class="table" id="right-table">
                    <thead>
                        <tr>
                            <th scope="col">가격</th>
                            <th scope="col">면적</th>
                            <th scope="col">거래 일자</th>
                        </tr>
                    </thead>

                    <tr
                        v-for="aptDealInfo in aptDealInfos"
                        :key="aptDealInfo['가격'] + aptDealInfo['거래 일자']"
                    >
                        <td>{{ formatPrice(aptDealInfo["가격"]) }}</td>
                        <td>
                            {{ squareMeterToPyung(aptDealInfo["면적"])[0] }}평
                            ({{ squareMeterToPyung(aptDealInfo["면적"])[1] }}m²)
                        </td>
                        <td>{{ aptDealInfo["거래 일자"] }}</td>
                    </tr>
                </table>
                <VPageNavigation
                    :current-page="currentPage"
                    :total-page="totalPage"
                    @pageChange="onPageChange"
                ></VPageNavigation>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
