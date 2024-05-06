<template>
  <div id="managerQuestion">
    <a-table
      :columns="columns"
      :data="dataList"
      :pagination="{
        showTotal: true,
        pageSize: searchParmas.pageSize,
        current: searchParmas.pageNum,
        total,
      }"
    >
      <template #optional="{ record }">
        <a-button status="primary" @click="doUpdate(record)">修正</a-button>
        <a-popconfirm content="削除しますか?" type="error" ok="printlog">
          <a-button status="danger" @click="doDelet(record)">削除</a-button>
        </a-popconfirm>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import axios from "@/util/axios";
import { useRouter } from "vue-router";
import message from "@arco-design/web-vue/es/message";

const url = "http://localhost:8081";
const router = useRouter();
const show = ref(true);
const dataList = ref([]);
const total = ref(0);
const searchParmas = ref({
  pageSize: 10,
  pageNum: 1,
});

const printlog = (v: any) => {
  console.log("oklog", v);
};

const loadDate = async () => {
  const res = await axios.post(url + "/quest/list/page", searchParmas.value);
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

const doUpdate = (question: any) => {
  router.push({
    path: "/update/question",
    query: {
      id: question.id,
    },
  });
  console.log(question);
};

onMounted(() => {
  loadDate();
});

const columns = [
  {
    title: "id",
    dataIndex: "id",
  },
  {
    title: "題目名",
    dataIndex: "title",
  },
  {
    title: "内容",
    dataIndex: "content",
  },
  {
    title: "タブー",
    dataIndex: "tag",
  },
  {
    title: "答え",
    dataIndex: "answer",
  },
  {
    title: "コミット回数",
    dataIndex: "submitNum",
  },
  {
    title: "正解回数",
    dataIndex: "acceptedNum",
  },
  {
    title: "作成日",
    dataIndex: "createTime",
  },
  {
    title: "題目設定",
    dataIndex: "judgeConfig",
  },
  {
    title: "TestCase",
    dataIndex: "judegCase",
  },
  {
    title: "編集",
    slotName: "optional",
  },
];
</script>

<style lang="scss" scoped></style>
