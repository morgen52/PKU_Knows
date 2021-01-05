<template>
  <v-app id="inspire">
    <v-app-bar
      app
      color="white"
      elevate-on-scroll
    >
        <v-btn icon @click="getUserInfo">
            <v-icon color="blue-grey darken-4" size="34px">mdi-account-outline</v-icon>
        </v-btn>
        <v-text-field
            dense
            flat
            hide-details
            color="black"
            background-color="grey lighten-5"
            rounded
            solo
            v-model="searchtext"
            clearable
            label="请输入搜索内容"
        >
        </v-text-field>
        <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
            <template v-slot:activator="{ on, attrs }">
                <v-btn
                icon
                v-bind="attrs"
                v-on="on"
                >
                    <v-icon color="blue-grey darken-4" size="28px">mdi-text-box-plus-outline</v-icon>
                </v-btn>
                
            </template>
                <v-card text flat>
                    <v-toolbar dark color="white" elevation="1">
                        <v-btn color="black" icon dark @click="dialog = false">
                            取消
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-toolbar-items>
                            <v-btn color="primary" dark text @click="postQuestion">
                                发布问题
                            </v-btn>
                        </v-toolbar-items>
                    </v-toolbar>
                    <v-list four-line flat text>
                        <v-list-item flat text>
                            <v-list-item-content>
                                <v-text-field
                                    label="问题：尽量简洁明晰(必填)"
                                    :rules="rules"
                                    hide-details
                                    background-color="white"
                                    v-model="questext"
                                    flat
                                    text
                                >
                                </v-text-field>
                            </v-list-item-content>
                        </v-list-item>
                        <v-list-item flat text>
                            <v-list-item-content>
                                <v-textarea
                                    label="补充：说明(选填)"
                                    hide-details
                                    background-color="white"
                                    v-model="detailtext"
                                    flat
                                    filled
                                    auto-grow
                                    auto-force
                                    counter
                                    text
                                >
                                </v-textarea>
                            </v-list-item-content>
                        </v-list-item>
                                    <v-select
                                      style="margin-top:20px; width:80px; margin-left:20px"
                                      :items="itemsx"
									  v-model="topic"
                                      label="话题"      
                                    ></v-select>
                        <v-list-item flat text>
                            <v-list-item-content>
                                <v-textarea
                                    label="添加标签,以空格隔开"
                                    hide-details
                                    rows=1
                                    background-color="white"
                                    v-model="labeltext"
                                    flat
                                    filled
                                    auto-grow
                                    auto-force
                                    text
                                >
                                </v-textarea>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list>
                  </v-card>
        </v-dialog>
    </v-app-bar>

    <v-main style="padding: 0px;">
