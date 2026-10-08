import axios from "axios";

function listHouse(mapParams, success, fail) {
    axios.get("/api/house", { params: mapParams }).then(success).catch(fail);
}

function listKeyword(word, success, fail) {
    axios.get(`/api/house/area?word=${word.value}`).then(success).catch(fail);
}

export { listHouse, listKeyword };
