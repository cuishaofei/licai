/**
 * Created by cuishaofei on 2019/3/23.
 */
$(function (){
    //初始化表格数据
    initTableData();
    //初始化理财收益汇总表数据
    initAllListTotalTableData();
    //初理财资产分布表
    initAllListProportion();
    //理财投资策略对比表
    initAllListStrategy();
    //每年的累计收益
    initEveryYearProfit();
    //初始化各理财产品当年收益对比的图表
    initContrastByCharts("/total/getYearProfitContrast","当年收益对比","yearProfitContrast");
    //初始化各理财产品累计收益对比的图表
    initContrastByCharts("/total/getTotalProfitContrast","累计收益对比","totalProfitContrast");
});

//初始化表格数据
function initTableData(){
    $('#allList').datagrid({
        method : "get",
        url : "/project/getProjectList",
        rowStyler : function(index,row){
            if (row.currentMoney == 0){
                return 'background-color:#6293BB;';
            }
        }
    });
}

//初始化理财收益汇总表数据
function  initAllListTotalTableData() {
    $('#allListTotal').datagrid({
        method : "get",
        url : "/total/getTotal"
    });
}

//初理财资产分布表
function  initAllListProportion() {
    $('#allListProportion').datagrid({
        method : "get",
        url : "/total/getProportion"
    });
}


//理财投资策略对比表
function  initAllListStrategy() {
    $('#allListStrategy').datagrid({
        method : "get",
        url : "/total/getStrategy"
    });
}

//每年的累计收益
function  initEveryYearProfit() {
    $('#everyYearProfit').datagrid({
        method : "get",
        url : "/total/getYearProfit"
    });
}

//记一笔样式处理
function rowformater(value, row, index) {
    return "<a href='javascript:void(0);' onclick='showDetail("+row.id+");'>查看</a> | <a href='javascript:void(0);' onclick='addHistory("+row.id+");'>记录</a>";
}

//记一笔弹出详情页
function addHistory(rowID){
    //清空之前的数据
    $("#optionMoney").textbox("setValue","");
    $('#option').combobox('select',1);
    $("#currentMoney").textbox("setValue","");
    $('#optionDate').datebox({
        optionDate:""
    });
    //在隐藏域给ID赋值
    $("#addHistoryRowID").attr("value",rowID);
    $('#addHistory').dialog('open');
    $('#addHistory').window('center');
}

//记一笔处理逻辑
function submitFunction() {
    var id = $("#addHistoryRowID").val();
    var optionMoney = $("#optionMoney").val();
    var option = $("#option").val();
    var optionDate = $("#optionDate").val();
    var currentMoney = $("#currentMoney").val();
    //校验输入的金额是否正确
    if(optionMoney == "" && currentMoney == ""){
        $.messager.alert('提示消息','请输入操作金额或当前金额');
        return;
    }
    if(optionMoney != "" && currentMoney != ""){
        if(!checkNum(optionMoney)){
            $.messager.alert('提示消息','操作金额输入有误,只能输入小数');
            return;
        }
        if(!checkNum(currentMoney)){
            $.messager.alert('提示消息','当前金额输入有误,只能输入小数');
            return;
        }
    }
    if(optionMoney != "" && currentMoney == ""){
        if(!checkNum(optionMoney)){
            $.messager.alert('提示消息','操作金额输入有误,只能输入小数');
            return;
        }
    }
    if(optionMoney == "" && currentMoney != ""){
        if(!checkNum(currentMoney)){
            $.messager.alert('提示消息','当前金额输入有误,只能输入小数');
            return;
        }
    }

    var param = {"id":id,"optionMoney":optionMoney,"option":option,"optionDate":optionDate,"currentMoney":currentMoney};
    $.ajax({
        url: "/history/addHistory",
        type: "POST",
        contentType:'application/json;charset=UTF-8',
        data: JSON.stringify(param),
        dataType: "JSON",
        success : function (data) {
            if(data == true){
                $('#addHistory').dialog('close');
                initTableData();
                $.messager.alert('提示消息','更新成功');
            }else{
                $.messager.alert('提示消息','更新失败');
            }
        },
        error : function (data){
        }
    });
}

