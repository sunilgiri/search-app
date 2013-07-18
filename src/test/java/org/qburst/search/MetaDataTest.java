package org.qburst.search;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.junit.Test;



public class MetaDataTest {
	@Test
	public void test() throws Exception {
		PDDocument doc = PDDocument.load("/home/user/my-stuffs/my-boox/Manning.MongoDB.in.Action.Dec.2011.pdf");
		PDDocumentInformation info = doc.getDocumentInformation();
		System.out.println( "Page Count=" + doc.getNumberOfPages() );
		System.out.println( "Title=" + info.getTitle() );
		System.out.println( "Author=" + info.getAuthor() );
		System.out.println( "Subject=" + info.getSubject() );
		System.out.println( "Keywords=" + info.getKeywords() );
		System.out.println( "Creator=" + info.getCreator() );
		System.out.println( "Producer=" + info.getProducer() );
		System.out.println( "Creation Date=" + info.getCreationDate() );
		System.out.println( "Modification Date=" + info.getModificationDate());
		System.out.println( "Trapped=" + info.getTrapped() );
	}
}
