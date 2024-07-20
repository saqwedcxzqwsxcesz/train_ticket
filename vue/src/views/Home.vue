<template>
  <div :style="backgroundDiv">
    <div style="">
      <div style="font-size: 30px; text-align: center; padding: 150px 0 ;font-size: 100px" >欢迎使用</div>
    </div>

  </div>


</template>

<script>
import request from "@/utils/request";

export default {
  name: "Home",
  data() {
    return {
      backgroundDiv: {
        backgroundImage: 'url(' + require('../assets/img.png') + ')',
        backgroundRepeat: 'no-repeat',
        backgroundSize: '100% 100%',
      },
    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
