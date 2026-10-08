<script setup>
import { ref, onMounted } from "vue";
import { listHouse } from "@/api/map";
import { useRoute } from "vue-router";
import VKaKaoMap from "@/components/common/VKaKaoMap.vue";
import axios from "axios";

const houseIndexs = ref({});
const route = useRoute();
const searchInput = ref("");
const suggestions = ref([]);

// 현재 지도 정보를 가진 객체
const mapParams = ref({
    lat: 36.33064,
    lng: 127.336,
    word: "",
    dis: 5,
});

const center = ref({
    c_lat: 0,
    c_lng: 0,
});

const { VITE_KAKAO_REST_SERVICE_KEY } = import.meta.env;

onMounted(() => {
    searchInput.value = route.query.searchInput;
    moveToArea();
    getHouseList();
});

// 현재 지도 정보 업데이트(지도 드래그, 확/축소 이벤트 시)
const updateMapInfo = (lat, lng, level) => {
    if (mapParams.value) {
        mapParams.value.lat = lat;
        mapParams.value.lng = lng;
        mapParams.value.dis = level;

        getHouseList();
    }
};

// rest에서 현 좌표 기준 매물정보 가져오기
const getHouseList = async () => {
    listHouse(
        mapParams.value,
        ({ data }) => {
            houseIndexs.value = data.articles.map((articleObject) => ({
                apartmentName: articleObject.apartmentName,
                lng: articleObject.lng,
                lat: articleObject.lat,
                place: articleObject.place,
                aptCode: articleObject.aptCode,
            }));
        },
        (err) => {
            console.error(err);
        }
    );
};

const getPlaceName = async () => {
    if (searchInput.value != "") {
        try {
            const response = await axios.get(
                "https://dapi.kakao.com/v2/local/search/keyword.json",
                {
                    params: {
                        query: searchInput.value,
                    },
                    headers: {
                        Authorization: `KakaoAK ${VITE_KAKAO_REST_SERVICE_KEY}`,
                    },
                }
            );

            if (response.data.documents.length > 0) {
                const firstPlace = response.data.documents[0];
                const { x: lng, y: lat } = firstPlace;
                center.value.c_lng = lng;
                center.value.c_lat = lat;
            } else {
                console.log("검색된 장소가 없습니다.");
            }
        } catch (error) {
            console.error("장소 검색 오류:", error);
        }
    }
};

const moveToArea = () => {
    suggestions.value = [];
    getPlaceName();
    searchInput.value = "";
};

const onSearchInput = async () => {
    // 검색어 입력 시 연관 검색어를 요청
    try {
        //인풋키가 있을 때만
        if (searchInput.value.length !== 0) {
            const response = await axios.get(
                "https://dapi.kakao.com/v2/local/search/keyword.json",
                {
                    params: {
                        query: searchInput.value,
                    },
                    headers: {
                        Authorization: `KakaoAK ${VITE_KAKAO_REST_SERVICE_KEY}`,
                    },
                }
            );

            if (response.data.documents.length > 0) {
                // 검색어가 있는 경우 suggestions에 저장
                suggestions.value = response.data.documents;
            } else {
                // 검색어가 없는 경우 suggestions를 초기화
                console.log("none SearchKey");
            }
        }
    } catch (error) {
        console.log("장소검색 오류");
    }
};
</script>

<template>
    <div id="searchList-box">
        <div class="input-group">
            <input
                v-model="searchInput"
                @input="onSearchInput"
                @keyup.enter="moveToArea"
                type="text"
                class="form-control"
                list="suggestion_box"
                placeholder="검색어..."
                style="border: 1px solid navy"
            />
            <button
                class="btn"
                type="button"
                @click="moveToArea"
                style="
                    background-color: #14213d;
                    color: white;
                    border: 1px solid navy;
                "
            >
                검색
            </button>
            <!-- 연관 검색어를 표시할 listbox -->
            <datalist id="suggestion_box">
                <option
                    v-for="(suggestion, index) in suggestions"
                    :key="index"
                    :value="suggestion.place_name"
                ></option>
            </datalist>
        </div>
        <VKaKaoMap
            :mapParams="mapParams"
            :houseIndexs="houseIndexs"
            :center="center"
            @updateMap="updateMapInfo"
        ></VKaKaoMap>
    </div>
</template>

<style scoped></style>
