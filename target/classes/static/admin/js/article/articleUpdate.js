var vditor;


layui.use(['form','layer','layedit','laydate','upload'],function(){
        var form = layui.form
        layer = layui.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;
    //上传缩略图
    upload.render({
        elem: '.thumbBox',
        url: '/admin/upload',
        method : "post",
        done: function(res, index, upload){
            // console.log(res);
            $('#firstPicture').attr('src',res.data);
            $('.thumbBox').css("background","#fff");
        }
    });

    //表单验证
    form.verify({
        articleName:function (value, item) {
            var titleV=$(".title").val();
            if (titleV == null || titleV=='') {
                return '标题不能为空';
            }
        },


        tagCheckbox: function (value, item) {
            var checkbox_name = $(item).attr('name');
            var val = $('input:checkbox[name="' + checkbox_name + '"]:checked').val();
            if (val == null) {
                return '标签必选其一';
            }
        },

        typeRadio: function (value, item) {
            var radio_name = $(item).attr('name');
            var val = $('input:radio[name="' + radio_name + '"]:checked').val();
            if (val == null) {
                return '分类必选其一';
            }
        },
        imgVerify : function (value,item) {
            var imgSrc=$(item).attr('src');
            if (imgSrc == null) {
                return '封面不能为空';
            }
        }
    });

    form.on("submit(updateArticle)",function(data){

        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //获取checkbox[name='tag']的值
        var tagArr = new Array();
        $("input:checkbox[name='tag']:checked").each(function(i){
            tagArr[i] = $(this).val();
        });
        var typeIdVal=$("input:radio[name='type']:checked").val();
        var commentableVal=$("input:radio[name='commentable']:checked").val();

        // 实际使用时的提交信息
        vditor.getHTML().then(function (result) {
            // 实际使用时的提交信息
            $.post("/admin/articles",{
                _method :'PUT',
                id :$("#id").val(),
                title : $(".title").val(),  //文章标题
                //截取文章内容中的一部分文字放入文章摘要
                introduction : $(".introduction").val() =='' ? vditor.getValue().substring(0,50) : $(".introduction").val(),  //文章摘要
                content : result,  //文章内容
                contentMd : vditor.getValue(),  //编辑器内容
                firstPicture : $("#firstPicture").attr("src"),  //缩略图
                putTop : data.field.putTop == 'on' ? 1 : 0,    //置顶
                status : $('.status select').val(),    //发布状态
                commentable : commentableVal,    //开启评论
                typeId : typeIdVal,                //分类
                tagArr : tagArr                     //标签
            },function(res){
                if(res.code==200){
                    setTimeout(function(){
                        vditor.clearCache();
                        top.layer.close(index);
                        top.layer.msg("文章修改成功！");
                        parent.layer.closeAll("iframe");
                        //刷新父页面
                        parent.layui.table.reload('articleListTable');
                    },500);
                }else{
                    setTimeout(function(){
                        layer.alert(res.msg);
                        top.layer.close(index);
                        //刷新父页面
                        parent.layui.table.reload('articleListTable');
                    },500);
                }

            })

        })

        return false;
    })


})
function initEditor(contentMd){
    vditor = new Vditor('vditor', {
        // mode: "wysiwyg-show",
        typewriterMode: true,
        cache:false,
        counter: 100,
        height: 300,
        hint: {
            emojiPath: 'https://cdn.jsdelivr.net/npm/vditor@1.8.3/dist/images/emoji',
            emojiTail: '<a href="https://hacpai.com/settings/function" target="_blank">设置常用表情</a>',
            emoji: {
                'sd': '💔',
                'j': 'https://unpkg.com/vditor@1.3.1/dist/images/emoji/j.png',
            },
        },
        tab: '\t',
        upload: {
            accept: 'image/*,.wav',
            token: 'test',
            url: '/api/upload/editor',
            linkToImgUrl: '/api/upload/fetch',
            filename (name) {
                return name.replace(/[^(a-zA-Z0-9\u4e00-\u9fa5\.)]/g, '').
                replace(/[\?\\/:|<>\*\[\]\(\)\$%\{\}@~]/g, '').
                replace('/\\s/g', '')
            },
        },
    })
    vditor.clearCache();
    vditor.setValue(contentMd);
}
