package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * 
 * @author Zhaoyuan(Sarah) Zhang
 *
 */
public class WebBrowser extends Application {

	// create myTab to display web page
	MyTab myTab = new MyTab();
	// WebView manages the visual representation of a web page(myTab's webView)
	private WebView webView = myTab.getWebView();
	// create a new TabPane to display tabs
	private TabPane tabPane = new TabPane();
	// The URL text field
	private TextField textField = new TextField();
	// WebEngine manages web pages non-visually(loading, reloading, error handling etc)
	private WebEngine engine = webView.getEngine();
	// initialize webZoom to 1 by default
	private double webZoom = 1;
	private String homePage = "www.acc.com";
	private WebHistory history;
	private ColorPicker colorPicker = new ColorPicker(Color.LIGHTSTEELBLUE);
	
	private MenuBar menuBar = new MenuBar();
	private Menu bookmarkMenu = new Menu("Bookmark");
	private String myPassword = "123456";

	@Override
	public void start(Stage stage) {
		
	
		PasswordField password = new PasswordField();		
		password.setLayoutX(400);
		password.setLayoutY(200);
		password.setPromptText("Input Password!");
		password.setFocusTraversable(false);
		
		Button checkPasswordButton = new Button("Check Password");	
		checkPasswordButton.setLayoutX(400);
		checkPasswordButton.setLayoutY(230);
		
		Label resultLabel = new Label();
		resultLabel.setFont(Font.font("verdama", 25));
		resultLabel.setTextFill(Color.BROWN);
		resultLabel.setLayoutX(400);
		resultLabel.setLayoutY(250);
			
		Group firstRoot = new Group();
		Image backgroundTitle = new Image("flower.png");
		ImageView backgroundImage = new ImageView(new Image("background.jpg"));
		Scene firstScene = new Scene(firstRoot);
		stage.getIcons().add(backgroundTitle);
		stage.setTitle("Sarah's webBrowser");
		firstRoot.getChildren().add(backgroundImage);
		firstRoot.getChildren().add(password);
		firstRoot.getChildren().add(checkPasswordButton);
		firstRoot.getChildren().add(resultLabel);
		stage.setScene(firstScene);
		stage.sizeToScene();
		stage.show();
		
		checkPasswordButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				try {
					if(!password.getText().equals(myPassword)) {
						resultLabel.setText("Invalid Password！");
					}else{
						// launchButton is used to launch web page
						Button launchButton = new Button();
						launchButton.setText("launch");
						launchButton.setTooltip(new Tooltip("Launch website"));
						// backwardButton is used to go backward to previous web page
						Button backwardButton = new Button();
						backwardButton.setText("<-");
						backwardButton.setTooltip(new Tooltip("Go To Previous Web"));
						// forwardButton is used to go forward to next web page which was lunched before
						Button forwardButton = new Button();
						forwardButton.setText("->");
						forwardButton.setTooltip(new Tooltip("Go To Next Web"));
						// homePageButton is used to launch home page
						Button homePageButton = new Button();
						homePageButton.setText("Homepage");
						// newTabButton is used to add a new web page
						Button newTabButton = new Button();
						newTabButton.setText("NewTab");
						newTabButton.setTooltip(new Tooltip("Add a New Web"));
						// refreshButton is used to refresh web page
						Button refreshButton = new Button();
						refreshButton.setText("Refresh");
						// zoomInButton is used to zoom in web page
						Button zoomInButton = new Button();
						zoomInButton.setText("+");
						zoomInButton.setTooltip(new Tooltip("ZoomIn Web"));
						// zoomOutButton is used to zoom out web page
						Button zoomOutButton = new Button();
						zoomOutButton.setText("-");
						zoomOutButton.setTooltip(new Tooltip("ZoomOut Web"));
						// historyButton is used to display web histories
						Button historyButton = new Button();
						historyButton.setText("History");
						historyButton.setTooltip(new Tooltip("Press To Check Web History"));
						// printButton is used to print web page
						Button printButton = new Button();
						printButton.setText("Print");
						//saveButton is used to save web pages to the book mark
						Button saveButton = new Button();
						ImageView starImage = new ImageView("star.png");
						starImage.setFitHeight(18);
						starImage.setPreserveRatio(true);
						saveButton.setGraphic(starImage);
						saveButton.setTooltip(new Tooltip("Save WebPage To BookMark"));
						
						//add book mark menu to the menu bar
						menuBar.getMenus().add(bookmarkMenu);
						
						
						// colorPicker is used to change theme(color) for the web browser
						// (but buttons below inside can be just changed color simultaneously)
						colorPicker.setOnAction(event -> {
				
							launchButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							backwardButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							forwardButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							homePageButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							newTabButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							refreshButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							zoomInButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							zoomOutButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							printButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							historyButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							saveButton.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							menuBar.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
							myTab.setStyle("-fx-background-color:" + colorPicker.getValue().toString().replace("0x", "#"));
				
						});
						// hBox is used to display web page text and launch this web
						HBox hBox = new HBox();
						hBox.getChildren().addAll(backwardButton, forwardButton, textField, launchButton, saveButton, colorPicker);
						// buttonBox is used to display different buttons which have different functions
						HBox buttonBox = new HBox();
						buttonBox.getChildren().addAll(homePageButton, newTabButton, refreshButton, zoomInButton, zoomOutButton,
								printButton, historyButton, menuBar);
				
						tabPane.getTabs().add(myTab);
											
						VBox root = new VBox();
							
						root.setBackground(new Background(setBackground()));				
						root.getChildren().addAll(hBox, buttonBox, tabPane, webView);
				
						// Set growth parameters						
						VBox.setVgrow(webView, Priority.ALWAYS);
						HBox.setHgrow(textField, Priority.ALWAYS);
				
						launchButton.setOnAction(event -> {
							launchWebPage();
						});
				
						backwardButton.setOnAction(event -> {
							backwardPage();
						});
				
						forwardButton.setOnAction(event -> {
							forwardPage();
						});
				
						homePageButton.setOnAction(event -> {
							launchHomePage();
						});
				
						newTabButton.setOnAction(event -> {
							createNewTab();
						});
				
						refreshButton.setOnAction(event -> {
							refreshPage();
						});
				
						zoomInButton.setOnAction(event -> {
							zoomIn();
						});
				
						zoomOutButton.setOnAction(event -> {
							zoomOut();
						});
				
						printButton.setOnAction(event -> {
							printWebPage();
						});
						
						saveButton.setOnAction(event -> {
							
							engine = webView.getEngine();
							MenuItem webHistoryItem = new MenuItem(engine.getLocation());
							bookmarkMenu.getItems().add(webHistoryItem);
						
							webHistoryItem.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									
									engine.load(webHistoryItem.getText());
									textField.setText(engine.getLocation());
								}								
							});
						});
												
				
						historyButton.setOnAction(event -> {
				
							Stage historyStage = new Stage();
							VBox pane = new VBox();
							historyStage.setTitle("History");
							Scene scene = new Scene(pane, 600, 400);
							historyStage.setScene(scene);
							pane.getChildren().add(switchToHistoryTableView());
							historyStage.show();
				
						});
				
						tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				
							@Override
							public void changed(ObservableValue<? extends Tab> ov, Tab t1, Tab t2) {
//								System.out.println("old  "+ ((MyTab)t1).getWebView().getEngine().getLocation());
//								System.out.println("new  "+ ((MyTab)t2).getWebView().getEngine().getLocation());
				
								root.getChildren().remove(webView);
								webView = ((MyTab) (t2)).getWebView();
								root.getChildren().add(webView);
								myTab = (MyTab) t2;
								engine = webView.getEngine();
							}
						});

						// set title image and title name for the web browser
						Image title = new Image("flower.png");
						stage.getIcons().add(title);
						stage.setTitle("Sarah's WebBrowser");
				
						stage.setScene(new Scene(root));
						stage.sizeToScene();
						stage.show();					
						
							
						}
					
				} catch (Exception e) {	
					resultLabel.setText(e.getMessage());
				}		
			}
			
		});
		
	}

	private BackgroundImage setBackground() {

		BackgroundImage backgroundImage = new BackgroundImage(new Image("background.jpg"), BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

		return backgroundImage;
	}

	private void createNewTab() {
		launchHomePage();
	}

	private void launchWebPage() {

		if (textField.getText().contains("http://www.")) {
			engine.load(textField.getText());
		} else if (textField.getText().contains("www.")) {
			engine.load("http://" + textField.getText());
		} else {
			engine.load("http://www" + textField.getText());
		}
		((MyTab) (tabPane.getSelectionModel().getSelectedItem())).setText(textField.getText());
		myTab.setText(textField.getText());

	}

	private void launchHomePage() {

		MyTab tab = new MyTab();
		myTab.setText("New Tab");
		tab.setTabName(homePage);
		tabPane.getTabs().add(tab);
		engine.load("http://" + homePage);
		textField.setText(homePage);

	}

	private void refreshPage() {

		webView.setZoom(1);
		engine.reload();
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		textField.setText(entries.get(history.getCurrentIndex()).getUrl());
		myTab.setText(entries.get(history.getCurrentIndex()).getUrl());

	}

	private void zoomIn() {
		webZoom += 0.25;
		webView.setZoom(webZoom);
	}

	private void zoomOut() {
		webZoom -= 0.25;
		webView.setZoom(webZoom);
	}

	private void printWebPage() {

		PrinterJob printerJob = PrinterJob.createPrinterJob();
		if (printerJob != null) {
			engine.print(printerJob);
			printerJob.endJob();
		}
	}

	private void backwardPage() {

		history = engine.getHistory();
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		
		try {//measure1:
			history.go(-1);
		} catch (Exception e) {
			return;
		}
//		if (history.getCurrentIndex() > 0) {//measure2:
//			history.go(-1);
//		}
		textField.setText(entries.get(history.getCurrentIndex()).getUrl());
		myTab.setText(entries.get(history.getCurrentIndex()).getUrl());
	}

	private void forwardPage() {

		history = engine.getHistory();
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		try {//measure1:
			history.go(1);
		} catch (Exception e) {
			return;
		}
//		if (history.getCurrentIndex() < entries.size() - 1) {//measure2:
//			history.go(1);
//		}
		textField.setText(entries.get(history.getCurrentIndex()).getUrl());
		myTab.setText(entries.get(history.getCurrentIndex()).getUrl());
	}

	// https://riptutorial.com/javafx/example/27946/add-button-to-tableview?fbclid=IwAR2BRLvTxcynuGWyIZLqksAEM0Oyh3q1NNyIIhTr59ZeQn7QHrRYjIQQsNs#:
	// ~:text=Example%23,setCellFactory(Callback%20value)%20method.&text=In%20this%20application%20we%20are,selected%20and%20its%20information%20printed
	private TableView<WebHistory.Entry> switchToHistoryTableView() {

		history = engine.getHistory();
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		
		TableView<WebHistory.Entry> table = new TableView<WebHistory.Entry>();//
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<WebHistory.Entry, String> urlColumn = new TableColumn<>("Web Address");//
		urlColumn.setCellValueFactory(new PropertyValueFactory<>("Url"));
		table.getColumns().add(urlColumn);

		TableColumn<WebHistory.Entry, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("LastVisitedDate"));
		table.getColumns().add(dateColumn);

		TableColumn<WebHistory.Entry, Void> buttonColumn = new TableColumn<>("Press To Back");
		table.getColumns().add(buttonColumn);
		
		Callback<TableColumn<WebHistory.Entry, Void>, TableCell<WebHistory.Entry, Void>> cellFactory = new Callback<TableColumn<WebHistory.Entry, Void>, TableCell<WebHistory.Entry, Void>>() {

			@Override
			public TableCell<WebHistory.Entry, Void> call(TableColumn<WebHistory.Entry, Void> arg0) {

				final TableCell<WebHistory.Entry, Void> cell = new TableCell<WebHistory.Entry, Void>() {

					private final Button btn = new Button("back to website");

					{
						btn.setOnAction((ActionEvent event) -> {
							engine.load(table.getItems().get(getIndex()).getUrl());
							textField.setText(table.getItems().get(getIndex()).getUrl());
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		buttonColumn.setCellFactory(cellFactory);
		
		for(WebHistory.Entry entry: entries) {
			table.getItems().add(entry);
		}
		
		return table;	
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
