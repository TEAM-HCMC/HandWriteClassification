<style lang="css" scoped>

.home {
    margin-top: 15%;
    width: 100%;
}

.carousel-inner > .item >img,
.carousel-inner > .item > a >img {
    weight: 100%;
    height: 50vh;
    margin: 0 auto;
}

.carousel-inner {
    height: 60vh;
}

#myCarousel {
    width: 80%;
    margin: 0 auto;
}

.carousel-caption {}

</style>

<template lang="html">

<div class="home">
    <h1>Hello Capstone!</h1>
    <div id="myCarousel" class="carousel slide" data-ride="carousel">

        <!--페이지-->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
            <li data-target="#myCarousel" data-slide-to="3"></li>
            <li data-target="#myCarousel" data-slide-to="4"></li>
        </ol>
        <!--페이지-->

        <div class="carousel-inner">
            <!--슬라이드1-->
            <div class="item active">
                <img src="../../../assets/logo.png" alt="First slide">
                <div class="container">
                    <div class="carousel-caption">
                        <h1>무엇을 위한 서비스인가요?</h1>
                        <p>원래 있던 필적이미지들을 바탕으로 비교하고 싶은 글씨들이 그사람이 쓴 것인지 판별해주는 서비스입니다!</p>
                    </div>

                </div>
            </div>
            <!--슬라이드1-->

            <!--슬라이드2-->
            <div class="item">
                <img src="../../../assets/step1.png" data-src="" alt="Second slide">
                <div class="container">
                    <div class="carousel-caption">
                        <h1>Step 1</h1>
                        <p>우선, 판별하고싶은 사람의 글씨가 담긴 이미지들을 업로드 합니다.</p>
                    </div>
                </div>
            </div>
            <!--슬라이드2-->

            <!--슬라이드3-->
            <div class="item">
                <img src="../../../assets/step2.png" data-src="" alt="Third slide">
                <div class="container">
                    <div class="carousel-caption">
                        <h1>Step 2</h1>
                        <p>그리고 업로드한 글씨체와 비교하고싶은 이미지를 업로드합니다.</p>
                    </div>
                </div>
            </div>
            <!--슬라이드3-->

            <!--슬라이드4-->
            <div class="item">
                <img src="../../../assets/step3.png" data-src="" alt="Third slide">
                <div class="container">
                    <div class="carousel-caption">
                        <h1>Step 3</h1>
                        <p>모델 학습 버튼을 눌러 판별하기위한 과정을 수행합니다.</p>
                    </div>
                </div>
            </div>
            <!--슬라이드4-->

            <!--슬라이드5-->
            <div class="item">
                <img src="../../../assets/step4.png" data-src="" alt="Third slide">
                <div class="container">
                    <div class="carousel-caption">
                        <h1>Finally</h1>
                        <p>결과 확인하기를 눌러 비교하기 위해 넣은 글씨체가 처음 이미지들과 얼마나 비슷했는지 확인합니다!</p>
                    </div>
                </div>
            </div>
            <!--슬라이드5-->
        </div>

        <!--이전, 다음 버튼-->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
    </div>

</div>

</template>

<script>
export default {


}
</script>
