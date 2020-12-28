<!--登录页面-->
<template>
    <v-app 
    id="login"
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
                        
                        <v-tabs
                          v-model="tab"
                          background-color="transparent"
                          color="white"
                          centered
                          style="margin-bottom: 10px;"
                        >
                          <v-tab style="color: #FFFFFF;">
                            密码登录
                          </v-tab>
                          <v-tab style="color: #FFFFFF;">
                            手机验证码
                          </v-tab>
                        </v-tabs>
                        <v-tabs-items v-model="tab" style="background-color: transparent;">
                        <!--密码登录-->
                        <v-tab-item>
                        <!--手机号、密码的输入-->
                        <v-text-field v-model="title" :rules="[rules1.required,rules1.counter]" counter="11" name="user" label=" 请输入手机号"
                        prepend-icon="mdi-account" clearable dark></v-text-field>
                        
                        <v-text-field :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'" :rules="[rules2.required,rules2.min]" :type="show ? 'text': 'password'"
                        v-model="passward" name="passward" label="请输入密码" prepend-icon="mdi-key" @click:append="show = !show"
                        dark></v-text-field>
                        </v-tab-item>
                        <!--手机验证码登录-->
                        <v-tab-item>
                        <!--手机号、验证码的输入-->
                        <v-text-field v-model="title" :rules="[rules1.required,rules1.counter]" counter="11" name="user" label=" 请输入手机号"
                        prepend-icon="mdi-account" clearable dark></v-text-field>
                        <v-row>
                            <v-col cols="8" style="padding-right: 0px;">
                                <v-text-field 
                                label="请输入验证码"
								v-model="code"
                                counter="6" 
                                prepend-icon="mdi-key"
                                dark
                                ></v-text-field>
                            </v-col>
                            <v-col cols="3" style="padding-left: 0px;padding-top: 20px;">
                                <v-btn class="ma-2" color="info"  height="30px" width="100px" :disabled="disable" :class="{get:isGet}" @click="getVCode" dark>
                                    {{getC}}
                                    <template v-slot:loader>
                                        <span class="custom-loader">
                                            <v-icon light>mdi-cached</v-icon>
                                        </span>
                                    </template>
                                </v-btn>
                            </v-col>
                        </v-row>
                        <!--
                        <v-text-field label="请输入验证码" prepend-icon="mdi-key" counter=6 dark></v-text-field>
                        -->
                        </v-tab-item>
                        </v-tabs-items>
                        
                        <!--没有账号、注册的显示和路由-->
                        <p class="white--text" style="margin-bottom: 40px; margin-top: 10px;">
                            <router-link to="/reset" class="white--text">忘记密码</router-link>
                            &nbsp;|&nbsp;没有账号？<router-link to="/signup" class="white--text">注册</router-link>
                        </p>
                        <!--最下方的登录按钮-->
                        <v-btn class="ma-2" :loading="loading2" :disabled="loading2" color="info" width="150px" @click="signIn"
                        dark>
                            登录
                            <template v-slot:loader>
                                <span class="custom-loader">
                                    <v-icon light>mdi-cached</v-icon>
                                </span>
                            </template>
                        </v-btn>
                    </v-col>
                </v-row>
            </v-container>

        </v-main>
    </v-app>
</template>

<script>
    export default {
        name:'Login',
        props:{
            source:String,
        },
        data() {
            return {
                tab: null,
                note: {//加入背景图片
                    backgroundImage: "url(" + require("../assets/images/background.jpg") + ")",
                    backgroundRepeat: "no-repeat",
                    backgroundSize: "100% 100%",
                },
                rules1: {
                    require: value => !!value || '必填',
                    counter: value => value.length === 11 || "位数不正确"
                },
                show: false,
                rules2: {
                    required: value => !!value || '必填',
                    min: m => m.length >= 8 || '密码至少8位',
                    userMatch: () => ('用户不存在或是密码错误！')
                },
                loader: null,
                loading: false,
                loading2: false,
                disable:false,
                getC:'获取验证码',
                isGet:false,
                count:60,
            }
        },
        watch: {
            loader() {
                const l = this.loader
                this[l] = !this[l]
                setTimeout(() => (this[l] = false), 3000)
                this.loader = null
            },
        },
        methods:{
			signIn(){
				this.loader = this.loading2;
				var data = {
					'code':this.code,
					'phone':this.title
				}
				var _self = this;
				this.$axios.post('https://auth.pkucs.cn/api/sms/token',this.$qs.stringify(data)
				).then(function(response){
					console.log(response);
					if(response.data.code == 0){
						_self.token = response.data.data;
						_self.$router.push("/homepage");
					}
					else{
						alert("登录失败");
					}
				}).catch(function(error){
					console.log(error);
				})
			},
            getVCode(){
                var countDown=setInterval(()=>{
                    if(this.count<1){
                        this.isGet=false;
                        this.disable=false;
                        this.getC='获取验证码';
                        this.count=60;
                        clearInterval(countDown);
                    }
                    else{
                        this.isGet=true;
                        this.disable=true;
                        this.getC=this.count-- + '秒';
                    }
                },1000);
				var _self = this;
				var data = {'phone':this.title};
				this.$axios.post('https://auth.pkucs.cn/api/sms/code',this.$qs.stringify(data)
				).then(function (response) {
					console.log(response);
					if(response.data.code == 404){
						_self.count = 0;
						alert("该手机号尚未绑定北大学号！");
					}
					if(response.data.code == 400){
						_self.count = 0;
						alert("一小时内该手机号获取验证码的次数已达上限！");
					}
					// console.log(response)
				}).catch(function(err){
					_self.count = 0;
					console.log(err);
				})
            }
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
