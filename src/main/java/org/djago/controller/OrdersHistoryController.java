package org.djago.controller;
//package org.storemgr.controller;
//
//import java.net.URL;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.ResourceBundle;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.djago.events.ActionColumnCellFactoryCallback;
//import com.djago.model.Order;
//import com.djago.service.OrderService;
//import com.djago.uimodels.OrderUIModel;
//
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.Region;
//import javafx.util.Callback;
//import net.rgielen.fxweaver.core.FxWeaver;
//import net.rgielen.fxweaver.core.FxmlView;
//
//@Component
//@FxmlView("order-history.fxml")
//public class OrdersHistoryController implements Initializable {
//
//	private static final Logger logger = LoggerFactory.getLogger(OrdersHistoryController.class);
//
//	@Autowired
//	private OrderService orderService;
//
//	@FXML
//	public TableView<OrderUIModel> order_history_table;
//
//	private final FxWeaver fxWeaver;
//
//	public ObservableList<OrderUIModel> data = FXCollections.observableArrayList();
//
//	@FXML
//	private DatePicker startDatePicker;
//
//	@FXML
//	private DatePicker endDatePicker;
//
//	@FXML
//	private Label salesTotalLbl;
//
//	@FXML
//	private Button searchBtn;
//	
//	@FXML
//	Label fromLbl;
//	
//	@FXML
//	Label toLbl;
//	
//	@FXML
//	TextField searchTextField;
//	
//	@FXML
//	ChoiceBox<String> otherExpenseHistChoiceBox ;
//
//	
//	enum SearchType { BY_DATE, BY_DATE_RANGE };
//	
//	SearchType search;
//	
//	TableColumn<OrderUIModel, Long> id = new TableColumn<>("Order ID");
//	TableColumn<OrderUIModel, LocalDate> orderDate = new TableColumn<>("Order Date");
//	TableColumn<OrderUIModel, String> firstName = new TableColumn<>("First Name");
//	TableColumn<OrderUIModel, String> lastName = new TableColumn<>("Last Name");
//	TableColumn<OrderUIModel, String> address = new TableColumn<>("Address");
//	TableColumn<OrderUIModel, String> city = new TableColumn<>("City");
//	TableColumn<OrderUIModel, String> country = new TableColumn<>("Country");
//	TableColumn<OrderUIModel, String> phone = new TableColumn<>("Phone");
//	TableColumn<OrderUIModel, String> status = new TableColumn<>("Status");
//	TableColumn<OrderUIModel, Double> amount = new TableColumn<>("Amount");
//	TableColumn<OrderUIModel, String> actionColumn = new TableColumn<>("Action");
//
//	public OrdersHistoryController(FxWeaver fxWeaver) {
//		this.fxWeaver = fxWeaver;
//	}
//
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		
//		initializeTableColumns();
//		data = FXCollections.observableArrayList();
//		order_history_table.setItems(data);
//		
//		otherExpenseHistChoiceBox.getItems().addAll("Date", "Range Of Date", "Order ID", "Phone Number", "First Name", "Last Name", "Email");
//		otherExpenseHistChoiceBox.setValue("Date");
//		endDatePicker.setVisible(false);
//		searchTextField.setVisible(false);
//		toLbl.setVisible(false);
//		fromLbl.setText("Date");
//		searchTextField.textProperty().addListener(new ChangeListener<String>() {
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				if (!newValue.matches("\\d*")) {
//					searchTextField.setText(newValue.replaceAll("[^\\d]", ""));
//				}
//			}
//		});
//		
//		otherExpenseHistChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				if(newValue.equals("Date")) {
//					fromLbl.setText("Date");
//					toLbl.setVisible(false);
//					endDatePicker.setVisible(false);
//					startDatePicker.setVisible(true);
//					searchTextField.setVisible(false);
//				} else if(newValue.equals("Order ID")) {
//					fromLbl.setText("Order ID");
//					searchTextField.setVisible(true);
//					endDatePicker.setVisible(false);
//					startDatePicker.setVisible(false);
//					toLbl.setVisible(false);
//				} else if(newValue.equals("Phone Number")) {
//					fromLbl.setText("Phone No");
//					searchTextField.setVisible(true);
//					endDatePicker.setVisible(false);
//					startDatePicker.setVisible(false);
//					toLbl.setVisible(false);
//				} else {
//					fromLbl.setText("From");
//					toLbl.setVisible(true);
//					endDatePicker.setVisible(true);
//					startDatePicker.setVisible(true);
//					searchTextField.setVisible(false);
//				}
//			}
//		});
//	}
//
//	@FXML
//	public void search(ActionEvent evt) {
//		
//		LocalDate startDate = startDatePicker.getValue();
//		LocalDate endDate = endDatePicker.getValue();
//		Long id = null;
//		if(!searchTextField.getText().equals(""))
//			id = Long.valueOf(searchTextField.getText());
//
//		List<Order> orders = new ArrayList<>();
//		
//		switch (otherExpenseHistChoiceBox.getValue())
//        {
//            case "Date":
//            	orders = orderService.findOrdersByDate(startDate);
//                break;
//            case "Range Of Date":
//            	orders = orderService.findOrdersByDateRange(startDate, endDate);
//                break;
//            case "Order ID":
//            	orders = orderService.findOrderByOrderID(id);
//                break;
//            case "Phone Number":
//            	
//            	break;
//            case "First Name":
//            	
//                break;
//            case "Last Name":
//               
//                break;
//            case "Email":
//            	
//                break;
//        }
//		
//		double total = 0.0;
//		
//		data.clear();
//		for (Order order : orders) {
//			data.add(new OrderUIModel(order));
//			total += order.getAmount();
//		}
//		salesTotalLbl.setText(String.valueOf(total));
//	}
//	
//	private void initializeTableColumns() {
//
//		startDatePicker.setEditable(false);
//		endDatePicker.setEditable(false);
//
//		id.setCellValueFactory(new PropertyValueFactory<OrderUIModel, Long>("id"));
//		orderDate.setCellValueFactory(new PropertyValueFactory<OrderUIModel, LocalDate>("orderDate"));
//
//		firstName.setCellValueFactory(new PropertyValueFactory<OrderUIModel, String>("firstName"));
//		lastName.setCellValueFactory(new PropertyValueFactory<OrderUIModel, String>("lastName"));
////		address.setCellValueFactory(new PropertyValueFactory<OrderUIModel, String>("address"));
//		
//		city.setCellValueFactory(new PropertyValueFactory<OrderUIModel, String>("city"));
//		country.setCellValueFactory(new PropertyValueFactory<OrderUIModel, String>("country"));
//		phone.setCellValueFactory(new PropertyValueFactory<OrderUIModel, String>("phone"));
//		status.setCellValueFactory(new PropertyValueFactory<OrderUIModel, String>("status"));
//		amount.setCellValueFactory(new PropertyValueFactory<OrderUIModel, Double>("amount"));
//		actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
//
//		// set column widths based on table size
//		id.prefWidthProperty().bind(order_history_table.widthProperty().divide(10));
//		orderDate.prefWidthProperty().bind(order_history_table.widthProperty().divide(10));
//
//		firstName.prefWidthProperty().bind(order_history_table.widthProperty().divide(11));
//		lastName.prefWidthProperty().bind(order_history_table.widthProperty().divide(11));
////		address.prefWidthProperty().bind(sales_info_table.widthProperty().divide(9));
//
//		city.prefWidthProperty().bind(order_history_table.widthProperty().divide(10));
//		country.prefWidthProperty().bind(order_history_table.widthProperty().divide(10));
//
//		phone.prefWidthProperty().bind(order_history_table.widthProperty().divide(10));
//		status.prefWidthProperty().bind(order_history_table.widthProperty().divide(10));
//		amount.prefWidthProperty().bind(order_history_table.widthProperty().divide(10));
//		
//		actionColumn.prefWidthProperty().bind(order_history_table.widthProperty().divide(10));
//
//		Callback<TableColumn<OrderUIModel, String>, TableCell<OrderUIModel, String>> actionColCellFactory = 
//				new ActionColumnCellFactoryCallback(false, fxWeaver, orderService, null, null, null, null);
//
//		actionColumn.setCellFactory(actionColCellFactory);
//
//		order_history_table.setFixedCellSize(Region.USE_COMPUTED_SIZE);
//		order_history_table.getColumns().addAll(id, orderDate, 
//				firstName, lastName, 
////				address, 
//				city, country, phone, status,	amount, actionColumn);
//		salesTotalLbl.setText(String.valueOf(0.0));
//		
//		endDatePicker.setOnAction(event -> {
//
//			LocalDate endDate = endDatePicker.getValue();
//
//			logger.debug("Selected date: " + endDate);
//
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Search Date Range Error");
//			alert.setContentText("Start Date must be before End Date!");
////			alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
//
//			LocalDate startDate = startDatePicker.getValue();
//			if (startDate.isAfter(endDate)) {
////				searchBtn.setDisable(true);
//				//sendDatePicker.getEditor().clear();
//				
//				Optional<ButtonType> result = alert.showAndWait();
//			} else {
////				searchBtn.setDisable(false);
//			}
//		});
//	}
//	
//	public void refreshOrderHistory() {
//		data.clear();
//		
//		for (Order order : orderService.findConfirmedOrders())
//			data.add(new OrderUIModel(order));
//		
//		order_history_table.setItems(data);
//		order_history_table.refresh();
//	}
//}