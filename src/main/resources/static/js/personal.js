//左侧菜单栏点击事件
$('.userList .clickLi').click(function () {
    var flag = $(this).attr('class').substring(8);
    $('#personalDate,#basicSetting,#leaveMessage,#privateWord,#myArticle').css("display","none");
    $("#" + flag).css("display","block");
});
//修改密码input设置为空
$('.basicSetting').click(function () {
    $('#phone').val("");
    $('#authCode').val("");
    $('#password').val("");
    $('#surePassword').val("");
});
//更改头像
function imgChange(e) {
    var dom =$("input[id^='imgTest']")[0];
    var reader = new FileReader();
    reader.onload = (function (file) {
        return function (e) {
            $.ajax({
                type:'POST',
                url:'/personal/uploadHead',
                dataType:'json',
                data:{
                    img:this.result
                },
                success:function (data) {
                    if(data['status'] == 403){
                        $.get("/jump/toLogin",function(data,status,xhr){
                            window.location.replace("/jump/login");
                        });
                    } else {
                        if(data['status'] == 200){
                            $('#headPortrait').attr("src",data['avatarImgUrl']);
                            successNotice("更改头像成功");
                        } else {
                            dangerNotice("更改头像失败")
                        }
                    }

                },
                error:function () {
                }
            });
        };
    })(e.target.files[0]);
    reader.readAsDataURL(e.target.files[0]);
}

//获得头像url
showHeadPortrait();
//获得个人信息
getAccountPersonalInfo();
//获得头像url
function showHeadPortrait() {
    $.ajax({
        type:'get',
        url:'/personal/getHeadPortraitUrl',
        dataType:'json',
        data:{
        },
        success:function (data) {
            $('#headPortrait').attr("src",data['avatarImgUrl']);
        },
        error:function () {
        }
    });
}

//获得个人信息
function getAccountPersonalInfo() {
    $.ajax({
        type:'get',
        url:'/personal/getAccountPersonalInfo',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if(data['status'] == 403){
                $.get("/jump/toLogin",function(data,status,xhr){
                    window.location.replace("/jump/login");
                });
            } else {
                $('#username').attr("value",data['result']['username']);
                var personalPhone = data['result']['phone'];
                $('#personalPhone').html(personalPhone.substring(0,3) + "****" + personalPhone.substring(7));
                $('#trueName').attr("value",data['result']['trueName']);
                $('#birthday').attr("value",data['result']['birthday']);
                var gender = data['result']['gender'];
                if(gender == "男"){
                    $('.genderTable input').eq(0).attr("checked","checked");
                } else {
                    $('.genderTable input').eq(1).attr("checked","checked");
                }
                $('#email').attr("value",data['result']['email']);
                $('#personalProfile').val(data['result']['personalProfile']);
            }
        },
        error:function () {
        }
    });

    //保存个人资料
    var savePersonalDateBtn = $('#savePersonalDateBtn');
    var username = $('#username');
    var trueName = $('#trueName');
    var birthday = $('#birthday');
    var gender = $('.genderTable input');
    var email = $('#email');
    var personalProfile = $('#personalProfile');
    savePersonalDateBtn.click(function () {
        var usernameValue = username.val();
        var genderValue = "男";
        if(usernameValue.length === 0){
            dangerNotice("昵称不能为空");
        } else if(!gender[0].checked && !gender[1].checked){
            dangerNotice("性别不能为空");
        } else {
            if(gender[0].checked){
                genderValue = "男";
            } else {
                genderValue = "女";
            }
            $.ajax({
                type:'post',
                url:'/personal/savePersonalInfo',
                dataType:'json',
                data:{
                    username:username.val(),
                    trueName:trueName.val(),
                    birthday:birthday.val(),
                    gender:genderValue,
                    email:email.val(),
                    personalProfile:personalProfile.val()
                },
                success:function (data) {
                    if(data['status'] == 403){
                        $.get("/jump/toLogin",function(data,status,xhr){
                            window.location.replace("/jump/login");
                        });
                    } else {
                        if(data['status'] == 200){
                            alert("更改个人信息成功,重新登录后生效");
                            $.get("/jump/toLogin",function(data,status,xhr){
                                window.location.replace("/jump/login");
                            });
                        } else if (data['status'] == 500){
                            dangerNotice("该昵称已被占用");
                        } else if (data['status'] == 201){
                            successNotice("更改个人信息成功");
                        } else {
                            dangerNotice("更改个人信息失败");
                        }
                    }
                },
                error:function () {
                }
            });
        }
    });
}

