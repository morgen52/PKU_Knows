<!--用户页-->
<template>
    <v-app id="user">
        <v-app-bar 
        app
        color="white"
        elevate-on-scroll
        >
            <v-btn icon color="blue-grey darken-4" x-large to="/homepage">
                <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn icon color="blue-grey darken-4" to="/cog">
                <v-icon>mdi-cog</v-icon>
            </v-btn>
        </v-app-bar>
        
        <v-main class="text-center" style="padding: 0px;">
            <!--最上面的头像、名字-->
            <v-col
            justify="center"
            align="center"
            align-self="center"
            style="margin-bottom: 10px;"
            >
                <v-avatar size="80px">
                    <img alt="头像" v-bind:src="avator">
                </v-avatar>
                <v-col
                cols="9"
                sm="5"
                md="4"
                style="padding: 0px;"
                >
                    <v-text-field
                    single-line
                    v-model="userName"
                    dense
                    v-bind:readonly="unchange"
                    v-bind:solo="unchange"
                    v-bind:hide-details="unchange"
                    flat
                    counter=11
                    class="title centered-input"
                    ></v-text-field>
                </v-col>
             
            </v-col>
            
            <!--下面的一行 关注 收藏 回答-->
            <v-card
            justify="center"
            align="center"
            class="mx-auto"
            flat
            >
                <v-row justify="center" align="center">
                    <v-col cols="5" sm="2" md="1" align="center" justify="center">
                        <v-list-item-title>{{focus}}</v-list-item-title>
                        <v-list-item-subtitle>关注问题数</v-list-item-subtitle>
                    </v-col>
                    <v-divider vertical></v-divider>
                    <v-col cols="5" sm="2" md="1" align="center" justify="center">
                        <v-list-item-title>{{collects}}</v-list-item-title>
                        <v-list-item-subtitle>收藏问题数</v-list-item-subtitle>
                    </v-col>
                </v-row>
            </v-card>
            <!--最下面的个人信息-->
            <v-container>
                <v-row cols="12" sm="6" justify="center" align="center">
                    <v-col cols="2" sm="2">
                        <v-header>性别:</v-header>
                    </v-col>
                    <v-col cols="8" sm="6">
                        <v-select
                        v-if="!unchange"
                        v-model="gender"
                        v-bind:readonly="unchange"
                        v-bind:autofocus="unchange"
                        :items="gender_items"
                        item-text="state"
                        dense
                        ></v-select>
                        <v-text-field
                        v-if="unchange"
                        v-model="gender"
                        v-bind:readonly="unchange"
                        dense
                        class="myright"
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row cols="12" sm="6" :style="marginBetween" justify="center" align="center">
                    <v-col cols="2" sm="2">
                        <v-header>院系:</v-header>
                    </v-col>
                    <v-col cols="8" sm="6">
                        <v-select
                        v-if="!unchange"
                        v-model="dept"
                        v-bind:readonly="unchange"
                        v-bind:autofocus="unchange"
                        :items="master_items"
                        item-text="state"
                        dense
                        ></v-select>
                        <v-text-field
                        v-if="unchange"
                        v-model="dept"
                        v-bind:readonly="unchange"
                        dense
                        class="myright"
                        ></v-text-field>
                    </v-col>
                    
                </v-row>
                <v-row cols="12" sm="6" :style="marginBetween" justify="center" align="center">
                    <v-col cols="2" sm="2">
                        <v-header>年级:</v-header>
                    </v-col>
                    <v-col cols="8" sm="6">
                        <v-select
                        v-if="!unchange"
                        v-model="enroll"
                        v-bind:readonly="unchange"
                        v-bind:autofocus="unchange"
                        :items="grade_items"
                        item-text="state"
                        dense
                        ></v-select>
                        <v-text-field
                        v-if="unchange"
                        v-model="enroll"
                        v-bind:readonly="unchange"
                        dense
                        class="myright"
                        ></v-text-field>
                    </v-col>
                    
                </v-row>
                <v-row cols="12" sm="6" :style="marginBetween" justify="center" align='center'>
                    <v-col cols="2" sm="2" >
                        <v-header>简介:</v-header>
                    </v-col>
                    <v-col cols="8" sm="6">
                        <v-textarea
                        v-model="motto"
                        rows=1
                        v-bind:readonly="unchange"
                        v-bind:clearable="!unchange"
                        v-bind:hide-details="unchange"
                        dense
                        no-resize=True
                        auto-grow=True
                        class="myright"
                        counter="50"
                        ></v-textarea>
                    </v-col>
                    
                </v-row>
            </v-container>
            <!--编辑/保存按钮-->
            <v-row
                align="center"
                justify="space-around">
                <v-btn 
                style="margin-top: 10px;"
                depressed
                text
                elevation=1
                v-if="unchange"
                @click="unchange=false">
                    编辑资料
                </v-btn>
            </v-row>
            <v-row
                align="center"
                justify="space-around">
                <v-btn 
                depressed
                text
                elevation=1
                v-if="!unchange"
                @click="saveChange">
                    保存
                </v-btn>
            </v-row>
        </v-main>
    </v-app>
</template>

<script>
    export default{
        el:"#user",
        name:'User',
		methods:{
			saveChange(){
				this.unchange=!this.unchange;
				this.$store.commit('setShowInfo',{
					gender:this.gender,
					dept:this.dept,
					enroll:this.enroll,
					motto:this.motto,
					userName:this.userName})
			},
			setUserName(){
				this.$store.commit('setUserName',this.username);
			}
		},
        data(){
			var _self = this;
            return{
                unchange:true,
				avator:_self.$store.state.showInfo.avator,
				gender:_self.$store.state.showInfo.gender,
				dept:_self.$store.state.showInfo.dept,
				enroll:_self.$store.state.showInfo.enroll,
				motto:_self.$store.state.showInfo.motto,
				userName:_self.$store.state.showInfo.userName,
                gender_items:[
                    {state:'不展示'},
                    {state:_self.$store.state.user.gender}
                ],
                master_items:[
                    {state:'不展示'},
                    {state:_self.$store.state.user.dept},
                ],
                grade_items:[
                    {state:'不展示'},
                    {state:_self.$store.state.user.enroll},
                ],
                sides:{
                    padding:"0px",
                    marginLeft:"0px",
                    marginRight:"0px",
                },
                info:{
                    marginTop:"33px",
                    marginRight:"2px",
                    height:"26px",
                    fontSize:"14px"
                },
                focus:"300",
                collects:"200",
                reply:"100",
                no_flex:{
                    flex:"none",
                    width:"90px"
                },
                perInfo:{
                    padding:"0px",
                    marginTop:"0px",
                    textAlign:"right",
                    justifyContent:"center",
                    marginRight:"30px",
                },
                marginBetween:{
                    padding:"0px",
                    marginTop:"0px",
                    marginBottom:"0px",
                },
                
            }
        },
    }
</script>

<style>
    .myright input{
        text-align: right;
    }
    .borderless{
        border:0;
    }
    .v-select{
        direction: rtl;
    }
    .centered-input input {
      text-align: center
    }
</style>
