<template>
  <div
    id="Monaco-Editor"
    ref="monacoEditorRef"
    style="min-height: 400px; width: 300px"
  ></div>
  <!-- <button @click="filleValue">NEWCODE</button> -->
</template>

<script setup lang="ts">
import { withDefaults, onMounted, ref, toRaw, defineProps } from "vue";
import * as monaco from "monaco-editor";

const monacoEditorRef = ref();
const monacoEditor = ref();

interface Props {
  value: string;
  handleChange: (v: string) => void;
}

const props = withDefaults(defineProps<Props>(), {
  value: () => "",
  handleChange: (v: string) => {
    console.log();
  },
});

onMounted(() => {
  if (!monacoEditorRef.value) {
    return;
  }
  monacoEditor.value = monaco.editor.create(monacoEditorRef.value, {
    value: props.value,
    language: "java",
    automaticLayout: true,
    colorDecorators: true,
    minimap: {
      enabled: true,
    },
    readOnly: false,
    theme: "vs-dark",
  });
  monacoEditor.value.onDidChangeModelContent(() => {
    console.log("编辑框的内容为", toRaw(monacoEditor.value).getValue());
    props.handleChange(toRaw(monacoEditor.value).getValue());
  });
});

const filleValue = () => {
  if (!monacoEditor.value) {
    return;
  }
  toRaw(monacoEditor.value).setValue("newCODE");
};
</script>

<style scoped></style>