var phone = $('#phone');
var authCode = $('#authCode');
var password = $('#password');
var surePassword = $('#surePassword');

phone.blur(function () {
    var pattren = /^1[345789]\d{9}$/;
    var phoneValue = phone.val();
    if(pattren.test(phoneValue)){
        phone.removeClass("wrong");
        phone.addClass("right");
    } else {
        phone.removeClass("right");
        phone.addClass("wrong");
    }
});
phone.focus(function () {
    $('.notice').css("display","none");
});

// 定义发送时间间隔(s)
var my_interval;
my_interval = 60;
var timeLeft = my_interval;
//重新发送计时函数
var timeCount = function() {
    window.setTimeout(function() {
        if(timeLeft > 0) {
            timeLeft -= 1;
            $('#authCodeBtn').html(timeLeft + "秒重新发送");
            timeCount();
        } else {
            $('#authCodeBtn').html("重新发送");
            timeLeft=60;
            $("#authCodeBtn").attr('disabled',false);
        }
    }, 1000);
};
//发送短信验证码
$('#authCodeBtn').click(function () {
    $('.notice').css("display","none");
    $('#authCodeBtn').attr('disabled',true);
    var phoneLen = phone.val().length;
    if(phoneLen == 0){
        dangerNotice("手机号不能为空");
        $('#authCodeBtn').attr('disabled',false);
    } else {
        if(phone.hasClass("right")){
            $.ajax({
                type:'post',
                url:'/phone/getVerificationCode',
                dataType:'json',
                data:{
                    phone:$('#phone').val(),
                    sign:"changePassword"
                },
                success:function (data) {
                    if(parseInt(data) == 1) {
                        successNotice("短信验证码发送成功");
                        timeCount();
                    }
                },
                error:function () {
                }
            });
        } else {
            dangerNotice("手机号不正确");
            $('#authCodeBtn').attr('disabled',false);
        }
    }

});

//修改密码
$('#changePasswordBtn').click(function () {
    $('.notice').css("display","none");
    if(phone.val().length === 0){
        dangerNotice("手机号不能为空");
    } else if (phone.hasClass("wrong")){
        dangerNotice("手机号不正确");
    } else if (authCode.val().length === 0){
        dangerNotice("验证码不能为空");
    } else if (password.val().length === 0){
        dangerNotice("新密码不能为空");
    } else if (surePassword.val().length === 0){
        dangerNotice("确认密码不能为空");
    } else{
        if (password.val() !== surePassword.val()){
            dangerNotice("确认密码不正确");
        } else {
            $.ajax({
                type:'post',
                url:'/login/modifyPassword',
                dataType:'json',
                data:{
                    phone:phone.val(),
                    authCode:authCode.val(),
                    newPassword:password.val()
                },
                success:function (data) {
                    if(data == "0"){
                        dangerNotice("验证码不正确")
                    }else if (data == "2"){
                        dangerNotice("手机号不存在")
                    }else if(data == "1"){
                        successNotice("密码修改成功");
                    }
                },
                error:function () {
                    alert("修改密码失败");
                }
            })
        }
    }
});



//点击与管理员联系
$('.privateWord').click(function () {
    getPrivateWordByPublisher(1);
});

//获得管理员联系内容
function getPrivateWordByPublisher(currentPage) {
    $.ajax({
        type:'post',
        url:'/personal/getPrivateWordByPublisher',
        dataType:'json',
        data:{
            rows:"5",
            pageNum:currentPage
        },
        success:function (data) {
            if(data['status'] == 403){
                $.get("/jump/toLogin",function(data,status,xhr){
                    window.location.replace("/jump/login");
                });
            }
            putInPrivateWord(data);

            //分页
            $("#privateWordPagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    getPrivateWordByPublisher(currentPage);
                }
            });
        },
        error:function () {
            alert("获取悄悄话失败");
        }
    })
}

