layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //分类列表
    var tableIns = table.render({
        elem: '#typeList',
        url : '/admin/types',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "typeListTable",
        response: {
            statusName: 'code' //规定数据状态的字段名称，默认：code
            ,statusCode: 200 //规定成功的状态码，默认：0
            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            ,countName: 'count' //规定数据总数的字段名称，默认：count
            ,dataName: 'data' //规定数据列表的字段名称，默认：data
        },
        parseData: function(res) { //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.count, //解析数据长度
                "data": res.data //解析数据列表
            };
        },
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: 'ID', width:60, align:"center"},
            {field: 'typeName', title: '分类名称', align:'center', minWidth:110},
            {title: '操作', width:170, templet:'#typeListBar',fixed:"right",align:"center"}
        ]]
    });


    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("typeListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    typeName: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加分类按钮
    $(".addType_btn").click(function(){
        addType();
    });
    var addTypeIndex;
    function addType(edit){
        var index = layer.open({
            title : "添加分类",
            type : 1,                   //页面层
            area: ['auto', 'auto'],   //宽高
            skin: 'layui-layer-rim', //加上边框
            content : $('#addType'),
            success : function(layero, index){
                addTypeIndex=index;
            },
            end:function() {
                $("#addType")[0].reset();
            }
            });
        // layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            // layui.layer.full(index);
        })
    }
    //添加分类名称
    $(".addTypeBtn").click(function(){
        $.post("/admin/types",{
            typeName : $(".typeName").val(),  //文章标题
        },function(res){
            if(res.code==0){
                tableIns.reload();
                layer.close(addTypeIndex);
            }else{
                layer.alert(res.msg);
            }
        })
    })


    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('typeListTable'),
            data = checkStatus.data,
            typeId = [];
        if(data.length > 0) {
            for (var i in data) {
                typeId.push(data[i].id);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/types/"+typeId,{
                    // ids : typeId,  //将需要删除的newsId作为参数传入
                    _method : 'DELETE'
                },function(data){
                    tableIns.reload();
                    layer.close(index);
                })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(typeList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            editType(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                $.post("/admin/types/"+data.id,{
                    _method : 'DELETE'
                },function(data){
                    if(data.code==200){
                        tableIns.reload();
                        layer.msg("删除成功");
                        layer.close(index);
                    }else{
                        tableIns.reload();
                        layer.alert(data.msg);
                    }

                })
            });
        }
    });
    var editTypeIndex;
    //编辑分类
    function editType(rowData){
        var index = layer.open({
            title : "添加分类",
            type : 1,                   //页面层
            area: ['auto', 'auto'],   //宽高
            skin: 'layui-layer-rim', //加上边框
            content : $('#editType'),
            success : function(layero, index){
                editTypeIndex=index;
                $.get("/admin/types/"+rowData.id,{

                },function(data){
                    if(data.code==200){
                        $(".id").val(data.data.id);
                        $(".typeNameEdit").val(data.data.typeName);
                    }else{
                        layer.alert(data.msg);
                    }

                })
            },
            end:function() {
                $("#addType")[0].reset();
            }
        });
    }

    //更新分类名称
    $(".updateTypeBtn").click(function(){
        $.post("/admin/types",{
            id:$(".id").val(),
            typeName : $(".typeNameEdit").val(),  //文章标题
            _method : 'PUT'
        },function(res){
            if(res.code==200){
                tableIns.reload();
                layer.close(editTypeIndex);
            }else{
                layer.alert(res.msg);
            }
        })
    })


})