<!--反馈页面-->
<template>
	<v-app
	id="cog"
	>
		<v-app-bar
		app
		color="white"
		elevate-on-scroll
        >
            <v-btn icon color="blue-grey darken-4" x-large to="/user">
                <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
		</v-app-bar>
		<v-main style="margin-top: 0px;">
            <!--修改密码，跳转到设置密码页面-->
            <v-banner
            single-line>
                修改密码
                <template v-slot:actions>
                    <v-btn
                    icon
                    to="/set">
                        <v-icon large>mdi-chevron-right</v-icon>
                    </v-btn>
                </template>
            </v-banner>
            <!--意见反馈，弹出对话框-->
            <v-dialog v-model="dialog">
                <template v-slot:activator="{ on, attrs }">
                    <v-banner
                    single-line
                    
                    >
                        意见反馈
                        <template v-slot:actions>
                            <v-btn
                            icon
                            v-bind="attrs"
                            v-on="on">
                                <v-icon large>mdi-chevron-right</v-icon>
                            </v-btn>
                        </template>
                    </v-banner>
                </template>
                <v-card text flat>
                    <v-toolbar dark color="white" elevation="1">
                        <v-btn color="black" icon dark @click="dialog = false">
                            收起
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-toolbar-items>
                            <v-btn color="primary" dark text @click="dialog = false">
                                提交
                            </v-btn>
                        </v-toolbar-items>
                    </v-toolbar>
                    <v-list one-line flat text>
                        <v-list-item flat text>
                            <v-list-item-content>
                                <v-textarea
                                    label="在此处撰写反馈"
                                    hide-details
                                    background-color="white"
                                    flat
                                    filled
                                    height="360px"
                                    no-resize=True
                                    auto-force
                                    text
                                >
                                </v-textarea>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list>
                  </v-card>
            </v-dialog>
			<!--关于北大知道，暂时是我编的-->
            <v-dialog v-model="dialog1">
                <template v-slot:activator="{ on, attrs }">
                    <v-banner
                    single-line
                    >
                        关于北大知道
                        <template v-slot:actions>
                            <v-btn
                            icon
                            v-bind="attrs"
                            v-on="on">
                                <v-icon large>mdi-chevron-right</v-icon>
                            </v-btn>
                        </template>
                    </v-banner>
                </template>
                <v-card text flat>
                    <v-toolbar dark color="white" elevation="1">
                        <v-btn color="black" icon dark @click="dialog1 = false">
                            收起
                        </v-btn>
                        <v-spacer></v-spacer>
                    </v-toolbar>
                    <v-list one-line flat text>
                        <v-list-item flat text>
                            <v-list-item-content>
                                北大知道是2020年秋季软件工程课程项目，是一个以相对明确的问答形式为主的平台，旨在为北大校内各方提供一个快速获取有效信息的平台，尽可能解决信息不对称问题。
                            </v-list-item-content>
                        </v-list-item>
                    </v-list>
                  </v-card>
            </v-dialog>
            
			<v-row justify="center" align="center" style="margin-top: 40px;">
				<v-btn
				@click="exit"
				text
				color="red"
				to="/"
				>退出当前账号</v-btn>
			</v-row>
		</v-main>
	</v-app>
</template>

<script>
	export default{
		name:'Cog',
        data(){
            return{
                dialog:false,
                dialog1:false,
            }
        },
		methods:{
			exit(){
				this.$store.commit('setUser',{
									dept:"",
									gender:"",
									major:"",
									name:"",
									stuT:"",
									userName:"",
									usrT:"",
									enroll:""
								});
				this.$store.commit('setShowInfo',{
										gender:"",
										dept:"",
										enroll:"",
										motto:"",
										userName:""
									});
				this.$store.commit('setToken',"");
				this.$store.commit('setAlready');
			}
		}
	}
</script>

<style>
</style>
