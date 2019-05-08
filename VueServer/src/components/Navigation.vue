<style lang="css" scoped>

.nav_area {
    background-color: #FFF;
}

.nav_bar {
    font-weight: bold;
    overflow: hidden;
    color: #2F3B52;
}

.uol {
    font-size: 2vw;
    float: left;
    font-weight: bold;
    margin: 1rem 0rem 1rem 0rem;
    padding-left: 2vw;
}

.uol>li {
    float: left;
    width: 10vw;
    height: 10vw;
}

.vertical_align {
    display: block;
    margin-top: 40%;
}

.logo {
    width: 100%;
    height: 100%;
}

</style>

<template lang="html">

<section class="nav_area">
    <div class="nav_bar">

        <ul class="uol">
            <li>
                <router-link to="/">
                    <img src="../assets/logo.png" alt="" class="logo">
                </router-link>
            </li>
            <li>
                <div class="vertical_align">
                    <router-link to="/">
                        HOME
                    </router-link>
                </div>
            </li>
            <li>
                <div class="vertical_align">
                    <router-link to="/service">
                        SERVICE
                    </router-link>
                </div>
            </li>
            <li>
                <div class="vertical_align">
                    <router-link to="/about">
                        ABOUT
                    </router-link>
                </div>
            </li>
        </ul>

    </div>
</section>

</template>

<script>

export default {}

</script>
