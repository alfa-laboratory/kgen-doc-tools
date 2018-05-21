<template>
  <div v-click-outside="onOutside">
    <div class="select" v-if="selected !== undefined">
      <div class="select-styled" :class="{ active: isActive }">
        <div @click="onClick" class="file-element_text_block">
          <img class="type-element-icon" v-if="selected.type == 'file'" src="/images/file.svg"/>
          <img class="type-element-icon" v-if="selected.type == 'folder'" src="/images/folder.svg"/>
          <span class="file-element_text">{{selected.text}}</span>
        </div>
        <span></span>
        <div class="select-options-block" :class="{ active: isActive }">
          <ul class="select-options" :class="{ active: isActive }">
            <name-element
              v-for="(option, index) in optionsLocal"
              v-if="!option.empty"
              @clickElement="onElementClick(index)"
              @edit="onEditElement(index)"
              @enter="onNewElementText"
              @delete="onDeleteElement(index)"
              :class="{active: option.active}"
              :edit="option.edit"
              :text="option.text"
              :type="option.type"
              :index="index"
            >
            </name-element>
          </ul>
        </div>
      </div>
      <div class="element-blocks" v-if="isActive">
        <div class="element-blocks_file" @click="onNewFile">
          <span class="element-blocks_plus">+</span>
          <img src="/images/file.svg"/>
        </div>
        <div class="element-blocks_folder" @click="onNewFolder">
          <span class="element-blocks_plus">+</span>
          <img src="/images/folder.svg"/>
        </div>
      </div>
    </div>
  </div>


</template>

<script>
  import NameElement from "./NameElement.vue"
  import axios from 'axios'

  export default {
    props: [
      'options', 'path'
    ],
    data() {
      return {
        selected: undefined,
        selectedIndex: 0,
        isActive: false,
        optionsLocal: this.options
      }
    },
    mounted() {
      this.chooseSelect()
    },
    //TODO remove `empty` from arrays
    methods: {
      chooseSelect() {
        const selectFileItem = {
          text: '--select file--',
          value: '',
          type: 'empty',
          active: false,
          edit: false,
          empty: true
        };
        const emptyFileItem = {
          text: '--empty--',
          value: '',
          type: 'empty',
          active: false,
          edit: false,
          empty: true
        };

        if (this.optionsLocal.length === 0) {
          this.selected = emptyFileItem;
          this.selectedIndex = -1;
        } else {
          this.selected = selectFileItem;
          this.selectedIndex = -1;
        }
      },
      onClick() {
        this.isActive = !this.isActive;
        this.closeEdit();
      },
      onNewFile() {
        this.closeEdit();
        this.optionsLocal.push({
          text: 'newFile.md',
          value: 'newFile.md',
          type: 'file',
          active: false,
          edit: true,
          notCreated: true
        });
      },
      onNewFolder() {
        this.closeEdit();
        this.optionsLocal.push({
          text: 'newFolder',
          value: 'newFolder',
          type: 'folder',
          active: false,
          edit: true,
          notCreated: true
        });
      },
      onDeleteElement(index) {
        this.closeEdit();

        let filePath = this.path + "/" + this.optionsLocal[index].value;
        axios({
          method: 'delete',
          url: "/file",
          params: {
            path: filePath
          },
          headers: {
            'Content-Type': 'text/html; charset=utf-8'
          },
        }).catch(function (error) {
          console.error(error)
        });

        this.$delete(this.optionsLocal, index);

        if (this.selectedIndex === index) {
          this.chooseSelect();

          this.$emit('deleteSelected')
        }
      },
      onElementClick(index) {
        this.closeEdit();

        this.isActive = false;
        let element = this.optionsLocal[index];

        if (element.notCreated) {
          return
        }

        this.selected.active = false;
        this.selected = element;
        this.selectedIndex = index;
        element.active = true;

        this.$emit('change', this.selected)
      },
      onEditElement(editIndex) {
        this.closeEdit();
        console.log('edit element' + editIndex);
        this.optionsLocal[editIndex].edit = true;
      },
      onNewElementText(index, newText) {
        console.log('new text' + newText);
        const self = this;
        let editElement = this.optionsLocal[index];

        const oldText = editElement.text;

        editElement.edit = false;
        editElement.text = newText;
        editElement.value = newText;

        if (editElement.notCreated) {
          let filePath = this.path + "/" + newText;
          editElement.notCreated = false;

          axios({
            method: 'post',
            url: "/createFile",
            data: {
              path: filePath,
              type: editElement.type
            }
          }).then((response) => {
            if (!response.status)
              self.$delete(self.optionsLocal, index)
          }).catch(function (error) {
            self.$delete(self.optionsLocal, index)
          });
        } else {
          let filePath = this.path + "/" + oldText;

          axios({
            method: 'post',
            url: "/renameFile",
            data: {
              path: filePath,
              name: newText
            }
          }).catch(function (error) {
            console.error(error)
          });
        }
      },
      onOutside() {
        this.isActive = false;
        this.closeEdit()
      },
      closeEdit() {
        this.optionsLocal.forEach((option, index) => {
          this.optionsLocal[index].edit = false
        });

        this.optionsLocal = this.optionsLocal.filter((option) => {
          return option.notCreated !== true;
        })
      }
    },
    directives: {
      'click-outside': {
        bind: function (el, binding, vNode) {
          const self = this;
          // Provided expression must evaluate to a function.
          if (typeof binding.value !== 'function') {
            const compName = vNode.context.name;
            let warn = `[Vue-click-outside:] provided expression '${binding.expression}' is not a function, but has to be`
            if (compName) {
              warn += `Found in component '${compName}'`
            }

            console.warn(warn)
          }
          // Define Handler and cache it on the element
          const bubble = binding.modifiers.bubble;
          const handler = (e) => {
            if (bubble || (!el.contains(e.target) && el !== e.target)) {
              binding.value(e)
            }
          }
          el.__vueClickOutside__ = handler

          // add Event Listeners
          document.addEventListener('click', handler)
        },

        unbind: function (el, binding) {
          // Remove Event Listeners
          document.removeEventListener('click', el.__vueClickOutside__)
          el.__vueClickOutside__ = null

        }
      }
    },
    components: {
      "nameElement": NameElement
    }
  }
