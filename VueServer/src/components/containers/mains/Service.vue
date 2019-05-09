<style lang="css" scoped>

.image_input {
    position: relative;
    float: left;
    left: 50%;
}

.image_input > div {
    position: relative;
    float: left;
    left: -50%;
    width: 10em;
    height: 10em;
    margin: 2em;
}

</style>

<template lang="html">

<div class="service">

    <div class="image_input">
        <div class="input_train_image">
            <file-pond name="image/contour/train" ref="pond" label-idle="Drop files here..." allow-multiple="true" accepted-file-types="image/jpeg, image/png" :server="{  process, revert,  restore, load, fetch }" v-bind:files="myFiles" v-on:init="handleFilePondInit" />
        </div>

        <div class="input_compare_image">
            <file-pond name="contour/compare" ref="pond" label-idle="Drop files here..." allow-multiple="true" accepted-file-types="image/jpeg, image/png" :server="{  process, revert,  restore, load, fetch }" v-bind:files="myFiles" v-on:init="handleFilePondInit"
            />
        </div>
    </div>



    <div class="model">
        <div cla<div class="input_train_image">

        </div>

        <div class="input_compare_image">

        </div>
    </div>


</div>

</template>

<script>

const axios = require('axios');

// Import Vue FilePond
import vueFilePond from 'vue-filepond';
// Import FilePond styles
import 'filepond/dist/filepond.min.css';

// Import image preview plugin styles
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css';

// Import image preview and file type validation plugins
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import FilePondPluginImagePreview from 'filepond-plugin-image-preview';

const FilePond = vueFilePond(FilePondPluginFileValidateType, FilePondPluginImagePreview);


const baseUrl = require('../../../config/testUrl.js');

export default {

    data: function() {
        return {
            myFiles: []
        };
    },

    methods: {
        handleFilePondInit: function() {

        },

        process(fieldName, file, metadata, load, error, progress, abort) {
            console.log("process start");
            console.log("file : " + file);
            console.log("metadata : " + metadata);
            const uploadUrl = baseUrl + fieldName;

            // fieldName is the name of the input field
            // file is the actual file object to send
            const formData = new FormData();
            formData.append("image",file);
            formData.append("name","이니셜이 들어가야할 자리");
            console.log("formData create.");

            const request = new XMLHttpRequest();
            request.open('POST', uploadUrl);
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
                    error('request staus was '+request.status);
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
        FilePond
    }

}

</script>
