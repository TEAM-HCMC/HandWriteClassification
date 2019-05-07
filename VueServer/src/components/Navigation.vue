<template lang="html">

<section class="nav_area">
  <div class="nav_bar">

    <ul class="unorder_list">
      <li>
        <img src="../assets/logo.png" alt="" class="logo">
      </li>
      <li class="home">
        HOME
      </li>
      <li class="service">
        SERVICE
      </li>
      <li class="">
        TEMP
      </li>
      <li class="about">
        ABOUT
      </li>
    </ul>

  </div>
</section>


</template>









<script>
export default {}
</script>










<style lang="css" scoped>
.nav_area{
  position: relative;
  background-color: #FFF;

  border:1px solid gray
}

.nav_bar{
  position: relative;
  padding: 10px;
  width: 1080px;
  margin: 0 auto;
  font-size: 20px;
  background-color: #FFF;
  color:#2F3B52;


  border:1px solid gray;
}

.unorder_list li{
  position: relative;
  font-weight: bold;
  width:80px;
  height: 100px;

  border:1px solid gray;
}

.logo{

  width: 80px;


}
</style>
