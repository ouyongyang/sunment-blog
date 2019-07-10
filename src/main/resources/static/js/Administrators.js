//点击文章管理
$('.superAdminList .articleManagement').click(function () {
    getArticleManagement(1);
});

var deleteArticleId="";
var deleteCategories="";


$('.superAdminList .superAdminClick').click(function () {
    var flag = $(this).attr('class').substring(16);
    $('#statistics,#articleManagement,#articleComment,#articleCategories,#secAccountManagement,#userFeedback,#privateWord').css("display","none");
    $("#" + flag).css("display","block");
});

//获得文章管理文章
function getArticleManagement(currentPage) {
    $.ajax({
        type:'get',
        url:'/administrators/getArticleManagement',
        dataType:'json',
        data:{
            rows:5,
            pageNum:currentPage
        },
        success:function (data) {
            putInArticleManagement(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#articleManagementPagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    getArticleManagement(currentPage);
                }
            });
        },
        error:function () {
            alert("获取文章信息失败");
        }
    });
}

//填充文章管理
function putInArticleManagement(data) {
    var articleManagementTable = $('.articleManagementTable');
    articleManagementTable.empty();
    $.each(data['result'], function (index, obj) {
        articleManagementTable.append($('<tr id="a' + obj['id'] + '"><td><a href="findArticle?articleId=' + obj['articleId'] + '&originalAuthor=' + obj['originalAuthor'] + '">' + obj['articleTitle'] + '</a></td><td>' + obj['publishDate'] + '</td><td>' + obj['articleCategories'] + '</td> <td><span class="am-badge am-badge-success">' + obj['visitorNum'] + '</span></td>' +
            '<td>' +
            '<div class="am-dropdown" data-am-dropdown>' +
            '<button class="articleManagementBtn articleEditor am-btn am-btn-secondary">编辑</button>' +
            '<button class="articleDeleteBtn articleDelete am-btn am-btn-danger">删除</button>' +
            '</div>' +
            '</td>' +
            '</tr>'));
    });
    articleManagementTable.append($('<div class="my-row" id="page-father">' +
        '<div id="articleManagementPagination">' +
        '<ul class="am-pagination  am-pagination-centered">' +
        '</ul>' +
        '</div>' +
        '</div>'));

    $('.articleManagementBtn').click(function () {
        var $this = $(this);
        var id = $this.parent().parent().parent().attr("id").substring(1);
        window.location.replace("/jump/edit?id=" + id);
    });
    $('.articleDeleteBtn').click(function () {
        var $this = $(this);
        deleteArticleId = $this.parent().parent().parent().attr("id").substring(1);
        $('#deleteAlter').modal('open');
    })
}

/*删除文章*/
$('.sureArticleDeleteBtn').click(function () {
    $.ajax({
        type:'get',
        url:'/administrators/deleteArticle',
        dataType:'json',
        data:{
            id:deleteArticleId
        },
        success:function (data) {
            if(data == 0){
                alert("删除文章失败")
            } else {
                alert("删除文章成功");
                getArticleManagement(1);
            }
        },
        error:function () {
            alert("删除失败");
        }
    });
})

getStatisticsInfo();

//获取统计信息
function getStatisticsInfo() {
    $.ajax({
        type:'get',
        url:'/administrators/getStatisticsInfo',
        dataType:'json',
        data:{
        },
        success:function (data) {
            $('.commentNum').html(data['commentNum']);
            $('.allVisitor').html(data['allVisitor']);
            $('.yesterdayVisitor').html(data['yesterdayVisitor']);
            $('.allUser').html(data['allUser']);
            $('.articleNum').html(data['articleNum']);

        },
        error:function () {
            alert("获取统计信息失败");
        }
    });
}



//点击反馈
$('.superAdminList .userFeedback').click(function () {
    getAllFeedback(1);
});

