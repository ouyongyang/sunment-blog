var category = "";
$.ajax({
    type: 'HEAD', // 获取头信息，type=HEAD即可
    url : window.location.href,
    async:false,
    success:function (data, status, xhr) {
        category = xhr.getResponseHeader("category");
    }
});

ajaxFirst(1,category);

function ajaxFirst(currentPage,category1) {
    $.ajax({
        type:'GET',
        url:'/categories/getCategoryArticle',
        dataType:'json',
        data:{
            category:category1,
            rows:"5",
            pageNum:currentPage
        },
        success:function (data) {
            putInTagArticleInfo(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#pagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    ajaxFirst(currentPage, category1);
                }
            });

        },
        error:function () {
            alert("获取分类文章失败");
        }
    });
}

//填充标签文章信息
function putInTagArticleInfo(data) {
    var categoryTimeline = $('.categoryTimeline');
    categoryTimeline.empty();
    var timeLine = $('<div class="timeline timeline-wrap"></div>');
    timeLine.append($('<div class="timeline-row">' +
        '<span class="node" style="-webkit-box-sizing: content-box;-moz-box-sizing: content-box;box-sizing: content-box;">' +
        '<i class="fa fa-folder"></i>' +
        '</span>' +
        '<h1 class="title  am-animation-slide-top"># '+ data['category'] + '</h1>' +
        '</div>'));
    $.each(data['result'],function (index,obj) {

        var timelineRowMajor = $('<div class="timeline-row-major"></div>');
        timelineRowMajor.append($('<span class="node am-animation-slide-top am-animation-delay-1"></span>'));
        var content = $('<div class="content am-comment-main am-animation-slide-top am-animation-delay-1"></div>');
        content.append($('<header class="am-comment-hd" style="background: #fff">' +
            '<div class="contentTitle am-comment-meta">' +
            '<a href="/jump/findArticle?articleId=' + obj['articleId'] + '&originalAuthor=' + obj['originalAuthor'] + '">' + obj['articleTitle'] + '</a>' +
            '</div>' +
            '</header>'));
        var amCommentBd = $('<div class="am-comment-bd"></div>');
        amCommentBd.append($('<i class="fa fa-calendar"> <a href="/jump/archives?archive='+ obj['publishDate'] + '">' + obj['publishDate'] + '</a></i>' +
            '<i class="fa fa-folder"> <a href="/jump/categories?category=' + obj['articleCategories'] + '">' + obj['articleCategories'] + '</a></i>'));
        var amCommentBdTags = $('<i class="am-comment-bd-tags fa fa-tag"></i>');
        for(var i=0;i<obj['articleTags'].length;i++){
            var tag = $('<a href="/jump/tags?tag=' + obj['articleTags'][i] + '">' + obj['articleTags'][i] + '</a>');
            amCommentBdTags.append(tag);
            if(i != (obj['articleTags'].length-1)){
                amCommentBdTags.append(",");
            }
        }
        amCommentBd.append(amCommentBdTags);
        content.append(amCommentBd);
        timelineRowMajor.append(content);
        timeLine.append(timelineRowMajor);
    });
    categoryTimeline.append(timeLine);
    categoryTimeline.append($('<div class="my-row" id="page-father">' +
        '<div id="pagination">' +
        '<ul class="am-pagination  am-pagination-centered">' +
        '<li class="am-disabled"><a href="#">&laquo; 上一页</a></li>' +
        '<li class="am-active"><a href="#">1</a></li>' +
        '<li><a href="#">2</a></li>' +
        '<li><a href="#">3</a></li>' +
        '<li><a href="#">4</a></li>' +
        '<li><a href="#">5</a></li>' +
        '<li><a href="#">下一页 &raquo;</a></li>' +
        '</ul>' +
        '</div>' +
        '</div>'));
}

$.ajax({
    type:'GET',
    url:'/categories/allCategoriesNameArticleNum',
    dataType:'json',
    data:{
    },
    success:function (data) {
        var categories = $('.categories');
        categories.empty();
        categories.append($('<div class="categories-title">' +
            '<h3>Categories</h3>' +
            '</div>'));
        var categoriesComment = $('<div class="categories-comment am-animation-slide-top"></div>');
        $.each(data['result'], function (index, obj) {
            categoriesComment.append($('<div class="category">' +
                '<span><a class="categoryName">' + obj['categoryName'] + '</a><span class="categoryNum">(' + obj['categoryArticleNum'] + ')</span></span>' +
                '</div>'));
        });
        categories.append(categoriesComment);
        $('.categoryName').click(function () {
            var $this = $(this);
            var categoryName = $this.html();
            ajaxFirst(1,categoryName);
        })
    },
    error:function () {
        alert("获取分类信息失败");
    }
});