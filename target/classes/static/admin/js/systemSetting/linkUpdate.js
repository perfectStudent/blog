layui.use(['form','layer','table','upload','util'],function(){
    var form = layui.form,
        layer =layui.layer,
        $ = layui.jquery,
        util = layui.util,
        upload = layui.upload,
        table = layui.table;


    //上传logo
    upload.render({
        elem: '.linkLogo',
        url: '/admin/upload',
        method : "post",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
            $('.linkLogoImg').attr('src', res.data);
            $('.linkLogo').css("background","#fff");
        }
    });

    form.on("submit(updateLink)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        $.post("/admin/links",{
            _method :'PUT',
            id : $(".id").val(),
            linkLogo : $(".linkLogoImg").attr('src'),  //网站logo
            websiteName : $(".linkName").val(),  //网站名称
            urlAddress : $(".linkUrl").val()    //网址
        },function(res){
            if (res.code==200){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg("链接修改成功！");
                    parent.layer.closeAll("iframe");
                    //刷新父页面
                    parent.layui.table.reload("linkListTab");
                },500);
            }else{
                setTimeout(function(){
                    layer.alert(res.msg);
                    top.layer.close(index);
                },500);
            }
        })
        return false;
    })

})