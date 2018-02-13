<template>
  <div class="container">
    <div class="editor-column">
      <editor></editor>
    </div>
    <div class="preview-column">
      <preview></preview>
    </div>
  </div>

</template>

<script>
  import Editor from './Editor.vue'
  import Preview from './Preview.vue'

  export default {
    name: 'app',
    data() {
      return {
        msg: 'Welcome to Your Vue.js App'
      }
    },
    components: {
      'editor': Editor,
      'preview': Preview
    }
  }
</script>

<style>
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
  }

  .preview-column {
    float: left;
    height: 98%;
    width: 50%;

  }
</style>
