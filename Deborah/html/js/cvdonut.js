var cvlastID = 'id';

var cvhandleMouseover = function (e) {
	var target = e.target || e.srcElement;
	cvlastID = e.target.id;
	if (cvlastID != "") {
		cvtextvalue = cvlastID;
	}
};

if (document.addEventListener) {
	document.addEventListener('mouseover', cvhandleMouseover, false);
}
else {
	document.attachEvent('onmouseover', cvhandleMouseover);
}
var cvwidth = 200,
	cvheight = 200,
	cvradius = Math.min(cvwidth, cvheight) / 2;

var cvcolor = d3.scale.category20();

var cvpie = d3.layout.pie()
	.value(function(d) { return d.xrij; })
	.sort(null);

var cvarc = d3.svg.arc()
	.innerRadius(cvradius - 100)
	.outerRadius(cvradius - 20);
			
var cvtip = d3.tip()
	.attr('class', 'd3-tip')
	.offset([-5, 0])
	.html(function(d) {
		return "<strong>" + cvtextvalue + ":</strong> <span style='color:red'>" + cvamount + "</span>";
	})

var cvamount;
var cvtextvalue = "";

	

var cvsvg = d3.select("body").select("#donutgraphs").append("svg")
	.attr("width", cvwidth)
	.attr("height", cvheight)
	.append("g")
	.attr("transform", "translate(" + cvwidth / 2 + "," + cvheight / 2 + ")");
				
cvsvg.call(cvtip);

d3.tsv("data/CVDonutGraphData.tsv", function(error, data) {
	data.forEach(function(d) {
		d.rij = d.rij;
		d.beroep = d.beroep;
	});
	var cvpath = cvsvg.datum(data).selectAll("path")
		.data(cvpie)
		.enter().append("path")
		.attr("fill", function(d, i) { return cvcolor(i); })
		.attr("d", cvarc)
		.attr("id", function(d) {return d.data.rij;})
		.on('mouseover', cvmouseover)
		.on('mousemove', cvtip.show)
		.on('mouseout', cvtip.hide)
		.each(function(d) { this._current = d; }); // store the initial angles

	d3.selectAll("#cvinput")
		.on("change", cvchange);

	var cvtimeout = setTimeout(function() {
		d3.select("input[value=\"xberoep\"]").property("checked", true).each(cvchange);
	}, 2000);

	function cvchange() {
		var value = this.value;
		if (this.value === 'xberoep') {
			cvpath.attr("id", function(d) {return d.data.beroep;})
		}
		else {
			cvpath.attr("id", function(d) {return d.data.rij;})
		}
		clearTimeout(cvtimeout);
		cvpie.value(function(d) { return d[value]; }); // change the value function
		cvpath = cvpath.data(cvpie); // compute the new angles
		cvpath.transition().duration(750).attrTween("d", cvarcTween); // redraw the arcs
	}
});

// Store the displayed angles in _current.
// Then, interpolate from _current to the new angles.
// During the transition, _current is updated in-place by d3.interpolate.
function cvarcTween(a) {
	var i = d3.interpolate(this._current, a);
	this._current = i(0);
	return function(t) {
		return cvarc(i(t));
	};
}

function cvmouseover(d) {
	cvamount = +d.value;
}