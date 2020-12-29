<!--设置密码页面-->
<template>
	<v-app
	id="set"
	>
		<v-app-bar 
		app 
		color="rgba(239, 243, 245, 100)"
		class="addPadding"
		flat
		style="z-index: 0;">
			<v-toolbar-side-icon>
				<v-btn icon to="/cog"><v-icon large>mdi-chevron-left</v-icon></v-btn>
			</v-toolbar-side-icon>
			<v-toolbar-title class="texts">
				设置密码
			</v-toolbar-title>
			<v-spacer></v-spacer>
		</v-app-bar>
		<v-main class="Main">
			<v-card style="z-index: 0;">
				<v-form  ref="form" v-model="valid">
					<v-row cols="12" sm="6" justify="center" align="center" class="rows">
						<v-col cols="3" sm="2" class="header">
							<v-header>新密码:</v-header>
						</v-col>
						<v-col cols="8" sm="6" class="inputs">
							<v-text-field 
							v-model="pass1"
							:append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
							:rules="rules2" 
							:type="show1 ? 'text': 'password'"
							counter 
                            @click:append="show1 = !show1"
							required
							dense
							></v-text-field>
						</v-col>
					</v-row>
                    <v-divider></v-divider>
                    <v-row cols="12" sm="6" justify="center" align="center" class="rows">
                        <v-col cols="3" sm="2" class="header">
                            <v-header>确认密码:</v-header>
                        </v-col>
                        <v-col cols="8" sm="6" class="inputs">
                            <v-text-field 
                            v-model="pass2"
                            :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                            :rules="[affirm]" 
                            :type="show2 ? 'text': 'password'"
                            counter 
                            @click:append="show2 = !show2"
                            required
                            dense
                            ></v-text-field>
                        </v-col>
                    </v-row>
					<v-divider></v-divider>
				</v-form>
			</v-card>
            <v-row justify="center" align="center" class="bottons">
                <v-btn
                @click="validate"
                :disabled="!valid"
                class="ma-2"
                color="info"
                to="/user"
                >确认</v-btn>
            </v-row>
		</v-main>
	</v-app>
</template>

<script>
	export default{
		name:'Set',
		data(){
			return{
				show1: false,
                show2: false,
				rules2: [
					v => !!v || '密码必填',
					m => m.length >= 8 || '新密码至少8位',
				],
                valid:false,
				pass1:"",
                pass2:"",
			}
		},
		methods:{
			validate(){
				this.$refs.form.validate();  
                },
            affirm(val){
                if(val!=this.pass1){
                    return "两次密码不一致";
                    }
                return true;
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
	.bottons{
		padding-top: 60px;
	}
</style>
