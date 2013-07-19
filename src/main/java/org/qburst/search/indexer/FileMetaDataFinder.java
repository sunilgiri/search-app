package org.qburst.search.indexer;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

/**
 * @author Gilsha
 * 
 */
public abstract class FileMetaDataFinder {
	public Map getMetaDataFromFile(String filePath) throws IOException {
		File file = new File(filePath);
		PDDocument doc = PDDocument.load(filePath);
		PDDocumentInformation info = doc.getDocumentInformation();
		Map metaData = new HashMap();
		metaData.put("page_count", doc.getNumberOfPages());
		metaData.put("title", info.getTitle());
		metaData.put("author", info.getAuthor());
		metaData.put("subject", info.getSubject());
		metaData.put("keywords", info.getKeywords());
		metaData.put("creator", info.getCreator());
		metaData.put("producer", info.getProducer());
		metaData.put("created_date", info.getCreationDate());
		metaData.put("lats_modified", new Date(file.lastModified()));
		return metaData;
	}
}
