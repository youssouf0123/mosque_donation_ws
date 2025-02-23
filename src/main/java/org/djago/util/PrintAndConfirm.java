package org.djago.util;

import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.djago.model.Order;
import org.djago.model.Product;
import org.djago.model.Order.OrderStatus;
import org.djago.service.OrderService;
import org.djago.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PrintAndConfirm<T extends Object> {



//	OrdersController pendingOrdersController;

	private static final Logger logger = LoggerFactory.getLogger(PrintAndConfirm.class);
	int updateQty;
	 public Product myProduct = null;
	
	public PrintAndConfirm(T order, OrderService orderService, ProductService productService) {
		
		// ==========================Print========================================================
		
		myProduct = null;
		ByteArrayInputStream bs = null;

		ByteArrayInputStream bs_adm = null;

		if (order instanceof Order) {
			bs = new ByteArrayInputStream(((Order) order).getInvoicePDF());
			bs_adm = new ByteArrayInputStream(((Order) order).getInvoiceDOCX());
		} else {
			//Return go in here
//			bs = new ByteArrayInputStream(((Return) order).getReturnPDF());
		}

		// -------> -------->Update Admin<----------- <-------------//

		XWPFDocument document_adm = null;
		try {
			document_adm = new XWPFDocument(bs_adm);
		} catch (IOException e) {
			logger.info("Youssouf error");
			e.printStackTrace();
		} finally {
			try {
				document_adm.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		boolean isQtInAdmin = true;

		XWPFTable table = document_adm.getTables().get(0);

		for (int i = 1; i < table.getRows().size(); i++) {

			XWPFTableRow row = table.getRow(i);

			for (Product product : productService.findAllProducts()) {
				if (product.getName().equals(row.getCell(0).getText())) {
					System.out.println("Youssouf -Diarra -Unique" + product);
					updateQty = Integer.valueOf(product.getQuantity()) - Integer.valueOf(row.getCell(1).getText());
					System.out.println("Youssouf -Diarra -updateQty" + updateQty);
					if (updateQty < 0) {
						this.myProduct = product;
						System.out.println("myProduct Youssouf -Diarra " + this.myProduct);
						isQtInAdmin = false;
						return;
					}
					break;
				}
			}
		}
		
		System.out.println("Youssouf -Diarra -Unique" + isQtInAdmin);
		
		//Not necessary but I am just being extra safe
		if(isQtInAdmin)
			for (int i = 1; i < table.getRows().size(); i++) {

				XWPFTableRow row = table.getRow(i);

				for (Product product : productService.findAllProducts()) {
					if (product.getName().equals(row.getCell(0).getText())) {
						System.out.println("Youssouf -Diarra -Unique" + product);

						int updateQty = Integer.valueOf(product.getQuantity()) - Integer.valueOf(row.getCell(1).getText());
						if (updateQty < 0)
							return;
						product.setQuantity(updateQty);
						productService.updateProduct(product);
						break;
					}
				}
			}
		else 
			return;
		// -------> -------->End Update Admin<----------- <-------------//

		PDDocument document = null;
		try {
			System.out.println("Diarra " + order);
//			for (Order order1 : orderService.findAll()) {
//				if (((Order) order).getId() == order1.getId()) {
//
//					order1.setStatus(OrderStatus.CONFIRMED);
//					orderService.save(order1);
//
//					gestockController.refreshAdminTable();
//					pendingOrdersController.refreshPendingOrdersTable();
//					tableView.refresh();
//					orderHistoryController.refreshOrderHistory();
//					gestockController.item_table_admin.refresh();
//					break;
//				}
//			}
			((Order) order).setStatus(OrderStatus.CONFIRMED);
			orderService.updateOrder((Order) order);
			document = PDDocument.load(bs);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// choose your printing method:
		try {
			Printing.print(document);
		} catch (IOException | PrinterException e) {
			 logger.info("No printer were found");
			e.printStackTrace();
		} finally {
			try {
				document.close();
				bs_adm.close();
				bs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ==========================Print========================================================
	}
}