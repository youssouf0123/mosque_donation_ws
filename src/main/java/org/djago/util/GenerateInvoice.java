package org.djago.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.djago.controller.request.ProductCartItem;
import org.djago.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class GenerateInvoice {

	@Autowired
	Environment env;

	private static final Logger logger = LoggerFactory.getLogger(GenerateInvoice.class);

	public ByteArrayOutputStream generate(
			List<ProductCartItem> data, 
			double totalAmount, 
			Order order, 
			String template
	) throws InvalidFormatException, IOException, URISyntaxException 
	{
		InputStream templateStream = getClass().getClassLoader().getResourceAsStream(template);

		logger.info(templateStream.toString());
		
		XWPFDocument document = new XWPFDocument(OPCPackage.open(templateStream));

		for (XWPFParagraph paragraph : document.getParagraphs()) {
			
			for (XWPFRun r : paragraph.getRuns()) {
				
				String text = r.getText(0);
				if (text != null && text.contains("${date}")) {
					text = text.replace("${date}", LocalDate.now().toString());
					r.setText(text, 0);
				}
				if (text != null && text.contains("${id}")) {
					text = text.replace("${id}", String.valueOf(order.getId()));
					r.setText(text, 0);
				}
				if (text != null && text.contains("${name}")) {
					text = text.replace("${name}", order.getCustomerFirstName() + " " + order.getCustomerLastName());
					r.setText(text, 0);
				}
				if (text != null && text.contains("${invoice_total}")) {

					text = text.replace("${invoice_total}", Double.toString(totalAmount));
//					text = text.replace("${invoice_total}", GestockController.totalPriceFormatter.format(invoiceTotal));

					r.setText(text, 0);
				}
				if (text != null && text.contains("${total_phonetic}")) {
					text = text.replace("${total_phonetic}", "Cent Mille Francs");
					r.setText(text, 0);
				}
			}
		}

		XWPFTable table = document.getTables().get(0);

		for (ProductCartItem entry : data) {

			logger.debug(
					"{} - {} - {} - {}", 
					entry.getName(), 
					entry.getQuantity(), 
					entry.getPrice(),
					entry.getQuantity().multiply(entry.getPrice())
			);

			XWPFTableRow row = table.createRow();
			row.getCell(0).setText(entry.getName());
			row.getCell(1).setText(entry.getQuantity().toString());

//			row.getCell(2).setText(GestockController.totalPriceFormatter.format(Double.valueOf(data.get(i)[2])));
//			row.getCell(3).setText(GestockController.totalPriceFormatter.format(Double.valueOf(data.get(i)[3])));
			row.getCell(2).setText(String.valueOf(entry.getPrice()));
			row.getCell(3).setText(String.valueOf(entry.getQuantity().multiply(entry.getPrice())));

			row.getCell(0).setColor("ffe6e6");
			row.getCell(1).setColor("ffe6e6");
			row.getCell(2).setColor("ffe6e6");
			row.getCell(3).setColor("ffe6e6");
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		document.write(bos);

		document.close();
		return bos;
	}

}