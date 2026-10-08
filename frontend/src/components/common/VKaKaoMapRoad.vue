<script setup>
import { onMounted } from "vue";

const props = defineProps({
    currLng: String,
    currLat: String,
});

onMounted(() => {
    if (window.kakao && window.kakao.maps) {
        initMap();
    } else {
        const script = document.createElement("script");
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
            import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
        }&libraries=services,clusterer`;
        /* global kakao */
        script.onload = () => kakao.maps.load(() => initMap());
        document.head.appendChild(script);
    }
});

const initMap = () => {
    console.log("KaKao-initMap");

    var roadviewContainer = document.getElementById("roadview"); //로드뷰를 표시할 div
    var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
    var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
    var position = new kakao.maps.LatLng(
        Number(props.currLat),
        Number(props.currLng)
    );

    // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
    roadviewClient.getNearestPanoId(position, 50, function (panoId) {
        if (panoId) {
            // 로드뷰 정보가 있는 경우
            roadview.setPanoId(panoId, position); // panoId와 중심좌표를 통해 로드뷰 실행
        } else {
            // 로드뷰 정보가 없는 경우
            roadviewContainer.innerHTML =
                "해당 위치는 로드뷰를 지원하지 않습니다.";
        }
    });
};
</script>

<template>
    <div id="roadview" style="width: 100%; height: 100%">로드맵</div>
</template>

<style></style>
