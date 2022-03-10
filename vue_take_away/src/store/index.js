import { createStore } from 'vuex'

export default createStore({
  state: {
    user:null,
    type:''
  },
  mutations: {
    set_user(state,user){
      state.user=user
    },
    set_type(state,type){
      state.type=type
    }
  },
  actions: {
  },
  modules: {
  }
})
