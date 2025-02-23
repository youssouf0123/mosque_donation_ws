package org.djago.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

public class Utils {

	static Logger logger = LoggerFactory.getLogger(Utils.class);

	public static ByteArrayOutputStream generatePDF(ByteArrayOutputStream bos) throws IOException {

		InputStream in = new ByteArrayInputStream(bos.toByteArray());
		XWPFDocument doc = new XWPFDocument(in);

		PdfOptions options = PdfOptions.create();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PdfConverter.getInstance().convert(doc, output, options);
		in.close();
		doc.close();

		return output;
	}

//	public static List<ProductUIModel> parseAndGetProductList(byte[] invoice) {
//
//		List<ProductUIModel> rV = new ArrayList<>();
//
//		ByteArrayInputStream bs = new ByteArrayInputStream(invoice);
//
//		XWPFDocument document = null;
//		try {
//			document = new XWPFDocument(bs);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		XWPFTable table = document.getTables().get(0);
//
//		for (int i = 1; i < table.getRows().size(); i++) {
//			XWPFTableRow row = table.getRow(i);
//			logger.info("{}-{}-{}-{}", row.getCell(0).getText(), row.getCell(1).getText(), row.getCell(2).getText(),
//					row.getCell(3).getText());
//			
//			//Using substring method to get rid of the $ sign
//			rV.add(new ProductUIModel(row.getCell(0).getText(), Integer.valueOf(row.getCell(1).getText()),
//					Double.valueOf(row.getCell(2).getText().substring(1)), Double.valueOf(row.getCell(3).getText().substring(1))));
//		}
//		return rV;
//	}
}