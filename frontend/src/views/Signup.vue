<!--注册页面-->
<template>
	<v-app
	id="signup"
	>
		<v-app-bar 
		app 
		color="rgba(239, 243, 245, 100)"
		flat
		class="addPadding"
		style="z-index: 0;">
			<v-toolbar-side-icon>
				<v-btn icon to="/"><v-icon large>mdi-chevron-left</v-icon></v-btn>
			</v-toolbar-side-icon>
			<v-toolbar-title class="texts">
				注册
			</v-toolbar-title>
			<v-spacer></v-spacer>
		</v-app-bar>
		<v-main class="Main">
			<v-card style="z-index: 0;">
				<v-form  ref="form" v-model="valid1">
					<v-row cols="12" sm="6" justify="center" align="center" class="rows">
						<v-col cols="3" sm="2" class="header">
							<v-header>手机号:</v-header>
						</v-col>
						<v-col cols="8" sm="6" class="inputs">
							<v-text-field 
							v-model="phone"
							:rules="rules1" 
							:counter="11" 
							dense
							required
                            placeholder="使用北大门户绑定手机号注册"
							></v-text-field>
						</v-col>
					</v-row>
					<v-divider></v-divider>
					<v-row cols="12" sm="6" justify="center" text-align="center" class="rows-btn">
						<v-col cols="3" sm="2" class="header">
							<v-header>验证码:</v-header>
						</v-col>
						<v-col cols="4" sm="3" class="inputs">
							<v-text-field 
							v-model="code"
                            :rules="rules3"
							:counter="6" 
							dense
							required
							></v-text-field>
						</v-col>
						<v-col cols="4" sm="3">
							<v-btn class="ma-2" color="info"  height="30px" width="100px" :disabled="disable" :class="{get:isGet}" @click="getVCode">
								{{getC}}
								<template v-slot:loader>
									<span class="custom-loader">
										<v-icon light>mdi-cached</v-icon>
									</span>
								</template>
							</v-btn>
						</v-col>
					</v-row>
					<v-divider></v-divider>
					
					
				</v-form>
			</v-card>
			<v-form ref="form" v-model="valid2">
				<v-checkbox style="marginLeft:20px"
				:rules="[affirm]"
				color="blue"
				required
				>
                    <template v-slot:label>
                        <div class="blue--text" style="font-size: 15px;">
                            <v-tooltip bottom>
                                <template v-slot:activator="{on}">
                                    <v-dialog v-model="dialog" v-on="on">
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn
                                            text
                                            class="blue--text"
                                            v-bind="attrs"
                                            v-on="on"
                                            style="padding: 0px;margin: 0px;font-size: 15px;text-decoration: underline;"
                                            @click="read=true">
                                            我已阅读北大知道用户协议
                                            </v-btn>
                                        </template>
                                        <v-card text flat>
                                            <v-toolbar dark color="white" elevation="1">
                                                <v-btn color="black" icon dark @click="dialog = false">
                                                    收起
                                                </v-btn>
                                                <v-spacer></v-spacer>
                                            </v-toolbar>
                                            <v-list one-line flat text>
                                                <v-list-item flat text>
                                                    <v-list-item-content>
                                                        <p>1.账号的取得</p>
                                                        <p>
                                                            您注册成功后，即成为北大知道注册用户，用户须对在北大知道的注册信息的真实性、合法性、有效性承担全部责任。您可自行创建、修改用户名，但用户名命名及使用应遵守相关法律法规并符合网络道德，不得冒充他人或恶意注册使人误认；不得利用他人的名义发布任何信息；不得恶意使用注册帐号导致其他用户误认；用户名中不能含有任何侮辱、诽谤、淫秽或暴力等侵害他人合法权益或违反公序良俗的词语。如您违反前述规定，北大知道有权随时限制或拒绝您使用该账号，甚至注销该账号。 
                                                        </p>
                                                        <p>2.用户名的管理</p>
                                                        <p>
                                                            请勿注册和使用与其他同学相同、相仿的用户名；
                                                            请勿注册和使用不文明、不健康的用户名；
                                                            请勿注册和使用易产生歧义、引起他人误解或带有各种奇形怪状符号的用户名。
                                                            请您妥善保管您注册时填写的用户帐号和密码，不要将帐号密码告知他人，因用户原因导致帐号或密码泄露而造成的法律后果由用户负责。
                                                        </p>
                                                        <p>3.隐私保护</p>
                                                        <p>
                                                            北大知道将运用各种安全技术和程序建立完善的管理制度来保护您的个人信息，以免遭受未经授权的访问、使用或披露.
                                                            未经您的同意，北大知道不会向学校官方以外的任何公司、组织和个人披露您的个人信息，但法律法规另有规定的除外。
                                                        </p>
                                                        <p>4.言论负责</p>
                                                        <p>
                                                            北大知道是一个问答平台，请您在使用过程中对自己的言论负责，不要传播违法信息。
                                                        </p>
                                                    </v-list-item-content>
                                                </v-list-item>
                                            </v-list>
                                          </v-card>
                                    </v-dialog>
                                    
                                </template>
                            </v-tooltip>
                        </div>
                    </template>
                </v-checkbox>
			</v-form>
			<v-row justify="center" align="center" class="bottons">
				<v-btn
				@click="validate"
				:disabled="!(valid1&&valid2)"
				class="ma-2"
				color="info"
				to="/"
				>注册</v-btn>
			</v-row>
		</v-main>
	</v-app>
</template>

<script>
	export default{
		name:'Signup',
		props:{
			source:String,
		},
		data(){
			return{
				rules1: [
					value => !!value || '手机号必填',
					value => value.length === 11 || "位数不正确"
				],
                rules3:[
                    v=> !!v || '验证码必填',
                    m=> m.length==6 || '位数不正确'
                ],
				getC:'获取验证码',
				isGet:false,
				count:60,
				disable:false,
				checkbox:false,
                valid1:false,
                valid2:true,
				phone:"",
				code:"",
				pass:"",
                dialog:false,
                read:false,
			}
		},
		methods:{
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
				var data = {'phone':this.phone};
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
			},
			validate(){
				this.$refs.form.validate()
			},
            affirm(val){
                if(!val||!this.read){
                    return "请阅读北大知道用户协议！";
                }
                return true;
            }
		}
	}
</script>

<style>
	.texts{
		color: rgba(0, 0, 0, 1);
		font-size: 20px;
		font-family: Arial;
	}
	.v-card.v-sheet.theme--light{
		border-radius: 0px;
	}
	.addPadding{
		padding-bottom: 120px;
		border-width: 0px;
	}
	.Main{
		margin-top: 30px;
		padding-top: 20px;
		background-color: #EFF3F5;
	}
	.get{
		background: #cdcdcd;
		border-color:#cdcdcd;
		font-size: 14px;
	}
	.rows{
		padding-bottom: 30px;
		padding-top:0px;
		height:60px;
	}
	.rows-btn{
		padding-bottom:0px;
		padding-top:0px;
		height:60px;
	}
	.header{
        padding-top: 20px;
		width: 42px;
		height: 30px;
		font-size:14px;
		color: rgba(16, 16, 16, 100);
		font-family: PingFangSC-regular;
	}
	.inputs{
		width: 267px;
		height: 30px;
		color: rgba(149, 149, 149, 100);
		font-size: 14px;
		text-align: left;
		font-family: Arial;
		border: 1px solid rgba(255, 255, 255, 100);
	}
	.v-label.theme--light{
		color:rgba(0, 132, 255, 100);
		font-size: 14px;
		font-family: PingFangSC-regular;
	}
	.bottons{
		padding-top: 40px;
	}
</style>
