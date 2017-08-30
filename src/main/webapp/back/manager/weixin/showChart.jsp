<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/echarts.js"></script>
<script src="http://gallerybox.echartsjs.com/dep/echarts/map/js/china.js"></script>
<script type="text/javascript">
	
	function show(){
		$("#LoadingBar").css("display","block");
		$.ajax({
			type : "POST",
			url : "back/createChart.action",
			dataType:"JSON",
			success:function(data){
				var list=[];//大的集合
				var mapstr=new Object();
				var obj = eval(data);
				for(var i=0;i<data.rows.length;i++){
					var datastr={};
					datastr.name=obj.rows[i].city;
					datastr.value=obj.rows[i].value;
					list.push(datastr);
				};
				var data = list;//人数统计
				var geoCoordMap =obj.json;//地图数据
				
				var convertData = function (data) {
				    var res = [];
				    for (var i = 0; i < data.length; i++) {
				        var geoCoord = geoCoordMap[data[i].name];
				        if (geoCoord) {
				            res.push({
				                name: data[i].name,
				                value: geoCoord.concat(data[i].value)
				            });
				        }
				    }
				    return res;
				};
				
				option = {
				    backgroundColor: '#404a59',
				    title: {
				        text: '微信粉丝分布图',
				        subtext: '（仅显示全国用户）',
				        left: 'center',
				        textStyle: {
				            color: '#fff'
				        }
				    },
				    tooltip : {
				        trigger: 'item'
				    },
				    legend: {
				        orient: 'vertical',
				        y: 'bottom',
				        x:'right',
				        data:['人数'],
				        textStyle: {
				            color: '#fff'
				        }
				    },
				    geo: {
				        map: 'china',
				        label: {
				            emphasis: {
				                show: false
				            }
				        },
				        roam: true,
				        itemStyle: {
				            normal: {
				                areaColor: '#323c48',
				                borderColor: '#111'
				            },
				            emphasis: {
				                areaColor: '#2a333d'
				            }
				        }
				    },
				    series : [
				        {
				            name: '人数',
				            type: 'scatter',
				            coordinateSystem: 'geo',
				            data: convertData(data),
				            symbolSize: function (val) {
				                return val[2] / 10;
				            },
				            label: {
				                normal: {
				                    formatter: '{b}',
				                    position: 'right',
				                    show: false
				                },
				                emphasis: {
				                    show: true
				                }
				            },
				            itemStyle: {
				                normal: {
				                    color: '#ddb926'
				                }
				            }
				        },
				        {
				            name: 'Top 5',
				            type: 'effectScatter',
				            coordinateSystem: 'geo',
				            data: convertData(data.sort(function (a, b) {
				                return b.value - a.value;
				            }).slice(0, 6)),
				            symbolSize: function (val) {
				                return val[2] / 10;
				            },
				            showEffectOn: 'render',
				            rippleEffect: {
				                brushType: 'stroke'
				            },
				            hoverAnimation: true,
				            label: {
				                normal: {
				                    formatter: '{b}',
				                    position: 'right',
				                    show: true
				                }
				            },
				            itemStyle: {
				                normal: {
				                    color: '#f4e925',
				                    shadowBlur: 10,
				                    shadowColor: '#333'
				                }
				            },
				            zlevel: 1
				        }
				    ]
				};
				$("#LoadingBar").css("display","none");
				var myChart = echarts.init(document.getElementById('main'));
				myChart.setOption(option);
			}
		});
	};
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onLoad="show()">
	
	<!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 990px;height:455px;" >
    	<div id="LoadingBar" style="text-align:center;" >
			<img style="margin:auto;width:450px;height:450px;" src='images/loading.gif'/>
		</div>
    </div>
</body>
</html>