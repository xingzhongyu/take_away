var e=Object.defineProperty,t=Object.defineProperties,r=Object.getOwnPropertyDescriptors,o=Object.getOwnPropertySymbols,a=Object.prototype.hasOwnProperty,s=Object.prototype.propertyIsEnumerable,n=(t,r,o)=>r in t?e(t,r,{enumerable:!0,configurable:!0,writable:!0,value:o}):t[r]=o;import{K as i,L as l,M as d,N as c,u,O as f,P as p,Q as _,z as m,r as b,o as k,c as y,w as C,B as x,C as O,F as v,e as h,p as j,k as w,l as g,q as P,R as I}from"../vendor.69e0273a.js";import{_ as z}from"../uni-card.32412ff4.js";import{r as G}from"../uni-app.es.3ef08382.js";import{_ as B}from"../uni-goods-nav.960f4e30.js";import{A as S}from"../api.0a8de5c9.js";const D={name:"Cart",data:()=>({options:[{icon:"home",text:"首页"},{icon:"list",text:"订单"},{icon:"person",text:"我的"}],buttonGroup:[{text:"结算",backgroundColor:"#ff0000",color:"#fff"}]}),computed:i(["user","cart","address"]),methods:(F=((e,t)=>{for(var r in t||(t={}))a.call(t,r)&&n(e,r,t[r]);if(o)for(var r of o(t))s.call(t,r)&&n(e,r,t[r]);return e})({},l(["add_order","delete_cart","delete_all_cart"])),T={async onClick(e,t){await this.add_order_async(e),this.delete_cart(t),d({url:"./Orders"})},add_order_async(e){let t={userId:c("userId"),foodId:e.foodId,num:e.num,address:this.address};return new Promise(((e,r)=>{u({method:"POST",url:S+"/TaOrder",data:t,success:t=>{e(t.data)}})}))},onClick_nav(e){switch(e.index){case 0:f({url:"../index/index"});break;case 1:f({url:"./Orders"});break;case 2:f({url:"./Person"})}},async buttonClick(){for(let e of this.cart)await this.add_order_async(e);this.delete_all_cart(),d({url:"./Orders"})}},t(F,r(T))),onShow(){void 0===c("userId")&&p({content:"是否登录？",success:function(e){e.confirm?_({url:"../login/login"}):e.cancel&&f({url:"../index/index"})}})}};var F,T;D.render=function(e,t,r,o,a,s){const n=P,i=I,l=G(b("uni-card"),z),d=G(b("uni-goods-nav"),B),c=j;return k(),y(c,null,{default:C((()=>[(k(!0),x(v,null,O(e.cart,((t,r)=>(k(),y(l,{title:t.title,extra:"x"+t.num,note:!0,key:r},{footer:C((()=>[h(i,{type:"warn",size:"mini",onClick:t=>e.delete_cart(r)},{default:C((()=>[w("删除")])),_:2},1032,["onClick"]),h(i,{type:"default",size:"mini",style:{"margin-left":"50rpx"},onClick:e=>s.onClick(t,r)},{default:C((()=>[w("购买")])),_:2},1032,["onClick"])])),default:C((()=>[h(n,{style:{color:"#DC143C"}},{default:C((()=>[w("总价：¥"+g(t.detail_price.toFixed(2)),1)])),_:2},1024)])),_:2},1032,["title","extra"])))),128)),h(d,{class:"goods_nav",style:{"z-index":"1000"},fill:!0,options:a.options,buttonGroup:a.buttonGroup,onClick:s.onClick_nav,onButtonClick:s.buttonClick},null,8,["options","buttonGroup","onClick","onButtonClick"])])),_:1})},D.__scopeId="data-v-637fbdf5",m(D);export default D;