<template>
  <li class="file-element">
    <div :class="{hide: edit}" class="file-element-text-block" @click="onClick">
      <img class="type-element-icon_child" v-if="type == 'file'" src="/images/file.svg"/>
      <img class="type-element-icon_child" v-if="type == 'folder'" src="/images/folder.svg"/>
      <span class="file-element_text_child">{{text}}</span>
    </div>
    <template v-if="edit">
      <input class="file-element_input" v-model="newText" @keyup.enter="onEnter">
    </template>
    <div class="file-element_action file-element_delete" @click="onDelete">
      <img class="icon-element" src="/images/cancel-button.svg"/>
    </div>
    <div class="file-element_action file-element_edit" @click="onEdit">
      <img class="icon-element" src="/images/pencil.svg"/>
    </div>
  </li>
</template>

<script>
  export default {
    data() {
      return {
        newText: this.text
      }
    },
    props: [
      "text", "edit", "index", "type"
    ],
    methods: {
      onClick() {
        this.$emit('clickElement')
      },
      onDelete() {
        this.$emit('delete')
      },
      onEdit() {
        this.$emit('edit')
      },
      onEnter() {
        this.$emit('enter', this.index, this.newText)
      }
    }
  }
</script>

<style lang="scss">
  .file-element {
    position: relative;
    vertical-align: middle;
  }

  .file-element-text-block {
    padding: 12px;
    display: inline-block;
    width: 85%;
    white-space: nowrap;
    overflow: hidden;

    &:after {
      content: '';
      display: block;
      width: 10px;
      background: linear-gradient(to right, rgba(255,255,255,0), #e6e6e6);
      position: absolute;
      right: 0;
      top: 0;
      z-index: 9999;
      height: 100%;
    }

    &.hide {
      visibility: hidden;
    }
  }

  .type-element-icon_child {
    width: 1em;
    display: inline-block;
  }

  .file-element_text_child {
    display: inline-block;
    max-width: 100%;
    overflow: hidden;
  }

  .file-element_input {
    text-indent: 0;
    position: absolute;
    left: 10%;
    top: 25%;
    width: 75%;
  }

  .file-element_action {
    z-index: 999;
  }

  .file-element_delete {
    position: absolute;
    text-indent: 0;
    display: inline-block;
    top: 25%;
    left: -1.1em;
  }

  .file-element_edit {
    position: absolute;
    text-indent: 0;
    display: inline-block;
    top: 25%;
    right: -1.1em;
  }

  .icon-element {
    width: 1em;
    height: 1em;
  }

  /*.hover-change {*/
  /*box-sizing: border-box;*/
  /*border: 0;*/
  /*background: url(/images/cancel-button.svg) no-repeat;*/
  /*}*/

  /*.hover-change:hover {*/
  /*box-sizing: border-box;*/
  /*background: url(/images/x-button.svg) no-repeat;*/
  /*}*/

</style>
