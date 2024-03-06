<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchParams.text"
      placeholder="input search text"
      enter-button="Search"
      size="large"
      @search="onSearch"
    />
    <Divider />
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="文章">
        <PostLIst :post-list="postList"/>
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList :user-list="userList"/>
      </a-tab-pane>
      <a-tab-pane key="picture" tab="图片">
        <PictureList :picture-list="pictureList"/>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import PostLIst from "@/components/PostLIst.vue";
import UserList from "@/components/UserList.vue";
import PictureList from "@/components/PictureList.vue";
import Divider from "@/components/DividerPage.vue";
import { useRoute, useRouter } from "vue-router";
import requestAxios from "@/plugins/requestAxios";

const route = useRoute();
const router = useRouter();
/**
 * 路由参数中的category
 */
const activeKey = route.params.category;
const postList = ref([]);
const userList = ref([]);
const pictureList = ref([]);

/**
 * 初始化参数
 */
const initSearchParams = {
  text: "",
  pageSize: 10,
  pageNum: 1,
};

const searchParams = ref(initSearchParams);

requestAxios.post("post/list/page/vo", {}).then((response: any) => {
  postList.value = response.records;
});

requestAxios.post("user/list/page/vo", {}).then((response: any) => {
  userList.value = response.records;
});

/**
 * url改变则将 searchParams 改变
 */
watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    text: route.query.text,
  } as any;
});

const searchRequest = () => {
  requestAxios.post("picture/list/page/vo", {"searchText": searchParams.value.text}).then((response: any) => {
    pictureList.value = response.records;
  });
}

const onSearch = (value: string) => {
  router.push({
    query: searchParams.value,
  });
  searchRequest();
};

const onTabChange = (key: string) => {
  router.push({
    path: `/${key}`,
    query: searchParams.value,
  });
};
</script>
