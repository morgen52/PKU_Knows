<!--登录页面-->
<template>
	<v-app 
	id="login"
	:style="note"
	dark>
		<v-main>
			<v-container fill-height>
				<v-row justify="center" align="center">
					<v-col cols="12" sm="4" md="3" align="center" dark>
						<!--最上面的logo-->
						<v-img alt="北大知道LOGO" 
						:src="require('../assets/images/myLogo.png')" 
						class="logos"
						contain dark width="278px" 
						height="58px" >
						</v-img>
						<!--标语-->
						<h2 class="slogans">
							连接园子里的已知与未知
						</h2>
						<!--手机号、密码的输入-->
						<v-text-field v-model="title" :rules="[rules1.required,rules1.counter]" counter="11" name="user" filled label=" 请输入手机号"
						prepend-icon="mdi-account" clearable dark></v-text-field>

						<v-text-field :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'" :rules="[rules2.required,rules2.min]" :type="show ? 'text': 'password'"
						v-model="passward" name="passward" filled label="请输入密码" prepend-icon="mdi-key" counter @click:append="show = !show"
						dark></v-text-field>
						<!--没有账号、注册的显示和路由-->
						<p class="white--text" style="margin-bottom: 40px;">
							<router-link to="/reset" class="white--text">忘记密码</router-link>
							&nbsp;|&nbsp;没有账号？<router-link to="/signup" class="white--text">注册</router-link>
						</p>
						<!--最下方的登录按钮-->
						<v-btn class="ma-2" :loading="loading2" :disabled="loading2" color="info" width="150px" @click="loader='loading2'"
						dark to="/homepage">
							登录
							<template v-slot:loader>
								<span class="custom-loader">
									<v-icon light>mdi-cached</v-icon>
								</span>
							</template>
						</v-btn>
						<!--目前没有加入微信跳转功能-->
						<p class="white--text" style="margin-top: 20px;">
							————其他登录方式————
						</p>
							<v-btn icon><v-icon color="green">mdi-wechat</v-icon></v-btn>
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
