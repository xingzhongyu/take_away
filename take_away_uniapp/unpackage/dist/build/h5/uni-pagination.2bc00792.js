import{r as e,o as t,c as n,w as a,e as i,k as r,l as s,D as u,B as c,C as l,F as o,p as h,q as d}from"./vendor.69e0273a.js";import{r as p,_ as g}from"./uni-app.es.3ef08382.js";const x={name:"UniPagination",emits:["update:modelValue","input","change"],props:{value:{type:[Number,String],default:1},modelValue:{type:[Number,String],default:1},prevText:{type:String,default:"上一页"},nextText:{type:String,default:"下一页"},current:{type:[Number,String],default:1},total:{type:[Number,String],default:0},pageSize:{type:[Number,String],default:10},showIcon:{type:[Boolean,String],default:!1},pagerCount:{type:Number,default:7}},data:()=>({currentIndex:1,paperData:[]}),computed:{maxPage(){let e=1,t=Number(this.total),n=Number(this.pageSize);return t&&n&&(e=Math.ceil(t/n)),e},paper(){const e=this.currentIndex,t=this.pagerCount,n=this.total,a=this.pageSize;let i=[],r=[],s=Math.ceil(n/a);for(let c=0;c<s;c++)i.push(c+1);r.push(1);const u=i[i.length-(t+1)/2];return i.forEach(((n,a)=>{(t+1)/2>=e?n<t+1&&n>1&&r.push(n):e+2<=u?n>e-(t+1)/2&&n<e+(t+1)/2&&r.push(n):(n>e-(t+1)/2||s-t<n)&&n<i[i.length-1]&&r.push(n)})),s>t?((t+1)/2>=e?r[r.length-1]="...":e+2<=u?(r[1]="...",r[r.length-1]="..."):r[1]="...",r.push(i[i.length-1])):(t+1)/2>=e||e+2<=u||(r.shift(),r.push(i[i.length-1])),r}},watch:{current(e){this.currentIndex=e},value(e){this.currentIndex=e<1?1:e},modelValue(e){this.currentIndex=e<1?1:e}},created(){this.currentIndex=+this.value},methods:{selectPage(e,t){if(parseInt(e))this.currentIndex=e,this.change("current");else{let e=Math.ceil(this.total/this.pageSize);if(t<=1)return void(this.currentIndex-5>1?this.currentIndex-=5:this.currentIndex=1);if(t>=6)return void(this.currentIndex+5>e?this.currentIndex=e:this.currentIndex+=5)}},clickLeft(){1!==Number(this.currentIndex)&&(this.currentIndex-=1,this.change("prev"))},clickRight(){Number(this.currentIndex)>=this.maxPage||(this.currentIndex+=1,this.change("next"))},change(e){this.$emit("input",this.currentIndex),this.$emit("update:modelValue",this.currentIndex),this.$emit("change",{type:e,current:this.currentIndex})}}};x.render=function(x,m,_,f,I,v){const y=h,b=p(e("uni-icons"),g),k=d;return t(),n(y,{class:"uni-pagination"},{default:a((()=>[i(y,{class:"uni-pagination__total is-phone-hide"},{default:a((()=>[r("共 "+s(_.total)+" 条",1)])),_:1}),i(y,{class:u(["uni-pagination__btn",1===I.currentIndex?"uni-pagination--disabled":"uni-pagination--enabled"]),"hover-class":1===I.currentIndex?"":"uni-pagination--hover","hover-start-time":20,"hover-stay-time":70,onClick:v.clickLeft},{default:a((()=>[!0===_.showIcon||"true"===_.showIcon?(t(),n(b,{key:0,color:"#666",size:"16",type:"arrowleft"})):(t(),n(k,{key:1,class:"uni-pagination__child-btn"},{default:a((()=>[r(s(_.prevText),1)])),_:1}))])),_:1},8,["class","hover-class","onClick"]),i(y,{class:"uni-pagination__num uni-pagination__num-flex-none"},{default:a((()=>[i(y,{class:"uni-pagination__num-current"},{default:a((()=>[i(k,{class:"uni-pagination__num-current-text is-pc-hide",style:{color:"#409EFF"}},{default:a((()=>[r(s(I.currentIndex),1)])),_:1}),i(k,{class:"uni-pagination__num-current-text is-pc-hide"},{default:a((()=>[r("/"+s(v.maxPage||0),1)])),_:1}),(t(!0),c(o,null,l(v.paper,((e,c)=>(t(),n(y,{key:c,class:u([{"page--active":e===I.currentIndex},"uni-pagination__num-tag tag--active is-phone-hide"]),onClick:t=>v.selectPage(e,c)},{default:a((()=>[i(k,null,{default:a((()=>[r(s(e),1)])),_:2},1024)])),_:2},1032,["class","onClick"])))),128))])),_:1})])),_:1}),i(y,{class:u(["uni-pagination__btn",I.currentIndex>=v.maxPage?"uni-pagination--disabled":"uni-pagination--enabled"]),"hover-class":I.currentIndex===v.maxPage?"":"uni-pagination--hover","hover-start-time":20,"hover-stay-time":70,onClick:v.clickRight},{default:a((()=>[!0===_.showIcon||"true"===_.showIcon?(t(),n(b,{key:0,color:"#666",size:"16",type:"arrowright"})):(t(),n(k,{key:1,class:"uni-pagination__child-btn"},{default:a((()=>[r(s(_.nextText),1)])),_:1}))])),_:1},8,["class","hover-class","onClick"])])),_:1})},x.__scopeId="data-v-7f89fc32";export{x as _};
