import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useHouseStore = defineStore("house", () => {
    const realEstates = ref([]);
    const currentPage = ref(1);
    const totalPage = ref(0);
    const lat = ref("");
    const lng = ref("");
    // const totalPageCount = ref(0);
    const getRealEstates = async (aptParams) => {
        const { data } = await axios.get(`/api/house/detail`, {
            params: aptParams,
        });
        console.log("getRealEstates의 응답 데이터: ", data);

        realEstates.value = data.aptInfos;
        currentPage.value = data.currentPage;
        totalPage.value = data.totalPageCount;
        lat.value = data.aptInfos[0].lat;
        lng.value = data.aptInfos[0].lng;
    };

    return {
        realEstates,
        getRealEstates,
        currentPage,
        totalPage,
        lat,
        lng,
    };
});
