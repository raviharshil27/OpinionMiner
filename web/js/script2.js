
var buildString = "iphone camera";



function getReviews(q,callback)
{

    $.ajax({
        type: 'GET',
        url: 'tweet',
        contentType: 'application/xml',
        xhrFields: {withCredentials: true},
        dataType: 'json',
        data: 'q'+q,
        success:
            function TweetTick(reviews)
       {
           var container=$('#tweet-container');
           container.html('');
           $(reviews.result).each(function(el){
                               
                               if(this.score>0.5)
                               {
                                    var str = '<div class="tweet alert alert-success"><div class="avatar"><div class="avatar"><a href="http://twitter.com/'+this.from_user+'" target="_blank"><img src="'+this.profile_image_url+'" alt="'+this.from_user+'" /></a></div></div><div ><div class="user"><a href="http://twitter.com/'+this.from_user+'" target="_blank">'+this.from_user+'</a></div><div class="time">'+relativeTime(this.created_at)+'</div>';
                                    str+='<div class="text">'+this.text+'</div></div></div>';
                               }
                               /*else if(this.score>-0.5&& this.score<0.5)
                               {
                                        var str = '<div class="tweet alert alert-info"><div class="avatar"><div class="avatar"><a href="http://twitter.com/'+this.from_user+'" target="_blank"><img src="'+this.profile_image_url+'" alt="'+this.from_user+'" /></a></div></div><div ><div class="user"><a href="http://twitter.com/'+this.from_user+'" target="_blank">'+this.from_user+'</a></div><div class="time">'+relativeTime(this.created_at)+'</div>';
                                    str+='<div class=" ">'+formatTwitString(this.text)+'</div></div></div>';
                               }*/
                                else if(this.score <-0.5)
                                {
                                    var str = '<div class="tweet alert-error alert"><div class="avatar"><div class="avatar"><a href="http://twitter.com/'+this.from_user+'" target="_blank"><img src="'+this.profile_image_url+'" alt="'+this.from_user+'" /></a></div></div><div ><div class="user"><a href="http://twitter.com/'+this.from_user+'" target="_blank">'+this.from_user+'</a></div><div class="time">'+relativeTime(this.created_at)+'</div>';
                                    str+='<div class="text" >'+this.text+'</div></div></div>';
                                }

            container.append(str);
            }
           );
           container.jScrollPane();
       }
    })
}


function formatTwitString(str)
{
	str=' '+str;
	str = str.replace(/((ftp|https?):\/\/([-\w\.]+)+(:\d+)?(\/([\w/_\.]*(\?\S+)?)?)?)/gm,'<a href="$1" target="_blank">$1</a>');
	str = str.replace(/([^\w])\@([\w\-]+)/gm,'$1@<a href="http://twitter.com/$2" target="_blank">$2</a>');
	str = str.replace(/([^\w])\#([\w\-]+)/gm,'$1<a href="http://twitter.com/search?q=%23$2" target="_blank">#$2</a>');
	return str;
}

function relativeTime(pastTime)
{	
	var origStamp = Date.parse(pastTime);
	var curDate = new Date();
	var currentStamp = curDate.getTime();
	
	var difference = parseInt((currentStamp - origStamp)/1000);

	if(difference < 0) return false;

	if(difference <= 5)				return "Just now";
	if(difference <= 20)			return "Seconds ago";
	if(difference <= 60)			return "A minute ago";
	if(difference < 3600)			return parseInt(difference/60)+" minutes ago";
	if(difference <= 1.5*3600) 		return "One hour ago";
	if(difference < 23.5*3600)		return Math.round(difference/3600)+" hours ago";
	if(difference < 1.5*24*3600)	return "One day ago";
	
	var dateArr = pastTime.split(' ');
	return dateArr[4].replace(/\:\d+$/,'')+' '+dateArr[2]+' '+dateArr[1]+(dateArr[3]!=curDate.getFullYear()?' '+dateArr[3]:'');
}
function setTwtTracker(target)
{
   /* $.ajax({
        type: 'GET',
        url: 'tweet',
        contentType: 'application/xml',
        xhrFields: {withCredentials: true},
        dataType: 'json',
        data: 'q'+q,
        success:function(){*/
  var testdata = [
    {
      key: "positive",
      y: 100,
      color:"green"
    },
    {
      key: "netural",
      y: 300,
      color:"gray"
    },
    {
      key: "negative",
      y: 200,
      color:"pink"
    }
  ];


nv.addGraph(function() {
    var width = 290,
        height = 330;

    var chart = nv.models.pieChart()
        .x(function(d) {return d.key})
        .y(function(d) {return d.y})
        .showLabels(false)
        .values(function(d) {return d})
        .width(width)
        .height(height);

      d3.select("#twttracker")
          .datum([testdata])
        .transition().duration(1200)
          .attr('width', width)
          .attr('height', height)
          .call(chart);
     d3.selectAll(".nv-slice").attr("fill",function(d,i){
         switch(i)
         {
             case 0:
             return "green"
             case 1:
             return "gray"
             case 2:
             return"pink"
         }
     })
    chart.dispatch.on('stateChange', function(e) {nv.log('New State:', JSON.stringify(e));});

    return chart;
});
}
   // })
//}
function setFeatureTracker(target)
{
data=[
  {
    "key": "negative",
    "color": "#d62728",
    "values": [
      {
        "label" : "camera" ,
        "value" : -10
      } ,
      {
        "label" : "screen resolution" ,
        "value" : -80
      } ,
      {
        "label" : "battery" ,
        "value" : -3
      } ,
      {
        "label" : "processor" ,
        "value" : -40
      } ,
      {
        "label" : "os" ,
        "value" : -4
      } 
    ]
  },
  {
    "key": "positive",
    "color": "green",
    "values": [
      {
        "label" : "camera" ,
        "value" : 250
      } ,
      {
        "label" : "screen resolution" ,
        "value" : 160
      } ,
      {
        "label" : "battery" ,
        "value" : 18
      } ,
      {
        "label" : "processor" ,
        "value" : 800
      } ,
      {
        "label" : "os" ,
        "value" : 70
      } 
    ]
  }
];
nv.addGraph(function() {
  var chart = nv.models.multiBarHorizontalChart()
      .x(function(d) { return d.label })
      .y(function(d) { return d.value })
      .margin({top: 30, right: 20, bottom: 50, left: 175})
      .showValues(true)
      .tooltips(false)
      .showControls(false);

  chart.yAxis
      .tickFormat(d3.format(',.2f'));

  d3.select('#featuretracker svg')
      .datum(data)
    .transition().duration(500)
      .call(chart);

  nv.utils.windowResize(chart.update);

  return chart;
});

}