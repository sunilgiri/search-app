/**
 * 
 */
package org.qburst.search;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.junit.Test;

/**
 * @author Cyril
 * 
 */
public class ConnectionTest {
	@Test
	public void test() {
		String urlString = "http://localhost:8983/solr";
		SolrServer solr = new HttpSolrServer(urlString);
		solr.setParser(new XMLResponseParser());
	}
}
