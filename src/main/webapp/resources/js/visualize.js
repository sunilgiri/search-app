function visualize(nodes) {
	var highlightsArr = [];
	nodes.forEach(function(d){ highlightsArr.push(d.highlights.length); });
	var radiusScale = d3.scale.linear()
		.domain([d3.min(highlightsArr), d3.max(highlightsArr)])
		.range([6, 100]);
	var color = d3.scale.category20();
	var w = $("#vis").width(), h = $("#vis").height()-5;
	if($(".svg_vis").length > 0)
		$(".svg_vis").remove();
	var force = d3.layout.force()
	    .gravity(0.05)
	    .charge(function(d, i) { return i ? 0 : -2000; })
	    .nodes(nodes)
	    .size([w, h]);
	var root = nodes[0];
	root.fixed = true;
	force.start();
	var svg = d3.select("#vis").append("svg:svg")
	    .attr("width", w)
	    .attr("height", h)
	    .attr("class","svg_vis");
	svg.append("svg:rect")
	    .attr("width", w)
	    .attr("height", h);
	var circle = svg.selectAll("circle")
	    .data(nodes.slice(1))
	    .enter().append("svg:circle")
	    .attr("r", function(d) {
	    	d.radius = radiusScale(d.highlights.length);
	    	return d.radius; 
	    })
	    .style("fill", function(d, i) { return color(i); })
	    .call(force.drag)
	    .append("svg:title")
	    .text(function(d){ return d.fileName; });;
	force.on("tick", function(e) {
		  var q = d3.geom.quadtree(nodes),
		      i = 0,
		      n = nodes.length;
		  while (++i < n) q.visit(collide(nodes[i]));
		  svg.selectAll("circle")
		      .attr("cx", function(d) { return d.x; })
		      .attr("cy", function(d) { return d.y; });
	});
	function collide(node) {
		var r = node.radius + 16,
			nx1 = node.x - r,
			nx2 = node.x + r,
			ny1 = node.y - r,
			ny2 = node.y + r;
		return function(quad, x1, y1, x2, y2) {
			if (quad.point && (quad.point !== node)) {
				var x = node.x - quad.point.x,
				y = node.y - quad.point.y,
				l = Math.sqrt(x * x + y * y),
				r = node.radius + quad.point.radius;
				if (l < r) {
					l = (l - r) / l * .5;
					node.x -= x *= l;
					node.y -= y *= l;
					quad.point.x += x;
					quad.point.y += y;
				}
			}
			return x1 > nx2 || x2 < nx1 || y1 > ny2 || y2 < ny1;
		};
	}
}

