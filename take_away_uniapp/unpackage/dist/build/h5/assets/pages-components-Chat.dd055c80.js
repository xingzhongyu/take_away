import{ak as s,u as t,z as e,o as a,c as o,w as l,e as c,k as n,l as d,D as i,B as r,C as u,F as p,p as f,Z as h,R as _,m as k}from"../vendor.69e0273a.js";import{A as m}from"../api.0a8de5c9.js";const g={data:()=>({talkList:[],ajax:{},orderId:0,content:"",ws:null}),onLoad(t){this.orderId=t.orderId,this.websocket.socketTask.onMessage((t=>{this.talkList.push(JSON.parse(t.data)),this.$nextTick((()=>{this.content="",s({scrollTop:99999,duration:0})}))}))},async mounted(){this.talkList=await this.getHistoryMsg(),this.$nextTick((()=>{s({scrollTop:99999,duration:0})}))},inject:["websocket"],computed:{},methods:{re_type:function(s){return"user"===s?"/static/a1.png":"shop"===s?"/static/a2.png":"courier"===s?"/static/a3.png":"?"},async getHistoryMsg(){return new Promise(((s,e)=>{t({url:m+"/TaChat",data:{orderId:this.orderId},method:"GET",success:t=>{s(t.data)}})}))},joinHistoryMsg(){},async send(){let s={time:(new Date).getTime(),content:this.content,type:"user",orderId:this.orderId};this.content="",status=await this.websocket.send(s)}}};g.render=function(s,t,e,m,g,y){const x=f,w=k,T=h,I=_;return a(),o(x,null,{default:l((()=>[c(x,{class:i(["tips color_fff size_12 align_c",{show:g.ajax.loading}]),onClick:y.getHistoryMsg},{default:l((()=>[n(d(g.ajax.loadText),1)])),_:1},8,["class","onClick"]),c(x,{class:"box-1",id:"list-box"},{default:l((()=>[c(x,{class:"talk-list"},{default:l((()=>[(a(!0),r(p,null,u(g.talkList,((s,t)=>(a(),o(x,{key:t,id:`msg-${s.id}`},{default:l((()=>[c(x,{class:i(["item flex_col","user"==s.type?"push":"pull"])},{default:l((()=>[c(w,{src:y.re_type(s.type),mode:"aspectFill",class:"pic"},null,8,["src"]),c(x,{class:"content"},{default:l((()=>[n(d(s.content),1)])),_:2},1024)])),_:2},1032,["class"])])),_:2},1032,["id"])))),128))])),_:1})])),_:1}),c(x,{class:"box-2"},{default:l((()=>[c(x,{class:"flex_col"},{default:l((()=>[c(x,{class:"flex_grow"},{default:l((()=>[c(T,{type:"text",class:"content",modelValue:g.content,"onUpdate:modelValue":t[0]||(t[0]=s=>g.content=s),placeholder:"请输入聊天内容","placeholder-style":"color:#DDD;","cursor-spacing":6},null,8,["modelValue"])])),_:1}),c(I,{class:"send",onClick:y.send},{default:l((()=>[n("发送")])),_:1},8,["onClick"])])),_:1})])),_:1})])),_:1})},g.__scopeId="data-v-aefd9f60",e(g);export default g;