<!--        <v-btn
            fab
            fixed
            right
            class="mx-auto"
            elevation=1
            icon
            @click="toptop"
        >
            <v-icon>mdi-chevron-double-up</v-icon>
        </v-btn>-->
        <v-card color="grey lighten-5" flat>
            <v-tabs
                show-arrows
                centered
				v-model = 'tabNum'
            >
                <v-tabs-slider></v-tabs-slider>
            
                <v-tab>关注</v-tab>
                <v-tab>收藏</v-tab>
                <v-tab>实时</v-tab>

                <v-card flat text width="137">
                    <v-select
                        :items="items0"
                        v-model="select0"
                        solo
                        text
                        flat
                        style="font-size: 14px;"
                    >
                    </v-select>
                </v-card>
                <v-card flat text width="137">
                    <v-select
                        :items="items1"
                        v-model="select1"
                        solo
                        single-line
                        text
                        flat
                        style="font-size: 14px;"
                    ></v-select>
                </v-card>                    
                <v-tab-item>
                    <v-container>
                        <v-row background-color="grey">
                            <v-col
                                v-for="(item, index) in items2"
                                :key="index"
                                @click="questionDetail(item)"
                                cols=12
                                fluid
                                md="12"
                            >
                                <v-card
                                    class="mx-auto"
                                    height="174"
                                >
                                    <template>
                                        <v-row style="margin-left: 10px; margin-right: 10px;">
                                                <v-list color="transparent">
                                                    <v-list-item>
                                                        <v-row>
                                                            <v-chip-grop
                                                                column
                                                                class="ma-2"
                                                                active-class="deep-purple accent-4 white--text"
                                                            >
                                                                <v-chip
                                                                    color="primary lighten-2"
                                                                >
                                                                    {{item.topic}}
                                                                </v-chip>
                                                            </v-chip-grop>
                                                            <v-chip-grop
                                                                v-for="(item, index) in item.tag"
                                                                :key="index"
                                                                column
                                                                class="ma-2"
                                                                active-class="deep-purple accent-4 white--text"
                                                            >
                                                                <v-chip
                                                                    color="primary lighten-3"
                                                                >
                                                                    {{item}}
                                                                </v-chip>
                                                            </v-chip-grop>
                                                        </v-row>
                                                    </v-list-item>
                                                    <v-list-item>
                                                        <v-list-item-content>
                                                            <v-list-item-title>{{item.title}}</v-list-item-title>
                                                            <v-list-item-subtitle>{{item.answer+'回答  '+item.subscribe+'关注  '+item.favorite + '收藏  '+item.createTime}}</v-list-item-subtitle>
                                                            <v-text style="margin-top:10px">{{item.info}}</v-text>
                                                            <v-text style="height: 20px;">{{item.txt}}</v-text>
                                                        </v-list-item-content>
                                                    </v-list-item>
                                                </v-list>
                                        </v-row>
                                    </template>
                                </v-card>
                            </v-col>
                        </v-row>
                        <v-row justify="center" align="center" style="text-align: center;" class="mx-auto">
                            <v-text center style="justify-content: center;font-size: 10px;">
                                已经没有关注的问题了
                            </v-text>
                        </v-row>
                    </v-container>
                </v-tab-item>
                  
                <v-tab-item>
                    <v-container>
                        <v-row background-color="grey">
                            <v-col
                                v-for="(item, index) in items3"
                                :key="index"
                                @click="questionDetail(item)"
                                cols=12
                                fluid
                                md="12"
								placeholder="没有收藏内容"
                            >
                                <v-card
                                    class="mx-auto"
                                    height="174"
                                >
                                    <v-row style="margin-left: 10px; margin-right: 10px;">
                                            <v-list color="transparent">
                                                <v-list-item>
                                                    <v-row>
                                                        <v-chip-grop
                                                            column
                                                            class="ma-2"
                                                            active-class="deep-purple accent-4 white--text"
                                                        >
                                                            <v-chip
                                                                color="primary lighten-2"
                                                            >
                                                                {{item.topic}}
                                                            </v-chip>
                                                        </v-chip-grop>
                                                        <v-chip-grop
                                                            v-for="(item, index) in item.tag"
                                                            :key="index"
                                                            column
                                                            class="ma-2"
                                                            active-class="deep-purple accent-4 white--text"
                                                        >
                                                            <v-chip
                                                                color="primary lighten-3"
                                                            >
                                                                {{item}}
                                                            </v-chip>
                                                        </v-chip-grop>
                                                    </v-row>
                                                </v-list-item>
                                                <v-list-item>
                                                    <v-list-item-content>
														<v-list-item-title>{{item.title}}</v-list-item-title>
														<v-list-item-subtitle>{{item.answer+'回答  '+item.subscribe+'关注  '+item.favorite + '收藏  '+item.createTime}}</v-list-item-subtitle>
														<v-text style="margin-top:10px">{{item.info}}</v-text>
														<v-text style="height: 20px;">{{item.txt}}</v-text>
                                                    </v-list-item-content>
                                                </v-list-item>
                                            </v-list>
                                    </v-row>
                                </v-card>
                            </v-col>
                        </v-row>
                        <v-row justify="center" align="center" style="text-align: center;" class="mx-auto">
                            <v-text center style="justify-content: center;font-size: 10px;">
                                已经没有收藏的问题了
                            </v-text>
                        </v-row>						
                    </v-container>
                </v-tab-item>
                
                <v-tab-item>
                    <v-container>
                        <v-row background-color="grey">
                            <v-col
                                v-for="(item, index) in items4"
                                :key="index"
                                @click="questionDetail(item)"
                                cols=12
                                fluid
                                md="12"
                            >
                                <v-card
                                    class="mx-auto"
                                    height="174"
                                >
                                    <v-row style="height: 172px;margin-left: 10px; margin-right: 10px;">
                                            <v-list color="transparent">
                                                <v-list-item>
                                                    <v-row>
                                                        <v-chip-grop
                                                            column
                                                            class="ma-2"
                                                            active-class="deep-purple accent-4 white--text"
                                                        >
                                                            <v-chip
                                                                color="primary lighten-2"
                                                            >
                                                                {{item.topic}}
                                                            </v-chip>
                                                        </v-chip-grop>
                                                        <v-chip-grop
                                                            v-for="(item, index) in item.tag"
                                                            :key="index"
                                                            column
                                                            class="ma-2"
                                                            active-class="deep-purple accent-4 white--text"
                                                        >
                                                            <v-chip
                                                                color="primary lighten-3"
                                                            >
                                                                {{item}}
                                                            </v-chip>
                                                        </v-chip-grop>
                                                    </v-row>
                                                </v-list-item>
                                                <v-list-item style="height: 60;">
                                                    <v-list-item-content>
                                                        <v-list-item-title>{{item.title}}</v-list-item-title>
                                                        <v-list-item-subtitle>{{item.answer+'回答  '+item.subscribe+'关注  '+item.favorite + '收藏  '+item.createTime}}</v-list-item-subtitle>
                      <v-text style="margin-top:10px">{{item.info}}</v-text>
                                                        <v-text style="height: 20px;">{{item.txt}}</v-text>
                                                    </v-list-item-content>
                                                </v-list-item>
                                            </v-list>
                                    </v-row>
                                </v-card>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-tab-item>
            </v-tabs>
        </v-card>
    </v-main>
    <v-bottom-navigation
        grow
        fixed
        text
        flat
        height="40"
    >
        <v-btn
            flat
            text
            icon
            color="grey"
            @click="getPreQuestionPage"
        >
            <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
        <v-text
            auto-grow
            class="mx-auto"
            style="color: grey; display: flex; align-items: center; justify-content: center;"
        >
            {{pagenow}}/{{pagesum}}
        </v-text>
        <v-btn
            text
            flat
            icon
            color="grey"
            @click="getNextQuestionPage"
        >
            <v-icon>mdi-chevron-right</v-icon>
        </v-btn>
    </v-bottom-navigation>
  </v-app>
