export const sendAjaxRequest = (url, method, data, successCallback, errorCallback) => {
    $.ajax({
        url: url,
        method: method,
        data: data,

        success: function(response) {
            if (successCallback) {
                successCallback(response);
            }
        },
        error: function(xhr, status, error) {
            if (errorCallback) {
                errorCallback(xhr, status, error);
            }
        }
    });
}