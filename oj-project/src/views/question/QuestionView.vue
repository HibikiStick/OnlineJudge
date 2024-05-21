<template>
  <div id="questionView">
    <a-form :model="searchParmas" layout="inline">
      <a-form-item field="title" label="題目">
        <a-input
          v-model="searchParmas.title"
          placeholder="please enter titles."
        />
      </a-form-item>
      <a-form-item field="tags" label="Tag">
        <a-input v-model="searchParmas.tags" placeholder="please enter tags." />
      </a-form-item>
      <a-form-item>
        <a-button>Submit</a-button>
      </a-form-item>
    </a-form>
    <a-table
      :columns="columns"
      :data="dataList"
      :pagination="{
        showTotal: true,
        pageSize: searchParmas.pageSize,
        current: searchParmas.current,
        total,
      }"
      @page-change="onPageChange"
    >
      <template #tags="{ record }">
        <a-space wrap>
          <a-tag
            v-for="(item, index) of record.tags"
            :key="index"
            color="green"
            bordered
            >{{ item }}</a-tag
          >
        </a-space>
      </template>
      <template #acceptedRate="{ record }">{{ record.acceptedNum }} </template>
      <template #createTime="{ record }">
        <a-button>{{
          moment(record.createTime).format("YYYY-MM-DD")
        }}</a-button>
      </template>
      <template #optional="{ record }">
        <a-button status="primary" @click="toQuestionPage(record)"
          >チャレンジ</a-button
        >
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, toRaw } from "vue";
import axios from "@/util/axios";
import { useRouter } from "vue-router";
import message from "@arco-design/web-vue/es/message";
import moment from "moment";

const url = "http://localhost:8081";
const router = useRouter();
const show = ref(true);
const dataList = ref([]);
const total = ref(0);
const searchParmas = ref({
  title: "",
  tags: [],
  pageSize: 10,
  current: 1,
});

const printlog = (v: any) => {
  console.log("oklog", v);
};

const loadDate = async () => {
  const res = await axios.post(
    url + "/quest/my/list/page/vo",
    searchParmas.value
  );
  if (res.code == 0) {
    dataList.value = res.data.records;
    total.value = res.data.total;
  }
};

const doDelet = async (question: any) => {
  const res = await axios.post(url + "/quest/delete", { id: question.id });
  if (res != null && res.code == 0) {
    message.info("削除成功");
    // toDo更新数据
  }
};

const toQuestionPage = (question: any) => {
  router.push({
    path: "/view/question/",
    query: {
      id: question.id,
    },
  });
  console.log("jump", question);
};

const onPageChange = (current: number) => {
  searchParmas.value.current = current;
  loadDate();
};

onMounted(() => {
  loadDate();
});

const columns = [
  {
    title: "№",
    dataIndex: "id",
  },
  {
    title: "題目名",
    dataIndex: "title",
  },
  {
    title: "タブー",
    slotName: "tags",
  },
  {
    title: "完成率",
    dataIndex: "acceptedNum",
    slotName: "acceptedRate",
  },
  {
    title: "作成日",
    dataIndex: "createTime",
  },
  {
    title: "編集",
    slotName: "optional",
  },
];
</script>

<style lang="scss" scoped></style>
