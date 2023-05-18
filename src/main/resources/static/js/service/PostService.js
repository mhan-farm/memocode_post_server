import {sendAjaxRequest} from "../ajax/ajax-settings";

const getAll = () => {
    return sendAjaxRequest(
        "/ajax/posts",
        "POST",
            null,
        (response) => {
            console.log("성공");
        },
        (xhr, status, error) => {
            console.log("실패");
        }
    )
}

const PostService = {
    getAll
}

export default PostService;