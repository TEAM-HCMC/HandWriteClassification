<style lang="css" scoped>

.service {
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

.model {
    position: relative;
    width: 100%;
    margin-top: 8em;
    margin-bottom: 3em;
}

.model > div {
    position: relative;
    display: inline-block;
    margin-left: 3em;
    margin-right: 3em;
}

</style>

<template lang="html">

<div class="service">

    <div class="image">
        <div class="input_train_image">
            <span class="description">
            검증 기준 <br>이미지를 업로드
          </span>
            <file-pond name="/image/save/train" ref="pond" label-idle="Drop files here..." allow-multiple="true" accepted-file-types="image/jpeg, image/png" :server="{  process, revert,  restore, load, fetch }" v-bind:files="myFiles" />
            <div class="contour_train_image">
                <button v-on:click="contour('train/')">이미지 업로드 완료</button>
            </div>
        </div>

        <div class="input_compare_image">
            <div class="description">
                검증 대상
                <br>이미지를 업로드
            </div>
            <file-pond name="/image/save/compare" ref="pond" label-idle="Drop files here..." allow-multiple="true" accepted-file-types="image/jpeg, image/png" :server="{  process, revert,  restore, load, fetch }" v-bind:files="myFiles" />
            <div class="contour_train_image">
                <button v-on:click="contour('compare/')">이미지 업로드 완료</button>
            </div>
        </div>
    </div>



    <div class="model">
        <div class="train_model">
            <div class="description">
                모델 학습 시키기
            </div>
            <button v-on:click="train">모델 학습 시작</button>
        </div>

        <div class="compare_model">
            <div class="description">
                필적 검증 하기
            </div>
            <button v-on:click="compare">필적 검증 시작</button>
        </div>
    </div>

    <div class="result">
        <button v-on:click="getResult">결과확인</button>
        <div class="correct">
            {{rate.correct}}
        </div>
        <div class="wrong">
            {{rate.wrong}}
        </div>
    </div>


</div>

</template>

<script>

// Import Vue FilePond
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
            }
        };
    },

    created(){
      if(cookieUtils.getJwt()===null){
        // this.$router.push("/home");
      }
    },

    methods: {
        contour(direction) {
                image.startContour(direction);
            },
            train() {
                model.startTrain(localStorage.getItem('name'));
            },

            compare() {
                model.startCompare(localStorage.getItem('name'));
            },
            getResult() {
                model.getResult().then((resDto) => {
                    this.rate.correct = resDto.correct;
                    this.rate.wrong = resDto.wrong;
                });
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
        fileUtils
    }

}

</script>
