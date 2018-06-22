<template>
  <div class="container">
    <div class="editor-column" :class="{ full: isEditFull }" v-show="showEdit">
      <editor></editor>
    </div>
    <div class="preview-column" :class="{ full: isPreviewFull }" v-show="showPreview">
      <preview></preview>
    </div>
    <controls
      @editChange="onEditChange"
      @previewChange="onPreviewChange"
      class="controlls-panel">
    </controls>
  </div>

</template>

<script>
  import Editor from './Editor.vue'
  import Preview from './Preview.vue'
  import Controls from './Controls.vue'

  export default {
    name: 'app',
    data() {
      return {
        msg: 'Welcome to Your Vue.js App',
        showEdit: true,
        showPreview: true
      }
    },
    methods: {
      onEditChange(visible) {
        this.showEdit = visible;
      },
      onPreviewChange(visible) {
        this.showPreview = visible;
      }
    },
    computed: {
      isEditFull() {
        return this.showEdit && !this.showPreview;
      },
      isPreviewFull() {
        return !this.showEdit && this.showPreview;
      }
    },
    components: {
      'editor': Editor,
      'preview': Preview,
      'controls': Controls
    }
  }
</script>

<style lang="scss">
  @import "../css/styles.css";

  html, body {
    height: 100%;
    overflow: hidden;
  }

  .container {
    height: 100%;
    width: 100%;
  }

  .editor-column {
    float: left;
    width: 45%;
    height: 98%;
    padding-right: 2em;
    &.full{
      width: 98%;
    }
  }

  .preview-column {
    float: left;
    height: 98%;
    width: 50%;
    &.full{
      width: 98%;
    }
  }

  .controlls-panel {
    position: absolute;
    right: 0;
    top: 0;
  }
</style>
