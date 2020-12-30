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
					<v-row cols="12" sm="6" justify="center" align="center" class="rows">
						<v-col cols="3" sm="2" class="header">
							<v-header>密码:</v-header>
						</v-col>
						<v-col cols="8" sm="6" class="inputs">
							<v-text-field 
							v-model="pass"
							:append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
							:rules="rules2" 
							:type="show ? 'text': 'password'"
							counter 
                            @click:append="show = !show"
							required
							dense
							></v-text-field>
						</v-col>
					</v-row>
					<v-divider></v-divider>
					
					
				</v-form>
			</v-card>
			<v-form ref="form" v-model="valid2">
				<v-checkbox style="marginLeft:20px"
				:rules="[v => !!v || '请阅读北大知道用户协议']"
				color="blue"
				required
				label="我已阅读北大知道用户协议"
				></v-checkbox>
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
				show: false,
				rules2: [
					v => !!v || '密码必填',
					m => m.length >= 8 || '密码至少8位',
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
			},
			validate(){
				this.$refs.form.validate()
			},
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
		margin-top: 0px;
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
