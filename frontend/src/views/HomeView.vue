<script setup>
import { ref, onMounted } from "vue";
import { listKeyword } from "@/api/map";
import { useRouter } from "vue-router";
import { mdiHomeSiloOutline, mdiMapSearchOutline } from "@mdi/js";

let searchInput = ref("");
const datalist = ref([]);
const router = useRouter();

const getAreaKeyword = async () => {
    listKeyword(
        searchInput,
        ({ data }) => {
            datalist.value = data.map((item) => item.place);
        },

        (err) => {
            console.error(err);
            datalist.value = [];
        }
    );
};

const goToList = () => {
    router.push({ name: "list", query: { searchInput: searchInput.value } });
};

onMounted(() => {
    getAreaKeyword();
});
</script>

<template>
    <div class="home-container">
        <div id="just_block"></div>
        <h1>
            <p><v-icon :icon="mdiHomeSiloOutline" /></p>
            <p>뭘방</p>
            <h4>찾고 싶은 부동산 매물을 검색해보세요!</h4>
        </h1>
        <div id="just_block"></div>
        <form class="d-flex justify-content-center" @submit.prevent="goToList">
            <div class="row">
                <div class="input-group">
                    <input
                        v-model="searchInput"
                        @input="getAreaKeyword"
                        type="text"
                        class="form-control"
                        list="suggestion_box"
                        placeholder="검색어..."
                    />
                    <datalist id="suggestion_box">
                        <option
                            v-for="(item, index) in datalist"
                            :key="index"
                            :value="item"
                        ></option>
                    </datalist>
                    <button
                        class="btn"
                        type="submit"
                        style="background-color: #14213d; color: white"
                    >
                        <v-icon :icon="mdiMapSearchOutline" />
                        검색
                    </button>

                    <!-- 검색버튼 클릭 시 검색어에 해당하는 지역을 띄운 house/list 페이지 이동하기 구현 -->
                </div>
            </div>
        </form>
        <br /><br />

        <!-- <div class="row d-flex justify-content-center">
            <div class="col-lg-4 col-md-12">
                <div
                    id="carouselExampleAutoplaying"
                    class="carousel slide"
                    data-bs-ride="carousel"
                >
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img
                                src="../../src/assets/img/house.jpg"
                                class="d-block w-100"
                                alt="house"
                            />
                        </div>
                        <div class="carousel-item">
                            <img
                                src="../../src/assets/img/skyscrapers.jpg"
                                class="d-block w-100"
                                alt="skyscrapers"
                            />
                        </div>
                        <div class="carousel-item">
                            <img
                                src="../../src/assets/img/town.jpg"
                                class="d-block w-100"
                                alt="town"
                            />
                        </div>
                    </div>
                    <button
                        class="carousel-control-prev"
                        type="button"
                        data-bs-target="#carouselExampleAutoplaying"
                        data-bs-slide="prev"
                    >
                        <span
                            class="carousel-control-prev-icon"
                            aria-hidden="true"
                        ></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button
                        class="carousel-control-next"
                        type="button"
                        data-bs-target="#carouselExampleAutoplaying"
                        data-bs-slide="next"
                    >
                        <span
                            class="carousel-control-next-icon"
                            aria-hidden="true"
                        ></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
            <div class="col-lg-1"></div>
            <div class="col-lg-4 col-md-12">
                <div class="card">
                    <img
                        src="../../src/assets/img/town.jpg"
                        class="card-img-top"
                        alt="town"
                    />
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">
                            Some quick example text to build on the card title
                            and make up the bulk of the card's content.
                        </p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>
        </div> -->
    </div>
</template>

<style scoped>
.input-group > .form-control {
    width: 700px;
    height: 50px;
    flex: none;
}

.home-container {
    width: 100%;
    height: 85vh;
    background-image: url(../../src/assets/img/happyhouse.gif);
    background-size: cover;
    background-repeat: no-repeat;
}
</style>
