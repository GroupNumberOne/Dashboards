var vaclastID = 'id';

var vachandleMouseover = function (e) {
	var target = e.target || e.srcElement;
	vaclastID = e.target.id;
	if (vaclastID != "") {
		vactextvalue = vaclastID;
	}
};

if (document.addEventListener) {
	document.addEventListener('mouseover', vachandleMouseover, false);
}
else {
	document.attachEvent('onmouseover', vachandleMouseover);
}

var vacwidth = 200,
	vacheight = 200,
	vacradius = Math.min(vacwidth, vacheight) / 2;

var vaccolor = d3.scale.category20();

var vacpie = d3.layout.pie()
	.value(function(d) { return d.xdienstverband; })
	.sort(null)

var vacarc = d3.svg.arc()
	.innerRadius(vacradius - 100)
	.outerRadius(vacradius - 20);
				
var vactip = d3.tip()
	.attr('class', 'd3-tip')
	.offset([-10, 0])
	.html(function(d) {
		return "<strong>" + vactextvalue + ":</strong> <span style='color:red'>" + vacamount + "</span>";
	})

var vacamount;
var vactextvalue = "";
	
var vacsvg = d3.select("body").select("#donutgraphs").append("svg")
	.attr("width", vacwidth)
	.attr("height", vacheight)
	.append("g")
	.attr("transform", "translate(" + vacwidth / 2 + "," + vacheight / 2 + ")");
				
vacsvg.call(vactip);

d3.tsv("data/VacatureDonutGraphData.tsv", function(error, data) {
	data.forEach(function(d) {
		d.dienstverband = d.dienstverband;
		d.functie = d.functie;
	});
			  
	var vacpath = vacsvg.datum(data).selectAll("path")
	.data(vacpie(data))
	.enter().append("path")
	.attr("fill", function(d, i) { return vaccolor(i); })
	.attr("d", vacarc)
	.attr("id", function(d) {return d.data.dienstverband;})
	.on('mouseover', vacmouseover)
	.on('mousemove', vactip.show)
	.on('mouseout', vactip.hide)
	.each(function(d) { this._current = d;}) // store the initial angles

	d3.selectAll("#vacinput")
		.on("change", vacchange);

	var vactimeout = setTimeout(function() {
		d3.select("input[value=\"xfunctie\"]").property("checked", true).each(vacchange);
	}, 2000);

	function vacchange() {
		var value = this.value;
		if (this.value === 'xfunctie') {
			vacpath.attr("id", function(d) {return d.data.functie;})
		}
		else {
			vacpath.attr("id", function(d) {return d.data.dienstverband;})
		}
		clearTimeout(vactimeout);
		vacpie.value(function(d) { return d[value]; }); // change the value function
		vacpath = vacpath.data(vacpie); // compute the new angles
		vacpath.transition().duration(750).attrTween("d", vacarcTween); // redraw the arcs
	}
});

// Store the displayed angles in _current.
// Then, interpolate from _current to the new angles.
// During the transition, _current is updated in-place by d3.interpolate.
function vacarcTween(a) {
	var i = d3.interpolate(this._current, a);
	this._current = i(0);
	return function(t) {
		return vacarc(i(t));
	};
}

function vacmouseover(d) {
	vacamount = +d.value;
}