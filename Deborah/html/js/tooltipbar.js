var tooltipmargin = {top: 20, right: 20, bottom: 20, left: 30},
    tooltipwidth = 150 - tooltipmargin.left - tooltipmargin.right,
    tooltipheight = 250 - tooltipmargin.top - tooltipmargin.bottom;

var tooltipformatPercent = d3.format(".0%");

var tooltipx = d3.scale.ordinal()
    .rangeRoundBands([0, tooltipwidth], .1);

var tooltipy = d3.scale.linear()
    .range([tooltipheight, 0]);

var tooltipxAxis = d3.svg.axis()
    .scale(tooltipx)
    .orient("bottom");

var tooltipyAxis = d3.svg.axis()
    .scale(tooltipy)
    .orient("left");

var tooltiptip = d3.tip()
  .attr('class', 'd3-tip')
  .offset([-10, 0])
  .html(function(d) {
    return "<strong>Aantal:</strong> <span style='color:red'>" + d.aantal + "</span>";
  })

var tooltipsvg = d3.select("body").select("#bargraphs").append("svg")
	.attr("id", "tooltip")
    .attr("width", tooltipwidth + tooltipmargin.left + tooltipmargin.right)
    .attr("height", tooltipheight + tooltipmargin.top + tooltipmargin.bottom)
  .append("g")
    .attr("transform", "translate(" + tooltipmargin.left + "," + tooltipmargin.top + ")");

tooltipsvg.call(tooltiptip);

d3.tsv("data/ToolTipBarGraphData.tsv", tooltiptype, function(error, data) {
  tooltipx.domain(data.map(function(d) { return d.soort; }));
  tooltipy.domain([0, d3.max(data, function(d) { return d.aantal; })]);

  tooltipsvg.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + tooltipheight + ")")
      .call(tooltipxAxis);

  tooltipsvg.append("g")
      .attr("class", "y axis")
      .call(tooltipyAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Aantal");
		
  tooltipsvg.selectAll(".bar")
      .data(data)
    .enter().append("rect")
      .attr("class", "bar")
      .attr("x", function(d) { return tooltipx(d.soort); })
      .attr("width", tooltipx.rangeBand())
	  .attr("fill", "orange")
      .attr("y", function(d) { return tooltipy(d.aantal); })
      .attr("height", function(d) { return tooltipheight - tooltipy(d.aantal); })
      .on('mouseover', tooltiptip.show)
      .on('mouseout', tooltiptip.hide)

});

function tooltiptype(d) {
  d.aantal = +d.aantal;
  return d;
}