//填充管理员联系内容
function putInPrivateWord(data) {
    var yesterdayContent = $('.yesterdayContent');
    yesterdayContent.empty();
    if(data['result'].length == 0){
        yesterdayContent.append($('<div class="noYesterday">' +
            '你的曾今我好像未曾参与耶' +
            '</div>'));
    } else {
        var says = $('<div class="says"></div>');
        $.each(data['result'], function (index, obj) {
            var say = $('<div class="say"></div>');
            var youSay = $('<div class="youSay"></div>');
            youSay.append($('<div class="youSayTime">' +
                obj['publisherDate'] +
                '</div>'));
            youSay.append($('<div class="you">' +
                '<span>' + obj['publisher'] + '</span>：' + obj['privateWord'] +
                '</div>'));
            if(obj['replyContent'] !== ""){
                youSay.append($('<div class="me">' +
                    obj['replyContent'] + '：' + obj['replier'] +
                    '</div>'));
            } else {
                youSay.append($('<div class="me">' +
                    '<span class="noReply">暂未收到回复</span>' +
                    '</div>'));
            }
            say.append(youSay);
            says.append(say);
        });
        says.append($('<div class="my-row" id="page-father">' +
            '<div id="privateWordPagination">' +
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
        yesterdayContent.append(says);
    }
}


//发布管理员联系
$('.userSayBtn').click(function () {
    var userSay = $('#userSay');
    if(userSay.val().length == 0){
        dangerNotice("你还没说两句呢");
    } else {
        $.ajax({
            type:'post',
            url:'/personal/sendPrivateWord',
            dataType:'json',
            data:{
                privateWord:userSay.val()
            },
            success:function (data) {
                if(data['status'] == 403){
                    $.get("/toLogin",function(data,status,xhr){
                        window.location.replace("/login");
                    });
                } else {
                    if(data['status'] == 200){
                        successNotice("管理员联系成功");
                    }
                    userSay.val("");
                    getPrivateWordByPublisher(1);
                }
            },
            error:function () {
                alert("管理员联系失败");
            }
        })
    }
});


// 点击评论留言
$('.leaveMessage').click(function () {
    getUserComment(1);
});

//点击评论留言中的留言
$('#userLeaveMessageClick').click(function () {
    getUserLeaveMessage(1);
});

//获得评论
function getUserComment(currentPage) {
    $.ajax({
        type:'post',
        url:'/personal/getPersonalComment',
        dataType:'json',
        data:{
            rows:"5",
            pageNum:currentPage
        },
        success:function (data) {
            if(data['status'] == 403){
                $.get("/jump/toLogin",function(data,status,xhr){
                    window.location.replace("/jump/login");
                });
            }
            putInCommentInfo(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#commentPagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    getUserComment(currentPage);
                }
            });
        },
        error:function () {
            alert("获取评论留言失败");
        }
    })
}
//获得留言
function getUserLeaveMessage(currentPage) {
    $.ajax({
        type:'post',
        url:'/personal/getPersonalLeaveMessage',
        dataType:'json',
        data:{
            rows:"5",
            pageNum:currentPage
        },
        success:function (data) {
            if(data['status'] == 403){
                $.get("/jump/toLogin",function(data,status,xhr){
                    window.location.replace("/jump/login");
                });
            }
            putInLeaveMessageInfo(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#leaveMessagePagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    getUserLeaveMessage(currentPage);
                }
            });
        },
        error:function () {
            alert("获取评论留言失败");
        }
    })
}

