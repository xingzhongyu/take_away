import { createStore } from 'vuex'

// Create a new store instance.
export default createStore({
  state () {
    return {
		user:null,
      count: 0,
	  username:'',
	  orders:[],
	  cart:[],
	  address:'39.916527, 116.397128',
	  coupons:[],
    }
  },
  mutations: {
	  setUser(state,user){
		  state.user=user
	  },
	  setAddress(state,address){
		  state.address=address
	  },
    increment (state) {
      state.count++
    },
	setUsername(state,username){
		state.username=username
	},
	add_order(state,cart_food){
		let order=cart_food
		order['status']=0
		order['comment']=0
		state.orders.push(order)
	},
	set_orders(state,orders){
		state.orders=orders
	},
	pay_order(state,index){
		state.orders[index].status=1
	},
	comment_order(state,index){
		state.orders[index].comment=1
	},
	add_cart(state,food){
		state.cart.push(food)
	},
	delete_cart(state,index){
		state.cart.splice(index,1)
	},
	delete_all_cart(state){
		state.cart=[]
	},
	set_coupons(state,coupons){
		state.coupons=coupons
	}
  }
})

