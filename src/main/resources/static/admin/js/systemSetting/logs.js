layui.use(['table','util'],function(){
	var table = layui.table,
    util = layui.util
	//系统日志
    table.render({
        elem: '#logs',
        url : '/admin/logs',
        cellMinWidth : 95,
        page : true,
        height : "full-20",
        limit : 20,
        limits : [10,15,20,25],
        id : "systemLog",
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
            {field: 'userName', title: '操作人', width:150},
            {field: 'operation', title: '操作方式', align:'center'},
            {field: 'method', title: '方法名', align:'center'},
            {field: 'params', title: '参数', align:'center'},
            {field: 'ip', title: '操作IP',  align:'center',minWidth:130},
            {field: 'createTime', title: '操作时间', align:'center',templet:function(d){
                    return util.toDateString(d.createTime, 'yyyy年MM月dd日 HH:mm:ss')
            }},
        ]]
    });
 	
})
