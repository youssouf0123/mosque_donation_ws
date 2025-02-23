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
//import com.djago.events.ActionColumnCellFactoryCallbackReturns;
//import com.djago.model.Order;
//import com.djago.model.Return;
//import com.djago.service.OrderService;
//import com.djago.service.ReturnService;
//import com.djago.uimodels.ReturnUIModel;
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
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.Toggle;
//import javafx.scene.control.ToggleGroup;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.Region;
//import javafx.util.Callback;
//import net.rgielen.fxweaver.core.FxWeaver;
//import net.rgielen.fxweaver.core.FxmlView;
//
//@Component
//@FxmlView("returns-history.fxml")
//public class ReturnsHistoryController implements Initializable {
//
//	private static final Logger logger = LoggerFactory.getLogger(ReturnsHistoryController.class);
//
//	@Autowired
//	private ReturnService returnService;
//	
//	@Autowired
//	private OrderService orderService;
//
//	@FXML
//	public TableView<ReturnUIModel> returns_history_table;
//
//	private final FxWeaver fxWeaver;
//
//	public ObservableList<ReturnUIModel> data = FXCollections.observableArrayList();
//
//	@FXML
//	private DatePicker startDatePicker;
//
//	@FXML
//	private DatePicker endDatePicker;
//
//	@FXML
//	private Label returnsTotalLbl;
//
//	@FXML
//	private Button searchBtn;
//
//	@FXML
//	RadioButton searchByDateBtn;
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
//	enum SearchType {
//		BY_DATE, BY_DATE_RANGE
//	};
//
//	SearchType search;
//
//	TableColumn<ReturnUIModel, Long> id = new TableColumn<>("ID");
//	TableColumn<ReturnUIModel, Long> orderId = new TableColumn<>("Order ID");
//	TableColumn<ReturnUIModel, String> firstName = new TableColumn<>("First Name");
//	TableColumn<ReturnUIModel, String> lastName = new TableColumn<>("Last Name");
//	TableColumn<ReturnUIModel, Double> amount = new TableColumn<>("Amount");
//	TableColumn<ReturnUIModel, LocalDate> returnDate = new TableColumn<>("Return Date");
//	TableColumn<ReturnUIModel, String> actionColumn = new TableColumn<>("Action");
//
//	public ReturnsHistoryController(FxWeaver fxWeaver) {
//		this.fxWeaver = fxWeaver;
//	}
//
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//
//		initializeTableColumns();
//		data = FXCollections.observableArrayList();
//
//		List<Return> returns = returnService.findAll();
//
//		double total = 0.0;
//
//		for (Return entry : returns) {
//			data.add(new ReturnUIModel(entry));
//			total += entry.getAmount();
//		}
//
//		returnsTotalLbl.setText(String.valueOf(total));
//		returns_history_table.setItems(data);
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
//
//	@FXML
//	public void search(ActionEvent evt) {
//
//		logger.info("searching !");
//		
//		LocalDate startDate = startDatePicker.getValue();
//		LocalDate endDate = endDatePicker.getValue();
//		Long id = null;
//		if(!searchTextField.getText().equals(""))
//			id = Long.valueOf(searchTextField.getText());
//
//		List<Return> returns = new ArrayList<>();
//		
//		switch (otherExpenseHistChoiceBox.getValue())
//        {
//            case "Date":
//            	returns = returnService.findReturnsByDate(startDate);
//                break;
//            case "Range Of Date":
//            	returns = returnService.findReturnsByDateRange(startDate, endDate);
//                break;
//            case "Order ID":
//            	for(Return retur : returnService.findAll()) {
//            		if(retur.getOrder().getId() == id) {
//            			returns.add(retur);
//            		}
//            	}
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
////		if (search == SearchType.BY_DATE) {
////			returns = returnService.findReturnsByDate(startDate);
////		} else {
////			returns = returnService.findReturnsByDateRange(startDate, endDate);
////		}
//
//		double total = 0.0;
//
//		data.clear();
//		for (Return entry : returns) {
//			data.add(new ReturnUIModel(entry));
//			total += entry.getAmount();
//		}
//		
//		returnsTotalLbl.setText(String.valueOf(total));
//	}
//
//	private void initializeTableColumns() {
//
//		startDatePicker.setEditable(false);
//		endDatePicker.setEditable(false);
//
//		id.setCellValueFactory(new PropertyValueFactory<ReturnUIModel, Long>("id"));
//		orderId.setCellValueFactory(new PropertyValueFactory<ReturnUIModel, Long>("orderId"));
//		firstName.setCellValueFactory(new PropertyValueFactory<ReturnUIModel, String>("firstName"));
//		lastName.setCellValueFactory(new PropertyValueFactory<ReturnUIModel, String>("lastName"));
//		amount.setCellValueFactory(new PropertyValueFactory<ReturnUIModel, Double>("amount"));
//		returnDate.setCellValueFactory(new PropertyValueFactory<ReturnUIModel, LocalDate>("returnDate"));
//		actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
//
//		// set column widths based on table size
//		id.prefWidthProperty().bind(returns_history_table.widthProperty().divide(6));
//		orderId.prefWidthProperty().bind(returns_history_table.widthProperty().divide(6));
//		firstName.prefWidthProperty().bind(returns_history_table.widthProperty().divide(6));
//		lastName.prefWidthProperty().bind(returns_history_table.widthProperty().divide(6));
//		amount.prefWidthProperty().bind(returns_history_table.widthProperty().divide(6));
//		returnDate.prefWidthProperty().bind(returns_history_table.widthProperty().divide(6));
//		actionColumn.prefWidthProperty().bind(returns_history_table.widthProperty().divide(6));
//
//		Callback<TableColumn<ReturnUIModel, String>, TableCell<ReturnUIModel, String>> actionColCellFactory = 
//				new ActionColumnCellFactoryCallbackReturns(
//						false, fxWeaver, orderService, null, null, null, null
//		);
//
//		actionColumn.setCellFactory(actionColCellFactory);
//
//		returns_history_table.setFixedCellSize(Region.USE_COMPUTED_SIZE);
//		returns_history_table.getColumns().addAll(
////				id, 
//				returnDate, orderId, firstName, lastName, amount, actionColumn
//		);
//		returnsTotalLbl.setText(String.valueOf(0.0));
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
//
//			LocalDate startDate = startDatePicker.getValue();
//			if (startDate.isAfter(endDate)) {
////				searchBtn.setDisable(true);
//				Optional<ButtonType> result = alert.showAndWait();
//			} else {
////				searchBtn.setDisable(false);
//			}
//		});
//	}
//}