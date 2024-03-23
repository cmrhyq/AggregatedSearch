package com.cmrhyq.search.datasource.search;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmrhyq.search.common.ErrorCode;
import com.cmrhyq.search.exception.BusinessException;
import com.cmrhyq.search.model.entity.Picture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname PictureServiceImpl.java
 * @project springboot-init
 * @package com.cmrhyq.search.service
 * @date 2024/3/7 0:00
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@Service
public class PictureSearchDataSource implements SearchDataSource<Picture> {

    /**
     * @param searchText 搜索内容
     * @param pageNum    页面数量
     * @param pageSize   页面大小
     * @return
     */
    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) throws UnsupportedEncodingException {
        long current = (pageNum - 1) * pageSize;
        if (searchText != null) {
            searchText = URLEncoder.encode(searchText, "UTF-8");
        }
//        String url = String.format("https://cn.bing.com/images/search?q=%s&form=HDRSC2&first=%s", searchText, current);
        String url = String.format("https://cn.bing.com/images/async?q=%s&first=%s&count=35&mmasync=1", searchText, current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
        }
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictures = new ArrayList<>();
        for (Element element : elements) {
            // 取图片地址（murl）
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            // 取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictures.add(picture);
            if (pictures.size() >= pageSize) {
                break;
            }
        }
        Page<Picture> picturePage = new Page<>(pageNum, pageSize);
        picturePage.setRecords(pictures);
        return picturePage;
    }
}
