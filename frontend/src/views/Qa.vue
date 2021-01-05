<template>
  <v-app id="inspire">
    <v-app-bar
      app
      color="white"
      elevate-on-scroll
    >
        <v-btn icon color="blue-grey darken-4" x-large to="/homepage">
            <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
<!--        <v-toolbar-title style="padding: 0px;">{{ques.substring(0,10)+(ques.length>10? "...":"")}}</v-toolbar-title>-->
         
        <v-toolbar-title style="padding: 0px;">{{title}}</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn
            fab
            small
            class="mx-auto"
            text
            icon
            @click="dialog1 = true"
        >
            <v-icon class="mr-1">mdi-dots-horizontal</v-icon>
        </v-btn>
    </v-app-bar>

    <v-main class="grey lighten-5" style="padding: 0px;">
        <v-btn
            fab
            fixed
            bottom
            right
            elevation=1
            @click="btrefresh"
        >
            <v-icon>mdi-refresh</v-icon>
        </v-btn>
        <v-list>
            <v-list-item>
                <v-card 
                    color="white"
                    flat
                >
                    <v-list>
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
                                        {{topic}}
                                    </v-chip>
                                </v-chip-grop>
                                <v-chip-grop
                                    v-for="(item, index) in tag"
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
                                <v-text class="headline mb-2" v-html="ques"></v-text>
                                <v-text>{{txt}}</v-text>
                                <v-list-item-subtitle style="margin-top:10px">{{info}}</v-list-item-subtitle>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list>
                </v-card>
            </v-list-item>
            <v-list-item>
                <v-dialog v-model="dialog">
                    <template v-slot:activator="{ on, attrs }">       
                        <v-card
                            class="mx-auto"
                            center
                            width="100"
                        >
                            <v-btn
                                v-bind="attrs"
                                v-on="on"
                                width="100"
                                center
                                text
                            >
                                <v-img
                                     alt="antOutline-edit Logo"
                                     class="shrink mr-2"
                                     contain
                                     src="../assets/icons/antOutline-edit.svg"
                                     transition="scale-transition"
                                     width="24"
                                />
                                写回答
                            </v-btn>
                        </v-card>
                    </template>
                    <v-card text flat>
                        <v-toolbar dark color="white" elevation="1">
                            <v-btn color="black" icon dark @click="dialog = false">
                                收起
                            </v-btn>
                            <v-spacer></v-spacer>
                            <v-toolbar-items>
                                <v-btn color="primary" dark text @click="postAnswer">
                                    发布
                                </v-btn>
                            </v-toolbar-items>
                        </v-toolbar>
                        <v-list one-line flat text>
                            <v-list-item flat text>
                                <v-list-item-content>
                                    <v-textarea
                                        label="在此处撰写答案"
										v-model='answerText'
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
                
                <v-card 
                    class="mx-auto"
                    width="100"
                    center
                    color="white"
                >
                    <v-btn
                        v-bind="attrs"
                        v-on="on"
                        width="100"
                        center
                        text
                        v-if="!isFocus"
                        @click="subcribeQuestion"
                    >       
                        <v-icon
                            alt="md-bookmark_border Logo"
                            class="shrink mr-2"
                            contain
                            width="24"
                            center
                        >mdi-bookmark-outline</v-icon>
                        关注问题
                    </v-btn>
                    <v-btn
                        v-bind="attrs"
                        v-on="on"
                        width="100"
                        center
                        text
                        v-if="isFocus"
                        @click="deleteSubscribe"
                    >       
                        <v-icon
                            alt="md-bookmark_border Logo"
                            class="shrink mr-2"
                            contain
                            width="24"
                            center
                            color="blue"
                        >mdi-bookmark</v-icon>
                        已经关注
                    </v-btn>
                </v-card>

                <v-card
                    class="mx-auto"
                    width="100"
                    center
                    color="white"
                >
                    <v-btn
                        v-bind="attrs"
                        v-on="on"
                        width="100"
                        center
                        text
                        v-if="!isCollect"
                        @click="collectQuestion"
                    >       
                        <v-icon
                            alt="md-bookmark_border Logo"
                            class="shrink mr-2"
                            contain
                            width="24"
                            center
                        >mdi-star-outline</v-icon>
                        收藏问题
                    </v-btn>
                    <v-btn
                        v-bind="attrs"
                        v-on="on"
                        width="100"
                        center
                        text
                        v-if="isCollect"
                        @click="deleteCollect"
                    >       
                        <v-icon
                            alt="md-bookmark_border Logo"
                            class="shrink mr-2"
                            contain
                            width="24"
                            center
                            color="yellow"
                        >mdi-star</v-icon>
                        已经收藏
                    </v-btn>
                </v-card>				
            </v-list-item>
            <v-list-item>
                <v-container>
                    <v-row background-color="grey">
                        <v-col
                            v-for="(item, index) in answer"
                            :key="index"
                            cols=12
                            fluid
                            md="12"
                        >
                            <v-card
                                class="mx-auto"
                                center
                            >
                                <v-list-item>
                                    <v-list-item-avatar color="white">
                                      <v-img
                                        :src="item.avator"
                                      ></v-img>
                                    </v-list-item-avatar>
                                    <v-list-item-content>
                                        <v-list-item-title>{{item.info}}</v-list-item-title>
                                        <v-list-item-subtitle>{{item.createTime}}</v-list-item-subtitle>
                                    </v-list-item-content>
                                    <v-btn
                                        fab
                                        small
                                        class="mx-auto"
                                        text
                                        icon
                                        @click="dialog1 = true"
                                    >
                                        <v-icon class="mr-1">mdi-dots-horizontal</v-icon>
                                    </v-btn>
                                </v-list-item>
                                <v-list-item>
                                    <v-text center>{{item.txt}}</v-text>
                                </v-list-item>
                                <v-list-item>
                                    <v-container>
                                        <v-btn icon @click="likeAnswer(item)">
                                            <v-icon :color="(item.liked == 1)?'blue':'none'">
                                                mdi-thumb-up
                                            </v-icon>
                                            {{item.like}}
                                        </v-btn>
                                        <v-btn icon style="margin-left:10px" @click="dislikeAnswer(item)">
                                            <v-icon :color="(item.liked == 2)?'blue':'none'">
                                                mdi-thumb-down
                                            </v-icon>
                                            {{item.dislike}}
                                        </v-btn>
                                        <v-btn
                                                fab
                                                small
                                                class="mx-auto"
                                                text
                                                icon
												@click="getCommentByAid(item)"
                                            >       
                                            <v-icon>
                                                mdi-comment-text-outline
                                            </v-icon>
                                        </v-btn>
                                        <v-btn icon @click="collectAnswer(item)">
                                            <v-icon :color="item.collected?'yellow':'none'">mdi-star</v-icon>
                                        </v-btn>
                                    </v-container>
                                </v-list-item>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-container>
                <v-dialog v-model="dialog0" fullscreen hide-overlay transition="dialog-bottom-transition">
                    <v-card text flat>
                        <v-toolbar color="white" elevation="1">
                            评论
                            <v-spacer></v-spacer>
                            <v-btn color="black" icon dark @click="dialog0 = !dialog0">
                                收起
                            </v-btn>
                        </v-toolbar>
                        <v-container class="py-0 fill-height">
                            <v-responsive grow>
                                <v-text-field
                                    flat
                                    text
                                    dense
                                    hide-details
                                    background-color="grey lighten-5"
                                    solo
                                    clearable
                                    label="请输入文字..."
                                    :value="replytext"
                                    style="padding: 10px;"
									v-model="replyTxt"
                                    @click:clear="clearMessage"
                                >
                                </v-text-field>
                            </v-responsive>
                            <v-btn color="primary" dark text @click="postComment">
                                发布
                            </v-btn>
                        </v-container>
                        <v-row background-color="grey">
                            <v-col
                                v-for="(item, index) in comments"
                                :key="index"
                                cols=12
                                fluid
                                md="12"
                            >
                                <v-card
                                    class="mx-auto"
                                >
                                    <v-list>
                                    <v-list-item>
                                        <v-list-item-avatar color="white">
                                          <v-img
                                            :src="item.avator"
                                          ></v-img>
                                        </v-list-item-avatar>
                                        <v-list-item-content>
                                            <v-list-item-title>{{item.userName}}</v-list-item-title>
                                            <v-list-item-subtitle>{{item.createTime}}</v-list-item-subtitle>
                                        </v-list-item-content>
                                            <v-btn
                                                fab
                                                small
                                                class="mx-auto"
                                                text
                                                icon
                                                @click="dialog1 = true"
                                            >
                                                <v-icon class="mr-1">mdi-dots-horizontal</v-icon>
                                            </v-btn>
                                    </v-list-item>
                                    <v-list-item>
                                        <v-text center>{{item.txt}}</v-text>
                                    </v-list-item>
                                    <v-list-item>
                                        <v-spacer></v-spacer>
                                        <v-btn
                                            icon
                                            @click="ReplyComment(item)"
                                        >
                                            回复
                                            <v-icon>mdi-reply-outline</v-icon>
                                        </v-btn>
                                    </v-list-item>
                                    </v-list>
                                </v-card>
                            </v-col>
                        </v-row>
                    </v-card>
                </v-dialog>
                <v-dialog
                    v-model="dialog1"
                    hide-overlay
                    transition="dialog-bottom-transition"
                >
                    <v-list>
                        <v-list-item>
                            <v-btn
                                @click="dialog1 = !dialog1"
                                color="white"
                                width="295"
                                auto-grow
                                class="mx-auto"
                            >
                                举报
                            </v-btn>
                        </v-list-item>
                        <v-list-item>
                            <v-btn
                                @click="dialog1 = !dialog1"
                                color="white"
                                width="295"
                                auto-grow
                                class="mx-auto"
                            >
                                取消
                            </v-btn>
                        </v-list-item>
                    </v-list>
                </v-dialog>
            </v-list-item>
        </v-list>
    </v-main>
  </v-app>
