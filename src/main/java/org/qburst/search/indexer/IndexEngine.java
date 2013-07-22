/**
 * 
 */
package org.qburst.search.indexer;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Cyril
 *
 */
public class IndexEngine {
	public void start() throws Exception {
		ISearchIndexer si = new SearchPDFIndexer();
		try{
			ArrayList<File> files = si.getFilesFromFolder();
			for (File file: files){
				System.out.println( file.getAbsolutePath());
				si.doIndexing(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			si.rollbackSolrServer();
		} finally{
			si.shutDownSolrServer();
		}
	}
	
	public static void main(String[] args) {
		IndexEngine ie = new IndexEngine();
		try{
			ie.start();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
