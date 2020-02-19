layui.use(['form','layer','laydate','table','laytpl','util'],function(){
    var form = layui.form,

        layer = layui.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        util = layui.util,
        table = layui.table;

    //新闻列表
    var tableIns = table.render({
        elem: '#commentList',
        url : '/admin/comments',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "commentListTable",
        response: {
            statusName: 'code' //规定数据状态的字段名称，默认：code
            ,statusCode: 200 //规定成功的状态码，默认：0
            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            ,countName: 'count' //规定数据总数的字段名称，默认：count
            ,dataName: 'data' //规定数据列表的字段名称，默认：data
        },
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: 'ID', width:60, align:"center"},
            {field: 'name', title: '昵称'},
            {field: 'email', title: '邮箱', align:'center'},
            {field: 'avatar', title: '头像',  align:'center'},
            {field: 'createTime', title: '创建时间', align:'center',templet : function(d){
                return util.toDateString(d.createTime, 'yyyy年MM月dd日 HH:mm:ss')
                }},
            {field: 'articleId', title: '文章ID', align:'center'},
            {field: 'content', title: '内容', align:'center', minWidth:110},
            {title: '操作', width:150, templet:'#commentListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("commentListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    name: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('commentListTable'),
            data = checkStatus.data,
            commentId = [];
        if(data.length > 0) {
            for (var i in data) {
                commentId.push(data[i].id);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/comments/"+commentId,{
                    _method : 'DELETE'
                },function(data){
                    if(data.code==200){
                        tableIns.reload();
                        layer.msg("删除成功");
                        layer.close(index);
                    }else{
                        layer.alert(data.msg);
                    }

                })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(commentList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            editComment(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                $.post("/admin/comments/"+data.id,{
                    _method : 'DELETE'
                },function(data){
                    if(data.code==200){
                        tableIns.reload();
                        layer.msg("删除成功");
                        layer.close(index);
                    }else{
                        layer.alert(data.msg);
                    }
                })
            });
        }
    });

    function editComment(rowData){
        var index =layer.open({
            title : "编辑评论",
            type : 2,
            content : "/admin/commentUpdate.html",
            success : function(layero, index){
                var body = layer.getChildFrame('body', index);
                // var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法
                var iframeWin = layero.find('iframe')[0].contentWindow;
                $.get("/admin/comments/"+rowData.id,{

                },function(data){
                    if(data.code==200){
                        data=data.data;
                        body.find(".id").val(data.id);
                        body.find(".articleId").val(data.articleId);
                        body.find(".name").val(data.name);
                        body.find("#avatar").attr('src',data.avatar);
                        body.find(".email").val(data.email);
                        body.find("#comment_content").text(data.content);
                        setTimeout(function(){
                            iframeWin.layui._initEdit();
                            iframeWin.layui.form.render();
                        },500)

                    }else{
                        layer.alert(data.msg);
                    }

                })
                setTimeout(function(){
                    layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }

})