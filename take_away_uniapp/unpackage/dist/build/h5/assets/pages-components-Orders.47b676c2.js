var t=Object.defineProperty,e=Object.getOwnPropertySymbols,s=Object.prototype.hasOwnProperty,r=Object.prototype.propertyIsEnumerable,a=(e,s,r)=>s in e?t(e,s,{enumerable:!0,configurable:!0,writable:!0,value:r}):e[s]=r;import{o as i,c as n,w as o,I as u,D as l,E as d,p as c,K as p,u as f,N as m,n as h,P as g,Q as y,O as b,z as O,A as C,r as _,B as j,C as N,F as w,e as x,k as z,l as S,q as $,R as I}from"../vendor.69e0273a.js";import{r as v}from"../uni-app.es.3ef08382.js";import{_ as k}from"../uni-card.32412ff4.js";import{_ as P}from"../uni-pagination.2bc00792.js";import{A}from"../api.0a8de5c9.js";import{_ as L}from"../Address.98715c14.js";import"../provinceData.d60c526a.js";const F={name:"uniCol",props:{span:{type:Number,default:24},offset:{type:Number,default:-1},pull:{type:Number,default:-1},push:{type:Number,default:-1},xs:[Number,Object],sm:[Number,Object],md:[Number,Object],lg:[Number,Object],xl:[Number,Object]},data:()=>({gutter:0,sizeClass:"",parentWidth:0,nvueWidth:0,marginLeft:0,right:0,left:0}),created(){let t=this.$parent;for(;t&&"uniRow"!==t.$options.componentName;)t=t.$parent;this.updateGutter(t.gutter),t.$watch("gutter",(t=>{this.updateGutter(t)}))},computed:{sizeList(){let{span:t,offset:e,pull:s,push:r}=this;return{span:t,offset:e,pull:s,push:r}},pointClassList(){let t=[];return["xs","sm","md","lg","xl"].forEach((e=>{const s=this[e];"number"==typeof s?t.push(`uni-col-${e}-${s}`):"object"==typeof s&&s&&Object.keys(s).forEach((r=>{t.push("span"===r?`uni-col-${e}-${s[r]}`:`uni-col-${e}-${r}-${s[r]}`)}))})),t.join(" ")}},methods:{updateGutter(t){t=Number(t),isNaN(t)||(this.gutter=t/2)}},watch:{sizeList:{immediate:!0,handler(t){let e=[];for(let s in t){const r=t[s];!r&&0!==r||-1===r||e.push("span"===s?`uni-col-${r}`:`uni-col-${s}-${r}`)}this.sizeClass=e.join(" ")}}}};F.render=function(t,e,s,r,a,p){const f=c;return i(),n(f,{class:l(["uni-col",a.sizeClass,p.pointClassList]),style:d({paddingLeft:`${Number(a.gutter)}rpx`,paddingRight:`${Number(a.gutter)}rpx`})},{default:o((()=>[u(t.$slots,"default",{},void 0,!0)])),_:3},8,["class","style"])},F.__scopeId="data-v-931355ca";const R={name:"uniRow",componentName:"uniRow",props:{type:String,gutter:Number,justify:{type:String,default:"start"},align:{type:String,default:"top"},width:{type:[String,Number],default:750}},created(){},computed:{marginValue(){return this.gutter?-this.gutter/2:0},typeClass(){return"flex"===this.type?"uni-row--flex":""},justifyClass(){return"start"!==this.justify?`uni-row--flex-justify-${this.justify}`:""},alignClass(){return"top"!==this.align?`uni-row--flex-align-${this.align}`:""}}};R.render=function(t,e,s,r,a,p){const f=c;return i(),n(f,{class:l(["uni-row",p.typeClass,p.justifyClass,p.alignClass]),style:d({marginLeft:`${Number(p.marginValue)}rpx`,marginRight:`${Number(p.marginValue)}rpx`})},{default:o((()=>[u(t.$slots,"default",{},void 0,!0)])),_:3},8,["class","style"])},R.__scopeId="data-v-4d73f177";const T={data:()=>({orders:[],current:1,pageSize:3,total:0}),components:{Address:L},async mounted(){let t=await this.getOrders();this.current=t.pageNum,this.pageSize=t.pageSize,this.total=t.total,this.orders=t.list},computed:((t,i)=>{for(var n in i||(i={}))s.call(i,n)&&a(t,n,i[n]);if(e)for(var n of e(i))r.call(i,n)&&a(t,n,i[n]);return t})({},p(["user"])),methods:{getAddress:function(t,e){const s=this.orders.filter((t=>t.taOrder.orderId===e))[0].taOrder;s.address=t,console.log(s)},getOrders:function(){return new Promise(((t,e)=>{f({method:"GET",url:A+"/TaOrderPage",data:{id:m("userId"),type:"user",pageNum:this.current,pageSize:this.pageSize},success:e=>{t(e.data)}})}))},handlePage:async function(t){"next"===t.type?this.current=this.current+1:"prev"===t.type&&(this.current=this.current-1);let e=await this.getOrders();this.current=e.pageNum,this.pageSize=e.pageSize,this.total=e.total,this.orders=e.list},toShop:function(t){h({url:"/pages/components/Food?dinerId="+t})},toChat(t){h({url:"./Chat?orderId="+t})},final:function(t,e){return t*e},async pay(t){console.log(t),0===t.status&&(t.status=1,f({method:"PUT",url:A+"/TaOrder",data:t,success:async t=>{let e=await this.getOrders();this.current=e.pageNum,this.pageSize=e.pageSize,this.total=e.total,this.orders=e.list,g({showCancel:!1,content:"调用支付接口"})}}))},refund:function(t){if(0===t.status)t.status=3;else if(1===t.status)t.status=4;else if(2===t.status)return void g({showCancel:!1,content:"已评价"});f({method:"PUT",url:A+"/TaOrder",data:t,success:async t=>{let e=await this.getOrders();this.current=e.pageNum,this.pageSize=e.pageSize,this.total=e.total,this.orders=e.list,g({showCancel:!1,content:"已退款"})}})},comment:function(t){if(1===t.status)h({url:"./Rate?orderId="+t.orderId});else{let e="";0===t.status?e="尚未支付":2===t.status&&(e="已评价"),g({showCancel:!1,content:e})}}},onLoad(){},onShow(){""===m("userId")&&g({content:"是否登录？",success:function(t){t.confirm?y({url:"../login/login"}):t.cancel&&b({url:"../index/index"})}})}};T.render=function(t,e,s,r,a,u){const l=$,d=C("Address"),p=c,f=I,m=v(_("uni-col"),F),h=v(_("uni-row"),R),g=v(_("uni-card"),k),y=v(_("uni-pagination"),P);return i(),n(p,null,{default:o((()=>[(i(!0),j(w,null,N(a.orders,((t,e)=>(i(),n(g,{title:t.taFood.title,note:"true",extra:"x"+t.taOrder.num,key:e},{footer:o((()=>[x(p,{class:"footer-box"},{default:o((()=>[x(h,null,{default:o((()=>[x(m,null,{default:o((()=>[x(f,{class:"order_bu",type:0===t.taOrder.status?"primary":"default",size:"mini",onClick:e=>u.pay(t.taOrder)},{default:o((()=>[z(S(t.taOrder.status>0?"已":"")+"支付",1)])),_:2},1032,["type","onClick"]),x(f,{class:"order_bu",type:t.taOrder.status<2?"primary":"default",size:"mini",onClick:e=>u.comment(t.taOrder)},{default:o((()=>[z(S(t.taOrder.status>=2?"已":"")+"评价",1)])),_:2},1032,["type","onClick"]),x(f,{class:"order_bu",type:"primary",size:"mini",onClick:e=>u.toShop(t.taFood.dinerId)},{default:o((()=>[z("进店")])),_:2},1032,["onClick"]),x(f,{class:"order_bu",type:"primary",size:"mini",onClick:e=>u.toChat(t.taOrder.orderId)},{default:o((()=>[z("聊天")])),_:2},1032,["onClick"]),x(f,{class:"order_bu",type:t.taOrder.status<2?"primary":"default",size:"mini",onClick:e=>u.refund(t.taOrder)},{default:o((()=>[z(S(t.taOrder.status>=3?"已":"")+"退款",1)])),_:2},1032,["type","onClick"])])),_:2},1024)])),_:2},1024)])),_:2},1024)])),default:o((()=>[x(l,{style:{color:"#DC143C"}},{default:o((()=>[z("总价： "),x(l,{style:{"text-decoration":"line-through"}},{default:o((()=>[z("¥"+S(u.final(t.taOrder.num,t.taFood.price).toFixed(2)),1)])),_:2},1024),x(l,null,{default:o((()=>[z("¥"+S(u.final(t.taOrder.finalTotalPrice,1).toFixed(2)),1)])),_:2},1024)])),_:2},1024),x(p,null,{default:o((()=>[x(d,{init:t.taOrder.address,index:t.taOrder.orderId,onSendAddress:u.getAddress,disabled:0!==t.taOrder.status},null,8,["init","index","onSendAddress","disabled"])])),_:2},1024)])),_:2},1032,["title","extra"])))),128)),x(y,{style:{},total:a.total,current:a.current,onChange:u.handlePage,pageSize:a.pageSize},null,8,["total","current","onChange","pageSize"])])),_:1})},T.__scopeId="data-v-770c8b6a",O(T);export default T;