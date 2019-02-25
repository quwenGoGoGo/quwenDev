function request(url, data={}, method){
    return new Promise(function (resolve, reject) {
        $.ajax(url, {
            data: JSON.stringify(data),
            method: method,
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
export{
    request
}


