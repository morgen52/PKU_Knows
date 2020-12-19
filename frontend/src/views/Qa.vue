<template>
  <v-app id="inspire">
    <v-app-bar
      app
      color="white"
      flat
    >
        <v-container>
            <v-btn
                to="/homepage"
                fab
                small
                text
            >       
                <v-img
                     alt="fa-left Logo"
                     class="shrink mr-2"
                     contain
                     src="../assets/icons/fas fa-chevron-left Copy.svg"
                     transition="scale-transition"
                     width="24"
                />
            </v-btn>
        </v-container>
    </v-app-bar>

    <v-main class="grey lighten-5" style="padding: 0px;">
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
                                    v-for="(item, index) in label1"
                                    :key="index"
                                    column
                                    class="ma-2"
                                    active-class="deep-purple accent-4 white--text"
                                >
                                    <v-chip
                                        color="primary lighten-3"
                                    >
                                        {{item.label}}
                                    </v-chip>
                                </v-chip-grop>
                            </v-row>
                        </v-list-item>
                        <v-list-item>
                            <v-list-item-content>
                                <v-list-item-title class="headline mb-2">{{ques}}</v-list-item-title>
                                <v-text>{{disc}}</v-text>
                                <v-list-item-subtitle style="margin-top:10px">{{imfo}}</v-list-item-subtitle>
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
                            width="140"
                        >
                            <v-btn
                                v-bind="attrs"
                                v-on="on"
                                width="140"
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
                                <v-btn color="primary" dark text @click="dialog = false">
                                    发布
                                </v-btn>
                            </v-toolbar-items>
                        </v-toolbar>
                        <v-list one-line flat text>
                            <v-list-item flat text>
                                <v-list-item-content>
                                    <v-textarea
                                        label="在此处撰写答案"
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
                    width="140"
                    center
                    color="white"
                >
                    <v-btn
                        v-bind="attrs"
                        v-on="on"
                        width="140"
                        center
                        text
                    >       
                        <v-img
                            alt="md-bookmark_border Logo"
                            class="shrink mr-2"
                            contain
                            src="../assets/icons/md-bookmark_border.svg"
                            transition="scale-transition"
                            width="24"
                            center
                        />
                        关注问题
                    </v-btn>
                </v-card>
            </v-list-item>
            <v-list-item>
                <v-container>
                    <v-row background-color="grey">
                        <v-col
                            v-for="(item, index) in answer"
                            :key="index"
                            @click="item"
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
                                        src="../assets/images/logo.png"
                                      ></v-img>
                                    </v-list-item-avatar>
                                    <v-list-item-content>
                                        <v-list-item-title>{{item.message}}</v-list-item-title>
                                        <v-list-item-subtitle>{{item.time}}</v-list-item-subtitle>
                                    </v-list-item-content>
                                    <v-dialog
                                        v-model="dialog1"
                                        hide-overlay
                                    >
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn
                                                fab
                                                small
                                                class="mx-auto"
                                                v-bind="attrs"
                                                v-on="on"
                                                text
                                                icon
                                            >
                                                <v-icon class="mr-1">mdi-dots-horizontal</v-icon>
                                            </v-btn>
                                        </template>
                                        <v-container class="py-0 fill-width">
                                            <v-card>
                                                <v-row>
                                                    <v-btn
                                                        class="mx-auto"
                                                        background-color="white"
                                                        color="white"
                                                        center
                                                        @click="dialog1 = false"
                                                        grow
                                                        width="325"
                                                    >
                                                        举报
                                                    </v-btn>
                                                    <v-btn
                                                        class="mx-auto"
                                                        background-color="white"
                                                        color="white"
                                                        center
                                                        @click="dialog1 = false"
                                                        grow
                                                        width="325"
                                                    >
                                                        取消
                                                    </v-btn>
                                                </v-row>
                                            </v-card>
                                        </v-container>
                                    </v-dialog>
                                </v-list-item>
                                <v-list-item>
                                    <v-text center>{{item.anstext}}</v-text>
                                </v-list-item>
                                <v-list-item>
                                    <v-container>
                                        <v-btn icon >
                                            <v-icon>
                                                mdi-thumb-up-outline
                                            </v-icon>
                                            {{item.like}}
                                        </v-btn>
                                        <v-btn icon style="margin-left:10px">
                                            <v-icon>
                                                mdi-thumb-down-outline
                                            </v-icon>
                                            {{item.dislike}}
                                        </v-btn>
                                        <v-dialog v-model="dialog0" fullscreen hide-overlay transition="dialog-bottom-transition">
                                            <template v-slot:activator="{ on, attrs }">
                                                <v-btn
                                                    fab
                                                    small
                                                    class="mx-auto"
                                                    v-bind="attrs"
                                                    v-on="on"
                                                    text
                                                    icon
                                                    style="margin-left:16px"
                                                >       
                                                <v-icon>
                                                    mdi-comment-text-outline
                                                </v-icon>
                                                </v-btn>
                                            </template>
                                            <v-card text flat>
                                                <v-toolbar color="white" elevation="1">
                                                    评论
                                                    <v-spacer></v-spacer>
                                                    <v-btn color="black" icon dark @click="dialog0 = false">
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
                                                            label="请输入文字..."
                                                            style="padding: 10px;"
                                                        >
                                                        </v-text-field>
                                                    </v-responsive>
                                                    <v-btn color="primary" dark text>
                                                        发布
                                                    </v-btn>
                                                </v-container>
                                                <v-row background-color="grey">
                                                    <v-col
                                                        v-for="(item, index) in comments"
                                                        :key="index"
                                                        @click="item"
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
                                                                    src="../assets/images/logo.png"
                                                                  ></v-img>
                                                                </v-list-item-avatar>
                                                                <v-list-item-content>
                                                                    <v-list-item-title>{{item.message}}</v-list-item-title>
                                                                    <v-list-item-subtitle>{{item.time}}</v-list-item-subtitle>
                                                                </v-list-item-content>
                                                                <v-dialog v-model="dialog2" hide-overlay transition="dialog-bottom-transition">
                                                                    <template v-slot:activator="{ on, attrs }">
                                                                        <v-btn
                                                                            fab
                                                                            small
                                                                            class="mx-auto"
                                                                            v-bind="attrs"
                                                                            v-on="on"
                                                                            text
                                                                            icon
                                                                        >
                                                                            <v-icon class="mr-1">mdi-dots-horizontal</v-icon>
                                                                        </v-btn>
                                                                    </template>
                                                                    <v-container class="py-0 fill-width">
                                                                            <v-row>
                                                                                <v-btn
                                                                                    class="mx-auto"
                                                                                    background-color="white"
                                                                                    color="white"
                                                                                    center
                                                                                    @click="dialog2 = false"
                                                                                    grow
                                                                                    width="325"
                                                                                >
                                                                                    举报
                                                                                </v-btn>
                                                                                <v-btn
                                                                                    class="mx-auto"
                                                                                    background-color="white"
                                                                                    color="white"
                                                                                    center
                                                                                    @click="dialog2 = false"
                                                                                    grow
                                                                                    width="325"
                                                                                >
                                                                                    取消
                                                                                </v-btn>
                                                                            </v-row>
                                                                    </v-container>
                                                                </v-dialog>
                                                            </v-list-item>
                                                            <v-list-item>
                                                                <v-text center>{{item.text}}</v-text>
                                                            </v-list-item>
                                                            <v-list-item>
                                                                <v-spacer></v-spacer>
                                                                <v-btn
                                                                    icon
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
                                        <v-btn icon>
                                            <v-icon>mdi-star-outline</v-icon>
                                        </v-btn>
                                    </v-container>
                                </v-list-item>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-container>
            </v-list-item>
        </v-list>
    </v-main>
  </v-app>
