package com.yang.sunment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.yang.sunment.component.StringAndArray;
import com.yang.sunment.mapper.ArticleMapper;
import com.yang.sunment.model.Article;
import com.yang.sunment.service.*;

import com.yang.sunment.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe:
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {



    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleLikesService articleLikesService;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentLikesService commentLikesService;

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private ArticleService articleService;

    @Override
    public JSONObject getArticleManagement(int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);
        List<Article> articles = articleMapper.getArticleManagement();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJson = new JSONObject();
        JSONObject articleJson;
        for(Article article : articles){
            articleJson = new JSONObject();
            articleJson.put("id",article.getId());
            articleJson.put("articleId",article.getArticleId());
            articleJson.put("originalAuthor",article.getOriginalAuthor());
            articleJson.put("articleTitle",article.getArticleTitle());
            articleJson.put("articleCategories",article.getArticleCategories());
            articleJson.put("publishDate",article.getPublishDate());
            String pageName = "jump/findArticle?articleId=" + article.getArticleId() + "&originalAuthor=" + article.getOriginalAuthor();
            articleJson.put("visitorNum",visitorService.getNumByPageName(pageName));

            returnJsonArray.add(articleJson);
        }
        returnJson.put("status",200);
        returnJson.put("result",returnJsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        returnJson.put("pageInfo",pageJson);

        return returnJson;
    }


    @Override
    public int deleteArticle(long id) {
        try {
            Article deleteArticle = articleMapper.findAllArticleId(id);
            articleMapper.updateLastOrNextId("last_article_id", deleteArticle.getLastArticleId(), deleteArticle.getNextArticleId());
            articleMapper.updateLastOrNextId("next_article_id", deleteArticle.getNextArticleId(), deleteArticle.getLastArticleId());
            //删除本篇文章
            articleMapper.deleteByArticleId(deleteArticle.getArticleId());
            //删除与该文章有关的所有文章点赞记录、文章评论、文章评论记录
            commentService.deleteCommentByArticleId(deleteArticle.getArticleId());
            commentLikesService.deleteCommentLikesByArticleId(deleteArticle.getArticleId());
            articleLikesService.deleteArticleLikesByArticleId(deleteArticle.getArticleId());
        }catch (Exception e){
            log.error("删除文章失败，文章id=" + id);
            return 0;
        }
        return 1;
    }

    /**
     * 修改文章
     * @return
     */
    @Override
    public JSONObject updateArticleById(Article article) {
        Article a = articleMapper.getArticleUrlById(article.getId());
        if("原创".equals(article.getArticleType())){
            article.setOriginalAuthor(article.getAuthor());
            String url = "http://localhost:8080/jump/findArticle?articleId=" + a.getArticleId() + "&originalAuthor=" + a.getOriginalAuthor();
            article.setArticleUrl(url);
        }
        articleMapper.updateArticleById(article);
        JSONObject articleReturn = new JSONObject();
        articleReturn.put("status",200);
        articleReturn.put("articleTitle",article.getArticleTitle());
        articleReturn.put("updateDate",article.getUpdateDate());
        articleReturn.put("author",article.getOriginalAuthor());
        //本博客中的URL
        articleReturn.put("articleUrl","/jump/findArticle?articleId=" + a.getArticleId() + "&originalAuthor=" + a.getOriginalAuthor());
        return articleReturn;
    }
    /**
     * 保存文章
     * @param article 文章
     * @return  status: 200--成功   500--失败
     */
    @Override
    public JSONObject insertArticle(Article article) {
        JSONObject articleReturn = new JSONObject();

        try {
            if("".equals(article.getOriginalAuthor())){
                article.setOriginalAuthor(article.getAuthor());
            }
            if("".equals(article.getArticleUrl())){
//                String url = "http://localhost/findArticle?articleId=" + article.getArticleId() + "&originalAuthor=" + article.getOriginalAuthor();
                String url = "http://localhost:8080/jump/findArticle?articleId=" + article.getArticleId() + "&originalAuthor=" + article.getOriginalAuthor();
                article.setArticleUrl(url);
            }
            Article endArticleId = articleMapper.findEndArticleId();
            //设置文章的上一篇文章id
            if(endArticleId != null){
                article.setLastArticleId(endArticleId.getArticleId());
            }
            articleMapper.insertArticle(article);
            //判断发表文章的归档日期是否存在，不存在则插入归档日期
            TimeUtil timeUtil = new TimeUtil();
            String archiveName = timeUtil.timeWhippletreeToYear(article.getPublishDate().substring(0, 7));
            archiveService.addArchiveName(archiveName);
            //新文章加入访客量
            visitorService.insertVisitorArticle("jump/findArticle?articleId=" + article.getArticleId() + "&originalAuthor=" + article.getOriginalAuthor());
            //设置上一篇文章的下一篇文章id
            if(endArticleId != null){
                articleService.updateArticleLastOrNextId("nextArticleId", article.getArticleId(), endArticleId.getArticleId());
            }

            articleReturn.put("status",200);
            articleReturn.put("articleTitle",article.getArticleTitle());
            articleReturn.put("updateDate",article.getUpdateDate());
            articleReturn.put("author",article.getOriginalAuthor());
            //本博客中的URL
            articleReturn.put("articleUrl","/jump/findArticle?articleId=" + article.getArticleId() + "&originalAuthor=" + article.getOriginalAuthor());
            return articleReturn;
        } catch (Exception e){
            articleReturn.put("status",500);
            log.error("用户 " + article.getAuthor() + " 保存文章 " + article.getArticleTitle() + " 失败");
            e.printStackTrace();
            return articleReturn;
        }


    }

    @Override
    public void updateArticleLastOrNextId(String lastOrNext, long lastOrNextArticleId, long articleId) {
        if("lastArticleId".equals(lastOrNext)){
            articleMapper.updateArticleLastId(lastOrNextArticleId, articleId);
        }
        if("nextArticleId".equals(lastOrNext)){
            articleMapper.updateArticleNextId(lastOrNextArticleId, articleId);
        }
    }

    /**
     * 通过id获取文章
     */
    @Override
    public Article findArticleById(int id) {
        return articleMapper.findArticleById(id);
    }

    /**
     * 获得草稿中的文章
     * @return
     */
    @Override
    public JSONObject getDraftArticle(Article article, String[] articleTags, int articleGrade) {
        JSONObject returnJson = new JSONObject();
        returnJson.put("id", article.getId());
        returnJson.put("articleTitle", article.getArticleTitle());
        returnJson.put("articleType", article.getArticleType());
        returnJson.put("articleCategories", article.getArticleCategories());
        returnJson.put("articleUrl", article.getArticleUrl());
        returnJson.put("originalAuthor", article.getOriginalAuthor());
        returnJson.put("articleContent", article.getArticleContent());
        returnJson.put("articleTags", articleTags);
        returnJson.put("articleGrade", articleGrade);
        return returnJson;
    }
    /**
     * 分页获得所有文章
     * @param rows 一页显示文章数
     * @param pageNo 第几页
     * @return 该页所有文章
     */
    @Override
    public JSONArray findAllArticles(String rows, String pageNo,String articleAbstract) {
        int pageNum = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(rows);

        PageHelper.startPage(pageNum,pageSize);
        //查询所有文章
        List<Article> articles = articleMapper.findAllArticles(articleAbstract);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        List<Map<String, Object>> newArticles = new ArrayList<>();
        Map<String, Object> map;

        for(Article article : articles){
            map = new HashMap<>();
            map.put("thisArticleUrl", "/jump/findArticle?articleId=" + article.getArticleId() + "&originalAuthor=" + article.getOriginalAuthor());
            map.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
            map.put("articleTitle", article.getArticleTitle());
            map.put("articleType", article.getArticleType());
            map.put("publishDate", article.getPublishDate());
            map.put("originalAuthor", article.getOriginalAuthor());
            map.put("articleCategories", article.getArticleCategories());
            map.put("articleAbstract", article.getArticleAbstract());
            map.put("articleLikes", article.getArticleLikes());
            newArticles.add(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(newArticles);
        Map<String, Object> thisPageInfo = new HashMap<>();
        thisPageInfo.put("pageNum",pageInfo.getPageNum());
        thisPageInfo.put("pageSize",pageInfo.getPageSize());
        thisPageInfo.put("total",pageInfo.getTotal());
        thisPageInfo.put("pages",pageInfo.getPages());
        thisPageInfo.put("isFirstPage",pageInfo.isIsFirstPage());
        thisPageInfo.put("isLastPage",pageInfo.isIsLastPage());

        jsonArray.add(thisPageInfo);
        return jsonArray;
    }
    /**
     * 通过文章id和原作者获得文章名
     * @param articleId 文章id
     * @param originalAuthor 文章原作者
     * @return 文章名
     */
    @Override
    public Map<String,String> findArticleTitle(long articleId, String originalAuthor) {
        Article articleInfo = articleMapper.findArticleTitle(articleId, originalAuthor);
        Map<String, String> articleMap = new HashMap<>();
        articleMap.put("articleTitle", articleInfo.getArticleTitle());
        articleMap.put("articleAbstract", articleInfo.getArticleAbstract());
        return articleMap;
    }
    /**
     * 获得文章
     * @param articleId 文章id
     * @param originalAuthor 原作者
     * @param username 用户
     * @return
     */
    @Override
    public JSONObject getArticle(long articleId, String originalAuthor, String username) {
        Article article = articleMapper.getArticle(articleId, originalAuthor);

        JSONObject jsonObject = new JSONObject();
        if(article != null){
            Article lastArticle = articleMapper.findArticleByArticleId(article.getLastArticleId());
            Article nextArticle = articleMapper.findArticleByArticleId(article.getNextArticleId());
            jsonObject.put("status","200");
            jsonObject.put("author",article.getAuthor());
            jsonObject.put("articleId",articleId);
            jsonObject.put("originalAuthor",article.getOriginalAuthor());
            jsonObject.put("articleTitle",article.getArticleTitle());
            jsonObject.put("publishDate",article.getPublishDate());
            jsonObject.put("updateDate",article.getUpdateDate());
            jsonObject.put("articleContent",article.getArticleContent());
            jsonObject.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
            jsonObject.put("articleType",article.getArticleType());
            jsonObject.put("articleCategories",article.getArticleCategories());
            jsonObject.put("articleUrl",article.getArticleUrl());
            jsonObject.put("articleLikes",article.getArticleLikes());
            if(username == null){
                jsonObject.put("isLiked",0);
            }else {
                if(articleLikesService.isLiked(articleId, originalAuthor,username)){
                    jsonObject.put("isLiked",1);
                }else {
                    jsonObject.put("isLiked",0);
                }
            }
            if(lastArticle != null){
                jsonObject.put("lastStatus","200");
                jsonObject.put("lastArticleTitle",lastArticle.getArticleTitle());
                jsonObject.put("lastArticleUrl","/jump/findArticle?articleId=" + lastArticle.getArticleId() + "&originalAuthor=" + lastArticle.getOriginalAuthor());
            } else {
                jsonObject.put("lastStatus","500");
                jsonObject.put("lastInfo","无");
            }
            if(nextArticle != null){
                jsonObject.put("nextStatus","200");
                jsonObject.put("nextArticleTitle",nextArticle.getArticleTitle());
                jsonObject.put("nextArticleUrl","/jump/findArticle?articleId=" + nextArticle.getArticleId() + "&originalAuthor=" + nextArticle.getOriginalAuthor());
            } else {
                jsonObject.put("nextStatus","500");
                jsonObject.put("nextInfo","无");
            }
            return jsonObject;
        } else {
            jsonObject.put("status","500");
            jsonObject.put("errorInfo","获取文章信息失败");
            log.error("获取文章id " + articleId + " 失败");
            return jsonObject;
        }
    }
    /**
     * 文章点赞
     * @param articleId 文章id
     * @param originalAuthor 文章原作者
     * @return 目前点赞数
     */
    @Override
    public int updateLike(long articleId, String originalAuthor) {
        articleMapper.updateLikes(articleId, originalAuthor);
        return articleMapper.findLikes(articleId, originalAuthor);
    }
    /**
     * 分页获得该分类下的所有文章
     * @param category 分类名
     * @param rows 一页大小
     * @param pageNum 页数
     * @return
     */
    @Override
    public JSONObject findArticleByCategory(String category, int rows, int pageNum) {

        List<Article> articles;
        PageInfo<Article> pageInfo;
        JSONArray articleJsonArray = new JSONArray();
        PageHelper.startPage(pageNum, rows);
        if("".equals(category)){
            articles = articleMapper.findAllArticlesList();
            category = "全部分类";
        } else {
            articles = articleMapper.findArticleByCategory(category);
        }
        pageInfo = new PageInfo<>(articles);

        articleJsonArray = timeLineReturn(articleJsonArray, articles);

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        jsonObject.put("result",articleJsonArray);
        jsonObject.put("category",category);
        jsonObject.put("pageInfo",pageJson);

        return jsonObject;
    }
    /**
     * 计算该分类文章的数目
     * @param category 分类名
     * @return 该分类下文章的数目
     */
    @Override
    public int countArticleCategory(String category) {
        return articleMapper.countArticleCategory(category);
    }
    /**
     * 计算该归档日期文章的数目
     * @param archive 归档日期
     * @return 该归档日期下文章的数目
     */
    @Override
    public int countArticleArchive(String archive) {
        return articleMapper.countArticleArchive(archive);
    }
    /**
     * 分页获得该归档日期下的所有文章
     * @param archive 归档日期
     * @param rows 一页大小
     * @param pageNum 页数
     * @return
     */
    @Override
    public JSONObject findArticleByArchive(String archive, int rows, int pageNum) {
        List<Article> articles;
        PageInfo<Article> pageInfo;
        JSONArray articleJsonArray = new JSONArray();
        TimeUtil timeUtil = new TimeUtil();
        String showMonth = "hide";
        if(!"".equals(archive)){
            archive = timeUtil.timeYearToWhippletree(archive);
        }
        PageHelper.startPage(pageNum, rows);
        if("".equals(archive)){
            articles = articleMapper.findAllArticlesList();
        } else {
            articles = articleMapper.findArticleByArchive(archive);
            showMonth = "show";
        }
        pageInfo = new PageInfo<>(articles);

        articleJsonArray = timeLineReturn(articleJsonArray, articles);

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        jsonObject.put("result",articleJsonArray);
        jsonObject.put("pageInfo",pageJson);
        jsonObject.put("articleNum", articleMapper.countArticle());
        jsonObject.put("showMonth", showMonth);

        return jsonObject;
    }
    /**
     * 通过标签分页获得文章部分信息
     * @param tag
     * @return
     */
    @Override
    public JSONObject findArticleByTag(String tag, int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);
        List<Article> articles = articleMapper.findArticleByTag(tag);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        JSONObject articleJson;
        JSONArray articleJsonArray = new JSONArray();
        //二次判断标签是否匹配
        for(Article article : articles){
            String[] tagsArray = StringAndArray.stringToArray(article.getArticleTags());
            for(String str : tagsArray){
                if(str.equals(tag)){
                    articleJson = new JSONObject();
                    articleJson.put("articleId", article.getArticleId());
                    articleJson.put("originalAuthor", article.getOriginalAuthor());
                    articleJson.put("articleTitle", article.getArticleTitle());
                    articleJson.put("articleCategories", article.getArticleCategories());
                    articleJson.put("publishDate", article.getPublishDate());
                    articleJson.put("articleTags", tagsArray);
                    articleJsonArray.add(articleJson);
                }
            }
        }

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",201);
        jsonObject.put("result",articleJsonArray);
        jsonObject.put("tag",tag);
        jsonObject.put("pageInfo",pageJson);
        return jsonObject;
    }

    /**
     * 计算所有文章的数量
     * @return 所有文章的数量
     */
    @Override
    public int countArticle() {
        return articleMapper.countArticle();
    }

    /**
     * 我的文章
     * @param rows 一页显示文章数
     * @param pageNo 第几页
     * @return 该页所有文章
     */
    @Override
    public JSONArray myArticle(String rows, String pageNo, String author) {
        int pageNum = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(rows);

        PageHelper.startPage(pageNum,pageSize);
        //查询所有文章
        List<Article> articles = articleMapper.myArticle(author);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        List<Map<String, Object>> newArticles = new ArrayList<>();
        Map<String, Object> map;

        for(Article article : articles){
            map = new HashMap<>();
            map.put("thisArticleUrl", "/jump/findArticle?articleId=" + article.getArticleId() + "&originalAuthor=" + article.getOriginalAuthor());
            map.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
            map.put("articleTitle", article.getArticleTitle());
            map.put("articleType", article.getArticleType());
            map.put("publishDate", article.getPublishDate());
            map.put("originalAuthor", article.getOriginalAuthor());
            map.put("articleCategories", article.getArticleCategories());
            map.put("articleAbstract", article.getArticleAbstract());
            map.put("articleLikes", article.getArticleLikes());
            map.put("id", article.getId());
            newArticles.add(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(newArticles);
        Map<String, Object> thisPageInfo = new HashMap<>();
        thisPageInfo.put("pageNum",pageInfo.getPageNum());
        thisPageInfo.put("pageSize",pageInfo.getPageSize());
        thisPageInfo.put("total",pageInfo.getTotal());
        thisPageInfo.put("pages",pageInfo.getPages());
        thisPageInfo.put("isFirstPage",pageInfo.isIsFirstPage());
        thisPageInfo.put("isLastPage",pageInfo.isIsLastPage());

        jsonArray.add(thisPageInfo);
        return jsonArray;
    }

    /**
     * 封装时间线中数据成JsonArray形式
     */
    private JSONArray timeLineReturn(JSONArray articleJsonArray, List<Article> articles){
        JSONObject articleJson;
        for(Article article : articles){
            String[] tagsArray = StringAndArray.stringToArray(article.getArticleTags());
            articleJson = new JSONObject();
            articleJson.put("articleId", article.getArticleId());
            articleJson.put("originalAuthor", article.getOriginalAuthor());
            articleJson.put("articleTitle", article.getArticleTitle());
            articleJson.put("articleCategories", article.getArticleCategories());
            articleJson.put("publishDate", article.getPublishDate());
            articleJson.put("articleTags", tagsArray);
            articleJsonArray.add(articleJson);
        }
        return articleJsonArray;
    }
}
