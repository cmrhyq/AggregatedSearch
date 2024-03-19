<template>
  <a-list item-layout="horizontal" :data-source="props.postList">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta :description="item.content">
          <template #title>
            <a href="https://www.antdv.com/">{{ item.title }}</a>
          </template>
          <template #avatar>
            <a-avatar
              src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
            />
          </template>
        </a-list-item-meta>
      </a-list-item>
      <!--      专业版-->
      <!--      <MarkdownPro :value="item.content" theme="oneDark" :height="600"> {{item.content}}</MarkdownPro>-->
      <!--      预览版-->
      <MarkdownPreview :initialValue="item.content"></MarkdownPreview>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";
import MarkdownPro from "@/components/vue-markdown/pro";
import MarkdownPreview from "@/components/vue-markdown/preview";

interface Props {
  postList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  postList: () => [],
});
</script>

<style scoped></style>