//根据ID查看详情页面
function showDetail(rowID){
    //根据rowID获取服务端数据
    var param = {"id":rowID};
    var detailRow = "";
    $.ajax({
        url: "/history/getHistoryByID",
        type: "POST",
        contentType:'application/json;charset=UTF-8',
        data: JSON.stringify(param),
        dataType: "JSON",
        success : function (data) {
            $.each(data,function(idx,obj) {
                //测试数据
                detailRow = detailRow + "<tr><td>"+obj.option+"</td><td>"+obj.optionMoney+"</td><td>"+obj.createTime+"</td><td><a href='javascript:void(0);' onclick='deleteHistory("+obj.id+","+rowID+");'>删除</a></td></tr>";
            });
            //清空旧元素
            $("#showDetailTableBody").empty();
            $("#showDetailTableBody").append(detailRow);
            $('#showDetail').dialog('open');
            $('#showDetail').window('center');
        },
        error : function (data){
        }
    });
}

//根据ID删除详情页的记录
function deleteHistory(historyId,rowID){
    $.messager.confirm('确认框','确定要删除吗？',function(r){
        if (r){
            var historyIdJson = {"historyId":historyId};
            $.ajax({
                url: "/history/deleteHistoryByID",
                type: "POST",
                contentType:'application/json;charset=UTF-8',
                data: JSON.stringify(historyIdJson),
                dataType: "JSON",
                success : function (data) {
                    if(data == true){
                        $.messager.alert('提示消息','删除成功');
                        //刷新数据
                        var detailRow = "";
                        $.ajax({
                            url: "/history/getHistoryByID",
                            type: "POST",
                            contentType:'application/json;charset=UTF-8',
                            data: JSON.stringify({"id":rowID}),
                            dataType: "JSON",
                            success : function (data) {
                                $.each(data,function(idx,obj) {
                                    //测试数据
                                    detailRow = detailRow + "<tr><td>"+obj.option+"</td><td>"+obj.optionMoney+"</td><td>"+obj.createTime+"</td><td><a href='javascript:void(0);' onclick='deleteHistory("+obj.id+","+rowID+");'>删除</a></td></tr>";
                                });
                                //清空旧元素
                                $("#showDetailTableBody").empty();
                                $("#showDetailTableBody").append(detailRow);
                            },
                            error : function (data){
                            }
                        });

                    }else{
                        $.messager.alert('提示消息','删除失败');
                    }
                },
                error : function (data){
                }
            });
        }
    });
}

//初始化各理财产品收益对比的图表
function initContrastByCharts(url,text,elementID) {
    //从后台拿数据
    $.ajax({
        url: url,
        type: "POST",
        contentType:'application/json;charset=UTF-8',
        data: {},
        dataType: "JSON",
        success : function (data) {
            var lable = new Array();
            var lableData = new Array();
            $.each(data,function(idx,obj) {
                lable[idx] = obj.name;
                lableData[idx] = obj.value;
            });
            lable = lable.reverse();
            lableData = lableData.reverse();
            var maxValue = lableData[lableData.length - 1];
            maxValue = maxValue;
            var minValue = lableData[0];
            var myChart = echarts.init(document.getElementById(elementID));
            var app = {};
            option = null;
            option = {
                title: {
                    text: '当年收益对比',
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    min:minValue,
                    max:maxValue
                },
                yAxis: {
                    type: 'category',
                    data: lable
                },
                series: [
                    {
                        name: '投资项目',
                        type: 'bar',
                        data: lableData
                    }
                ]
            };
            myChart.setOption(option, true);
        }
    });
}

//年月日时分秒
function clsFormatter(dateV){
    var dateVal=new Date(Date.parse(dateV.toString()));
    var y = dateVal.getFullYear();
    var m = dateVal.getMonth()+1;
    var d = dateVal.getDate();
    var h=dateVal.getHours();
    var m2=dateVal.getMinutes();
    //var s=date.getSeconds();
    var resultVal=y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(m2<10?('0'+m2):m2+':00');
    return resultVal;
}

function clsParser(s){
    if (!s) return new Date();
    var s1=s.split(' ');
    var sa=s1[0];
    var sb=s1[1];
    var ss = (sa.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    var tt = (sb.split(':'));
    var h = parseInt(tt[0]);
    var mi = parseInt(tt[1]);

    if (!isNaN(y) && !isNaN(m) && !isNaN(d)&& !isNaN(h)&& !isNaN(mi)){
        return new Date(y,m-1,d,h,mi);
    } else {
        return new Date();
    }
}

//只能输入小数正则校验
function checkNum(data) {
    var reg = new RegExp("^[0-9]+([.]{1}[0-9]+){0,1}$");
    if (data != "") {
        if (!reg.test(data)) {
            return false;
        }
        return true;
    }
}