</template>

<script>
    export default 
    {
		name:"Qa",
        data: () =>
        ({
            dialog: false,
            dialog0: false,
            dialog1: false,
            dialog2: false,
            id:'label1',
            topic:'课程',
            label:'标签',
            ques:'如何进行软件开发？',
            disc:'如题：如何进行软件开发',
            imfo:'3回答 10关注 20浏览 20-12-14 10:10:00',
            label1:[
                {
                    label:'软件工程'
                },
                {
                    label:'设计'
                }
            ],
            answer:[
                { 
                    head:'../assets/images/logo.png',
                    message:'路人甲·男·信科·18本',
                    time:'20-12-14 12:23:20',
                    anstext:'学习计算机编程语言。想要进行软件开发，学习计算机编程语言是必不可少的。例如java、php、python、html、css、js等等。',
                    like:'4',
                    dislike:'0',
                },
                { 
                    head:'../assets/images/logo.png',
                    message:'姓名·信科·18本科生',
                    time:'2020-12-13 11:34',
                    anstext:'回答...',
                    like:'2',
                    dislike:'0',
                },
                { 
                    head:'../assets/images/logo.png',
                    message:'姓名·信科·18本科生',
                    time:'2020-12-13 11:34',
                    anstext:'回答...',
                    like:'2',
                    dislike:'0',
                },
                { 
                    head:'../assets/images/logo.png',
                    message:'姓名·信科·18本科生',
                    time:'2020-12-13 11:34',
                    anstext:'回答...',
                    like:'2',
                    dislike:'0',
                },
            ],
            comments:[
                { 
                    head:'../assets/images/logo.png',
                    message:'姓名·信科·18本科生',
                    time:'2020-12-13 11:34',
                    text:'回答不错，谢谢',
                },
                { 
                    head:'../assets/images/logo.png',
                    message:'姓名·信科·18本科生',
                    time:'2020-12-13 11:34',
                    text:'评论...',
                },
                { 
                    head:'../assets/images/logo.png',
                    message:'姓名·信科·18本科生',
                    time:'2020-12-13 11:34',
                    text:'评论...',
                },
            ],
        }),
   }
</script>