</template>

<script>
    export default 
    {
		name:"Qa",
		mounted:function(){
			this.getQuestion();
			this.getAnswers();
			if(this.$store.state.subscribeList.length > 0 && this.$store.state.subscribeList.indexOf(this.$store.state.questionId) != -1)
				this.isFocus = true;
			else
				this.isFocus = false;
			if(this.$store.state.favoriteList.length > 0 && this.$store.state.favoriteList.indexOf(this.$store.state.questionId) != -1){
				console.log('isCollect = true');
				this.isCollect= true;
			}
			else{
				this.isCollect = false;
				console.log('isCollect = false');
			}
			this.btrefresh();
		},
        methods: {
			collectAnswer(item){
				var _self = this;
				if(!item.collected){
					item.collected=true;
					var data = {
						type:'answer',
						oid:item._id
					};
					this.$axios.post("https://qa.pkucs.cn/api/qa/favorite",this.$qs.stringify(data),{
						headers: {
							'Authorization':this.$store.state.token
						}
					}).then(function(response){
						console.log(response);
						if(response.data.code == 0){
							_self.$store.commit('addFavoriteAns',{id:item._id});
							if(!_self.isCollect){
								_self.collectQuestion();
							}
						}
					}).catch(function(error){
						console.log(error);
					})
				}
				else{
					item.collected = false;
					//写一个delete collect answer的请求,记得改store里的数据
					var data1 = {
						type:'answer',
						oid:item._id
					};
					this.$axios.delete("https://qa.pkucs.cn/api/qa/favorite",{
						params:data1,
						headers: {
							'Authorization':this.$store.state.token
						}
					}).then(function(response){
						console.log(response);
						if(response.data.code == 0){
							_self.$store.commit('deleteFavoriteAns',{id:item._id});
							console.log('取消收藏成功');
						}
					}).catch(function(error){
						console.log(error);
					})
					var l = this.answer.length;
					var del = true;
					console.log('this.answer.length:');
					console.log(this.answer.length);
					for(var i = 0; i < l ;i ++){
						if(this.answer[i].collected){
							del = false;
							break;
						}
					}
					if(del){
						this.deleteCollect();
					}
				}
				
			},
			collectQuestion(){
				this.isCollect=!this.isCollect;
				var data = {
					type:'question',
					oid:this.$store.state.questionId
				};
				var _self = this;
				this.$axios.post("https://qa.pkucs.cn/api/qa/favorite",this.$qs.stringify(data),{
					headers: {
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					console.log(response);
					if(response.data.code == 0){
						_self.$store.commit('addFavorite');
					}
				}).catch(function(error){
					console.log(error);
				})
			},
			deleteCollect(){
				this.isCollect=!this.isCollect;
				var _self = this;
				var data = {
					type:'question',
					oid:this.$store.state.questionId
				};
				this.$axios.delete("https://qa.pkucs.cn/api/qa/favorite",{
					params:data,
					headers: {
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					console.log(response);
					if(response.data.code == 0){
						_self.$store.commit('deleteFavorite');
					}
				}).catch(function(error){
					console.log(error);
				})
			},
			subcribeQuestion(){
				this.isFocus=!this.isFocus;
				var data = {
					qid:this.$store.state.questionId
				};
				var _self = this;
				this.$axios.post("https://qa.pkucs.cn/api/qa/subscription",this.$qs.stringify(data),{
					headers: {
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					console.log(response);
					if(response.data.code == 0){
						_self.$store.commit('addSubscribe');
					}
				}).catch(function(error){
					console.log(error);
				})
			},
			deleteSubscribe(){
				this.isFocus=!this.isFocus;
				var _self = this;
				var data = {
					qid:this.$store.state.questionId
				};
				this.$axios.delete("https://qa.pkucs.cn/api/qa/subscription",{
					params:data,
					headers: {
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					console.log(response);
					if(response.data.code == 0){
						_self.$store.commit('deleteSubscribe');
					}
				}).catch(function(error){
					console.log(error);
				})
			},
			ReplyComment(item){
				// console.log(item);
				this.replyTxt='@'+item.userName+':';
				this.cid=item._id;
			},
			postComment(){
				this.dialog = false;
				var _self = this;
				var url = 'https://qa.pkucs.cn/api/qa/answer/'+this.aid+'/comment';
				var data = {
					txt:this.replyTxt,
					setting:this.$store.state.setting,
					pid:0
				};
				if(this.cid != 0){
					data.pid = this.cid;
					this.cid = 0;
				}
				this.$axios.post(url,this.$qs.stringify(data),{
					headers: {
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					if(response.data.code == 0){
						_self.getCommentByAid({_id:_self.aid});
						_self.$forceUpdate();
					}
				}).catch(function(error){
					console.log(error);
				})
			},
			getCommentByAid(item){
				this.dialog0 = true;
				this.aid = item._id;
				var id = item._id;
				var _self = this;
				var url = 'https://qa.pkucs.cn/api/qa/answer/'+id+'/comment';
				console.log(item);
				this.$axios.get(url,{
					headers:{
						'Authorization':this.$store.state.token
					},
					params:{
						page:0,
						size:100
					}
				}).then(function(response){
					console.log(response);
					if(response.data.code == 0){
						var i = 0;
						var l = response.data.data.length;
						for (i = 0; i < l; i++){
							var unixTimestamp = new Date(response.data.data[i].createTime * 1000);
							var commonTime = unixTimestamp.toLocaleString();
							response.data.data[i].createTime = commonTime;	
							if(response.data.data[i].user == undefined){
								response.data.data[i].userName = '匿名用户';
								response.data.data[i].avator = '../assets/images/logo.png';
							}
							else{
								if(response.data.data[i].user.userName == undefined){
									response.data.data[i].userName = '匿名用户';
								}
								else{
									response.data.data[i].userName = response.data.data[i].user.userName;
								}
								if(response.data.data[i].user.avatar == undefined){
									response.data.data[i].avator = require('../assets/images/logo.png');
								}
								else{
									response.data.data[i].avator = response.data.data[i].user.avatar;
								}
							}						  
						}
						_self.comments = response.data.data;
						console.log(_self.comments);
					}
				}).catch(function(error){
					console.log(error);
				})
			},
			getAnswers(){
				var id = this.$store.state.questionId;
				var _self = this;
				var url = 'https://qa.pkucs.cn/api/qa/question/'+id+'/answer';
				this.$axios.get(url,{
					headers:{
						'Authorization':this.$store.state.token
					},
					params:{
						page:0,
						size:100
					}
				}).then(function(response){
					if(response.data.code == 0){
						var i = 0;
						var l = response.data.data.length;
						for (i = 0; i < l; i++){
							var unixTimestamp = new Date(response.data.data[i].createTime * 1000);
							var commonTime = unixTimestamp.toLocaleString();
							response.data.data[i].createTime = commonTime;
							if(_self.$store.state.likedAnswers[response.data.data[i]._id] == 1)
								response.data.data[i].liked = 1;
							else if (_self.$store.state.likedAnswers[response.data.data[i]._id] == 2)
								response.data.data[i].liked = 2;
							else
								response.data.data[i].liked = 0;
							if(response.data.data[i].user == undefined){
								response.data.data[i].info = '匿名用户';
								response.data.data[i].avator = require('../assets/images/logo.png');
							}
							else{
								response.data.data[i].avator = response.data.data[i].user.avatar;
								response.data.data[i].info = "";
								for(var key in response.data.data[i].user){
									if(key != 'userName' && key != 'motto' && key != 'dept' && key != 'enroll')
										continue;
									var value = response.data.data[i].user[key];
									if(value != undefined){
										response.data.data[i].info += (value+' ');
									}
								}
							}
							if(_self.$store.state.favoriteAnsList.length > 0 && _self.$store.state.favoriteAnsList.indexOf(response.data.data[i]._id) != -1)
								response.data.data[i].collected = true;
							else
								response.data.data[i].collected = false;
						}
						_self.answer = response.data.data;
						// console.log(_self.answer);
					}
				}).catch(function(error){
					console.log(error);
				})
			},
			getQuestion(){
				var id = this.$store.state.questionId;
				var _self = this;
				var url = 'https://qa.pkucs.cn/api/qa/question/'+id;
				this.$axios.get(url,{
					headers:{
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					//console.log(response);
					if(response.data.code == 0){
						var unixTimestamp = new Date(response.data.data.createTime * 1000);
						var commonTime = unixTimestamp.toLocaleString();
						_self.topic = response.data.data.topic;
						_self.tag = response.data.data.tag;
						_self.title = response.data.data.title;
						_self.txt = response.data.data.txt;
						_self.info = response.data.data.answer+'回答  '+response.data.data.subscribe+'关注  '+response.data.data.favorite + '收藏  '+commonTime;
						_self.tag = response.data.data.tag;
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
            //用于处理赞和踩
            putLike(id,liked){
				var url = 'https://qa.pkucs.cn/api/qa/answer/'+id;
				var data = { like:liked};
				this.$axios.put(url,this.$qs.stringify(data),{
					headers: {
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					console.log(response);
				}).catch(function(error){
					console.log(error);
				})
			},
			dislikeAnswer(item){
                if(item.liked == 2){
					this.putLike(item._id,0);
					item.liked = 0;
					item.dislike = item.dislike - 1;
					this.$store.commit('updateLikedAnswers',{
										id:item._id,
										change:0,
										});
					this.$forceUpdate();
					return;
				}
				else if(item.liked == 1){
					this.putLike(item._id,0);
					item.like = item.like - 1;
					this.putLike(item._id,2);
				}
				else{
					this.putLike(item._id,2);
				}
				item.liked = 2;
				item.dislike = item.dislike + 1;
				this.$store.commit('updateLikedAnswers',{
									id:item._id,
									change:2,
									});
				this.$forceUpdate();
            },
            likeAnswer(item){
                if(item.liked == 1){
					this.putLike(item._id,0);
					item.liked = 0;
					item.like = item.like - 1;
					this.$store.commit('updateLikedAnswers',{
										id:item._id,
										change:0,
										});
					this.$forceUpdate();
					return;
				}

				else if(item.liked == 2){
					this.putLike(item._id,0);
					item.dislike = item.dislike - 1;
					this.putLike(item._id,1);
				}
				else{
					this.putLike(item._id,1);
				}
				item.liked = 1;
				item.like = item.like + 1;
				this.$store.commit('updateLikedAnswers',{
									id:item._id,
									change:1,
									});
				this.$forceUpdate();
            },
			postAnswer(){
				this.dialog = false;
				var _self = this;
				var url = 'https://qa.pkucs.cn/api/qa/question/'+this.$store.state.questionId+'/answer';
				var data = {
					txt:this.answerText,
					setting:this.$store.state.setting,
					subscribe:true
				};
				this.$axios.post(url,this.$qs.stringify(data),{
					headers: {
						'Authorization':this.$store.state.token
					}
				}).then(function(response){
					// console.log(response);
					if(response.data.code == 0){
						_self.getAnswers();
						_self.btrefresh();
					}
				}).catch(function(error){
					console.log(error);
				})
			},
            clearMessage () {
                    this.replytext = ''
            },
        },
        data: () =>
        ({
            dialog: false,
            dialog0: false,
            dialog1: false,
            dialog2: false,
            replytext:'',
			replyTxt:'',
            topic:'',
            title:'',
            txt:'',
            info:'',
			answerText:'',
            isFocus:false,
			isCollect:false,
			aid:'',
			cid:0,
            tag:[],
            answer:[],
            comments:[],
        }),
        
   }
</script>
<style>
    .bookmark{
        fill:black;
        color:black;
    }
</style>
