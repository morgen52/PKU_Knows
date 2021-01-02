import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	
	state: {
		token:"",
		setting:0,
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
		questionId:0,
		likedAnswers:{},
	},
	mutations: {
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
		addPageSum(state){
			state.pagesum[data.idx] = state.pagesum[data.idx] + 1;
		},
		setShowInfo(state,info){
			state.showInfo.gender = info.gender;
			state.showInfo.dept = info.dept;
			state.showInfo.enroll = info.enroll;
			state.showInfo.motto = info.motto;
			state.showInfo.userName = info.userName;
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
