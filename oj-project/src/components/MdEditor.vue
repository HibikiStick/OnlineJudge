<template>
  <Editor
    :value="value"
    :mode="mode"
    :plugins="plugins"
    @change="handleChange"
  ></Editor>
</template>

<script setup lang="ts">
import gfm from "@bytemd/plugin-gfm";
import higlight from "@bytemd/plugin-highlight";
import { Editor } from "@bytemd/vue-next";
import { ref, withDefaults, defineProps } from "vue";

//定义组件属性的类型
interface Props {
  value: string;
  mode?: string;
  handleChange: (v: string) => void;
}

const plugins = [
  gfm(),
  higlight(),
  // Add more plugins here
];
const props = withDefaults(defineProps<Props>(), {
  value: () => "",
  handleChange: (v: string) => {
    console.log(v);
  },
});
// const value = ref("");

// const handleChange = (v: string) => {
//   console.log("mdedit", v);
//   value.value = v;
// };
</script>
<style scoped>
.bytemd-toolbar-icon.bytemd-tippy.bytemd-tippy-right:last-child {
  display: null;
}
</style>
