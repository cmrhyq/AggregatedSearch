<template>
  <a-list item-layout="horizontal" :data-source="props.postList">
    <template #renderItem="{ item }">
      <a href="www.baidu.com">
        <a-card :title="item.title" :bordered="true" :loading="false" class="markdown-box">
          <!--      专业版-->
          <!--      <MarkdownPro :value="item.content" theme="oneDark" :height="600"> {{item.content}}</MarkdownPro>-->
          <!--      预览版-->
          <MarkdownPreview :initialValue="item.content"></MarkdownPreview>
        </a-card>
      </a>
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

<style scoped>
.markdown-box {
  max-height: 500px;
  overflow: hidden;
  box-shadow: 6px 3px 11px 1px #f0f0f0;
  margin: 10px 0 0 0;
}
</style>
