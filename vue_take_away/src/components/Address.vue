<template>
  <div class="app">
    <!--默认使用-->
    <elui-china-area-dht @change="onChange"></elui-china-area-dht>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import { EluiChinaAreaDht } from 'elui-china-area-dht'
const chinaData = new EluiChinaAreaDht.ChinaArea().chinaAreaflat
export default defineComponent({
    components: {
        EluiChinaAreaDht,
    },
    setup(props,context) {
        function onChange(e) {
            let a=[]
          a[0]=chinaData[e[0]]
          a[1]=chinaData[e[1]]
          a[2]=chinaData[e[2]]
          context.emit('send_address',a)
        }
        return {
            onChange,
        }
    },
})
</script>