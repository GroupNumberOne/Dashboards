var brushmargin = {top: 10, right: 10, bottom: 100, left: 40},
    brushmargin2 = {top: 230, right: 10, bottom: 20, left: 40},
    brushwidth =  550 - brushmargin.left - brushmargin.right,
    brushheight = 300 - brushmargin.top - brushmargin.bottom,
    brushheight2 = 300 - brushmargin2.top - brushmargin2.bottom;

var brushparseDate = d3.time.format("%Y-%m-%d").parse;

var brushx = d3.time.scale().range([0, brushwidth]),
    brushx2 = d3.time.scale().range([0, brushwidth]),
    brushy = d3.scale.linear().range([brushheight, 0]),
    brushy2 = d3.scale.linear().range([brushheight2, 0]);

var brushxAxis = d3.svg.axis().scale(brushx).orient("bottom"),
    brushxAxis2 = d3.svg.axis().scale(brushx2).orient("bottom"),
    brushyAxis = d3.svg.axis().scale(brushy).orient("left");

var brush = d3.svg.brush()
    .x(brushx2)
    .on("brush", brushed);

var brusharea = d3.svg.area()
    .interpolate("monotone")
    .x(function(d) { return brushx(d.date); })
    .y0(brushheight)
    .y1(function(d) { return brushy(d.price); });

var brusharea2 = d3.svg.area()
    .interpolate("monotone")
    .x(function(d) { return brushx2(d.date); })
    .y0(brushheight2)
    .y1(function(d) { return brushy2(d.price); });

var brushsvg = d3.select("body").select("#under").select("#brushbar").append("svg")
    .attr("width", brushwidth + brushmargin.left + brushmargin.right)
    .attr("height", brushheight + brushmargin.top + brushmargin.bottom);

brushsvg.append("defs").append("clipPath")
    .attr("id", "clip")
  .append("rect")
    .attr("width", brushwidth)
    .attr("height", brushheight);

var brushfocus = brushsvg.append("g")
    .attr("transform", "translate(" + brushmargin.left + "," + brushmargin.top + ")");

var brushcontext = brushsvg.append("g")
    .attr("transform", "translate(" + brushmargin2.left + "," + brushmargin2.top + ")");

d3.csv("data/BrushBarData.csv", function(error, data) {

  data.forEach(function(d) {
    d.date = brushparseDate(d.date);
    d.price = +d.price;
  });

  brushx.domain(d3.extent(data.map(function(d) { return d.date; })));
  brushy.domain([0, d3.max(data.map(function(d) { return d.price; }))]);
  brushx2.domain(brushx.domain());
  brushy2.domain(brushy.domain());

  brushfocus.append("path")
      .datum(data)
      .attr("clip-path", "url(#clip)")
      .attr("d", brusharea)
      .attr("fill", "steelblue");

  brushfocus.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + brushheight + ")")
      .call(brushxAxis);

  brushfocus.append("g")
      .attr("class", "y axis")
      .call(brushyAxis);

  brushcontext.append("path")
      .datum(data)
      .attr("d", brusharea2)
	  .attr("fill", "steelblue");

  brushcontext.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + brushheight2 + ")")
      .call(brushxAxis2);

  brushcontext.append("g")
      .attr("class", "x brush")
      .call(brush)
    .selectAll("rect")
      .attr("y", -6)
      .attr("height", brushheight2 + 7);
});

function brushed() {
  brushx.domain(brush.empty() ? brushx2.domain() : brush.extent());
  brushfocus.select("path").attr("d", brusharea);
  brushfocus.select(".x.axis").call(brushxAxis);
}