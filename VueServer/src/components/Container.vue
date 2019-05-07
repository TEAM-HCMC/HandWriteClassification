<style lang="css" scoped>

.body_area {
    margin: 0 auto;
}

.content {
    overflow: hidden;
}

.content>div {
    float: left;
}

.content-left {
    width: 75%;
}

.content-right {
    width: 25%;
}


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