//获得反馈信息
function getAllFeedback(currentPage) {
    $.ajax({
        type:'get',
        url:'/administrators/getAllFeedback',
        dataType:'json',
        data:{
            rows:5,
            pageNum:currentPage
        },
        success:function (data) {
            putInAllFeedback(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#feedbackPagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    getAllFeedback(currentPage);
                }
            });
        },
        error:function () {
            alert("获取反馈信息失败");
        }
    });
}

//填充反馈信息
function putInAllFeedback(data) {
    var feedbackInfos = $('.feedbackInfos');
    feedbackInfos.empty();
    if(data['result'].length == 0){
        feedbackInfos.append('<div class="noFeedback">无反馈信息</div>');
    } else {
        $.each(data['result'], function (index, obj) {
            var feedbackInfo = $('<div class="feedbackInfo"></div>');
            feedbackInfo.append('<div class="feedbackInfoTitle">' +
                '<span class="feedbackName">' + obj['person'] + '</span>' +
                '<span class="feedbackTime">' + obj['feedbackDate'] + '</span>' +
                '</div>');
            feedbackInfo.append('<div class="feedbackInfoContent">' +
                '<span class="feedbackInfoContentWord">反馈内容：</span>' +
                obj['feedbackContent'] +
                '</div>');
            var feedbackInfoContact = $('<div class="feedbackInfoContact"></div>');
            if(obj['contactInfo'] !== ""){
                feedbackInfoContact.append('<span class="contactInfo">联系方式：</span>' +
                    obj['contactInfo']);
            } else {
                feedbackInfoContact.append('<span class="contactInfo">联系方式：</span>' + '无'
                );
            }
            feedbackInfo.append(feedbackInfoContact);
            feedbackInfos.append(feedbackInfo);
        });
        feedbackInfos.append($('<div class="my-row" id="page-father">' +
            '<div id="feedbackPagination">' +
            '<ul class="am-pagination  am-pagination-centered">' +
            '<li class="am-disabled"><a href="">&laquo; 上一页</a></li>' +
            '<li class="am-active"><a href="">1</a></li>' +
            '<li><a href="">2</a></li>' +
            '<li><a href="">3</a></li>' +
            '<li><a href="">4</a></li>' +
            '<li><a href="">5</a></li>' +
            '<li><a href="">下一页 &raquo;</a></li>' +
            '</ul>' +
            '</div>' +
            '</div>'));
    }

}