//填充用户评论
function putInCommentInfo(date) {
    var comment = $('.comment');
    comment.empty();
    if(date['result'].length == 0){
        comment.append($('<div class="noComment">' +
            '你还没有任何评论哦' +
            '</div>'));
    } else {
        var amList = $('<ul class="am-list"></ul>');
        $.each(date['result'], function (index, obj) {
            amList.append($('<li class="am-g am-list-item-dated">' +
                '<a target="_blank" href="/jump/findArticle?articleId=' + obj['articleId'] + '&originalAuthor=' + obj['originalAuthor'] + '" style="padding: 5px 0 2px 0" class="leaveMessageTitle am-list-item-hd">' + obj['articleTitle'] + '</a>' +
                '<span class="am-list-date" style="color: #a7baaa">' + obj['commentDate'] + '</span>' +
                '<div class="leaveMessageContent">' +
                obj['answerer'] + '：' +obj['commentContent'] +
                '</div>' +
                '<span class="reply"><span class="replyNum">' + obj['replyNum'] + '</span>个回复</span>' +
                '</li>'))
        });
        var amListNewsBd = $('<div class="am-list-news-bd"></div>');
        amListNewsBd.append(amList);
        amListNewsBd.append($('<div class="my-row" id="page-father">' +
            '<div id="commentPagination">' +
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
        comment.append(amListNewsBd);
    }
}
//填充用户留言
function putInLeaveMessageInfo(data) {
    var userLeaveMessage = $('.userLeaveMessage');
    userLeaveMessage.empty();
    if(data['result'].length == 0){
        userLeaveMessage.append($('<div class="noLeaveMessage">' +
            '你还没有任何留言哦' +
            '</div>'))
    } else {
        var amList = $('<ul class="am-list"></ul>');
        $.each(data['result'], function (index, obj) {
            amList.append($('<li class="am-g am-list-item-dated">' +
                '<a target="_blank" href="/' + obj['pageName'] + '" class="leaveMessageTitle am-list-item-hd ">' + obj['answerer'] + "：" + obj['leaveMessageContent'] + '</a>' +
                '<span class="am-list-date">' + obj['leaveMessageDate'] + '</span>' +
                '</li>'));
        })
        var amListNewsBd = $('<div class="am-list-news-bd"></div>');
        amListNewsBd.append(amList);
        amListNewsBd.append($('<div class="my-row" id="page-father">' +
            '<div id="leaveMessagePagination">' +
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
        userLeaveMessage.append(amListNewsBd);
    }
}

//我的文章
$('.myArticle').click(function () {
    ajaxFirst(1);
});

function ajaxFirst(currentPage) {
    //加载时请求
    $.ajax({
        type: 'POST',
        url: '/index/myArticle',
        dataType: 'json',
        data: {
            rows:"5",
            pageNum:currentPage
        },
        success: function (data) {
            //放入数据
            putInArticle(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#pagination").paging({
                rows:data[data.length-1]['pageSize'],//每页显示条数
                pageNum:data[data.length-1]['pageNum'],//当前所在页码
                pages:data[data.length-1]['pages'],//总页数
                total:data[data.length-1]['total'],//总记录数
                callback:function(currentPage){
                    ajaxFirst(currentPage);
                }
            });
        },
        error: function () {
            alert("获得文章信息失败！");
        }
    });
}


//填充文章
function putInArticle(data) {
    $('.articles').empty();
    var articles = $('.articles');
    $.each(data, function (index, obj) {
        if(index != (data.length) - 1){
            var center = $('<div class="center">' +
                '<header class="article-header">' +
                '<div class="left"><h1 itemprop="name">' +
                '<a class="article-title" href="' + obj['thisArticleUrl'] + '" target="_blank">' + obj['articleTitle'] + '</a>' +
                '</h1></div>' +'<div id="a' + obj['id'] + '" class="right"><span id="editArticle" class="articleType am-badge am-badge-secondary am-text-default">修改</span><span id="deleteArticle" class="articleType am-badge am-badge-danger am-text-default">删除</span></div>'+
                '<div class="article-meta row">' +
                '<span class="articleType am-badge am-badge-success">' + obj['articleType'] + '</span>' +
                '<div class="articlePublishDate">' +
                '<i class="am-icon-calendar"><a class="linkColor" href="/jump/archives?archive=' + obj['publishDate'] + '"> ' + obj['publishDate'] + '</a></i>' +
                '</div>' +
                '<div class="originalAuthor">' +
                '<i class="am-icon-user"> ' + obj['originalAuthor'] + '</i>' +
                '</div>' +
                '<div class="categories">' +
                '<i class="am-icon-folder"><a class="linkColor" href="/jump/categories?category=' + obj['articleCategories'] + '"> ' + obj['articleCategories'] + '</a></i>' +
                '</div>' +
                '</div>' +
                '</header>' +
                '<div class="article-entry">' +
                obj['articleAbstract'] +
                '</div>' +
                '<div class="read-all">' +
                '<a href="' + obj['thisArticleUrl'] + '" target="_blank">阅读全文 <i class="am-icon-angle-double-right"></i></a>' +
                '</div>' +
                '<div class="article-tags">' +

                '</div>' +
                '</div>');
            articles.append(center);
            var articleTags = $('.article-tags');
            for(var i=0;i<obj['articleTags'].length;i++){
                var articleTag = $('<i class="am-icon-tag"><a class="tag" href="/jump/tags?tag=' + obj['articleTags'][i] + '"> ' + obj['articleTags'][i] + '</a></i>');
                articleTags.eq(index).append(articleTag);
            }
            // var likes = $('<span class="likes"><i class="am-icon-heart"> ' + obj['articleLikes'] + '个喜欢</i></span>');
            // articleTags.eq(index).append(likes);
        }
    })
    $('#deleteArticle').click(function () {
        var $this = $(this);
        deleteArticleId = $this.parent().attr("id").substring(1);
        $('#deleteAlter').modal('open');
    })
    $('#editArticle').click(function () {
        var $this = $(this);
        var id = $this.parent().attr("id").substring(1);
        window.location.replace("/jump/edit?id=" + id);
    });
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
                dangerNotice("删除文章失败")
            } else {
                successNotice("删除文章成功");
                ajaxFirst(1);
            }
        },
        error:function () {
            alert("删除失败");
        }
    });
})
