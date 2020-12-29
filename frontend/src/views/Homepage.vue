<template>
  <v-app id="inspire">
    <v-app-bar
      app
      color="white"
      elevate-on-scroll
    >
        <v-btn icon to="/user">
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
                                @click="item"
                                cols=12
                                fluid
                                md="12"
                            >
                                <v-card
                                    class="mx-auto"
                                    height="172"
                                >
                                    <template>
                                        <v-row>
                                            <v-btn
                                                to="/qa"
                                                class="auto"
                                                height="174"
                                                text
                                                block
                                            >
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
                                                                v-for="(item, index) in item.label"
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
                                                            <v-list-item-title>{{item.ques}}</v-list-item-title>
                                                            <v-list-item-subtitle>{{item.imfo}}</v-list-item-subtitle>
                                                            <v-text style="margin-top:10px">{{item.ans0bar}}</v-text>
                                                            <v-text>{{item.ans0text}}</v-text>
                                                        </v-list-item-content>
                                                    </v-list-item>
                                                </v-list>
                                            </v-btn>
                                        </v-row>
                                    </template>
                                </v-card>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-tab-item>
                  
                <v-tab-item>
                    <v-container>
                        <v-row background-color="grey">
                            <v-col
                                v-for="(item, index) in items3"
                                :key="index"
                                @click="item"
                                cols=12
                                fluid
                                md="12"
                            >
                                <v-card
                                    class="mx-auto"
                                    height="158"
                                >
                                    <v-row>
                                        <v-btn
                                            to="/qa"
                                            class="auto"
                                            height="160"
                                            text
                                            block
                                        >
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
                                                            v-for="(item, index) in item.label"
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
                                                        <v-list-item-title>{{item.ques}}</v-list-item-title>
                                                        <v-text style="margin-top:10px">{{item.ansbar}}:{{item.anstext}}</v-text>
                                                        <v-list-item-subtitle>{{item.imfo}}</v-list-item-subtitle>
                                                    </v-list-item-content>
                                                </v-list-item>
                                            </v-list>
                                        </v-btn>
                                    </v-row>
                                </v-card>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-tab-item>
                
                <v-tab-item>
                    <v-container>
                        <v-row background-color="grey">
                            <v-col
                                v-for="(item, index) in items4"
                                :key="index"
                                @click="item"
                                cols=12
                                fluid
                                md="12"
                            >
                                <v-card
                                    class="mx-auto"
                                    height="172"
                                >
                                    <v-row>
                                        <v-btn
                                            to="/qa"
                                            class="auto"
                                            height="174"
                                            text
                                            block
                                        >
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
                                                            v-for="(item, index) in item.label"
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
                                                        <v-list-item-title>{{item.ques}}</v-list-item-title>
                                                        <v-list-item-subtitle>{{item.imfo}}</v-list-item-subtitle>
                                                        <v-text style="margin-top:10px">{{item.ans0bar}}</v-text>
                                                        <v-text>{{item.ans0text}}</v-text>
                                                    </v-list-item-content>
                                                </v-list-item>
                                            </v-list>
                                        </v-btn>
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
            @click="leftbtrefresh(pagenow)"
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
            @click="rightbtrefresh(pagenow,pagesum)"
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
        methods: {
            leftbtrefresh(pagen) {
                document.body.scrollTop = 0;
                document.documentElement.scrollTop = 0;
                this.$forceUpdate();
                if(pagen>1)this.pagenow--;
            },
            rightbtrefresh(pagen,pages) {
                document.body.scrollTop = 0;
                document.documentElement.scrollTop = 0;
                this.$forceUpdate();
                if(pagen<pages)this.pagenow++;
            },
            toptop(){
                document.body.scrollTop = 0;
                document.documentElement.scrollTop = 0;
            },
			postQuestion(){
				this.dialog = false;
				
			},
        },
        data: () =>
        ({
            rules: [
                value => !!value || 'Required.',
                value => (value && value.length >= 1) || 'Min 1 characters',
            ],
            dialog: false,
            pagenow:"1",
            pagesum:"3",
            label:['标签'],
            items0: ['全部', '课程', '学术', '生活','情感','娱乐'],
            select0: '全部',
            itemsx: ['课程', '学术', '生活','情感','娱乐'],
            searchtext:'',
            questext:'',
            detailtext:'',
            labeltext:'',
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
            items2:[
                { 
                    topic:'课程',
                    label:['软件工程','设计'],
                    ques:'如何进行软件开发？',
                    imfo:'3回答 10关注 20浏览 20-12-14 10:10:00',
                    ans0bar:'最新回答：路人甲 20-12-14 12:23:20',
                    ans0text:'学习计算机编程语言。想要进行软件开发……'
                },
                { 
                    topic:'课程',
                    label:['软件工程','设计'],
                    ques:'如何进行UI设计？',
                    imfo:'3回答 4关注 10浏览 20-12-14 10:09:00',
                    ans0bar:'最新回答：路人乙 20-12-14 12:25:20',
                    ans0text:'这个问题很简单很简单，所以我不想回答……'
                },
                { 
                    topic:'课程',
                    label:['软件工程','设计'],
                    ques:'如何进行软件测试？',
                    imfo:'4回答 10关注 12浏览 20-12-14 10:08:00',
                    ans0bar:'最新回答：路人丙 20-12-14 12:26:20',
                    ans0text:'这个问题很简单很简单，所以我不想回答……'
                },
                { 
                    topic:'课程',
                    label:['软件工程','设计'],
                    ques:'如何进行软件重构？',
                    imfo:'2回答 12关注 15浏览 20-12-14 10:07:00',
                    ans0bar:'最新回答：路人丁 20-12-14 12:28:20',
                    ans0text:'这个问题很简单很简单，所以我不想回答……'
                },
            ],            
            items3:[
                { 
                    topic:'课程',
                    label:['软件工程','设计'],
                    ques:'如何进行软件重构？',
                    ansbar:'卷王',
                    anstext:'软件重构是指在不改变软件的功能和外部……',
                    imfo:'10赞同 20评论 20-12-14 10:15:00',
                },
                { 
                    topic:'课程',
                    label:['软件工程','设计'],
                    ques:'如何进行软件重构？',
                    ansbar:'超级卷王',
                    anstext:'为了改善软件的结构，提高软件的清晰……',
                    imfo:'20赞同 20评论 20-12-14 10:14:00',
                },
            ],
            items4:[
                { 
                    topic:'课程',
                    label:['软件工程','设计'],
                    ques:'如何进行软件开发？',
                    imfo:'3回答 10关注 20浏览 20-12-14 10:10:00',
                    ans0bar:'最新回答：路人甲 20-12-14 12:23:20',
                    ans0text:'学习计算机编程语言。想要进行软件开发……'
                },
                { 
                    topic:'课程',
                    label:['软件工程','设计'],
                    ques:'如何进行UI设计？',
                    imfo:'3回答 4关注 10浏览 20-12-14 10:09:00',
                    ans0bar:'最新回答：路人乙 20-12-14 12:25:20',
                    ans0text:'这个问题很简单很简单，所以我不想回答……'
                },
                { 
                    topic:'课程',
                    label:['软件工程','设计'],
                    ques:'如何进行软件测试？',
                    imfo:'4回答 10关注 12浏览 20-12-14 10:08:00',
                    ans0bar:'最新回答：路人丙 20-12-14 12:26:20',
                    ans0text:'这个问题很简单很简单，所以我不想回答……'
                },
                { 
                    topic:'课程',
                    label:['软件工程','设计'],
                    ques:'如何进行软件重构？',
                    imfo:'2回答 12关注 15浏览 20-12-14 10:07:00',
                    ans0bar:'最新回答：路人丁 20-12-14 12:28:20',
                    ans0text:'这个问题很简单很简单，所以我不想回答……'
                },
            ],
        }),
   }
</script>