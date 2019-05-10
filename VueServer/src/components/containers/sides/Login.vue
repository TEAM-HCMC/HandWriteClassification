<style lang="css" scoped>



</style>

<template lang="html">

<div class="login">
    <div class="login_form">
        <div class="input_id">
            ID :
            <input type="text" placeholder="email을 입력하세요." v-model="ACCOUNT_EMAIL">

        </div>
        <div class="input_password">
            PW :
            <input type="password" v-model="ACCOUNT_PW">
        </div>
        <button v-on:click="login">로그인</button>
    </div>
</div>

</template>

<script>

const axios = require('axios');
const baseUrl = require('../../../config/testUrl.js');
const cookieUtils = require('../../../utils/cookie.js');

export default {
    data() {
            return {
                ACCOUNT_EMAIL: '',
                ACCOUNT_PW: ''
            }
        },

        methods: {
            login() {
                let reqDto = {
                    'email': this.ACCOUNT_EMAIL,
                    'password': this.ACCOUNT_PW
                }

                let config = {
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }

                axios.post(baseUrl + '/account/login', reqDto, config)
                    .then((res) => {

                        cookieUtils.setCookie('jwt',res.data.jwt);
                        let getHeader = {
                            headers: {
                                'Content-Type': 'application/json',
                                'jwt': res.data.jwt,
                            }
                        }
                        axios.get(baseUrl + '/account', getHeader)
                            .then((res) => {
                                console.log(res);
                            })
                            .catch((error) => {
                                console.log(error);
                            });

                    })
                    .catch((error) => {
                        console.log(error);
                    });
            }
        }
}

</script>
