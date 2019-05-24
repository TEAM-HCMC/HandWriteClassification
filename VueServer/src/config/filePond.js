import vueFilePond from 'vue-filepond';
// Import FilePond styles
import 'filepond/dist/filepond.min.css';
// Import image preview plugin styles
// import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css';
// Import image preview and file type validation plugins
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
// import FilePondPluginImagePreview from 'filepond-plugin-image-preview';

const FilePond = vueFilePond(FilePondPluginFileValidateType);

const baseUrl = require('../config/serverUrl.js');

var process = function process(fieldName, file, metadata, load, error, progress, abort) {
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
};

var load = function load(source, load, error, progress, abort, headers) {
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
};

var fetch = function fetch(url, load, error, progress, abort, headers) {
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
};

var restore = function restore(uniqueFileId, load, error, progress, abort, headers) {
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
};

var revert = function revert(uniqueFileId, load, error) {

  // Should remove the earlier created temp file here
  // ...

  // Can call the error method if something is wrong, should exit after
  error('oh my goodness');

  // Should call the load method when done, no parameters required
  load();
};

export default {
  vueFilePond,
  FilePondPluginFileValidateType,
  // FilePondPluginImagePreview,
  FilePond,
}
