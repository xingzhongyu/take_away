var e=Object.defineProperty,t=Object.defineProperties,s=Object.getOwnPropertyDescriptors,a=Object.getOwnPropertySymbols,r=Object.prototype.hasOwnProperty,o=Object.prototype.propertyIsEnumerable,i=(t,s,a)=>s in t?e(t,s,{enumerable:!0,configurable:!0,writable:!0,value:a}):t[s]=a,n=(e,t)=>{for(var s in t||(t={}))r.call(t,s)&&i(e,s,t[s]);if(a)for(var s of a(t))o.call(t,s)&&i(e,s,t[s]);return e},l=(e,a)=>t(e,s(a));import{t as c,r as d,o as p,c as u,w as g,e as m,k as h,l as S,n as f,m as y,p as v,q as b,u as z,v as I,x as _,y as w,z as P,A as j,B as x,C as B,F as N}from"../vendor.69e0273a.js";import{_ as O}from"../uni-pagination.2bc00792.js";import{r as T}from"../uni-app.es.3ef08382.js";import{_ as C}from"../uni-card.32412ff4.js";/* empty css                                                          */import{A as k}from"../api.0a8de5c9.js";const D={name:"Shop",props:{dinerId:Number,title:String,score:Number,imgUrl:String,description:String,address:String},setup:e=>l(n({},c(e)),{clickCard:()=>{f({url:"/pages/components/Food?dinerId="+e.dinerId+"&address="+e.address})}})};D.render=function(e,t,s,a,r,o){const i=y,n=v,l=b,c=T(d("uni-card"),C);return p(),u(n,{class:"card_shop"},{default:g((()=>[m(c,{isShadow:!0,title:s.title,subTitle:"副标题",mode:"basic",extra:""+s.score,onClick:a.clickCard},{default:g((()=>[m(n,null,{default:g((()=>[m(n,null,{default:g((()=>[m(i,{mode:"aspectFill",src:s.imgUrl,class:"img"},null,8,["src"])])),_:1}),m(n,{class:"content-box"},{default:g((()=>[m(l,{style:{"font-size":"12px"}},{default:g((()=>[h(S(s.description),1)])),_:1})])),_:1})])),_:1})])),_:1},8,["title","extra","onClick"])])),_:1})},D.__scopeId="data-v-1a140435";const U={name:"Index",components:{Shop:D},onNavigationBarSearchInputChanged(e){this.searchWord=e.text},async onNavigationBarButtonTap(){let e=await this.getShops(this.searchWord);this.current=e.pageNum,this.pageSize=e.pageSize,this.total=e.total,this.shops=e.list},methods:{getShops:function(e=null){console.log(this);let t=k+"/TaDinerPage";return null!==e&&(t=k+"/TaDinerPage?word="+e),new Promise(((e,s)=>{z({method:"GET",url:t,data:{pageNum:this.current,pageSize:this.pageSize},success:t=>{e(t.data)}})}))}},setup(){let e=I();_((async()=>{let e=await a();t.shops=e.list,s.total=e.total,s.current=e.pageNum,s.pageSize=e.pageSize,r()}));const t=w({shops:[]}),s=w({pageSize:4,total:0,current:1}),a=(e=null)=>{let t=k+"/TaDinerPage";return null!==e&&(t=k+"/TaDinerPage?word="+e),new Promise(((e,a)=>{z({method:"GET",url:t,data:{pageNum:s.current,pageSize:s.pageSize},success:t=>{e(t.data)}})}))},r=()=>{console.log("test"),new qq.maps.Geolocation("G4MBZ-IGILU-4IDVJ-2BHBM-BWBHJ-3XFWT","myKey").getLocation(o,i)},o=t=>{if(console.log(t),console.log(t.district),void 0!==t.city&&void 0!==t.district&&void 0!==t.province){let s=t.province+"/"+t.city+"/"+t.district;e.commit("setAddress",s),sessionStorage.address=s;let a=t.province+"/"+t.city;sessionStorage.address2=a}else sessionStorage.address="山东省/济南市/历下区",sessionStorage.address2="山东省/济南市"},i=e=>{console.log(e),console.log("定位失败，请重试！"),r()};return l(n(n({},c(t)),c(s)),{handlePage:async e=>{"next"===e.type?s.current=s.current+1:"prev"===e.type&&(s.current=s.current-1);let r=await a();t.shops=r.list,s.total=r.total,s.current=r.pageNum,s.pageSize=r.pageSize}})}};U.render=function(e,t,s,a,r,o){const i=j("shop"),n=T(d("uni-pagination"),O),l=v;return p(),u(l,null,{default:g((()=>[(p(!0),x(N,null,B(e.shops,(e=>(p(),u(i,{style:{},title:e.title,score:e.score,dinerId:e.dinerId,description:e.description,imgUrl:e.imgUrl,key:e.dinerId,address:e.address},null,8,["title","score","dinerId","description","imgUrl","address"])))),128)),m(n,{style:{},total:e.total,current:e.current,onChange:a.handlePage,pageSize:e.pageSize},null,8,["total","current","onChange","pageSize"])])),_:1})},U.__scopeId="data-v-57c3d7a7",P(U);export default U;
