<template>
  <div class="controlls-block">
    <div class="controlls-subblock">
      <div class="controll-element" @click="onDownload">
        <img class="control-icon-element" src="/images/download.svg"/>
      </div>
      <div class="controll-element" @click="onUpload">
        <img class="control-icon-element" src="/images/upload.svg"/>
      </div>
    </div>

    <div class="controlls-subblock">
      <div class="controll-element" :class="{ disable: !isEditEnable }" @click="onDocumentClick">
        <img class="control-icon-element" src="/images/document.svg"/>
      </div>
      <div class="controll-element" :class="{ disable: !isPreviewEnable }" @click="onViewClick">
        <img class="control-icon-element" src="/images/view.svg"/>
      </div>
    </div>

    <input type="file" id="fileUploadInput" @change="onChange($event)" style="display: none" accept="image">
  </div>

</template>

<script>
  import axios from "axios"

  function enableEditor(self) {
    self.isEditEnable = !self.isEditEnable;
    self.$emit('editChange', self.isEditEnable);
  }

  function enablePreview(self) {
    self.isPreviewEnable = !self.isPreviewEnable;
    self.$emit('previewChange', self.isPreviewEnable)
  }

  export default {
    name: 'controlls',
    data() {
      return {
        isEditEnable: true,
        isPreviewEnable: true,
        uploadFileName: String
      }
    },
    methods: {
      onDocumentClick() {
        enableEditor(this);
        if (!this.isEditEnable && !this.isPreviewEnable) {
          enablePreview(this);
        }
      },
      onViewClick() {
        enablePreview(this);
        if (!this.isEditEnable && !this.isPreviewEnable) {
          enableEditor(this);
        }
      },
      onDownload() {
        const link = document.createElement('a');
        link.href = "/downloadZip";
        document.body.appendChild(link);
        link.click();
      },
      onUpload() {
        const element = document.getElementById('fileUploadInput');
        element.click();
      },
      onChange(event) {
        const uploadingFile = event.target.files[0];

        let formData = new FormData();
        formData.append('files1', uploadingFile);
        axios.post('/uploadImage',
          formData,
          {
            headers: {
              'Content-Type': 'multipart/form-data'
            },
            params: {
              name: uploadingFile.name
            }
          }
        )
          .then(function () {
            console.log('SUCCESS!!');
          })
          .catch(function () {
            console.log('FAILURE!!');
          });
      }
    }
  }
</script>

<style lang="scss">
  .controlls-block {
    z-index: 999;
    padding: 0.5em;
  }

  .controlls-subblock {
    padding-left: 0.5em;
    padding-right: 0.5em;
    display: inline-block;
  }

  .controll-element {
    display: inline-block;
    cursor: pointer;
    position: relative;
    &.disable {
      visibility: visible;
    }
    &.disable::before {
      position: absolute;
      border-top: 2px solid red;
      content: ' ';
      top: 50%;
      left: -5px;
      right: -5px;
      transform: rotate(45deg);
    }
    &.disable::after {
      position: absolute;
      border-top: 2px solid red;
      content: ' ';
      top: 50%;
      left: -5px;
      right: -5px;
      rotation: 45grad;
      transform: rotate(-45deg);
    }
  }

  .control-icon-element {
    width: 2em;
    height: 2em;
  }
</style>
