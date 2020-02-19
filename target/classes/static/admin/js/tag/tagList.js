layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //标签列表
    var tableIns = table.render({
        elem: '#tagList',
        url : '/admin/tags',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "tagListTable",
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
            {field: 'tagName', title: '标签名称', align:'center', minWidth:110},
            {title: '操作', width:170, templet:'#tagListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("tagListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    tagName: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加标签按钮
    $(".addTag_btn").click(function(){
        addTag();
    });
    var addTagIndex;
    function addTag(){
        var index = layer.open({
            title : "添加标签",
            type : 1,                   //页面层
            area: ['auto', 'auto'],   //宽高
            skin: 'layui-layer-rim', //加上边框
            content : $('#addTag'),
            success : function(layero, index){
                addTagIndex=index;
            },
            end:function() {
                $("#addTag")[0].reset();
            }
            });
    }
    //添加标签名称
    $(".addTagBtn").click(function(){
        $.post("/admin/tags",{
            tagName : $(".tagName").val(),  //文章标题
        },function(data){
            console.log(data);
            if(data.code==200){
                tableIns.reload();
                layer.close(addTagIndex);
            }else{
                layer.alert(data.msg);
            }
        })
    })


    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('tagListTable'),
            data = checkStatus.data,
            tagId = [];
        if(data.length > 0) {
            for (var i in data) {
                tagId.push(data[i].id);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/tags/"+tagId,{
                    _method : 'DELETE'
                },function(data){
                    if(data.code==200){
                        tableIns.reload();
                        layer.close(index);
                    }else {
                        layer.alert(data.msg);
                        tableIns.reload();
                        layer.close(index);
                    }

                })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(tagList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            editTag(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                $.post("/admin/tags/"+data.id,{
                    _method : 'DELETE'
                },function(data){
                    if(data.code==200){
                        tableIns.reload();
                        layer.close(index);
                    }else {
                        layer.alert(data.msg);
                        tableIns.reload();
                        layer.close(index);

                    }

                })
            });
        }
    });
    var editTagIndex;
    //编辑标签
    function editTag(rowData){
        var index = layer.open({
            title : "编辑标签",
            type : 1,                   //页面层
            area: ['auto', 'auto'],   //宽高
            skin: 'layui-layer-rim', //加上边框
            content : $('#editTag'),
            success : function(layero, index){
                editTagIndex=index;
                $.get("/admin/tags/"+rowData.id,{

                },function(data){
                    if(data.code==200){
                        $(".id").val(data.data.id);
                        $(".tagNameEdit").val(data.data.tagName);
                    }else{
                        layer.alert(data.msg);
                        layer.close(editTagIndex);
                    }
                })
            },
            end:function() {
                $("#addTag")[0].reset();
            }
        });
    }

    //更新标签名称
    $(".updateTagBtn").click(function(){
        $.post("/admin/tags",{
            id:$(".id").val(),
            tagName : $(".tagNameEdit").val(),  //文章标题
            _method : 'PUT'
        },function(data){
            if(data.code==0){
                tableIns.reload();
                layer.close(editTagIndex);
            }else{
                layer.alert(data.msg);
            }
        })
    })


})