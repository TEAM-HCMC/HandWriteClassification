<style lang="css" scoped>

.service {
    font-family: 'BMHANNAPro';
    height: 100%;
    width: 80%;
    margin: 0 auto;
    position: relative;
}

.image {
    position: relative;
    width: 100%;
    margin-bottom: 3em;
}

.description {
    font-weight: bold;
    font-size: 1.4vw;
}

.image > div {
    position: relative;
    display: inline-block;
    margin-left: 3em;
    margin-right: 3em;
}

.imgs {
    margin: 1em;
}

.fas fa-angle-down {
    color: #42b983;
}

ul {
    list-style-type: none;
    padding-left: 0px;
    margin-top: 0;
    text-align: left;
}

li {
    float: left;
    display: flex;
    padding: 0 0.9rem;
    background: white;
    border-radius: 5px;
}

.list-enter-active,
.list-leave-active {
    transition: all 1s;
}

.list-enter,
.list-leave-to {
    opacity: 0;
    transform: translateY(30px);
}

.step {
    position: relative;
    height: 100vh;
}

.train {
    padding-top: 20vh;
    height: 20%;
}

.input_train_image {
    padding-top: 1vh;
    height: 30vh;
    overflow: auto;
}

.input_compare_image {
    height: 30vh;
    overflow: auto;
}

.image_ok {
    padding-top: 19vh;
}

.arrow_down {
    position: absolute;
    display: block;
    width: 100%;
    bottom: 1em;
}

.arrow_up {
    display: block;
    width: 100%;
    padding-top: 2.5vh;
    padding-bottom: 2.5vh;
}

i {
    cursor: pointer;
}

.compare {
    padding-top: 20vh;
}

.model {
    position: relative;
    width: 100%;
    height: 100%;
    padding-top: 20vh;
}

.result {
    width: 100%;
    height: 100%;
    padding-top: 20vh;
    overflow: auto;
}

button {
    width: auto;
}

</style>

<template lang="html">

<div class="service">

    <div class="first step">

        <div class="train">
            <div class="input_train_image">
                <span class="description">
              검증 기준 <br>이미지를 업로드
            </span>
                <file-pond name="/image/save/train" ref="pond" label-idle="Drop files here..." allow-multiple="true" accepted-file-types="image/jpeg, image/png" :server="{  process, revert,  restore, load, fetch }" v-bind:files="myFiles" />
            </div>


            <div class="image_ok">
                <div class="showStep2">
                    <button class="form-control btn btn-primary" v-if="!isTrainImageMaking()" v-on:click="contour('train/')">이미지 업로드 완료</button>
                    <button class="form-control btn btn-primary" v-if="isTrainImageMaking()">이미지 업로드 중</button>
                </div>
            </div>

        </div>

        <div class="arrow_down">
            <i class="fas fa-angle-down fa-4x" v-on:click="first_next"></i>
        </div>

    </div>

    <div class="second step">

        <div class="compare">

            <div class="arrow_up">
                <i class="fas fa-angle-up fa-4x" v-on:click="second_back"></i>
            </div>

            <div class="input_compare_image">
                <div class="description">
                    검증 대상
                    <br>이미지를 업로드
                </div>
                <file-pond name="/image/save/compare" ref="pond" label-idle="Drop files here..." allow-multiple="true" accepted-file-types="image/jpeg, image/png" :server="{  process, revert,  restore, load, fetch }" v-bind:files="myFiles" />

            </div>
        </div>

        <div class="image_ok">
            <div class="showStep4">
                <button class="form-control btn btn-primary" v-if="!isCompareImageMaking()" v-on:click="contour('compare/')">이미지 업로드 완료</button>
                <button class="form-control btn btn-primary" v-if="isCompareImageMaking()">이미지 업로드 중</button>
            </div>
        </div>

        <div class="arrow_down">
            <i class="fas fa-angle-down fa-4x" v-on:click="second_next"></i>
        </div>

    </div>

    <div class="third step">

        <div class="model">

            <div class="arrow_up">
                <i class="fas fa-angle-up fa-4x" v-on:click="third_back"></i>
            </div>

            <div class="train_model">
                <div class="description">
                </div>
                <button class="form-control btn btn-primary" v-if="!isExistTrainImage()">학습을 위한 이미지가 없습니다.</button>
                <button class="form-control btn btn-primary" v-if="isExistTrainImage()&&!isTrainModelMaking()" v-on:click="train">모델 학습 시작</button>
                <button class="form-control btn btn-primary" v-if="isTrainModelMaking()">모델 학습 중</button>
            </div>
            <br>
            <br>
            <br>
            <br>
            <div class="compare_model">
                <div class="description">
                </div>
                <!-- isExistCompareImage -->
                <button class="form-control btn btn-primary" v-if="!isExistTrainModel()">학습된 모델이 필요합니다.</button>
                <button class="form-control btn btn-primary" v-if="isExistTrainModel()&&!isExistCompareImage()">검증대상 이미지가 필요합니다.</button>
                <button class="form-control btn btn-primary" v-if="isExistTrainModel()&&isExistCompareImage()&&!isCompareModelMaking()" v-on:click="compare">필적 검증 시작</button>
                <button class="form-control btn btn-primary" v-if="isCompareModelMaking()">필적 검증 중</button>
            </div>

        </div>

        <div class="arrow_down">
            <i class="fas fa-angle-down fa-4x" v-on:click="third_next"></i>
        </div>

    </div>

    <div class="fourth step">

        <div class="result">
            <div class="arrow_up">
                <i class="fas fa-angle-up fa-4x" v-on:click="fourth_back"></i>
            </div>

            <button class="form-control btn btn-primary" v-if="!isExistCompareModel()" >검증한 이미지 존재하지 않습니다.</button>
            <button class="form-control btn btn-primary" v-if="isExistCompareModel()" v-on:click="getResult">결과확인</button>
            <br><br>
            <div class="correct">
                {{rate.correct}}
            </div>
            <div class="wrong">
                {{rate.wrong}}
            </div>
            <br><br>
            <transition-group name="list" tag="ul">
                <li v-for="(img,index) in compared.imgs" :key="img">
                    <span>{{compared.accuracy[index]}}</span>
                    <img class="imgs" :src="img" />
                </li>
            </transition-group>
        </div>

    </div>

    <div class="myModal">
        <myModal v-if="showModal" v-on:click="backToHome">
            <h3 slot="header">로그인 해주세요!</h3>
            <span slot="body">로그인 후 이용할 수 있습니다.</span>
            <span slot="footer" v-on:click="backToHome">
        확인
        <i class="closeModalBtn fas fa-times" aria-hidden="true"></i>
      </span>
        </myModal>
    </div>

