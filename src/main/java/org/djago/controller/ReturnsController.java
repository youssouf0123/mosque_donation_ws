package org.djago.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.djago.controller.request.ProductCartItem;
import org.djago.controller.request.ReturnRequestObject;
import org.djago.model.Order;
import org.djago.model.Return;
import org.djago.service.OrderService;
import org.djago.service.ReturnService;
import org.djago.util.GenerateInvoice;
import org.djago.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/returns")
public class ReturnsController {

	private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

	@Autowired
	private ReturnService returnService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private GenerateInvoice generateInvoice;

	@GetMapping("/all")
	public ResponseEntity<List<Return>> getAllReturns() {
		List<Return> returns = returnService.findAll();
		logger.info(returns.toString());
		return new ResponseEntity<>(returns, HttpStatus.OK);
	}

	@GetMapping(value = "/downloadPDF/{id}", produces = "application/pdf")
	public ResponseEntity<byte[]> getPDF(@PathVariable("id") Long id) {

		Return returnObj = returnService.findReturnById(id);

		byte[] contents = returnObj.getReturnInvoicePDF();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));

		String filename = "test.pdf";

		headers.setContentDispositionFormData(filename, filename);

		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
		return response;
	}

	@PostMapping("/add")
	public ResponseEntity<?> returnOrder(@Valid @RequestBody ReturnRequestObject returns)
			throws InvalidFormatException, IOException, URISyntaxException {
//		Preconditions.checkNotNull(resource);
//		RestPreconditions.checkNotNull(service.getById(resource.getId()));
		Map<String, Integer> mapItemToQty = new Hashtable<>();
		logger.info(returns.toString());

		Order order = orderService.findOrderById(returns.getOrderId()).get();

//		Set<Return> returnList = order.getReturns();
		Collection<? extends Return> returnList = returnService.findAll().stream().filter(x -> x.getOrder().getId() == returns.getOrderId())
				.collect(Collectors.toSet());
		
		XWPFDocument document = null;

		XWPFTable table = null;
		for(Return returnItem : returnList) {
			Return myReturn = returnService.findReturnById(((Return)returnItem).getId());
			try {
				ByteArrayInputStream bs = new ByteArrayInputStream(myReturn.getReturnInvoiceDOCX());
				document = new XWPFDocument(bs);
				table = document.getTables().get(0);
				for (int i = 1; i < table.getRows().size(); i++) {

					XWPFTableRow row = table.getRow(i);
					if(!mapItemToQty.containsKey(row.getCell(0).getText()))
						mapItemToQty.put(row.getCell(0).getText(), Integer.valueOf(row.getCell(1).getText()));
					else 
						mapItemToQty.put(row.getCell(0).getText(), (Integer.valueOf(row.getCell(1).getText())
								+ mapItemToQty.get(row.getCell(0).getText())));
					
					logger.info("Youssouf table return row : " + row.getCell(0).getText() + " " + row.getCell(1).getText());
				}
			}catch (Exception e) {
				logger.info(e.toString());
			} finally {
				document.close();
			}
		}

		logger.info("Youssouf return list : " + returnList);
		
		logger.info("Youssouf return mapItemToQty : " + mapItemToQty);

		List<ProductCartItem> cartItems = returns.getTableRows();

		logger.info("Youssouf cartItems list : " + cartItems);

		document = null;

		table = null;
		try {
			ByteArrayInputStream bs = new ByteArrayInputStream(order.getInvoiceDOCX());
			document = new XWPFDocument(bs);
			table = document.getTables().get(0);
			for (int i = 1; i < table.getRows().size(); i++) {

				XWPFTableRow row = table.getRow(i);
				for (ProductCartItem cartItem : cartItems) {
					if (cartItem.getName().equals(row.getCell(0).getText())
							&&(mapItemToQty.getOrDefault(cartItem.getName(), 0).intValue() + cartItem.getQuantity().intValue()) > Integer.valueOf(row.getCell(1).getText())) {
						cartItem.setName(cartItem.getName() + "###" + row.getCell(1).getText() + "###" + mapItemToQty.getOrDefault(cartItem.getName(), 0)
						+ "###" + cartItem.getQuantity());
						return new ResponseEntity<>(cartItem, HttpStatus.OK);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e1) {
			logger.info(e1.toString());
		} finally {
			document.close();
		}

		Return returnToSave = new Return();
		returnToSave.setOrder(order);
//		
		Return savedReturn = returnService.save(returnToSave);
//
//		 calc totalAmount from returns object

		double totalAmount = returns.getTotal().doubleValue();

		returnToSave.setAmount(totalAmount);

		ByteArrayOutputStream invoiceDOCX = generateInvoice.generate(cartItems, totalAmount, order,
				"returns_template.docx");

		ByteArrayOutputStream invoiceAsPDF = Utils.generatePDF(invoiceDOCX);

		savedReturn.setReturnInvoiceDOCX(invoiceDOCX.toByteArray());
		savedReturn.setReturnInvoicePDF(invoiceAsPDF.toByteArray());

		savedReturn = returnService.updateReturn(savedReturn);
		
//      return RestPreconditions.checkFound(service.findById(id));			
//		return new ResponseEntity<>(returnToSave, HttpStatus.CREATED);
		return new ResponseEntity<>(savedReturn, HttpStatus.CREATED);
	}

}