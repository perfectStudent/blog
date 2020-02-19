var _layedit;
layui.use(['form','layer','layedit','laydate','upload'],function(){
    var form = layui.form,
        _layedit=layui.layedit,
        layer = layui.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    //用于同步编辑器内容到textarea
    layedit.sync(editIndex);
    //上传缩略图
    upload.render({
        elem: '.thumbBox',
        url: '/admin/json/userface.json',
        method : "get",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
            $('#avatar').attr('src', res.data); //图片链接（base64）
            $('.thumbBox').css("background","#fff");
        }
    });


    //
    form.verify({
        newsName : function(val){
            if(val == ''){
                return "文章标题不能为空";
            }
        },
        content : function(val){
            if(val == ''){
                return "文章内容不能为空";
            }
        }
    })
    form.on("submit(updateComment)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        $.post("/admin/comments",{
            _method : 'PUT',
            id : $(".id").val(),
            articleId : $(".articleId").val(),
            name :  $(".name").val(),
            email :  $(".email").val(),
            avatar : $("#avatar").attr("src"),  //缩略图
            content : layedit.getContent(editIndex).split('<audio controls="controls" style="display: none;"></audio>')[0],
        },function(res){
            if(res.code==200){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg("提交成功！");
                    //刷新父页面
                    parent.layui.table.reload('commentListTable');
                    //当你在iframe页面关闭自身时
                    var iframe = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(iframe); //再执行关闭
                },500);
            }else{
                top.layer.close(index);
                top.layer.alert(res.msg);
            }

        })
        return false;
    })

    var editIndex;
    layui._initEdit= function initEdit(){
        //创建一个编辑器
        editIndex = layedit.build('comment_content', {
            height: 535,
            tool: ['face', '|', 'left', 'center', 'right', '|', 'link'],
        });
    }

})


