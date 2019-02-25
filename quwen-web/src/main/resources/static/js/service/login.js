import * as api from '../api/api.js'
import * as util from '../utils/util.js'

$(function () {
    $("#login").click(
        function(){
            var username = $(".login-form input[name= 'username']").val();
            var password = $(".login-form input[name = 'password']").val();
            util.postRequest(api.login, {"username":username, "password":password}).then((res)=>{
                console.log(res);
                window.location.href = api.home;
            }).catch((res)=>{
                console.log(res);
            })
        }
    );
    $("#logout").click(
        function () {
            util.getRequest(api.logout).then((res)=>{
                console.log(res);
                window.location.reload();
            }).catch((res)=>{
                console.log(res);
            })
        }
    )
})