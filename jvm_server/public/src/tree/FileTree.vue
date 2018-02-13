<template>
  <div class="filetree-element">
    <template v-for="(data, index) in elements">
      <fileelement
        class="filetree-element_files"
        :options="data"
        :path="relativePath(index - 1)"
        @change="(value) => onChange(index, value)"
        @deleteSelected="onDelete(index)"
      >
      </fileelement>
      <template v-if="index !== (elements.length -1)">
        /
      </template>
    </template>
  </div>
</template>

<script>
  import FileElement from "./FileElement.vue"
  import axios from "axios"

  function openDirectory(self, relativePath) {
    axios.get("/files", {
      params: {
        path: relativePath
      }
    }).then((response) => {
      const data = response.data;
      const newElements = [];

      if (data.status) {
        data.files.forEach(function (file) {
          let fileType;
          if (file.type === 'File') {
            fileType = "file"
          } else {
            fileType = "folder"
          }

          const newFile = {text: file.name, value: file.name, type: fileType, active: false, edit: false};

          newElements.push(newFile)
        });

        self.elements.push(newElements)
      }
    });
  }

  export default {
    data() {
      return {
        elements: [],
        files: []
      }
    },
    mounted() {
      openDirectory(this, "")
    },
    methods: {
      onChange(index, value) {
        console.log('index=' + index);
        console.log('value=' + value);
        this.elements = this.elements.slice(0, index + 1);
        this.files = this.files.slice(0, index);

        this.files.push(value.value);

        if (value.type === 'file') {
          const relativePath = this.relativePath(index);
          if (relativePath !== "")
            this.$emit('change', this.relativePath(index))
        } else if (value.type === 'folder') {
          const relativePath = this.relativePath(index + 1);
          console.log('open folder:' + relativePath);

          openDirectory(this, relativePath);
          this.$emit('disable');
        }
      },
      onDelete(index) {
        this.elements = this.elements.slice(0, index + 1);
        this.files = this.files.slice(0, index);

        this.$emit('disable')
      },
      relativePath(lastIndex) {
        let filePath = "";
        this.files.forEach((value, index) => {
          if (index <= lastIndex) {
            filePath += value;

            if (index < this.files.length - 1)
              filePath += "/"
          }
        });

        return filePath;
      }
    },
    components: {
      'fileelement': FileElement
    }
  }
</script>

<style>
  .filetree-element {
    display: block;
  }

  .filetree-element_files {
    display: inline;
  }
</style>
