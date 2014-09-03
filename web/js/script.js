
var buildString = "iphone camera";
var pos=1,neg=1,net=0;
$(document).ready(function()
    {
        $(".tablesorter").tablesorter();
        getReviews("iphone camera");
//        setTwtTracker("iphone camera");
//        setFeatureTracker("iphone camera");
    }
);
$(document).ready(function() {

    //When page loads...
    $(".tab_content").hide(); //Hide all content
    $("ul.tabs li:first").addClass("active").show(); //Activate first tab
    $(".tab_content:first").show(); //Show first tab content

    //On Click Event
    $("ul.tabs li").click(function() {

        $("ul.tabs li").removeClass("active"); //Remove any "active" class
        $(this).addClass("active"); //Add "active" class to selected tab
        $(".tab_content").hide(); //Hide all tab content

        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
        $(activeTab).fadeIn(); //Fade in the active ID content
        return false;
    });

});
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
           var container=$('#message_list');
           container.html('');
           $(reviews.result).each(function(el){
                               
                               if(this.score>0.25)
                               {
                                   pos++;
                                    var str = '<div class="module_content "><div class="message"><p class="pos alert alert-success">'+this.text+'</p><a href="http://twitter.com/'+this.from_user+'" target="_blank"><p><strong>'+this.from_user+'</strong></p></a></div></div>';
                               }
                                else if(this.score <-0.25)
                                {
                                    neg++;
                                    var str = '<div class="module_content"><div class="message"><p class="pos alert alert-error">'+this.text+'</p><a href="http://twitter.com/'+this.from_user+'" target="_blank"><p><strong>'+this.from_user+'</strong></p></a></div></div>';
                                }
                               else
                               {
                                  net++;
                               }
            container.append(str);
            }

           );
     //         //   alert("get review" + pos);
                  setTwtTracker("iphone camera");
        setFeatureTracker("iphone camera");
                
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
  var colors=["rgb(56, 148, 56)","rgb(180, 174, 174)","rgb(204, 31, 18)"];
//alert("gettwicket"+ pos);
  d3.scale.mycolors=function()
  {
      return d3.scale.ordinal().range(colors);
  }
  var testdata = [
    {
      key: "positive",
      y: pos
    },
    {
      key: "netural",
      y: net
    },
    {
      key: "negative",
      y: neg
    }
  ];


nv.addGraph(function() {
    var width = 300,
        height =340;

    var chart = nv.models.pieChart()
        .x(function(d) {return d.key})
        .y(function(d) {return d.y})
        .showLabels(false)
        .values(function(d) {return d})
        .width(width)
        .height(height)
        .color(d3.scale.mycolors().range());

      d3.select("#twttracker")
          .datum([testdata])
        .transition().duration(1200)
          .attr('width', width)
          .attr('height', height)
          .call(chart);

    chart.dispatch.on('stateChange', function(e) {nv.log('New State:', JSON.stringify(e));});

    return chart;
});
}
   // })
//}
function setFeatureTracker(target)
{

             //  alert("pos"+pos);
data=[
  {
    "key": "negative",
    "color": "rgb(204, 31, 18)",
    "values": [
      {
        "label" : "camera" ,
        "value" : -neg
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
        "value" : pos
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
      .x(function(d) {return d.label})
      .y(function(d) {return d.value})
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