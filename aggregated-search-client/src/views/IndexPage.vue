<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchText"
      placeholder="input search text"
      enter-button="Search"
      size="large"
      @search="onSearch"
    />
    <Divider />
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="文章">
        <PostLIst :post-list="postList" />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList :user-list="userList" />
      </a-tab-pane>
      <a-tab-pane key="picture" tab="图片">
        <PictureList :picture-list="pictureList" />
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
import { message } from "ant-design-vue";

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
  searchType: activeKey,
  text: "",
  pageSize: 10,
  pageNum: 1,
};

const searchText = ref(route.query.text || "");

/**
 * 聚合数据，所有栏目一起搜索
 * @param params
 */
const loadAllData = (params: any) => {
  const query = {
    ...params,
    searchText: params.text,
  };
  requestAxios.post("search/all", query).then((response: any) => {
    postList.value = response.postList;
    userList.value = response.userList;
    pictureList.value = response.pictureList;
  });
};

/**
 * 单类数据，tab栏切换触发搜索
 * @param params
 */
const loadSingleData = (params: any) => {
  const { searchType } = params;
  if (!searchType) {
    message.error("类别为空");
    return;
  }
  const query = {
    ...params,
    searchText: params.text,
  };

  requestAxios.post("search/all", query).then((response: any) => {
    if (searchType === "post") {
      postList.value = response.postList;
    } else if (searchType === "user") {
      userList.value = response.userList;
    } else if (searchType === "picture") {
      pictureList.value = response.pictureList;
    } else {
      message.error("返回错误的类别");
      return;
    }
  });
};

const searchParams = ref(initSearchParams);

/**
 * url改变则将 searchParams 改变
 */
watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    text: route.query.text,
    searchType: route.params.category,
  } as any;
  loadSingleData(searchParams.value);
});

/**
 * 搜索事件
 * @param value
 */
const onSearch = (value: string) => {
  router.push({
    query: {
      ...searchParams.value,
      text: value,
    },
  });
};

/**
 * tab栏切换事件
 * @param key
 */
const onTabChange = (key: string) => {
  router.push({
    path: `/${key}`,
    query: searchParams.value,
  });
};
</script>