</template>

<script>
    export default 
    {
        name:"Homepage",
		mounted:function(){
			this.getQuestionByPage();//需要触发的函数
			this.getFavorite();
			this.getSubscription();
			this.$forceUpdate();
			if(this.$store.state.times == 1){
				this.getLikedAnswer();
				this.getSubscribeList();
				this.getFavoriteList();
				this.getFavoriteAnsId();
				this.$store.commit('subTimes');
			}
		},
		watch:{
			tabNum(){
				if(this.tabNum <= 2){
					this.pagenow = this.$store.state.pagenow[this.tabNum];
					this.pagesum = this.$store.state.pagesum[this.tabNum];
					this.btrefresh();
				}
			}
		},
        methods: {
			getFavoriteAnsId(){
				var _self = this;
				var url = 'https://qa.pkucs.cn/api/qa/favorite/answer/ids';
				this.$axios.get(url,{
					headers:{
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					console.log(response);
					_self.$store.commit('setFavoriteAnsList',{favoriteAnsList:response.data.data});
				}).catch(function(error){
					console.log(error);
				})
			},
			getFavoriteList(){
				var _self = this;
				var url = 'https://qa.pkucs.cn/api/qa/favorite/question/ids';
				this.$axios.get(url,{
					headers:{
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					console.log(response);
					_self.$store.commit('setFavoriteList',{favoriteList:response.data.data});
				}).catch(function(error){
					console.log(error);
				})
			},
			getSubscribeList(){
				var _self = this;
				var url = 'https://qa.pkucs.cn/api/qa/subscription/ids';
				this.$axios.get(url,{
					headers:{
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					console.log(response);
					_self.$store.commit('setSubscribeList',{subscribeList:response.data.data});
				}).catch(function(error){
					console.log(error);
				})
			},
			getLikedAnswer(){
				var _self = this;
				var url = 'https://qa.pkucs.cn/api/qa/like/answer';
				this.$axios.get(url,{
					headers:{
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					//console.log(response);
					_self.$store.commit('setLikedAnswers',response.data.data);
				}).catch(function(error){
					console.log(error);
				})
			},
			questionDetail(item){
				console.log(item);
				this.$store.state.questionId = item._id;
				this.$router.push('/qa');
			},
			getUserInfo(){
				if(this.$store.state.getInfoAlready){
					this.$router.push('/user');
					return;
				}
				var _self = this;
				this.$axios.get('https://auth.pkucs.cn/api/auth/userinfo',{
					headers:{
						Authorization:this.$store.state.token
					}
				}).then(function(response){
					if(response.data.code == 0){
						console.log(response);
						var usrinfo = {
							avator:response.data.data.avatar,
							major:response.data.data.major,
							dept:response.data.data.dept,
							name:response.data.data.name,
							stuT:response.data.data.stuT,
							usrT:response.data.data.usrT,
							enroll:response.data.data.enroll,
							gender:response.data.data.gender
						}
						_self.$store.commit("setUser",usrinfo);
						_self.$store.commit("setAlready");
						if(response.data.data.userName == undefined)
							response.data.data.userName = response.data.data.name;
						_self.$store.commit('setShowInfo',{
							gender:response.data.data.gender,
							dept:response.data.data.dept,
							enroll:response.data.data.enroll,
							userName:response.data.data.userName,
							motto:response.data.data.motto});
						_self.$router.push('/user');
					}
					else{
						console.log(response);
					}
				}).catch(function(error){
					console.log(error);
				})
			},
            btrefresh() {
                document.body.scrollTop = 0;
                document.documentElement.scrollTop = 0;
                this.$forceUpdate();
            },
			getQuestionByPage(){
				if(this.pagenow < 1){
					return;
				}
				var _self = this;
				this.$axios.get('https://qa.pkucs.cn/api/qa/question',{
					headers:{
						'Authorization':this.$store.state.token
					},
					params:{
						size:20,
						page:this.pagenow - 1
					}
				}).then(function(response){
					console.log(response);
					if(response.data.code == 0){
						if(response.data.data.length != 0){
							var i = 0;
							var l = response.data.data.length;
							for (i = 0; i < l; i++){
								var unixTimestamp = new Date(response.data.data[i].createTime * 1000);
								var commonTime = unixTimestamp.toLocaleString();
								response.data.data[i].createTime = commonTime;
								if(response.data.data[i].user == undefined)
									response.data.data[i].info = '匿名用户';
								else{
									response.data.data[i].info = "";
									for(var key in response.data.data[i].user){
										if(key != 'userName' && key != 'dept' && key != 'motto')
											continue;
										var value = response.data.data[i].user[key];
										if(value != undefined){
											response.data.data[i].info += (value+' ');
										}
									}
								}							  
							}
							_self.items4 = response.data.data;
						}
						else{
							if(_self.pagenow >= 1){
								_self.$store.commit('subPageNow',{idx:_self.tabNum});
								_self.pagenow = _self.pagenow - 1;
							}
						}
					}
				}).catch(function(error){
					console.log(error);
				})
			},
			getNextQuestionPage(){
				var l = 0;
				if(this.tabNum == 0)
					l = this.items2.length;
				else if(this.tabNum == 1)
					l = this.items3.length;
				else if(this.tabNum == 2)
					l = this.items4.length;
				if(l == 20){
					this.pagenow = this.pagenow + 1;
					if(this.tabNum == 0)
						this.getSubscription();
					else if(this.tabNum == 1)
						this.getFavorite();
					else if(this.tabNum == 2)
						this.getQuestionByPage();
					this.$store.commit('addPageNow',{idx:this.tabNum});
					if(this.pagenow > this.pagesum) {
						this.$store.commit('addPageSum',{idx:this.tabNum});
						this.pagesum = this.pagesum + 1;
					}
					this.btrefresh();
				}
				else{
					if(this.tabNum == 0)
						this.getSubscription();
					else if(this.tabNum == 1)
						this.getFavorite();
					else if(this.tabNum == 2)
						this.getQuestionByPage();
					this.btrefresh();
				}
			},
			getPreQuestionPage(){
				if(this.pagenow > 1){
					this.pagenow = this.pagenow - 1;
					this.$store.commit('subPageNow',{idx:this.tabNum});
					if(this.tabNum == 0)
						this.getSubscription();
					else if(this.tabNum == 1)
						this.getFavorite();
					else if(this.tabNum == 2)
						this.getQuestionByPage();
					this.btrefresh();
				}
				else{
					alert("这已经是第一页了");
				}
			},
            toptop(){
                document.body.scrollTop = 0;
                document.documentElement.scrollTop = 0;
            },
			postQuestion(){
				this.dialog = false;
				var _self = this;
				var data = {
					'title':this.questext,
					'txt':this.detailtext,
					'tag':this.labeltext,
					'topic':this.topic,
					'setting':this.$store.state.setting,
					'subscribe':true
				};
				this.$axios.post("https://qa.pkucs.cn/api/qa/question",this.$qs.stringify(data),{
					headers: {
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					console.log(response);
					_self.$store.commit('addSubscribeWithId',{id:response.data.data});
					_self.getSubscription();
					//alert("发送成功");
				}).catch(function(error){
					console.log(error);
					//alert("发送失败");
				})
			},
			getFavorite(){
				var _self = this;
				if(this.pagenow < 1){
					return;
				}
				this.$axios.get('https://qa.pkucs.cn/api/qa/favorite/question',{
					headers:{
						'Authorization':this.$store.state.token
					},
					params:{
						size:20,
						page:this.pagenow - 1
					}
				}).then(function(response){
					console.log(response);
					if(response.data.code == 0){
						if(response.data.data.length != 0){
							var i = 0;
							var l = response.data.data.length;
							for (i = 0; i < l; i++){
								var unixTimestamp = new Date(response.data.data[i].createTime * 1000);
								var commonTime = unixTimestamp.toLocaleString();
								response.data.data[i].createTime = commonTime;
								if(response.data.data[i].user == undefined)
									response.data.data[i].info = '匿名用户';
								else{
									response.data.data[i].info = "";
									for(var key in response.data.data[i].user){
										if(key != 'userName' && key != 'dept' && key != 'motto')
											continue;
										var value = response.data.data[i].user[key];
										if(value != undefined){
											response.data.data[i].info += (value+' ');
										}
									}
								}							  
							}
							_self.items3 = response.data.data;
						}
						else{
							if(_self.pagenow > 1){
								_self.$store.commit('subPageNow',{idx:_self.tabNum});
								_self.pagenow = _self.pagenow - 1;
							}
						}
					}
				}).catch(function(error){
					console.log(error);
				})
			},
			getSubscription(){
				if(this.pagenow < 1){
					return;
				}
				var _self = this;
				this.$axios.get('https://qa.pkucs.cn/api/qa/subscription',{
					headers:{
						'Authorization':this.$store.state.token
					},
					params:{
						size:20,
						page:this.pagenow - 1
					}
				}).then(function(response){
					console.log(response);
					if(response.data.code == 0){
						if(response.data.data.length != 0){
							var i = 0;
							var l = response.data.data.length;
							for (i = 0; i < l; i++){
								var unixTimestamp = new Date(response.data.data[i].createTime * 1000);
								var commonTime = unixTimestamp.toLocaleString();
								response.data.data[i].createTime = commonTime;
								if(response.data.data[i].user == undefined)
									response.data.data[i].info = '匿名用户';
								else{
									response.data.data[i].info = "";
									for(var key in response.data.data[i].user){
										if(key != 'userName' && key != 'dept' && key != 'motto')
											continue;
										var value = response.data.data[i].user[key];
										if(value != undefined){
											response.data.data[i].info += (value+' ');
										}
									}
								}							  
							}
							_self.items2 = response.data.data;
						}
						else{
							if(_self.pagenow > 1){
								_self.$store.commit('subPageNow',{idx:_self.tabNum});
								_self.pagenow = _self.pagenow - 1;
							}
						}
					}
				}).catch(function(error){
					console.log(error);
				})
			}
        },
        data(){
			var _self = this;
			return {
				rules: [
					value => !!value || 'Required.',
					value => (value && value.length >= 1) || 'Min 1 characters',
				],
				dialog: false,
				pagenow:_self.$store.state.pagenow[0],
				pagesum:_self.$store.state.pagesum[0],
				label:['标签'],
				items0: ['全部', '课程', '学术', '生活','情感','娱乐'],
				select0: '全部',
				itemsx: ['课程', '学术', '生活','情感','娱乐'],
				searchtext:'',
				questext:'',
				detailtext:'',
				topic:'',
				labeltext:'',
				tabNum:0,
				items:[
					{ title: '全部'},
					{ title: '课程'},
					{ title: '学术'},
					{ title: '生活'},
					{ title: '情感'},
					{ title: '娱乐'},
				],
				items1:['最新提问','最新回答'],
				select1: '最新提问',
	/*            items1:[
					{ title: '最新提问'},
					{ title: '最新回答'},
				],*/
				items2:[],            
				items3:[],
				items4:[],
			}
		}
   }
</script>
