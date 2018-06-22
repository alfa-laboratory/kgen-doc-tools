<template>
  <div class="preview-box">
    <template v-if="path !== undefined">
      <h2><a :href="requestPath" target="_blank">Preview</a></h2>
    </template>
    <template v-if="path === undefined">
      <h2>Preview</h2>
    </template>
    <div id="frame-parent" class="preview-frame" v-if="path !== undefined">
      <iframe id="frame-element" class="frame-view" :src="requestPath" @load="onLoad">
      </iframe>
    </div>
  </div>

</template>

<script>
  export default {
    name: 'preview',
    data() {
      return {
        dataPosition: 0,
        path: undefined
      }
    },
    computed: {
      requestPath() {
        return "/pages/" + this.path.replace(".md",".html");
      }
    },
    mounted() {
      this.$parent.$on('updated', () => this.update());

      this.$parent.$on('change', (path) => {
        console.log('receive path:'+path);
        this.path = path;
      });

      this.$parent.$on('disable', () => this.path = undefined);
    },
    methods: {
      onLoad: function () {
        document.getElementById("frame-element").contentWindow.document.getElementById("data-column").scrollTop = this.dataPosition;
      },
      update() {
        const element = document.getElementById("frame-element").contentWindow.document.getElementById("data-column");
        if (element !== null) {
          this.dataPosition = element.scrollTop
        }

        document.getElementById("frame-element").contentWindow.location.reload();
      }
    }
  }
</script>

<style>
  .preview-box {
    width: 100%;
    height: 100%;
  }

  .preview-frame {
    border: 1px black solid;
    width: 100%;
    height: 91%;
    overflow: scroll;
  }

  .frame-view {
    width: 100vw;
    height: 100vh;
    margin: 0;
    padding: 0;
    border: 0 none;
  }
</style>
