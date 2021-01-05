import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	
	state: {
		token:"",
		setting:7,
		times:1,
		user:{
			avator:"",
			dept:"",
			gender:"",
			major:"",
			name:"",
			stuT:"",
			userName:"",
			usrT:"",
			enroll:""
		},
		subscribe:0,
		favorite:0,
		getInfoAlready:false,
		showInfo:{
			gender:"",
			dept:"",
			enroll:"",
			motto:"",
			userName:""
		},
		pagenow:[1,1,1],
		pagesum:[1,1,1],
		tabNum:0,
		questionId:0,
		subsId:0,
		likedAnswers:{},
		subscribeList:[],
		favoriteList:[],
		favoriteAnsList:[],
	},
	mutations: {
		setTabNum(state,tabNum){
			state.tabNum = tabNum;
			console.log("enter index.js");
			console.log(state.tabNum);
		},
		addFavorite(state){
			if(state.favoriteList.length > 0 && state.favoriteList.indexOf(state.questionId) != -1)
				return;
			else{
				state.favoriteList.push(state.questionId);
				state.favorite = state.favorite + 1;
			}
		},
		deleteFavorite(state){
			var i = 0;
			var l = state.favoriteList.length;
			for (i = 0;i < l; i ++){
				if (state.favoriteList[i] == state.questionId)
					break;
			}
			if(i > -1){
				state.favoriteList.splice(i,1);
				state.favorite = state.favorite - 1;
			}
		},
		addFavoriteAns(state,data){
			if(state.favoriteAnsList.length > 0 && state.favoriteAnsList.indexOf(data.id) != -1)
				return;
			else
				state.favoriteAnsList.push(data.id);
		},
		deleteFavoriteAns(state,data){
			var i = 0;
			var l = state.favoriteAnsList.length;
			for (i = 0;i < l; i ++){
				if (state.favoriteAnsList[i] == data.id)
					break;
			}
			if(i > -1)	
				state.favoriteAnsList.splice(i,1);
		},
		addSubscribe(state){
			if(state.subscribeList.length > 0 && state.subscribeList.indexOf(state.questionId) != -1)
				return;
			else{
				state.subscribeList.push(state.questionId);
				state.subscribe = state.subscribe + 1;
			}
		},		
		addSubscribeWithId(state,data){
			if(state.subscribeList.length > 0 && state.subscribeList.indexOf(data.id) != -1)
				return;
			else{
				state.subscribeList.push(data.id);
				state.subscribe = state.subscribe + 1;
			}
		},
		deleteSubscribe(state){
			var i = 0;
			var l = state.subscribeList.length;
			for (i = 0;i < l; i ++){
				if (state.subscribeList[i] == state.questionId)
					break;
			}
			if(i > -1){	
				state.subscribeList.splice(i,1);
				state.subscribe = state.subscribe - 1;
			}
		},
		subTimes(state){
			state.times = 0;
		},
		setSubscribeList(state,data){
			state.subscribeList = data.subscribeList;
			state.subscribe = data.subscribeList.length;
		},		
		setFavoriteAnsList(state,data){
			state.favoriteAnsList = data.favoriteAnsList;
		},	
		setFavoriteList(state,data){
			state.favoriteList = data.favoriteList;
			state.favorite = data.favoriteList.length;
		},
		setLikedAnswers(state,liked){
			state.likedAnswers = liked;
		},
		updateLikedAnswers(state,msg){
			if(msg.change == 0){
				try{
					delete state.likedAnswers.msg.id;
				}catch{
					return;
				}
			}
			else{
				state.likedAnswers.msg.id = msg.change;
			}
		},
		addPageNow(state,data){
			state.pagenow[data.idx] = state.pagenow[data.idx]+ 1;
		},
		subPageNow(state,data){
			state.pagenow[data.idx] = state.pagenow[data.idx] - 1;
		},
		addPageSum(state,data){
			state.pagesum[data.idx] = state.pagesum[data.idx] + 1;
		},
		setShowInfo(state,info){
			state.showInfo.gender = info.gender;
			state.showInfo.dept = info.dept;
			state.showInfo.enroll = info.enroll;
			state.showInfo.motto = info.motto;
			state.showInfo.userName = info.userName;
			if(state.showInfo.gender == '不展示' && state.showInfo.dept == '不展示' && state.showInfo.enroll == '不展示')
				state.setting = 0;
			else
				state.setting = 7;
		},
		setAlready(state){
			state.getInfoAlready = !state.getInfoAlready;
		},
		setToken(state,str){
			state.token = str;
		},
		setUser(state,usr){
			state.user.avator = usr.avator;
			state.user.major = usr.major;
			state.user.dept = usr.dept;
			state.user.name = usr.name;
			state.user.stuT = usr.stuT;
			state.user.usrT = usr.usrT;
			state.user.enroll = usr.enroll;
			state.user.gender = usr.gender;
		}
	},
	actions: {
	},
	modules: {
	}
})


