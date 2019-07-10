//发表评论
$('#commentBtn').click(function () {
    var commentContent = $('#comment').val();
    if(commentContent == ""){
        alert("评论为空！");
    } else {
        $.ajax({
            type: 'POST',
            url: '/comment/publishComment',
            dataType: 'json',
            data: {
                commentContent:commentContent,
                originalAuthor:originalAuthor,
                articleId:articleId
            },
            success: function (data) {
                if(data[data.length-1]['status'] == 403){
                    $.get("/jump/toLogin",function(data,status,xhr){
                        window.location.replace("/jump/login");
                    });
                }else{
                    putInComment(data);
                }
            },
            error: function () {
                alert("获得文章信息失败！");
            }
        });
    }

});