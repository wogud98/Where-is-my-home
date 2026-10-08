import axios from "axios";

function listArticle(param, success, fail) {
    axios.get(`/api/board`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
    axios.get(`/api/board/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
    console.log("boardjs article", article);
    axios
        .post(`/api/board`, JSON.stringify(article), {
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then(success)
        .catch(fail);
}

function getModifyArticle(articleno, success, fail) {
    axios.get(`/api/board/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
    axios
        .put(`/api/board`, JSON.stringify(article), {
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then(success)
        .catch(fail);
}

function deleteArticle(articleno, success, fail) {
    axios.delete(`/api/board/${articleno}`).then(success).catch(fail);
}

export {
    listArticle,
    detailArticle,
    registArticle,
    getModifyArticle,
    modifyArticle,
    deleteArticle,
};
