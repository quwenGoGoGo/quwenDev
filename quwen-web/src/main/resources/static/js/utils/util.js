function postRequest(url, data={}){
    return new Promise(function (resolve, reject) {
        $.ajax(url, {
            data: JSON.stringify(data),
            method: "post",
            dataType:"json",
            contentType:'application/json;charset=UTF-8',
            success: function (res) {
                if (res.errno == 0){
                    resolve(res);
                }else{
                    reject(res);
                }
            },
            fail: function (err) {
                reject(err)
            }

        })
    })
}
function getRequest(url){
    return new Promise(function (resolve, reject) {
        $.ajax(url, {
            method: "get",
            dataType:"json",
            success: function (res) {
                if (res.errno == 0){
                    resolve(res);
                }else{
                    reject(res);
                }
            },
            fail: function (err) {
                reject(err)
            }

        })
    })
}
export{
    postRequest,
    getRequest
}


