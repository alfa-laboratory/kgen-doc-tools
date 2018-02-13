<template>
  <div class="editor-data">
    <div class="top-text">
      <div class="tree-column">
        <filetree
          @change="onChange"
          @disable="onDisable"
        ></filetree>
      </div>
    </div>
    <codemirror
      v-if="path"
      :class="{disable: !active}"
      :value="code"
      :options="cmOptions"
      @ready="onCmReady"
      @focus="onCmFocus"
      @input="onCmCodeChange"

    >
    </codemirror>
  </div>
</template>

<script>
  import {codemirror} from 'vue-codemirror'
  import axios from 'axios'
  import FileTree from './tree/FileTree.vue'
  // language
  import 'codemirror/mode/markdown/markdown.js'

  function debounce(func, wait, immediate) {
    let timeout;
    return function () {
      const context = this, args = arguments;
      const later = function () {
        timeout = null;
        if (!immediate) func.apply(context, args);
      };
      const callNow = immediate && !timeout;
      clearTimeout(timeout);
      timeout = setTimeout(later, wait);
      if (callNow) func.apply(context, args);
    };
  }

  const debounceUpdate = debounce(function (self, data, path) {
    axios.post('/file', data,
      {
        headers: {
          'Content-Type': 'text/html; charset=utf-8',
        },
        params: {
          path: path
        }
      })
      .then(function (response) {
        self.$parent.$emit('updated')
      })
  }, 500);

  export default {
    data() {
      return {
        code: '',
        cmOptions: {
          tabSize: 4,
          mode: 'text/x-markdown',
          // theme: 'lesser-dark',
          lineNumbers: true,
          line: true
        },
        active: false,
        path: undefined
      }
    },
    methods: {
      onCmReady(cm) {
        // console.log('the editor is readied!', cm)
      },
      onCmFocus(cm) {
        // console.log('the editor is focus!', cm)
      },
      onCmCodeChange(newCode) {
        debounceUpdate(this, newCode, this.path)
      },
      onChange(path) {
        this.path = path;

        const self = this;
        axios.get('/file', {
          params: {
            path: self.path
          }
        })
          .then(function (response) {
            console.log('receive response');
            self.code = response.data.toString();
            self.active = true;
          });

        this.$parent.$emit('change', path)
      },
      onDisable() {
        console.log('disable');

        this.active = false;
        this.$parent.$emit('disable')
      }
    },
    components: {
      codemirror,
      'filetree': FileTree
    }
  }
</script>

<style>
  @import '../node_modules/codemirror/lib/codemirror.css';
  /*@import '../node_modules/codemirror/theme/lesser-dark.css';*/

  .editor-data {
    width: 100%;
    height: 90%;
  }

  .vue-codemirror {
    height: 100%;
  }

  .CodeMirror {
    border: 1px solid #eee;
    height: inherit;
  }

  .top-text {
    display: table;
    padding: 1.2em;
  }

  .tree-column {
    display: table-cell;
  }

  .disable {
    visibility: hidden;
  }

</style>
