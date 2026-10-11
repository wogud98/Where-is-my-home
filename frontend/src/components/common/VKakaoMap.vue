<script setup>
import { ref, watch, onMounted, defineProps, defineEmits } from "vue";
import imgSrc from "@/assets/map/markerStar3.png";
import router from "../../router";

var map;
var mMarker;
var mapCenter;
var geocoder;
const positions = ref([]);
const markers = ref([]);

const props = defineProps({
    mapParams: Object,
    houseIndexs: Object,
    center: Object,
});

const emit = defineEmits(["updateMap"]);

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

watch(
    () => props.houseIndexs,
    () => {
        if (window.kakao) {
            positions.value = [];
            props.houseIndexs.forEach((houseIndex) => {
                let obj = {};
                obj.latlng = new kakao.maps.LatLng(
                    houseIndex.lat,
                    houseIndex.lng
                );
                mapCenter = new kakao.maps.LatLng(
                    houseIndex.lat,
                    houseIndex.lng
                );
                obj.title = houseIndex.apartmentName;
                obj.place = houseIndex.place;
                obj.aptCode = houseIndex.aptCode;
                obj.lat = houseIndex.lat;
                obj.lng = houseIndex.lng;
                positions.value.push(obj);
            });
            loadMarkers();
        }
    },
    { deep: true }
);

watch(
    () => props.center,
    () => {
        move_Center_Map();
    },
    { deep: true }
);

const initMap = () => {
    const container = document.getElementById("map");
    const options = {
        center: new kakao.maps.LatLng(36.35531, 127.2982),
        level: 3,
    };

    //지도 생성
    map = new kakao.maps.Map(container, options);

    // 지도 종류 선택 컨트롤
    var mapTypeControl = new kakao.maps.MapTypeControl();
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
    // 지도 확대 축소를 제어할 수 있는  줌 컨트롤
    var zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

    //지도에 올릴 마커 생성
    mMarker = new kakao.maps.Marker({
        position: mapCenter, // 지도의 중심좌표에 올립니다.
        map: map, // 생성하면서 지도에 올립니다.
    });

    geocoder = new kakao.maps.services.Geocoder();

    //현 지도 중심좌표 주소 검색 및 좌측 상단 표시
    searchAddrFromCoords(map.getCenter(), displayCenterInfo);

    //이벤트 리스너 등록
    kakao.maps.event.addListener(map, "dragstart", closeInfoWindowsOnDrag);
    kakao.maps.event.addListener(map, "zoom_changed", closeInfoWindowsOnZoom);

    //드래그 + 마우스 휠 이벤트
    kakao.maps.event.addListener(map, "idle", handleMapIdleEnd);

    //마커
    loadMarkers();
};

// 지도 function  ----------------------------------------

function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
}

// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수
function displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        var infoDiv = document.getElementById("centerAddr");
        for (var i = 0; i < result.length; i++) {
            // 행정동의 region_type 값은 'H' 이므로
            if (result[i].region_type === "H") {
                infoDiv.innerHTML = result[i].address_name;
                break;
            }
        }
    }
}

// 지도 정보 얻기
const handleMapIdleEnd = () => {
    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
    var latlng = map.getCenter();
    var level = map.getLevel();
    emit("updateMap", latlng.getLat(), latlng.getLng(), level);
};

// 마커 ------------------------------------------------
const loadMarkers = () => {
    deleteMarkers();
    const imgSize = new kakao.maps.Size(50, 50); // 마커 이미지
    const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

    // 마커를 생성
    markers.value = [];
    positions.value.forEach((position) => {
        const marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: position.latlng, // 마커를 표시할 위치
            title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
            clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
            image: markerImage, // 마커의 이미지
        });

        var iwContent = `
            <div style="padding: 5px; color: black; width:300px">
                ${position.title}
            </div>
            <div style="padding: 5px; color: black; width:300px">
                ${position.place}
            </div>
            <div style="padding: 5px; color: black; width:300px">
                이 매물
                <a href="#" id="detail${position.aptCode}" >자세히 보기</a>
            </div>
            <div class="wrap_roadview">
            <div id="roadview" style="width: 100%; height: 100%"></div>
        `;

        //인포윈도우 연결
        const infowindow = new kakao.maps.InfoWindow({
            content: iwContent,
            removable: true,
        });

        // 마커 객체에 인포윈도우 저장
        marker.infowindow = infowindow;

        // 마커 클릭 이벤트
        kakao.maps.event.addListener(marker, "click", function () {
            infowindow.open(map, marker);

            document.querySelector(`a#detail${position.aptCode}`).onclick = (
                e
            ) => {
                console.log(`${position.aptCode} 의 클릭이벤트 실행`);
                e.preventDefault();

                router.push(
                    `/detail/${position.aptCode}/${position.lat}/${position.lng}`
                );
            };
        });
        markers.value.push(marker);
    });
};

const move_Center_Map = () => {
    if (map) {
        map.panTo(
            new kakao.maps.LatLng(props.center.c_lat, props.center.c_lng)
        );
    }
};

const deleteMarkers = () => {
    if (markers.value.length > 0) {
        markers.value.forEach((marker) => marker.setMap(null));
    }
};

const closeInfoWindowsOnDrag = () => {
    closeInfoWindows();
};

const closeInfoWindowsOnZoom = () => {
    closeInfoWindows();
};

const closeInfoWindows = () => {
    markers.value.forEach((marker) => {
        const infowindow = marker.infowindow;

        if (infowindow && infowindow.getMap()) {
            infowindow.close();
        }
    });
};
</script>

<template>
    <div class="map_wrap">
        <div id="map" style="overflow: hidden"></div>
        <div class="hAddr">
            <span class="title"></span>
            <span id="centerAddr"></span>
        </div>

        <br />
    </div>
</template>

<style></style>
