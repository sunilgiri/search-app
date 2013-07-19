/**
 * 
 */
package org.qburst.search.indexer;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrServerException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

/**
 * @author Gilsha
 * 
 */
public interface ISearchIndexer {
	public Boolean isFileIndexed(String id);

	public void doIndexing(String fileName, String solrId);

	public String doSearch(String query);

	public void removeAllFilesFromIndex();

	public ArrayList getFilesFromFolder();

	public void shutDownSolrServer();
}
