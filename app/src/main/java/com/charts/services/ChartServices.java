package com.charts.services;

public class ChartServices
{
	public static String plot(double gain, double timeConstant, double transportDelay,
					   double kp, double ki, double kd)
	{
		String plot = "<head>\n" +
				"<script src=https://cdn.jsdelivr.net/npm/echarts/dist/echarts.min.js></script>\n" +
				"</head>\n" +
				"<body>\n" +
				"<div id=plot style=width:400px;height:350px></div>\n" +
				"<script>const chart=echarts.init(document.getElementById(\"plot\"));chart.showLoading();const K=.5,tau=5,L=2,Kp=12,Ki=2,Kd=.5,dt=.1,tMax=50,steps=Math.floor(500);let t=[],y=[],yPID=[],u=[],e=[],integral=0,derivative=0,prevError=0;for(let o=0;o<=steps;o++)t.push(o*dt),y.push(0),yPID.push(0),u.push(0),e.push(0);for(let e=1;e<=steps;e++)y[e]=e*dt>=2?y[e-1]+.02*(-y[e-1]+K):y[e-1];for(let t=1;t<=steps;t++)t*dt>=2&&(e[t]=1-yPID[t-1],integral+=e[t]*dt,derivative=(e[t]-prevError)/dt,u[t]=12*e[t]+2*integral+Kd*derivative,prevError=e[t]),yPID[t]=t*dt>=2?yPID[t-1]+.02*(-yPID[t-1]+K*u[t-Math.floor(20)]):yPID[t-1];const data1=t.map(((e,t)=>[e,y[t]])),data2=t.map(((e,t)=>[e,yPID[t]])),option={grid:{left:\"50\"},xAxis:{type:\"value\",name:\"Time (s)\",position:\"bottom\",nameLocation:\"middle\",offset:0,nameTextStyle:{color:\"rgba(255, 15, 15, 1)\"},nameGap:30,axisLine:{show:!0,lineStyle:{color:\"#403a40\"}},splitLine:{show:!0,lineStyle:{color:\"#403a40\"}}},yAxis:{type:\"value\",name:\"Response\",axisLine:{show:!0,lineStyle:{color:\"#403a40\"}},splitLine:{show:!0,lineStyle:{color:\"#403a40\"}}},series:[{name:\"First-Order System\",type:\"line\",symbol:\"none\",data:data1},{name:\"PID-Controlled System\",type:\"line\",symbol:\"none\",data:data2}],legend:{orient:\"horizontal\",top:\"bottom\"},backgroundColor:\"#121012\"};chart.setOption(option),chart.hideLoading()</script>\n" +
				"</body>";
		return plot;
	}
}
