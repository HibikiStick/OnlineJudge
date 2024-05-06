<template>
  <div class="home">
    <CodeEditor :value="value" :handle-change="onchange" />
    <MdEditor :value="value" :handle-change="onchange" />
  </div>
</template>

<script lang="ts" setup>
import { defineComponent, ref } from "vue";
import MdEditor from "../components/MdEditor.vue";
import CodeEditor from "../components/CodeEditor.vue";

const value = ref();

const onchange = (v: string) => {
  value.value = v;
  console.log(v);
};
</script>
