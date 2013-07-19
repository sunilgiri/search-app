/**
 * 
 */
package org.qburst.search.indexer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.qburst.search.model.Search;

/**
 * @author Gilsha
 * 
 */
public class SearchPDFIndexer extends FileMetaDataFinder implements
		ISearchIndexer {
	HttpSolrServer solr = new HttpSolrServer("http://10.4.0.56:8983/solr");
	public void doIndexing(String fileName, String solrId){
		try {
			Map metaData = getMetaDataFromFile(fileName);
			if(!isFileIndexed(solrId)){
				ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
				up.addFile(new File(fileName), "application/pdf");
				up.setParam("literal.id", solrId);
				up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
				up.setParam("literal.page_count", (String) metaData.get("page_count"));
				up.setParam("literal.title", (String) metaData.get("title"));
				up.setParam("literal.author", (String) metaData.get("author"));
				up.setParam("literal.subject", (String) metaData.get("subject"));
				up.setParam("literal.keywords", (String) metaData.get("keywords"));
				up.setParam("literal.creator", (String) metaData.get("creator"));
				up.setParam("literal.producer", (String) metaData.get("producer"));
				up.setParam("literal.created_date",
						(String) metaData.get("created_date"));
				up.setParam("literal.last_modified",
						(String) metaData.get("last_modified"));
				try {
					solr.request(up);
					UpdateResponse ur = solr.commit();
					ur.getStatus();
				} catch (SolrServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public Boolean isFileIndexed(String id){
		// TODO Auto-generated method stub
		SolrQuery query = new SolrQuery("id:"+id);
		QueryResponse rsp;
		try {
			rsp = solr.query( query );
			if(rsp.getResults().getNumFound()>0)
		    	return true;
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String doSearch(String queryString) {
		// TODO Auto-generated method stub
		SolrQuery query = new SolrQuery();
		query.setQuery("html");
		query.setFields("content", "author");
		query.setHighlight(true);
		query.addHighlightField("content");
		query.setHighlightSnippets(3);
		QueryResponse response;
		String jsonData = "{}";
		try {
			response = solr.query(query);
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
			String a = "aaData";
			try {
				jsonData = "{" + '"' + a +'"' + ":" + mapper.writeValueAsString(mySearch) + "}";
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonData;
	}

	public void removeAllFilesFromIndex(){
		// TODO Auto-generated method stub
		try {
			solr.deleteByQuery("id:*");
			solr.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList getFilesFromFolder() {
		// TODO Auto-generated method stub
		File folder = new File("/home/user/my-stuffs/my-boox");
		File[] listOfFiles = folder.listFiles();
		ArrayList<File> files = new ArrayList<File>();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				files.add(file);
			}
		}
		return files;
	}

	public void shutDownSolrServer() {
		// TODO Auto-generated method stub
		if (solr != null) {
			solr.shutdown();
			solr = null;
		}  
	}
}