</div>

</template>

<script>
import myModal from '../../../utils/Modal.vue'

const fileUtils = require('../../../config/filepond');
const image = require('../../../http/image');
const model = require('../../../http/model');

const baseUrl = require('../../../config/serverUrl.js');
const cookieUtils = require('../../../utils/cookie.js');

export default {

  data: function() {
    return {
      myFiles: [],
      rate: {
        'correct': "",
        'wrong': ""
      },
      compared: {
        imgs: [],
        accuracy: [],
      },

      showModal: false,
    };
  },

  created() {
    if (cookieUtils.getJwt() === null) {
      this.showModal = true;
    }
  },

  mounted() {
    var secondDiv = document.getElementsByClassName("second");
    secondDiv[0].style.display = "none";
    var thirdDiv = document.getElementsByClassName("third");
    thirdDiv[0].style.display = "none";
    var fourthDiv = document.getElementsByClassName("fourth");
    fourthDiv[0].style.display = "none";
  },

  methods: {

    first_next() {
      var firstDiv = document.getElementsByClassName("first");
      firstDiv[0].style.display = "none";
      var secondDiv = document.getElementsByClassName("second");
      secondDiv[0].style.display = "block";
    },

    second_back() {
      var firstDiv = document.getElementsByClassName("first");
      firstDiv[0].style.display = "block";
      var secondDiv = document.getElementsByClassName("second");
      secondDiv[0].style.display = "none";
    },

    second_next() {
      var secondDiv = document.getElementsByClassName("second");
      secondDiv[0].style.display = "none";
      var thirdDiv = document.getElementsByClassName("third");
      thirdDiv[0].style.display = "block";
    },

    third_back() {
      var secondDiv = document.getElementsByClassName("second");
      secondDiv[0].style.display = "block";
      var thirdDiv = document.getElementsByClassName("third");
      thirdDiv[0].style.display = "none";
    },

    third_next() {
      var thirdDiv = document.getElementsByClassName("third");
      thirdDiv[0].style.display = "none";
      var fourthDiv = document.getElementsByClassName("fourth");
      fourthDiv[0].style.display = "block";
    },

    fourth_back() {
      var thirdDiv = document.getElementsByClassName("third");
      thirdDiv[0].style.display = "block";
      var fourthDiv = document.getElementsByClassName("fourth");
      fourthDiv[0].style.display = "none";
    },

    isTrainImageMaking() {
      return (this.$store.state.status.trainImageFlag == "생성 중");
    },
    isExistTrainImage() {
      return (this.$store.state.status.trainImageFlag == "생성 완료");
    },

    isCompareImageMaking() {
      return (this.$store.state.status.compareImageFlag == "생성 중");
    },
    isExistCompareImage() {
      return (this.$store.state.status.compareImageFlag == "생성 완료");
    },

    isTrainModelMaking() {
      return (this.$store.state.status.trainModelFlag == "생성 중");
    },
    isCompareModelMaking() {
      return (this.$store.state.status.compareModelFlag == "생성 중");
    },
    isExistTrainModel() {
      return (this.$store.state.status.trainModelFlag == "생성 완료");
    },
    isExistCompareModel() {
      return (this.$store.state.status.compareModelFlag == "생성 완료");
    },

    backToHome() {
      this.showModal = false;
      this.$router.push("/");
    },

    contour(direction) {
      if (direction == "train/") {
        this.$store.state.status.trainImageFlag = "생성 중";
      } else {
        this.$store.state.status.compareImageFlag = "생성 중";
      }
      image.startContour(direction);
    },

    train() {
      this.$store.state.status.trainModelFlag = "생성 중";
      model.startTrain(this.$store.state.name);
    },

    compare() {
      this.$store.state.status.compareModelFlag = "생성 중";
      model.startCompare(this.$store.state.name);
    },

    getResult() {
      if (this.compared.imgs.length === 0) {
        model.getResult().then((resDto) => {
          this.rate.correct = resDto.correct;
          this.rate.wrong = resDto.wrong;
        });

        image.getComparedImgs().then((imgUrls) => {
          this.compared.imgs = [];
          this.compared.accuracy = [];
          imgUrls.forEach((now, idx, array) => {
            this.compared.imgs.push(now.url);
            this.compared.accuracy.push(now.accuracy);
          });
        });
      } else {
        this.rate.correct = "";
        this.rate.wrong = "";
        this.compared.imgs = [];
        this.compared.accuracy = [];
      }
    },

    process(fieldName, file, metadata, load, error, progress, abort) {
      console.log("process start");
      console.log("file : " + file);
      console.log("metadata : " + metadata);
      const uploadUrl = baseUrl + fieldName;

      // fieldName is the name of the input field
      // file is the actual file object to send
      const formData = new FormData();
      formData.append("image", file);
      console.log("formData create.");

      const request = new XMLHttpRequest();
      request.open('POST', uploadUrl);
      request.setRequestHeader("jwt", cookieUtils.getJwt());
      console.log("XMLHttpRequest open.");

      // Should call the progress method to update the progress to 100% before calling load
      // Setting computable to false switches the loading indicator to infinite mode
      request.upload.onprogress = (e) => {
        console.log("request.upload.onprogress.");
        progress(e.lengthComputable, e.loaded, e.total);
      };


      // Should call the load method when done and pass the returned server file id
      // this server file id is then used later on when reverting or restoring a file
      // so your server knows which file to return without exposing that info to the client
      request.onload = function() {
        console.log("request.onload");
        if (request.status >= 200 && request.status < 300) {
          // the load method accepts either a string (id) or an object
          load(request.responseText);
        } else {
          // Can call the error method if something is wrong, should exit after
          error('request staus was ' + request.status);
        }
      };

      console.log("now sending.");
      request.send(formData);

      // Should expose an abort method so the request can be cancelled
      return {
        abort: () => {
          // This function is entered if the user has tapped the cancel button
          request.abort();

          // Let FilePond know the request has been cancelled
          abort();
        }
      };
    },

    load: (source, load, error, progress, abort, headers) => {
      // Should request a file object from the server here
      // ...

      // Can call the error method if something is wrong, should exit after
      error('oh my goodness');

      // Can call the header method to supply FilePond with early response header string
      // https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/getAllResponseHeaders
      headers(headersString);

      // Should call the progress method to update the progress to 100% before calling load
      // (endlessMode, loadedSize, totalSize)
      progress(true, 0, 1024);

      // Should call the load method with a file object or blob when done
      load(file);

      // Should expose an abort method so the request can be cancelled
      return {
        abort: () => {
          // User tapped cancel, abort our ongoing actions here

          // Let FilePond know the request has been cancelled
          abort();
        }
      };
    },

    fetch: (url, load, error, progress, abort, headers) => {
      // Should get a file object from the URL here
      // ...

      // Can call the error method if something is wrong, should exit after
      error('oh my goodness');

      // Can call the header method to supply FilePond with early response header string
      // https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/getAllResponseHeaders
      headers(headersString);

      // Should call the progress method to update the progress to 100% before calling load
      // (computable, loadedSize, totalSize)
      progress(true, 0, 1024);

      // Should call the load method with a file object when done
      load(file);

      // Should expose an abort method so the request can be cancelled
      return {
        abort: () => {
          // User tapped abort, cancel our ongoing actions here

          // Let FilePond know the request has been cancelled
          abort();
        }
      };
    },

    restore: (uniqueFileId, load, error, progress, abort, headers) => {
      // Should get the temporary file object from the server
      // ...

      // Can call the error method if something is wrong, should exit after
      error('oh my goodness');

      // Can call the header method to supply FilePond with early response header string
      // https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/getAllResponseHeaders
      headers(headersString);

      // Should call the progress method to update the progress to 100% before calling load
      // (computable, loadedSize, totalSize)
      progress(true, 0, 1024);

      // Should call the load method with a file object when done
      load(serverFileObject);

      // Should expose an abort method so the request can be cancelled
      return {
        abort: () => {
          // User tapped abort, cancel our ongoing actions here

          // Let FilePond know the request has been cancelled
          abort();
        }
      };
    },

    revert: (uniqueFileId, load, error) => {

      // Should remove the earlier created temp file here
      // ...

      // Can call the error method if something is wrong, should exit after
      error('oh my goodness');

      // Should call the load method when done, no parameters required
      load();
    }
  },
  components: {
    fileUtils,
    myModal
  },

}
</script>