//点击员工消息
$('.superAdminList .privateWord').click(function () {
    $.ajax({
        type:'post',
        url:'/administrators/getAllPrivateWord',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if(data['result'].length == 0){
                $('.privateWord').append($('<div>无员工消息</div>'));
            } else {
                putInAllPrivateWord(data);
            }
        },
        error:function () {
            alert("获取员工消息失败");
        }
    });
});
//填充员工消息
function putInAllPrivateWord(data) {
    var privateWord = $('.superAdminInfo .privateWord');
    privateWord.empty();
    var amPanelGroup = $('<div class="am-panel-group" id="accordion"></div>');
    $.each(data['result'], function (index,obj) {
        var amPanel = $('<div class="am-panel am-panel-default"></div>');
        amPanel.append('<div class="am-panel-hd">' +
            '<h4 style="font-weight: 500" class="am-panel-title" data-am-collapse="{parent: \'#accordion\', target: \'#do-not-say-' + index + '\'}">' +
            obj['publisher'] +
            '</h4>' +
            '</div>');
        var doNotSay = $('<div id="do-not-say-' + index + '" class="am-panel-collapse am-collapse"></div>');
        var userPrivateWord = $('<div class="userPrivateWord am-panel-bd"></div>');
        var userPrivateWordUl = $('<ul class="am-list am-list-border"></ul>');
        $.each(obj['content'], function (index1, obj1) {
            if(obj1['replyContent'] !== ""){
                userPrivateWordUl.append('<li>' +
                    '<div class="userPrivateWordTime">' +
                    obj1['publisherDate'] +
                    '</div><br>' +
                    '<a id="p' + obj1['id'] + '">' + obj1['privateWord']+
                    '<br>' +
                    '<div class="myReply">' +
                    '回复：<span class="myReplyContent">' + obj1['replyContent'] + '</span>' +
                    '</div></a>' +
                    '</li>');
            } else {
                userPrivateWordUl.append('<li>' +
                    '<div class="userPrivateWordTime">' +
                    obj1['publisherDate'] +
                    '</div><br>' +
                    '<a id="p' + obj1['id'] + '">' + obj1['privateWord']+
                    '<br>' +
                    '<div class="myNoReply">' +
                    '回复：<span class="myReplyContent">还没有回复</span>' +
                    '</div></a><div class="userPrivateWordReply am-animation-slide-top">' +
                    '<textarea class="replyTextarea" placeholder="请回复"></textarea>' +
                    '<button type="button" class="userPrivateWordReplyBtn am-btn am-btn-success am-round">回复</button>' +
                    '<button type="button" class="userPrivateWordReplyCloseBtn am-btn am-round">取消</button>' +
                    '</div>' +
                    '</li>');
            }
        });
        userPrivateWord.append(userPrivateWordUl);
        doNotSay.append(userPrivateWord);
        amPanel.append(doNotSay);
        amPanelGroup.append(amPanel);
    });
    privateWord.append(amPanelGroup);

    $('.userPrivateWord a').click(function () {
        var $this = $(this);
        var userPrivateWordReply = $this.next();
        userPrivateWordReply.toggle();
    });
    $('.userPrivateWordReplyCloseBtn').click(function () {
        $('.userPrivateWordReplyCloseBtn').parent().css("display","none");
    });

    $('.userPrivateWordReplyBtn').click(function () {
        var $this = $(this);
        var replyId = $this.parent().prev().attr("id").substring(1);
        var textarea = $this.prev().val();
        if(textarea.length == 0){
            dangerNotice("你还没有填写回复内容！")
        } else {
            $.ajax({
                type:'post',
                url:'/administrators/replyPrivateWord',
                dataType:'json',
                data:{
                    replyId:replyId,
                    replyContent:textarea
                },
                success:function (data) {
                    if(data['status'] == 403){
                        $.get("/jump/toLogin",function(data,status,xhr){
                            window.location.replace("/jump/login");
                        });
                    } else {
                        successNotice("回复成功！");
                        $this.prev().val("");
                        $('#p' + data['result']['replyId']).find('.myReplyContent').html(data['result']['replyContent']);
                        $this.parent().css("display","none");
                        $this.parent().prev().find('.myNoReply').css("color","#b5b5b5");
                        $this.parent().prev().attr('disabled', 'true');
                    }
                },
                error:function () {
                    alert("获取员工消息失败");
                }
            });
        }
    });
}



//点击分类管理
$('.superAdminList .articleCategories').click(function () {
    getArticleCategories(1);
});
//获得文章分类
function getArticleCategories(currentPage) {
    $.ajax({
        type:'get',
        url:'/administrators/getArticleCategories',
        dataType:'json',
        data:{
            rows:5,
            pageNum:currentPage
        },
        success:function (data) {
            putInArticleCategories(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#articleCategoriesPagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    getArticleCategories(currentPage);
                }
            });
        },
        error:function () {
            alert("获取文章分类失败");
        }
    });
}

//填充文章分类
function putInArticleCategories(data) {
    var articleManagementTable = $('.articleCategoriesTable');
    articleManagementTable.empty();
    $.each(data['result'], function (index, obj) {
        articleManagementTable.append($('<tr id="a' + obj['id'] + '"><td>' + obj['id'] + '</td><td>' + obj['categoryName'] + '</td>' +
            '<td>' +
            '<div class="am-dropdown" data-am-dropdown>' +
            /*'<button class="articleEditorBtn articleEditor am-btn am-btn-secondary">编辑</button>' +*/
            '<button class="articleDeleteBtn articleDelete am-btn am-btn-danger">删除</button>' +
            '</div>' +
            '</td>' +
            '</tr>'));
    });
    articleManagementTable.append($('<div class="my-row" id="page-father">' +
        '<div id="articleCategoriesPagination">' +
        '<ul class="am-pagination  am-pagination-centered">' +
        '</ul>' +
        '</div>' +
        '</div>'));

    $('.articleCategoriesBtn').click(function () {
        $('#categories').modal('open');
    });
    $('.articleDeleteBtn').click(function () {
        var $this = $(this);
        deleteCategories = $this.parent().parent().parent().attr("id").substring(1);
        $('#delete').modal('open');
    })
}

