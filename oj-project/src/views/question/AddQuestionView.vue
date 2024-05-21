<template>
  <div id="addQuestionView" style="width: 80%">
    <a-form :model="form" label-align="right">
      <a-form-item field="title" label="題目名">
        <a-input v-model="form.title" label="題目名入力"></a-input>
      </a-form-item>
      <a-form-item field="tag" label="タブー">
        <a-input-tag v-model="form.tags" placeholder="タブー入力" />
      </a-form-item>
      <a-form-item field="content" label="題目内容">
        <MdEditor
          :value="form.content"
          :handle-change="onContentChange"
          style="width: 100%"
        >
        </MdEditor>
      </a-form-item>
      <a-form-item field="anwer" label="参考コード(答え)">
        <MdEditor
          :value="form.answer"
          :handle-change="onAnswerChange"
          style="width: 100%"
        >
        </MdEditor>
      </a-form-item>
      <a-form-item label="題目設定" :content-flex="false" :merge-props="false">
        <a-space direction="vertical">
          <a-form-item field="judgeConfig.memoryLimit" label="メモリ制限">
            <a-input
              v-model="form.judgeConfig.memoryLimit"
              placeholder="please enter your post..."
              style="min-width: 380px"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.stackLimit" label="スタック制限">
            <a-input
              v-model="form.judgeConfig.stackLimit"
              placeholder="please enter your post..."
              style="min-width: 380px"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.timeLimit" label="時間制限">
            <a-input
              v-model="form.judgeConfig.timeLimit"
              placeholder="please enter your post..."
              style="min-width: 380px"
            />
          </a-form-item>
        </a-space>
      </a-form-item>
      <!-- 测试用例 -->
      <a-form-item label="TestCase"></a-form-item>
      <a-form-item
        v-for="(judgecaseitem, index) of form.judgeCase"
        :label="`testCase-${index + 1}`"
        :key="index"
      >
        <a-form-item
          :field="`form.judgeCase[${index}].input`"
          :label="`入力ケース-${index + 1}`"
          :key="index"
        >
          <a-input v-model="judgecaseitem.input" placeholder="入力ケース入力" />
        </a-form-item>
        <a-form-item
          :field="`form.judgeCase[${index}].output`"
          :label="`出力ケース-${index + 1}`"
          :key="index"
        >
          <a-input
            v-model="judgecaseitem.output"
            placeholder="出力ケースを入力"
          />
        </a-form-item>
        <a-button
          @click="handleDelete(index)"
          :style="{ marginLeft: '10px', marginTop: '-30 px' }"
          status="danger"
          >Delete Case</a-button
        >
      </a-form-item>
      <a-form-item style="margin-top: 16px">
        <a-button @click="handleAdd" type="outline">Add Case</a-button>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="doSubmit">Submit</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import MdEditor from "../../components/MdEditor.vue";
import QuestionController from "../../controller/QuestionController";
import axios from "../../util/axios";
import { useRoute } from "vue-router";
import message from "@arco-design/web-vue/es/message";

const url = "http://localhost:8081";

const route = useRoute();
//update or add
const updatePage = route.path.includes("update");
const loadDate = async () => {
  const id = route.query.id;
  console.log(id);
  if (!id) {
    return;
  }
  const res = await axios.get(url + "/quest/get", { id: id });
  if (res.code != null && res.code == 0) {
    form.value = res.data as any;
    if (form.value.judgeCase == null) {
      form.value.judgeCase = [];
    } else {
      form.value.judgeCase = JSON.parse(form.value.judgeCase as any);
    }
    if (form.value.judgeConfig == null) {
      form.value.judgeConfig = {
        memoryLimit: 0,
        stackLimit: 0,
        timeLimit: 0,
      };
    } else {
      form.value.judgeConfig = JSON.parse(form.value.judgeConfig as any);
    }
    if (form.value.tags != null) {
      form.value.tags = JSON.parse(form.value.tags as any);
    }
  } else {
    message.error("加载失败" + res.msg);
  }
};
onMounted(() => {
  loadDate();
});
let form = ref({
  answer: "暴力破解",
  content: "题目内容",
  judgeCase: [
    {
      input: "1 2",
      output: "3 4",
    },
  ],
  judgeConfig: {
    memoryLimit: 1000,
    stackLimit: 1000,
    timeLimit: 1000,
  },
  tags: ["栈", "简单"],
  title: "A +B",
});

const handleAdd = () => {
  form.value.judgeCase.push({
    input: "",
    output: "",
  });
};
const handleDelete = (index: any) => {
  form.value.judgeCase.splice(index, 1);
};

const onAnswerChange = (value: any) => {
  form.value.answer = value;
};

const onContentChange = (value: any) => {
  form.value.content = value;
};

const doSubmit = () => {
  console.log(form.value);
  if (updatePage) {
    const res = axios.post(url + "/quest/edit", form.value);
    console.log("edit", res);
    if (res.code == 0) {
      message.success("更新成功");
    } else {
      message.error("更新失败" + res.msg);
    }
  } else {
    const res = QuestionController.addQuestion(form.value);
    if (res.code == 0) {
      message.success("新增题目成功!");
    }
  }
};
</script>

<style lang="scss" scoped></style>
