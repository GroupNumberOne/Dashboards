var dualbarmargin = {top: 80, right: 60, bottom: 20, left: 60},
    dualbarwidth = 320 - dualbarmargin.left - dualbarmargin.right,
    dualbarheight = 300 - dualbarmargin.top - dualbarmargin.bottom;

var dualbarx = d3.scale.ordinal()
    .rangeRoundBands([0, dualbarwidth], .1);

var dualbary0 = d3.scale.linear().domain([0, 1000]).range([dualbarheight, 0]),
	dualbary1 = d3.scale.linear().domain([0, 1000]).range([dualbarheight, 0]);

var dualbarxAxis = d3.svg.axis()
    .scale(dualbarx)
    .orient("bottom");

// create left yAxis
var dualbaryAxisLeft = d3.svg.axis().scale(dualbary0).ticks(4).orient("left");
// create right yAxis
var dualbaryAxisRight = d3.svg.axis().scale(dualbary1).ticks(6).orient("right");

var dualbartip = d3.tip()
  .attr('class', 'd3-tip')
  .offset([-10, 0])
  
  .html(function(d) {
    return "<strong>Aantal:</strong> <span style='color:red'>" + d.cvopleiding + "</span>";
  })
  
var dualbartip2 = d3.tip()
  .attr('class', 'd3-tip')
  .offset([-10, 0])
  
  .html(function(d) {
    return "<strong>Aantal:</strong> <span style='color:red'>" + d.vacniveau + "</span>";
  })

var dualbarsvg = d3.select("body").select("#bargraphs").append("svg")
    .attr("width", dualbarwidth + dualbarmargin.left + dualbarmargin.right)
    .attr("height", dualbarheight + dualbarmargin.top + dualbarmargin.bottom)
	.attr("id", "dualbar")
  .append("g")
    .attr("class", "graph")
    .attr("transform", "translate(" + dualbarmargin.left + "," + dualbarmargin.top + ")");
	
dualbarsvg.call(dualbartip);
dualbarsvg.call(dualbartip2);

d3.tsv("data/DualBarGraphData.tsv", dualbartype, function(error, data) {
  dualbarx.domain(data.map(function(d) { return d.opleiding; }));
  dualbary0.domain([0, d3.max(data, function(d) { return d.cvopleiding; })]);
  
  dualbarsvg.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + dualbarheight + ")")
      .call(dualbarxAxis);

  dualbarsvg.append("g")
         .attr("class", "y axis axisLeft")
         .attr("transform", "translate(0,0)")
         .call(dualbaryAxisLeft)
        .append("text")
         .attr("y", 6)
         .attr("dy", "-2em")
         .style("text-anchor", "end")
         .style("text-anchor", "end")
         .text("Aanbod");
        
dualbarsvg.append("g")
        .attr("class", "y axis axisRight")
         .attr("transform", "translate(" + (dualbarwidth) + ",0)")
         .call(dualbaryAxisRight)
        .append("text")
         .attr("y", 6)
         .attr("dy", "-2em")
         .attr("dx", "2em")
         .style("text-anchor", "end")
         .text("Vraag");

  dualbarbars = dualbarsvg.selectAll(".bar").data(data).enter();

  dualbarbars.append("rect")
      .attr("class", "bar1")
      .attr("x", function(d) { return dualbarx(d.opleiding); })
      .attr("width", dualbarx.rangeBand()/2)
      .attr("y", function(d) { return dualbary0(d.cvopleiding); })
      .attr("height", function(d,i,j) { return dualbarheight - dualbary0(d.cvopleiding); })
	  .on('mouseover', dualbartip.show)
      .on('mouseout', dualbartip.hide);

  dualbarbars.append("rect")
      .attr("class", "bar2")
      .attr("x", function(d) { return dualbarx(d.opleiding) + dualbarx.rangeBand()/2; })
      .attr("width", dualbarx.rangeBand() / 2)
      .attr("y", function(d) { return dualbary1(d.vacniveau); })
      .attr("height", function(d,i,j) { return dualbarheight - dualbary1(d.vacniveau); })
	  .on('mouseover', dualbartip2.show)
      .on('mouseout', dualbartip2.hide);

});

function dualbartype(d) {
  d.cvopleiding = +d.cvopleiding;
  return d;
}