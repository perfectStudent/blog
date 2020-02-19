layui.use(['form','layer','table','upload'],function(){
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        util = layui.util,
        upload = layui.upload,
        table = layui.table;

    //友链列表
    var tableIns = table.render({
        elem: '#linkList',
        url : '/admin/links',
        page : true,
        cellMinWidth : 95,
        height : "full-104",
        limit : 20,
        limits : [10,15,20,25],
        id : "linkListTab",
        response: {
            statusName: 'code' //规定数据状态的字段名称，默认：code
            ,statusCode: 200 //规定成功的状态码，默认：0
            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            ,countName: 'count' //规定数据总数的字段名称，默认：count
            ,dataName: 'data' //规定数据列表的字段名称，默认：data
        },
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'logo', title: 'LOGO', width:180, align:"center",templet:function(d){
                return '<a href="'+d.urlAddress+'" target="_blank"><img src="'+d.linkLogo+'" height="26" /></a>';
            }},
            {field: 'websiteName', title: '网站名称', minWidth:240},
            {field: 'websiteUrl', title: '网站地址',width:300,templet:function(d){
                    return '<a class="layui-blue" href="'+d.urlAddress+'" target="_blank">'+d.urlAddress+'</a>';
                }},
            {field: 'createTime', title: '添加时间', align:'center',minWidth:110,templet:function(d){
                    return util.toDateString(d.createTime, 'yyyy年MM月dd日 HH:mm:ss')
                }},
            {title: '操作', width:130,fixed:"right",align:"center", templet:function(){
                    return '<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a><a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>';
                }}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("linkListTab",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    websiteName: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加友链
    function addLink(){
        var index = layer.open({
            title : "添加友链",
            type : 2,
            area : ["300px","385px"],
            content : "/admin/linkAdd.html",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回友链列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
    }
    //修改友链
    function editLink(edit){
        var index = layer.open({
            title : "添加友链",
            type : 2,
            area : ["300px","385px"],
            content : "/admin/linkUpdate.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".id").val(edit.id);
                    body.find(".linkLogo").css("background","#fff");
                    body.find(".linkLogoImg").attr("src",edit.linkLogo);
                    body.find(".linkName").val(edit.websiteName);
                    body.find(".linkUrl").val(edit.urlAddress);
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回友链列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
    }
    $(".addLink_btn").click(function(){
        addLink();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('linkListTab'),
            data = checkStatus.data,
            ids = [];
        if(data.length > 0) {
            for (var i in data) {
                ids.push(data[i].id);
            }
            layer.confirm('确定删除选中的友链？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/links/"+ids,{
                    _method: 'DELETE'
                },function(data){
                    if(data.code==200){
                        tableIns.reload();
                        layer.close(index);
                    }else {
                        layer.msg(data.msg)
                        layer.close(index);
                    }
                })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(linkList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            editLink(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此友链？',{icon:3, title:'提示信息'},function(index){
                $.post("/admin/links/"+data.id,{
                    _method: 'DELETE'
                },function(data){
                    if(data.code==200){
                        tableIns.reload();
                        layer.close(index);
                    }else {
                        layer.alert(data.msg)
                        layer.close(index);
                    }
                })
            });
        }
    });

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


    form.on("submit(addLink)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        $.post("/admin/links",{
            linkLogo : $(".linkLogoImg").attr('src'),  //网站logo
            websiteName : $(".linkName").val(),  //网站名称
            urlAddress : $(".linkUrl").val(),    //网址
        },function(data){
            if(data.code==200){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg("链接添加成功！");
                    parent.layer.closeAll("iframe");
                    parent.layui.table.reload('linkListTab');
                },500);
            }else {
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.alert(data.msg);
                    parent.layui.table.reload('linkListTab');
                },500);
            }
        })

        return false;
    })

})