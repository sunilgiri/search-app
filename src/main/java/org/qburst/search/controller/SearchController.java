/**
 * 
 */
package org.qburst.search.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.codehaus.jackson.map.ObjectMapper;
import org.qburst.search.model.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Cyril
 * 
 */
@Controller
public class SearchController {
	private ObjectMapper mapper = new ObjectMapper();

	public SearchController(){
			
	}
		
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody String listNewJoinee(@RequestParam(value = "query") String q) {
		String jsonData = "";
		try {
			HttpSolrServer solr = new HttpSolrServer("http://10.4.0.56:8983/solr");
			SolrQuery query = new SolrQuery();
			query.setQuery(q);
			query.setFields("content", "author");
			query.setHighlight(true);
			query.addHighlightField("content");
			query.setHighlightSnippets(3);
			QueryResponse response = solr.query(query);
			SolrDocumentList results = response.getResults();
			Map<String, Map<String, List<String>>> highlights = response
					.getHighlighting();
			ArrayList<Search> mySearch = new ArrayList<Search>();
			int idx = 0;
			for (String key : highlights.keySet()){
				List<String> data = highlights.get(key).get("content");
				Search s = new Search();
				s.setHighlights(data);
				s.setId(key);
				s.setAuthor(results.get(idx).containsKey("author") ? results.get(idx).get("author") + "" : "");
				mySearch.add(s);
				idx++;
			}
			ObjectMapper mapper = new ObjectMapper();
			jsonData = mapper.writeValueAsString(mySearch);
			solr.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String a = "aaData";
		return "{" + '"' + a +'"' + ":" + jsonData + "}";
	}
	
}
