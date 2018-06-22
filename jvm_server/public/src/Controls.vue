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
        this.isEditEnable = !this.isEditEnable;
        this.$emit('editChange', this.isEditEnable)
      },
      onViewClick() {
        this.isPreviewEnable = !this.isPreviewEnable;
        this.$emit('previewChange', this.isPreviewEnable)
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
      border-top: 1px solid red;
      content: ' ';
      top: 50%;
      left: 0;
      right: 0;
      transform: rotate(45deg);
    }
    &.disable::after {
      position: absolute;
      border-top: 1px solid red;
      content: ' ';
      top: 50%;
      left: 0;
      right: 0;
      rotation: 45grad;
      transform: rotate(-45deg);
    }
  }

  .control-icon-element {
    width: 2em;
    height: 2em;
  }
</style>
