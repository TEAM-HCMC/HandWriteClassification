<style lang="css" scoped>

.body_area {
    margin: 0 auto;
    height: 100%;
}

.content {
    overflow: hidden;
    height: 100%;
}

.content>div {
    float: left;
}

.content-left {
    width: 80%;
    height: 100%;
}

.content-right {
    width: 20%;
    height: 100%;
    box-shadow: 3px -3px 3px -1px #42b983 inset;
}

/* .shadow {
    box-shadow: 5px 10px 10px rgba(0, 0, 0, 0.1)
} */


</style>

<template lang="html">

<section class="body_area">

    <div class="content">

        <div class="content-left">
            <Main></Main>
        </div>

        <div class="content-right">
            <Side></Side>
        </div>

    </div>

</section>

</template>

<script>

import Main from './containers/Main.vue'
import Side from './containers/Side.vue'


export default {
    components: {
        'Main': Main,
        'Side': Side
    }
}

</script>
