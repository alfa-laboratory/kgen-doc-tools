<template>
  <div class="controlls-block">
    <div class="controll-element" :class="{ disable: !isEditEnable }" @click="onDocumentClick">
      <img class="control-icon-element" src="/images/document.svg"/>
    </div>
    <div class="controll-element" :class="{ disable: !isPreviewEnable }" @click="onViewClick">
      <img class="control-icon-element" src="/images/view.svg"/>
    </div>
  </div>

</template>

<script>
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
        isPreviewEnable: true
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
      }
    }
  }
</script>

<style lang="scss">
  .controlls-block {
    z-index: 999;
    padding: 0.5em;
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
