<script setup>
import axios from "axios";

const getData = async () => {
  const url =
    "/data/B554287/DisabledPersonConvenientFacility/getDisConvFaclList";
  const params = {
    serviceKey: import.meta.env.VITE_DISABLED_FACILITIES_SERVICE_KEY,
    pageNo: "1",
    numOfRows: "20",
    faclNm: "서산제일교회",
    siDoNm: "",
    cggNm: "",
    roadNm: "남부순환로 68",
    faclTyCd: "UC0H03",
  };
  try {
    const response = await axios.get(url, { params });
    console.log(response);
  } catch (error) {
    console.log(error);
  }
};

getData();
/* Javascript 샘플 코드 */

// var xhr = new XMLHttpRequest();
// var url =
//   "/data/B554287/DisabledPersonConvenientFacility/getDisConvFaclList"; /*URL*/
// var queryParams =
//   "?" +
//   encodeURIComponent("serviceKey") +
//   "=" +
//   import.meta.env.VITE_DISABLED_FACILITIES_SERVICE_KEY; /*Service Key*/
// queryParams +=
//   "&" + encodeURIComponent("pageNo") + "=" + encodeURIComponent(""); /**/
// queryParams +=
//   "&" + encodeURIComponent("numOfRows") + "=" + encodeURIComponent(""); /**/
// queryParams +=
//   "&" +
//   encodeURIComponent("faclNm") +
//   "=" +
//   encodeURIComponent("서산제일교회"); /**/
// queryParams +=
//   "&" + encodeURIComponent("siDoNm") + "=" + encodeURIComponent(""); /**/
// queryParams +=
//   "&" + encodeURIComponent("cggNm") + "=" + encodeURIComponent(""); /**/
// queryParams +=
//   "&" +
//   encodeURIComponent("roadNm") +
//   "=" +
//   encodeURIComponent("남부순환로 68"); /**/
// queryParams +=
//   "&" +
//   encodeURIComponent("faclTyCd") +
//   "=" +
//   encodeURIComponent("UC0H03"); /**/
// xhr.open("GET", url + queryParams);
// xhr.onreadystatechange = function () {
//   if (this.readyState == 4) {
//     alert(
//       "Status: " +
//         this.status +
//         "nHeaders: " +
//         JSON.stringify(this.getAllResponseHeaders()) +
//         "nBody: " +
//         this.responseText
//     );
//   }
// };

// xhr.send("");
// console.log(xhr);
</script>

<template></template>

<style scoped></style>
