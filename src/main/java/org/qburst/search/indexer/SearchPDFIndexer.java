/**
 * 
 */
package org.qburst.search.indexer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;

/**
 * @author Gilsha
 * 
 */
public class SearchPDFIndexer extends AbstractSearchIndexer implements
		ISearchIndexer {
	
	@Override
	public Map<String, String> getMetaDataFromFile(File file)
			throws Exception {
		PDDocument doc = PDDocument.load(file);
		PDDocumentInformation info = doc.getDocumentInformation();
		Map<String, String> metaData = new HashMap<String, String>();
		metaData.put("page_count", doc.getNumberOfPages() + "");
		metaData.put("title", info.getTitle());
		metaData.put("subject", info.getSubject());
		metaData.put("keywords", info.getKeywords());
		metaData.put("creator", info.getCreator());
		metaData.put("producer", info.getProducer());
		metaData.put("url", file.getAbsolutePath());
		return metaData;
	}
	
	@Override
	public void doIndexing(File file) throws Exception {
		
		String ids = (file.getAbsolutePath().hashCode() + "").replace("-", "*");
		if (!isFileIndexed(ids)) {
			Map<String, String> metaData = getMetaDataFromFile(file);
			ContentStreamUpdateRequest up = new ContentStreamUpdateRequest(
					"/update/extract");
			up.addFile(file, "application/pdf");
			up.setParam("literal.id", ids);
			up.setParam("literal.page_count",
					(String) metaData.get("page_count"));
			up.setParam("literal.title", (String) metaData.get("title"));
			up.setParam("literal.subject", (String) metaData.get("subject"));
			up.setParam("literal.keywords", (String) metaData.get("keywords"));
			up.setParam("literal.creator", (String) metaData.get("creator"));
			up.setParam("literal.producer", (String) metaData.get("producer"));
			up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
			solr.request(up);
			UpdateResponse ur = solr.commit();
			ur.getStatus();
		}

	}
}
