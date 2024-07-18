package org.vervebridge.proj.libmanagementsystem;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class DashboardController implements Initializable{


    @FXML
    private AnchorPane savedBook_form;

    @FXML
    private ImageView savedBook_imgView;

    @FXML
    private TableView<saveBook> savedBook_tableView;

    @FXML
    private TableColumn<saveBook, String> savedBook_title;

    @FXML
    private TableColumn<saveBook, String> savedBook_author;

    @FXML
    private TableColumn<saveBook, String> savedBook_type;

    @FXML
    private TableColumn<saveBook, String> savedBook_publishedDate;

    @FXML
    private Button unsaveBtn;

    @FXML
    private AnchorPane returnBook_form;

    @FXML
    private TableView<returnBook> return_tableView;

    @FXML
    private TableColumn<returnBook, String> return_bookAuthor;

    @FXML
    private TableColumn<returnBook, String > return_bookTitle;

    @FXML
    private TableColumn<returnBook, String> return_bookType;

    @FXML
    private TableColumn<?, ?> return_issue_date;

    @FXML
    private Button return_book_btn;

    @FXML
    private Button return_btn;

    @FXML
    private ImageView return_image_view;

    @FXML
    private Button save_btn;

    @FXML
    private Button saved_books_btn;

    @FXML
    private Button signout_btn;

    @FXML
    private Label studentId_label;

    @FXML
    private Label take_GenreLabel;

    @FXML
    private Label take_authorLabel;

    @FXML
    private Button take_btn;

    @FXML
    private Button take_clearBtn;

    @FXML
    private TextField take_firstName;

    @FXML
    private ComboBox<?> take_gender;

    @FXML
    private ImageView take_image_view;

    @FXML
    private Label take_issuedDate;

    @FXML
    private TextField take_lastName;

    @FXML
    private Label take_publishedDateLabel;

    @FXML
    private Label take_studentIdLabel;

    @FXML
    private Button take_takeBtn;

    @FXML
    private TextField take_bookTitle;

    @FXML
    private Label take_titleLabel;

    @FXML
    private AnchorPane issue_form;

    @FXML
    private Button arrow_btn;

    @FXML
    private Button available_books_btn;

    @FXML
    private AnchorPane available_books_form;

    @FXML
    private AnchorPane nav_form;

    @FXML
    private ImageView available_books_img;

    @FXML
    private Label available_books_title;

    @FXML
    private Button bars_btn;

    @FXML
    private Circle circle_image;

    @FXML
    private Button close;

    @FXML
    private TableView<AvailableBooks> available_books_table_view;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_author;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_book_category;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_book_title;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_published_date;

    @FXML
    private Label currentForm_label;

    @FXML
    private Button edit_btn;

    @FXML
    private Button issue_book_btn;

    @FXML
    private Button minimize;

    @FXML
    private Button menu_btn;

    @FXML
    private AnchorPane main_centre_form;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    @FXML
    public ObservableList<AvailableBooks> dataList(){
        ObservableList<AvailableBooks> listBooks = FXCollections.observableArrayList();
        String sql = "SELECT * FROM book";

        connect = Database.connectDB();

        try{
            AvailableBooks aBooks;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                aBooks = new AvailableBooks(
                        result.getString("bookTitle"),
                        result.getString("author"),
                        result.getString("bookType"),
                        result.getString("image"),
                        result.getString("date")
                );
                listBooks.add(aBooks);
            }
        } catch (Exception e) {throw new RuntimeException(e);}
        return listBooks;
    }

    private ObservableList<AvailableBooks> listBook;

    public void showAvailableBooks(){
        listBook = dataList();
        col_ab_book_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_ab_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_ab_book_category.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col_ab_published_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        available_books_table_view.setItems(listBook);
    }

    public void selectAvailableBooks(){
        AvailableBooks bookData;
        bookData = available_books_table_view.getSelectionModel().getSelectedItem();
        int num = available_books_table_view.getSelectionModel().getFocusedIndex();

        if((num - 1) < -1) return ;

        available_books_title.setText(bookData.getTitle());

        String uri = "file:" + bookData.getImage();
        Image image = new Image(uri, 150, 180, false, true);
        available_books_img.setImage(image);

        getData.issuedBookTitle = bookData.getTitle();

        getData.savedTitle = bookData.getTitle();
        getData.savedAuthor = bookData.getAuthor();
        getData.savedGenre = bookData.getGenre();
        getData.savedImage = bookData.getImage();
        getData.savedDate = bookData.getDate();
        getData.savedStudentId = getData.studentId;
    }

    public void abTakeButton(ActionEvent event){
        if(event.getSource() == take_btn) issue_form.setVisible(true);
        available_books_form.setVisible(false);
        savedBook_form.setVisible(false);
        returnBook_form.setVisible(false);
    }

    public void navButtonDesign(ActionEvent event){
        if(event.getSource() == available_books_btn){

            available_books_form.setVisible(true);
            issue_form.setVisible(false);
            savedBook_form.setVisible(false);
            returnBook_form.setVisible(false);

            available_books_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, crimson, blue);" +
                    "-fx-text-fill: #fff;");
            issue_book_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");
            return_book_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");
            saved_books_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");

            currentForm_label.setText("Available Books");
            showAvailableBooks();
        }
        else if (event.getSource() == issue_book_btn) {
            available_books_form.setVisible(false);
            issue_form.setVisible(true);
            savedBook_form.setVisible(false);
            returnBook_form.setVisible(false);

            issue_book_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, crimson, blue);-fx-text-fill: #fff;");
            available_books_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");
            return_book_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");
            saved_books_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");

            currentForm_label.setText("Issue Books");
        }
        else if (event.getSource() == return_book_btn) {
            available_books_form.setVisible(false);
            issue_form.setVisible(false);
            savedBook_form.setVisible(false);
            returnBook_form.setVisible(true);

            return_book_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, crimson, blue);-fx-text-fill: #fff;");
            issue_book_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");
            available_books_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");
            saved_books_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");

            currentForm_label.setText("Return Books");

            showReturnBooks();
        }
        else if (event.getSource() == saved_books_btn){
            available_books_form.setVisible(false);
            issue_form.setVisible(false);
            savedBook_form.setVisible(true);
            returnBook_form.setVisible(false);

            saved_books_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, crimson, blue);-fx-text-fill: #fff;");
            issue_book_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");
            return_book_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");
            available_books_btn.setStyle("-fx-background-color: linear-gradient(to top left, #6666ff, #f2738c);");

            currentForm_label.setText("Saved Books");
            showSavedBooks();
        }
    }

    private final String[] comboBox = {"Male", "Female", "Others"};

    public void gender(){
        List<String> combo = new ArrayList<>();

        for(String data: comboBox) combo.add(data);

        ObservableList list = FXCollections.observableList(combo);

        take_gender.setItems(list);
    }

    public void findBook(ActionEvent event) {
        String sql = "SELECT * FROM book WHERE bookTitle='" + take_bookTitle.getText() + "'";

        connect = Database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Alert alert;
            boolean check = false;


            if(take_titleLabel.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the book");
            }
            else{
                while (result.next()){
                    take_titleLabel.setText(result.getString("bookTitle"));
                    take_authorLabel.setText(result.getString("author"));
                    take_GenreLabel.setText(result.getString("bookType"));
                    take_publishedDateLabel.setText(result.getString("date"));

                    getData.path = result.getString("image");
                    String uri = "file:" + getData.path;

                    Image image = new Image(uri, 130, 180, false, true);
                    take_image_view.setImage(image);

                    check = true;
                }

                if(!check) {
                    take_titleLabel.setText("Book is not available");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearTakeData(){
        take_bookTitle.setText("");
        take_titleLabel.setText("");
        take_authorLabel.setText("");
        take_GenreLabel.setText("");
        take_publishedDateLabel.setText("");
        take_image_view.setImage(null);
    }

    public void clearFindData() {
        take_titleLabel.setText("");
        take_authorLabel.setText("");
        take_GenreLabel.setText("");
        take_publishedDateLabel.setText("");
        take_image_view.setImage(null);
    }

    public void displayDate() {
        SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = customFormat.format(new java.util.Date());

        take_issuedDate.setText(date);
    }

    public void insertImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image file", "*png", "*jpg"));
        Stage stage = (Stage) nav_form.getScene().getWindow();

        File file = open.showOpenDialog(stage);

        if(file != null){
            Image image = new Image(file.toURI().toString(), 100, 100, false, true);
            circle_image.setFill(new ImagePattern(image));

            getData.path = file.getAbsolutePath();
            changeProfile();
        }
    }

    public void designInsertImage() {
        edit_btn.setVisible(false);

        circle_image.setOnMouseEntered((MouseEvent event) -> {
            edit_btn.setVisible(true);
        });
        circle_image.setOnMouseExited((MouseEvent event) -> {
            edit_btn.setVisible(false);
        });
        edit_btn.setOnMouseEntered((MouseEvent event) -> {
            edit_btn.setVisible(true);
        });
        edit_btn.setOnMousePressed((MouseEvent event) -> {
            edit_btn.setVisible(true);
        });
        edit_btn.setOnMouseExited((MouseEvent event) -> {
            edit_btn.setVisible(false);
        });
    }

    public void changeProfile() {
        String uri = getData.path;

        String sql = "UPDATE student SET image = '" + uri + "' WHERE studentId= '" + getData.studentId + "'";

        connect = Database.connectDB();

        try{
            statement = connect.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showProfile()   {
        String  uri = getData.path;

        Image image = new Image(uri, 100, 100, false, true);
        circle_image.setFill(new ImagePattern(image));

    }

    public ObservableList<returnBook> returnBookFun(){
        ObservableList<returnBook> bookReturnData = FXCollections.observableArrayList();

        String check = "Not Return";

        String sql = "SELECT * FROM issuedBook WHERE checkReturn = '" + check +"' and studentId= '" + getData.studentId +"'";

        connect = Database.connectDB();

        try{
            returnBook rBook;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                rBook = new returnBook(
                        result.getString("bookTitle"),
                        result.getString("author"),
                        result.getString("genre"),
                        result.getString("image"),
                        result.getString("date")
                );
                bookReturnData.add(rBook);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bookReturnData;
    }

    public void returnBooks(){
        String sql = "UPDATE issuedBook SET checkReturn= 'Returned' WHERE bookTitle ='" + getData.issuedBookTitle + "'";

        connect = Database.connectDB();

        Alert alert;

        try{
            if(return_image_view.getImage() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Select the Book !");
                alert.showAndWait();
            }else {
                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Returned !");
                alert.showAndWait();

                showReturnBooks();
                return_image_view.setImage(null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showReturnBooks() {
        ObservableList<returnBook> returnData = returnBookFun();

        return_bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        return_bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        return_bookType.setCellValueFactory(new PropertyValueFactory<>("genre"));
        return_issue_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        return_tableView.setItems(returnData);
    }

    public void selectReturnBook() {
        returnBook rBook = return_tableView.getSelectionModel().getSelectedItem();
        int num = return_tableView.getSelectionModel().getFocusedIndex();

        if((num - 1) < - 1) return ;

        String uri = "file:" + rBook.getImage();

        Image image = new Image(uri, 150, 180, false, true);

        return_image_view.setImage(image);

        getData.issuedBookTitle = rBook.getTitle();
    }

    public ObservableList<saveBook> savedBooksData() {
        ObservableList<saveBook> listSaveData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM savedBook WHERE studentId= '" + getData.studentId +"'";
        connect = Database.connectDB();

        try{
            saveBook sBook;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                sBook = new saveBook(
                        result.getString("bookTitle"),
                        result.getString("author"),
                        result.getString("bookType"),
                        result.getString("image"),
                        result.getString("date")
                );
                listSaveData.add(sBook);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listSaveData;
    }

    private ObservableList<saveBook> sBookList;
    public void showSavedBooks(){

        sBookList = savedBooksData();

        try {
            savedBook_title.setCellValueFactory(new PropertyValueFactory<>("title"));
            savedBook_author.setCellValueFactory(new PropertyValueFactory<>("author"));
            savedBook_type.setCellValueFactory(new PropertyValueFactory<>("genre"));
            savedBook_publishedDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        savedBook_tableView.setItems(sBookList);
    }

    public void saveBooks(){
        String sql = "INSERT INTO savedBook VALUES(?,?,?,?,?,?)";
        connect = Database.connectDB();

        try{
            Alert alert;

            if(available_books_title.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the Book !");
                alert.showAndWait();
            }else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, getData.savedStudentId);
                prepare.setString(2, getData.savedTitle);
                prepare.setString(3, getData.savedAuthor);
                prepare.setString(4, getData.savedGenre);
                prepare.setString(5, getData.savedImage);
                prepare.setString(6, getData.savedDate);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Book Saved !");
                alert.showAndWait();

                showSavedBooks();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void selectSavedBooks(){
        saveBook sBook = savedBook_tableView.getSelectionModel().getSelectedItem();
        int num = savedBook_tableView.getSelectionModel().getFocusedIndex();

        if((num - 1) < -1) return;

        String uri = "file:" + sBook.getImage();

        Image image = new Image(uri, 150, 180, false, true);
        savedBook_imgView.setImage(image);

        getData.savedImage = sBook.getImage();
        getData.savedTitle = sBook.getTitle();
    }

    public void unsaveBooks() {
        String sql = "DELETE from savedBook WHERE bookTitle= '" + getData.savedTitle + "' " + "and image= '" + getData.savedImage + "'";
        connect = Database.connectDB();

        try{
            Alert alert;

            if(savedBook_imgView.getImage() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Select the Book");
                alert.showAndWait();
            }else {
                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully unsaved");
                alert.showAndWait();

                showSavedBooks();
                savedBook_imgView.setImage(null);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void takeBook(){
        String sql = "INSERT INTO issuedBook VALUES(?,?,?,?,?,?,?,?,?,?)";
        connect = Database.connectDB();

        try {
            Alert alert;

            if(take_firstName.getText().isEmpty() ||
                    take_lastName.getText().isEmpty() ||
                    take_gender.getSelectionModel().isEmpty()
            ){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill Empty fields !");
                alert.showAndWait();
            } else if (take_titleLabel.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please indicate the book !");
                alert.showAndWait();
            }else {
                String check = "Not Return";
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, take_studentIdLabel.getText());
                prepare.setString(2, take_firstName.getText());
                prepare.setString(3, take_lastName.getText());
                prepare.setString(4, (String) take_gender.getSelectionModel().getSelectedItem());
                prepare.setString(5, take_titleLabel.getText());
                prepare.setString(6, getData.path);
                prepare.setString(7, take_issuedDate.getText());
                prepare.setString(8, check);
                prepare.setString(9, take_authorLabel.getText());
                prepare.setString(10, take_GenreLabel.getText());

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully taken the book");
                alert.showAndWait();
                connect.close();

                clearTakeData();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void studentId(){
        studentId_label.setText(getData.studentId);
        take_studentIdLabel.setText(getData.studentId);
    }

    private double x = 0;
    private double y = 0;

    public void sliderArrow(){
        TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(-213);

        slide.setOnFinished((ActionEvent event) -> {
            arrow_btn.setVisible(false);
            bars_btn.setVisible(true);
        });

        slide.play();
    }
    public void sliderMenu(){
        TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(0);

        slide.setOnFinished((ActionEvent event) -> {
            arrow_btn.setVisible(true);
            bars_btn.setVisible(false);
        });
        slide.play();
    }

    @FXML
    public void logout(ActionEvent event) {
        try {

            if(event.getSource() == signout_btn){
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXMLDocument.fxml")));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {
                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

                signout_btn.getScene().getWindow().hide();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void minimize(){
        Stage stage = (Stage)minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    public void close(){
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showReturnBooks();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        showAvailableBooks();
        studentId();
        gender();
        displayDate();
        showSavedBooks();
        designInsertImage();
//        showProfile();
    }
}
