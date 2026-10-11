<script setup>
import { ref } from "vue";

//selectOption prop 선언, 부모구성요소에서 > 자식 구성요소로 데이터 전달
defineProps({ selectOption: Array }); //selectOption 받기
//컴포넌트에서 발생시킬 수 있는 이벤트 선언
const emit = defineEmits(["onKeySelect"]);

const key = ref("");

//선택이 변경될 때 호출, 선택된 값과 onKeySelect이벤트 발생
//해당 이벤트는 부모 이벤트에게 전달되며, 부모는 이를 수신, 처리 가능
const onSelect = () => {
    emit("onKeySelect", key.value);
};
</script>

<template>
    <select
        v-model="key"
        class="form-select form-select-sm w-50"
        @change="onSelect"
    >
        <!-- 비어있는 경우 disabled true, 선택을 불가능하게. -->
        <option
            v-for="option in selectOption"
            :key="option.value"
            :value="option.value"
            :disabled="option.value === '' ? true : false"
        >
            {{ option.text }}
        </option>
    </select>
</template>

<style scoped></style>
