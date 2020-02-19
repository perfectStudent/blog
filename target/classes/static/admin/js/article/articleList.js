layui.use(['form','layer','table','laytpl','util'],function(){
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        util = layui.util,
        table = layui.table;

    //新闻列表
    var tableIns = table.render({
        elem: '#articleList',
        url : '/admin/articles',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "articleListTable",
        response: {
             statusName: 'code' //规定数据状态的字段名称，默认：code
            ,statusCode: 200 //规定成功的状态码，默认：0
            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            ,countName: 'count' //规定数据总数的字段名称，默认：count
            ,dataName: 'data' //规定数据列表的字段名称，默认：data
        },
    cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: 'ID', width:160, align:"center"},
            {field: 'title', title: '文章标题', width:260},
            {field: 'status', title: '发布状态',  align:'center',templet:function(d){
                return d.status ? '已发布' : '已存草稿';
            }},
            {field: 'articleLook', title: '评论开启', align:'center',templet:function(d){
                    return d.commentable ? '是' : '否';
            }},
            {field: 'putTop', title: '是否置顶', align:'center', templet:function(d){
                if(d.putTop){
                    return '<input type="checkbox" name="putTop" lay-filter="putTop" lay-skin="switch" lay-text="是|否"  checked >'
                }
                    return '<input type="checkbox" name="putTop" lay-filter="putTop" lay-skin="switch" lay-text="是|否"   >'
            }},
            {field: 'articleTime', title: '发布时间', align:'center', minWidth:110, templet:function(d){
                    return util.toDateString(d.createTime, 'yyyy年MM月dd日 HH:mm:ss')
            }},
            {title: '操作', width:170, templet:'#articleListBar',fixed:"right",align:"center"}
        ]]
    });



    //是否置顶
    form.on('switch(putTop)', function(data){
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.close(index);
        },500);
        // console.log($(data.othis).parents('tr').find('[data-field="id"]').find('.layui-table-cell').text())
        var id=$(data.othis).parents('tr').find('[data-field="id"]').find('.layui-table-cell').text();
        // console.log(data.elem.checked)
        $.post("/admin/articles/putTop",{
            _method: 'PUT',
            id : id,
            putTop : data.elem.checked ? 1 : 0    //置顶
        },function(res){
            if(res.code==200){
                if (data.elem.checked) {
                    layer.msg("置顶成功！");
                } else {
                    layer.msg("取消置顶成功！");
                }
            }else{
                layer.alert(res.msg);
            }

        })


    })

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("articleListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    title: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加文章
    function addArticle(){
        var index = layui.layer.open({
            title : "添加文章",
            type : 2,
            content : "/admin/articleAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                setTimeout(function(){
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            },end: function () {
                $(window).unbind("resize");
            }
        })
          layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layer.full(index);
        })
    }
    $(".addArticle_btn").click(function(){
        addArticle();
    })



    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('articleListTable'),
            data = checkStatus.data,
            articleId = [];
        if(data.length > 0) {
            for (var i in data) {
                articleId.push(data[i].id);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/articles/"+articleId,{
                    _method : 'DELETE'  //将需要删除的articleId作为参数传入
                },function(data){
                    if(data.code==200){
                        tableIns.reload();
                        layer.close(index);
                    }else {
                        layer.msg(data.msg);
                        layer.close(index);
                    }
                })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //更新文章
    function editArticle (rowData){
        var index = layui.layer.open({
            title : "编辑文章",
            type : 2,
            content : "/admin/articleUpdate.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                // var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
                var iframeWin = layero.find('iframe')[0].contentWindow;
                $.get("/admin/articles/"+rowData.id,{

                },function(obj){
                    if(obj.code==200){
                        body.find("#id").val(obj.data.id);
                        body.find(".title").val(obj.data.title);
                        body.find(".introduction ").val(obj.data.introduction );
                        // body.find("#vditor").text(obj.data.contentMd);
                        body.find("#firstPicture").attr('src',obj.data.firstPicture);
                        body.find(".status option").each(function(){
                            if($(this).val()==obj.data.status ? 1 : 0){
                                $(this).attr("selected",true);
                                return;
                            }
                        });
                        obj.data.putTop ? body.find("input:checkbox[name='putTop']").attr("checked",true) : '';
                        body.find("input:radio[name='commentable']").each(function(){
                            if($(this).val()==obj.data.commentable ? 1 : 0){
                                $(this).attr("checked",true);
                                return;
                            }
                        });
                        body.find("input:radio[name='type']").each(function(){
                            if($(this).val()==obj.data.typeId){
                                $(this).attr("checked",true);
                                return;
                            }
                        });
                        body.find("input:checkbox[name='tag']").each(function(){
                            for(var i=0;i<obj.data.tagList.length;i++){
                                if($(this).val()==obj.data.tagList[i]['id']){
                                    $(this).attr("checked",true);
                                }
                            }
                        });
                        setTimeout(function(){
                            iframeWin.initEditor(obj.data.contentMd);
                            iframeWin.layui.form.render();
                        },500);
                        //渲染表单
                    }else{
                        layer.alert(obj.msg);
                    }

                })
                setTimeout(function(){
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            },end: function () {
                $(window).unbind("resize");
            }
        })
            layer.full(index);
            //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
            $(window).on("resize",function(){
                layer.full(index);
            })
    }


    //列表操作
    table.on('tool(articleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            editArticle(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                $.post("/admin/articles/"+data.id,{
                    _method : 'DELETE'
                },function(data){
                    if(data.code==200){
                        tableIns.reload();
                        layer.close(index);
                    }else {
                        layer.msg(data.msg);
                        layer.close(index);
                    }
                })
            });
        }
    });

})