//模态框表单提交
$('#change_categories_btn').click(function () {
    var modal_phone = $("#modal_phone");
    var categoriesName = modal_phone.val();
    $.ajax({
        type:'post',
        url:'/administrators/addAtegories',
        dataType:'json',
        data:{
            categoriesName:categoriesName
        },
        success:function (data) {
            if(data == 0){
                $('#categories').modal('close');
                alert("添加分类成功");
                getArticleCategories(1);
            }
            else {
                alert("分类名已存在");
            }
        },
        error:function () {
            alert("添加失败");
        }
    });
})

/*删除文章分类*/
$('.deleteBtn').click(function () {
    $.ajax({
        type:'get',
        url:'/administrators/deleteCategories',
        dataType:'json',
        data:{
            id:deleteCategories
        },
        success:function (data) {
            if(data == 0){
                alert("删除文章分类失败")
            } else {
                alert("删除文章分类成功");
                getArticleCategories(1);
            }
        },
        error:function () {
            alert("删除失败");
        }
    });
})




//点击用户管理
$('.superAdminList .secAccountManagement').click(function () {
    secAccountManagement(1);
});
//获得用户管理
function secAccountManagement(currentPage) {
    $.ajax({
        type:'get',
        url:'/administrators/getSecAccountManagement',
        dataType:'json',
        data:{
            rows:5,
            pageNum:currentPage
        },
        success:function (data) {
            putInSecAccountManagement(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#secAccountManagementPagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    secAccountManagement(currentPage);
                }
            });
        },
        error:function () {
            alert("获取文章分类失败");
        }
    });
}

function onGenderRenderer(e) {
    var Genders = [ {id : 0, text : '不通过'}, {id : 1, text : '通过'}];
    for ( var i = 0; i < Genders.length; i++) {
        var g = Genders[i];
        if (g.id == e){
            return g.text;
        }
    }
}