</script>

<style lang="scss">
  $select-color: #ac3912;
  $select-background: darken(#ffffff, 5);
  $select-active-background: #dd4512;
  $select-width: 220px;
  $select-height: 30px;

  .file-element_text_block {
    white-space: nowrap;
    overflow: hidden;
    position: absolute;
    left: 0;
    right: 30px;
    top: 6px;
    bottom: 0;
  }

  .type-element-icon {
    width: 1em;
    display: inline-block;
  }

  .file-element_text {
    display: inline-block;
  }

  .select {
    cursor: pointer;
    display: inline-block;
    position: relative;
    font-size: 16px;
    color: black;
    height: 30px;
    width: 150px;
  }

  .element-blocks {
    position: absolute;
    bottom: 100%;
    top: -1.5em;
    left: 0;
    right: 0;
  }

  .element-blocks_plus {
    position: absolute;
    border: 0;
    border-radius: 50%;
    padding: 2px;
    top: -5px;
    left: -16px;
    font-size: larger;
  }

  .element-blocks_file {
    position: absolute;
    top: 25%;
    left: 25%;
    width: 1em;
  }

  .element-blocks_folder {
    position: absolute;
    top: 25%;
    right: 15%;
    width: 1em;
  }

  .select-styled {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: $select-background;
    padding: 8px 0;
    &:after {
      content: "";
      width: 0;
      height: 0;
      border: 7px solid transparent;
      border-color: $select-color transparent transparent transparent;
      position: absolute;
      top: 16px;
      right: 10px;
    }
    &:hover {
      background-color: darken($select-background, 2);
    }
    &.active {
      &:after {
        top: 9px;
        border-color: transparent transparent $select-color transparent;
      }
    }
  }

  .select-options-block {
    display: none;
    width: 200px;
    left: -25px;
    position: relative;
    height: 90vh;
    overflow-y: scroll;
    &.active {
      display: block;
    }
  }

  .select-options {
    display: none;
    position: absolute;
    top: 0;
    width: 150px;
    left: 25px;
    z-index: 998;
    margin: 0;
    padding: 0;
    list-style: none;
    background-color: darken($select-background, 5);
    &.active {
      display: block;
    }
    li {
      margin: 0;
      border-top: 1px solid darken($select-background, 10);
      &.active {
        background-color: $select-active-background;
      }
      &:hover {
        color: $select-background;
        background: $select-color;
      }
      &[rel="hide"] {
        display: none;
      }
    }
  }
</style>


