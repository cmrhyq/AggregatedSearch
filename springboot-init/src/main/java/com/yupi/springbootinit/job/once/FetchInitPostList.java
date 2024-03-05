package com.yupi.springbootinit.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.esdao.PostEsDao;
import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollUtil;
import org.springframework.boot.CommandLineRunner;

/**
 * 获取帖子信息
 *
 * @author <a href="https://github.com/cmrhyq">AlanHuang</a>
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Resource
    private PostEsDao postEsDao;

    @Override
    public void run(String... args) {
        try {
            String url = "https://www.code-nav.cn/api/post/list/page/vo";
            String json = "{\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"reviewStatus\":1,\"current\":1}";
            String result = HttpRequest.post(url)
                    .body(json)
                    .execute().body();
            Map<String, Object> map = JSONUtil.toBean(result, Map.class);
            JSONObject data = (JSONObject) map.get("data");
            JSONArray records = (JSONArray) data.get("records");
            List<Post> posts = new ArrayList<>();
            for (Object record : records) {
                JSONObject tempRecord = (JSONObject) record;
                Post post = new Post();
                post.setTitle(tempRecord.getStr("title"));
                post.setContent(tempRecord.getStr("content"));
                JSONArray tags = (JSONArray) tempRecord.get("tags");
                List<String> tagList = tags.toList(String.class);
                post.setTags(JSONUtil.toJsonStr(tagList));
                post.setUserId(1L);
                posts.add(post);
            }
            boolean b = postService.saveBatch(posts);
            if (b) {
                log.info("FetchInitPostList 任务成功，条数：{}", posts.size());
            } else {
                log.warn("FetchInitPostList 任务失败");
            }
        } catch (Exception e) {
            log.error("任务异常，{}", e.getMessage());
        }
    }
}