//填充用户管理
var agreeId="";
var disAgreeId="";
var deleteSecAccount="";
function putInSecAccountManagement(data) {
    var articleManagementTable = $('.secAccountManagementTable');
    articleManagementTable.empty();
    $.each(data['result'], function (index, obj) {
        var temp=onGenderRenderer(obj['type']);
        articleManagementTable.append($('<tr id="a' + obj['id'] + '"><td>' + obj['username'] + '</td>' +
            '<td>' + obj['phone'] + '</td>'+'<td>' + obj['email'] + '</td>'+'<td><div class="headPortrait"><img src="'+ obj['avatarImagesUrl'] +'"/></div></td>'+
            '<td>' + obj['lastLogintime'] + '</td>'+'<td>'+temp+'</td>'+
            '<td>' +
            '<div class="am-dropdown" data-am-dropdown>' +
            '<button class="agreeBtn articleAgree am-btn am-btn-secondary">通过</button>' +
            '<button class="secAccountDeleteBtn articleDelete am-btn am-btn-danger">删除</button>' +
            '<button class="disAgreeBtn articleDisAgree am-btn am-btn-secondary">不通过</button>' +
            '</div>' +
            '</td>' +
            '</tr>'));
    });
    articleManagementTable.append($('<div class="my-row" id="page-father">' +
        '<div id="secAccountManagementPagination">' +
        '<ul class="am-pagination  am-pagination-centered">' +
        '</ul>' +
        '</div>' +
        '</div>'));
//审批通过员工
    $('.agreeBtn').click(function () {
        var $this = $(this);
        agreeId = $this.parent().parent().parent().attr("id").substring(1);
        $.ajax({
            type:'post',
            url:'/administrators/agreeSecAccount',
            dataType:'json',
            data:{
                id:agreeId
            },
            success:function (data) {
                if(data == 0){
                    alert("审批通过成功");
                    secAccountManagement(1);
                }
                else {
                    alert("员工已审批通过!");
                }
            },
            error:function () {
                alert("失败");
            }
        });
    });
    //审批不通过员工
    $('.disAgreeBtn').click(function () {
        var $this = $(this);
        disAgreeId = $this.parent().parent().parent().attr("id").substring(1);
        $.ajax({
            type:'post',
            url:'/administrators/disAgreeSecAccount',
            dataType:'json',
            data:{
                id:disAgreeId
            },
            success:function (data) {
                if(data == 0){
                    alert("审批不通过成功");
                    secAccountManagement(1);
                }
                else {
                    alert("员工已审批不通过!");
                }
            },
            error:function () {
                alert("失败");
            }
        });
    });
    $('.secAccountDeleteBtn').click(function () {
        var $this = $(this);
        deleteSecAccount = $this.parent().parent().parent().attr("id").substring(1);
        $('#secAccountDelete').modal('open');
    })
}
/*删除用户*/
$('.secAccountDelete').click(function () {
    $.ajax({
        type:'get',
        url:'/administrators/deleteSecAccount',
        dataType:'json',
        data:{
            id:deleteSecAccount
        },
        success:function (data) {
            if(data == 0){
                alert("删除用户失败")
            } else {
                alert("删除用户成功");
                secAccountManagement(1);
            }
        },
        error:function () {
            alert("删除失败");
        }
    });
})


var commentDeleteId="";
//点击评论管理
$('.superAdminList .articleComment').click(function () {
    getArticleComment(1);
});
//获得评论管理
function getArticleComment(currentPage) {
    $.ajax({
        type:'get',
        url:'/administrators/getArticleComment',
        dataType:'json',
        data:{
            rows:5,
            pageNum:currentPage
        },
        success:function (data) {
            putInArticleComment(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#articleCategoriesPagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    getArticleComment(currentPage);
                }
            });
        },
        error:function () {
            alert("获取文章分类失败");
        }
    });
}

//填充评论管理
function putInArticleComment(data) {
    var articleManagementTable = $('.articleCommentTable');
    articleManagementTable.empty();
    $.each(data['result'], function (index, obj) {
        articleManagementTable.append($('<tr id="a' + obj['id'] + '"><td>' + obj['articleTitle'] + '</td><td>' + obj['commentUsername'] + '</td>' +
            '<td>' + obj['commentContent'] + '</td>'+'<td>' + obj['commentDate'] + '</td>'+'<td>' + obj['likes'] + '</td>'+
            '<td>' +
            '<div class="am-dropdown" data-am-dropdown>' +
            '<button class="articleDeleteBtn articleDelete am-btn am-btn-danger">删除</button>' +
            '</div>' +
            '</td>' +
            '</tr>'));
    });
    articleManagementTable.append($('<div class="my-row" id="page-father">' +
        '<div id="articleCategoriesPagination">' +
        '<ul class="am-pagination  am-pagination-centered">' +
        '</ul>' +
        '</div>' +
        '</div>'));

    $('.articleDeleteBtn').click(function () {
        var $this = $(this);
        commentDeleteId = $this.parent().parent().parent().attr("id").substring(1);
        $('#commentDelete').modal('open');
    })
}

/*删除评论*/
$('.commentDelete').click(function () {
    $.ajax({
        type:'get',
        url:'/administrators/deleteComment',
        dataType:'json',
        data:{
            id:commentDeleteId
        },
        success:function (data) {
            if(data == 0){
                alert("删除评论失败")
            } else {
                alert("删除评论成功");
                getArticleComment(1);
            }
        },
        error:function () {
            alert("删除失败");
        }
    });
})
