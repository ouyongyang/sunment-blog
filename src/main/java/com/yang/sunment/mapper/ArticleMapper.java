package com.yang.sunment.mapper;


import com.yang.sunment.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 文章sql
 */
@Mapper
@Repository
public interface ArticleMapper {

    @Select("select id,article_id As articleId,original_author As originalAuthor,article_title As articleTitle,article_categories As articleCategories,publish_date As publishDate from article order by id desc")
    List<Article> getArticleManagement();

    @Select("select article_id As articleId,last_article_id As last_articleId,next_article_id As next_articleId from article where id=#{id}")
    Article findAllArticleId(long id);

    @Update("update article set ${lastOrNextStr}=#{updateId} where article_id=#{articleId}")
    void updateLastOrNextId(@Param("lastOrNextStr") String lastOrNextStr, @Param("updateId") long updateId, @Param("articleId") long articleId);

    @Delete("delete from article where article_id=#{articleId}")
    void deleteByArticleId(long articleId);

    @Select("select article_id AS articleId,original_author AS originalAuthor from article where id=#{id}")
    Article getArticleUrlById(int id);

    @Update("update article set original_author=#{originalAuthor},article_title=#{articleTitle},update_date=#{updateDate},article_content=#{articleContent},article_tags=#{articleTags},article_type=#{articleType},article_categories=#{articleCategories},article_url=#{articleUrl},article_abstract=#{articleAbstract} where id=#{id}")
    void updateArticleById(Article article);

    @Select("select article_id As articleId from article order by id desc limit 1")
    Article findEndArticleId();

    @Insert("insert into article(article_id,author,original_author,article_title,publish_date,update_date,article_content,article_tags,article_type,article_categories,article_url,article_abstract,article_likes,last_article_id,next_article_id) " +
            "values(#{articleId},#{author},#{originalAuthor},#{articleTitle},#{publishDate},#{updateDate},#{articleContent},#{articleTags},#{articleType},#{articleCategories},#{articleUrl},#{articleAbstract},#{articleLikes},#{lastArticleId},#{nextArticleId})")
    void insertArticle(Article article);


    @Select("update article set last_article_id=#{lastArticleId} where article_id=#{articleId}")
    void updateArticleLastId(@Param("lastArticleLd") long lastArticleLd, @Param("articleId") long articleId);

    @Select("update article set next_article_id=#{nextArticleId} where article_id=#{articleId}")
    void updateArticleNextId(@Param("nextArticleId") long nextArticleId, @Param("articleId") long articleId);

    @Select("select id,article_id As articleId,original_author As originalAuthor,article_title As articleTitle,article_content As articleContent,article_categories As articleCategories,article_tags As articleTags,article_type As articleType,article_url As articleUrl from article where id=#{id}")
    Article findArticleById(int id);

    @Select("select article_id As articleId,original_author As originalAuthor,article_tags As articleTags,article_title As articleTitle,article_type As articleType,publish_date As publishDate,article_categories As articleCategories,article_abstract As articleAbstract,article_likes As articleLikes from article where article_abstract LIKE concat(concat('%',#{articleAbstract}),'%') order by id desc")
    List<Article> findAllArticles(@Param("articleAbstract") String articleAbstract);

    @Select("select article_title As articleTitle,article_abstract As articleAbstract from article where article_id=#{articleId} and original_author=#{originalAuthor}")
    Article findArticleTitle(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor);

    @Select("select id,article_id As articleId,author,original_author As originalAuthor,article_tags As articleTags,article_title As articleTitle,article_content As articleContent,article_type As articleType,publish_date As publishDate,update_date As updateDate,article_url As articleUrl,article_categories As articleCategories,article_abstract As articleAbstract,article_likes As articleLikes,last_article_id As  lastArticleId,next_article_id As nextArticleId from article where article_id=#{articleId} and original_author=#{originalAuthor}")
    Article getArticle(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor);

    @Select("select article_id As articleId,original_author As originalAuthor ,article_title As articleTitle from article where article_id=#{articleId}")
    Article findArticleByArticleId(@Param("articleId") long articleId);

    @Update("update article set article_likes=article_likes+2 where article_id=#{articleId} and original_author=#{originalAuthor}")
    void updateLikes(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor);

    @Select("select IFNULL(max(article_likes),0) from article where article_id=#{articleId} and original_author=#{originalAuthor}")
    int findLikes(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor);

    @Select("select article_id As articleId,original_author As originalAuthor,article_title As articleTitle,article_tags As articleTags,article_type As articleType,article_categories As articleCategories,publish_date As publishDate from article order by id desc")
    List<Article> findAllArticlesList();

    @Select("select article_id As articleId,original_author As originalAuthor,article_title As articleTitle,article_tags As articleTags,article_type As articleType,article_categories As articleCategories,publish_date As publishDate from article where article_categories=#{category} order by id desc")
    List<Article> findArticleByCategory(@Param("category") String category);

    @Select("select count(*) from article where article_categories=#{category}")
    int countArticleCategory(@Param("category") String category);

    @Select("select count(*) from article where publish_date like '%${archive}%'")
    int countArticleArchive(@Param("archive") String archive);

    @Select("select article_id As articleId,original_author As originalAuthor,article_title As articleTitle,article_tags As articleTags,article_type As articleType,article_categories As articleCategories,publish_date As publishDate from article where publish_date like '%${archive}%' order by id desc")
    List<Article> findArticleByArchive(@Param("archive") String archive);

    @Select("select count(*) from article")
    int countArticle();

    @Select("select article_id As articleId,original_author As originalAuthor,article_title As articleTitle,article_tags As articleTags,article_type As articleType,article_categories As articleCategories,publish_date As publishDate from article where article_tags like '%${tag}%' order by id desc")
    List<Article> findArticleByTag(@Param("tag") String tag);

    @Select("select id,article_id As articleId,original_author As originalAuthor,article_tags As articleTags,article_title As articleTitle,article_type As articleType,publish_date As publishDate,article_categories As articleCategories,article_abstract As articleAbstract,article_likes As articleLikes from article where author=#{author} order by id desc")
    List<Article> myArticle(@Param("author") String author);
}
