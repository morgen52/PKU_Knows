<template>
    <v-app 
    id="sso"
    :style="note"
    dark>
        <v-main>
            <v-container fill-height>
                <v-row justify="center" align="center">
                    <v-col cols="12" sm="4" md="4" align="center" dark>
                        <!--最上面的logo-->
                        <v-img alt="北大知道LOGO" 
                        :src="require('../assets/images/myLogo.png')"
                        class="logos"
                        contain dark width="278px" 
                        height="58px" >
                        </v-img>
                        <!--标语-->
                        <h2 class="slogans">
                            连接园子里的未知与已知
                        </h2>
                        
                        <v-btn class="ma-2" color="info" width="150px" @click="signIn"
                        dark>
                            登录
                        </v-btn>
                    </v-col>
                </v-row>
            </v-container>
        </v-main>
    </v-app>
</template>

<script>
    export default {
        name:'Sso',
        data() {
			return {
				tab: null,
				note: {//加入背景图片
					backgroundImage: "url(" + require("../assets/images/background.jpg") + ")",
					backgroundRepeat: "no-repeat",
					backgroundSize:"100% 100%",
				},
			}
		},
        methods:{
			signIn(){
				var token = window.location.href.slice(36,);
				console.log(window.location.href);
				console.log(token);
				this.$store.commit('setToken',token);
				this.$router.push('/homepage');
			},
        }
    }
</script>

<style>
    .v-application a {
        color: #ffffff;
    }
    .logos{
        margin-top: 0px;
        margin-bottom: 10px;
    }
    .slogans {
        width: 154px;
        height: 20px;
        color: rgba(255, 255, 255, 100);
        font-size: 14px;
        text-align: left;
        font-family: PingFangSC-regular;
        margin-top: 0px;
        margin-bottom: 50px;
    }

    .custom-loader {
        animation: loader 1s infinite;
        display: flex;
    }

    @-moz-keyframes loader {
        from {
            transform: rotate(0);
        }

        to {
            transform: rotate(360deg);
        }
    }

    @-webkit-keyframes loader {
        from {
            transform: rotate(0);
        }

        to {
            transform: rotate(360deg);
        }
    }

    @-o-keyframes loader {
        from {
            transform: rotate(0);
        }

        to {
            transform: rotate(360deg);
        }
    }

    @keyframes loader {
        from {
            transform: rotate(0);
        }

        to {
            transform: rotate(360deg);
        }
    }
</style>
