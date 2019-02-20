import * as api from '../api/api.js'
import * as util from '../utils/util.js'

$(function () {
    $("#login").click(
        function(){
            var username = $(".login-form input[name= 'username']").val();
            var password = $(".login-form input[name = 'password']").val();
            util.request(api.login, {"username":username, "password":password},'post').then((res)=>{
                console.log(res);
                window.location.href = api.home;
            }).catch((res)=>{
                console.log(res);
            })
        }
    );
    $("#logout").click(
        function () {
            util.request(api.logout).then((res)=>{
                console.log(res);
                window.location.reload();
            }).catch((res)=>{
                console.log(res);
            })
        }
    )
})