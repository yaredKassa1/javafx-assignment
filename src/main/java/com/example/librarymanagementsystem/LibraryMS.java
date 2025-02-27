package com.example.librarymanagementsystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LibraryMS extends Application {
    private Scene scene1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage secondStage) {
        secondStage.setTitle("Library Management System");

        GridPane grid = new GridPane();
        grid.setId("grid");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: BEIGE;");

        Text sceneTitle = new Text("Login");
        sceneTitle.setFont(Font.font("Thoma", FontWeight.NORMAL, 30));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        PasswordField passwordBox = new PasswordField();
        grid.add(passwordBox, 1, 2);

        Button loginButton = new Button("As Student");
        loginButton.setId("myB");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 1, 4);

        Button loginButton1 = new Button("As Admin");
        loginButton1.setId("myB1");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(loginButton1);
        grid.add(hbBtn1, 0, 4);

        Button loginButton12 = new Button("SIGNUP");
        loginButton12.setId("myB12");
        HBox hbBtn11 = new HBox(10);
        hbBtn11.setAlignment(Pos.CENTER);
        hbBtn11.getChildren().add(loginButton12);
        grid.add(hbBtn11, 0, 5);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);

        loginButton12.setOnAction(e -> studentInfo());


        loginButton.setOnAction(e -> {
            String username = userTextField.getText().trim();
            String password = passwordBox.getText().trim();
            String id=JDBC.selectStudID(username);
            String id2=JDBC.studPassword(username,password);


            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Form Error", "Please enter both username and password.");
            } else if (String.valueOf(id).equals(username) && String.valueOf(id2).equals(password)) {
                actionTarget.setText("Login successful!");
                StudentStage();
                secondStage.close();
            }

            else {
                showAlert("Login Failed", "Incorrect username or password.");
            }
        });

        loginButton1.setOnAction(e -> {
            String username1 = userTextField.getText().trim();
            String password1 = passwordBox.getText().trim();

            String admin=JDBC.adminID(username1);
            String admin1=JDBC.adminPassword(username1,password1);


            if (username1.isEmpty() || password1.isEmpty()) {
                showAlert("Form Error", "Please enter both username and password.");
            }


            else if (String.valueOf(admin).equals(username1) && String.valueOf(admin1).equals(password1)) {

                actionTarget.setText("Login successful!");
                AdminStage();
                secondStage.close();
            }

            else {
                showAlert("Login Failed", "Incorrect username or password.");
            }
        });

        scene1 = new Scene(grid, 600, 500);
        scene1.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styling.css")).toExternalForm());
        secondStage.setScene(scene1);
        secondStage.show();

    }
    private void AdminStage() {
        Stage secondStage=new Stage();

        secondStage.setTitle("Second Stage");

        Button display = new Button("DISPLAY");
        Button Register = new Button("REGISTER");
        Button login = new Button("LOGIN");

        display.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        Register.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white;");
        login.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

        GridPane gp=new GridPane();
        gp.setId("root");
        gp.setHgap(3);
        gp.setVgap(3);
        gp.setPadding(new Insets(20,20,20,20));
        gp.add(display,0,0);

        gp.add(Register,2,0);
        gp.add(login,3,0);

        display.setOnAction(e ->{displayBooks();
            secondStage.close();
        } );

        Register.setOnAction(e -> {Edit();
            secondStage.close();
        });
        login.setOnAction(e ->
            start(secondStage));

        Scene scene = new Scene(gp, 600, 500);
        scene.setCursor(Cursor.HAND);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styling.css")).toExternalForm());
        secondStage.setScene(scene);
        secondStage.show();

    }

    private void StudentStage() {
        Stage secondStage=new Stage();

        secondStage.setTitle("Student Stage");

        Button display = new Button("DISPLAY");
        Button login = new Button("LOGIN");


        display.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        login.setStyle("-fx-background-color: blue; -fx-text-fill: white;");


        GridPane gp=new GridPane();
        gp.setId("root");
        gp.setHgap(3);
        gp.setVgap(3);
        gp.setPadding(new Insets(20,20,20,20));
        gp.add(display,0,0);
        gp.add(login,1,0);


        TextField editionField = new TextField();
        gp.add(editionField, 0, 1);

        Button search=new Button("Search");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(search);
        gp.add(hbBtn1,1,1);

        display.setOnAction(e ->{ displayBooks();
            secondStage.close();
        });

        login.setOnAction(e ->
                start(secondStage));

        search.setOnAction(e -> {
            String UpdateUserID = String.valueOf(editionField.getText());
            System.out.println("bookID: " + UpdateUserID);
            if (editionField.getText().isEmpty()) {
                showAlert("Form Error", "empty entity invalid.");
            } else {
                int rest = JDBC.Search(UpdateUserID);
                if (rest == 1) {

                    ObservableList<BookStore> BookStore= FXCollections.observableArrayList();
                    TableView<BookStore> table=new TableView<>();
                    TableColumn<BookStore,String> idCol=new TableColumn<>("BookID");
                    idCol.setPrefWidth(100);
                    idCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
                    TableColumn<BookStore,String> titleCol=new TableColumn<>("Title");
                    titleCol.setPrefWidth(100);
                    titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                    TableColumn<BookStore,String> AuthorCol=new TableColumn<>("Author");
                    AuthorCol.setPrefWidth(100);
                    AuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
                    TableColumn<BookStore,String> EditionCol=new TableColumn<>("Edition");
                    EditionCol.setPrefWidth(100);
                    EditionCol.setCellValueFactory(new PropertyValueFactory<>("edition"));
                    TableColumn<BookStore,String> PublisherCol=new TableColumn<>("Publisher");
                    PublisherCol.setPrefWidth(100);
                    PublisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
                    TableColumn<BookStore,String> PBcol=new TableColumn<>("PublicationDate");
                    PBcol.setPrefWidth(100);
                    PBcol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
                    TableColumn<BookStore,String> PBcol1=new TableColumn<>("File");
                    PBcol1.setPrefWidth(100);
                    PBcol1.setCellValueFactory(new PropertyValueFactory<>("file"));

                    Connection conn=JDBC.getConnection();


                    try {
                        String query="select * from books where BookID = '" + UpdateUserID + "'";
                        PreparedStatement pStatement = conn.prepareStatement(query);
                        ResultSet rs= pStatement.executeQuery();
                        while (rs.next()){
                            BookStore.add(new BookStore(rs.getInt("BookID"),rs.getString("Title"),rs.getString("Author"),rs.getInt("Edition"),rs.getString("Publisher"),rs.getString("PublicationDate"),rs.getBlob("File")));



                        }
                    }catch(Exception ex){
                        ex.fillInStackTrace();
                    }


                    table.setItems(BookStore);
                    table.getColumns().addAll(idCol,titleCol,AuthorCol,EditionCol,PublisherCol,PBcol);
                    gp.add(table,0,2);


                } else {
                    showAlert("Form Error", "there is no book registered with this bookId");
                }
            }
            secondStage.close();
        });

        Scene scene = new Scene(gp, 600, 500);
        scene.setCursor(Cursor.HAND);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styling.css")).toExternalForm());
        secondStage.setScene(scene);
        secondStage.show();

    }

    private void studentInfo() {
        Stage primaryStage=new Stage();
        Stage ps=new Stage();

        GridPane root = new GridPane();
        root.setId("root1");
        root.setVgap(2);
        root.setHgap(5);
        root.setPadding(new Insets(5,5,5,5));

        Label bookId=new Label("studentID");
        root.add(bookId, 0, 1);
        TextField userTextField = new TextField();
        root.add(userTextField, 1, 1);

        Label Title=new Label("studentName");
        root.add(Title, 0,2);
        TextField titleField = new TextField();
        root.add(titleField, 1, 2);

        Label author=new Label("password");
        root.add(author, 0,3);
        TextField authorField = new TextField();
        root.add(authorField, 1, 3);

        Label edition=new Label("Department");
        root.add(edition, 0,4);
        TextField editionField = new TextField();
        root.add(editionField, 1, 4);

        Label publisher=new Label("YearOfAcademy");
        root.add(publisher, 0,5);
        DatePicker publisherField = new DatePicker();
        root.add(publisherField, 1, 5);


        Button butn1=new Button("ADD");
        root.add(butn1,1,6);

        final Text actiontarget2 = new Text();
        root.add(actiontarget2, 1,7);
        actiontarget2.setId("actiontarget2");

        butn1.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent e) {
                String userTextField1 = String.valueOf(userTextField.getText());
                String titleField1 = String.valueOf(titleField.getText());
                String authorField1 = String.valueOf(authorField.getText());
                String editionField1 = String.valueOf(editionField.getText());
                String publisherField1 = String.valueOf(publisherField.getValue());
                System.out.println("studentID " + userTextField1 + " studentName "
                        + titleField1 + " Password " + authorField1 + " Department " + editionField1 + " YearOfAcademy "
                        + publisherField1);
                if ((userTextField.getText().isEmpty()) || (titleField.getText().isEmpty()) || (authorField.getText().isEmpty()) || (editionField.getText().isEmpty())) {
                    showAlert("Form Error", "Please enter the attributes.");
                } else {
                    int rest = JDBC.addStudents(userTextField1, titleField1, authorField1, editionField1, publisherField1);
                    if (rest == 0)
                        actiontarget2.setText("please insert valid input");

                    else {
                        try {

                            File file = new File("C:\\Users\\Yared\\IdeaProjects\\LibraryManagementSystem/StudInfo.txt");
                            // file.createNewFile();

                            FileWriter fw = new FileWriter(file, true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter out = new PrintWriter(bw, true);
                            out.println(" studentID     " + userTextField1);
                            out.println(" studentName   " + titleField1);
                            out.println(" Password      " + authorField1);
                            out.println(" Department    " + editionField1);
                            out.println(" YearOfAcademy " + publisherField1);
                            out.println();
                            out.println();

                            FileReader fr = new FileReader(file);
                            BufferedReader br = new BufferedReader(fr);
                            String str = br.readLine();
                            while (str != null) {
                                str = br.readLine();
                                System.out.println(str);
                            }

                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.fillInStackTrace();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        primaryStage.close();
                        start(ps);

                    }
                }
            }

        });


        Scene scene = new Scene(root, 600, 500);
        scene.setCursor(Cursor.HAND);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styling.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void Edit() {
        Stage primaryS=new Stage();
        Stage primaryStage=new Stage();

        GridPane root = new GridPane();
        root.setId("root1");
        root.setVgap(2);
        root.setHgap(5);
        root.setPadding(new Insets(5,5,5,5));

        Label bookId=new Label("BookID");
        root.add(bookId, 0, 1);
        TextField userTextField = new TextField();
        root.add(userTextField, 1, 1);

        Label Title=new Label("title");
        root.add(Title, 0,2);
        TextField titleField = new TextField();
        root.add(titleField, 1, 2);

        Label author=new Label("Author");
        root.add(author, 0,3);
        TextField authorField = new TextField();
        root.add(authorField, 1, 3);

        Label edition=new Label("Edition");
        root.add(edition, 0,4);
        TextField editionField = new TextField();
        root.add(editionField, 1, 4);

        Label publisher=new Label("Publisher");
        root.add(publisher, 0,5);
        TextField publisherField = new TextField();
        root.add(publisherField, 1, 5);

        Label publicationDate=new Label("PublicationDate");
        root.add(publicationDate, 0,6);
        DatePicker PBField = new DatePicker();
        root.add(PBField, 1, 6);
        TextField filepathField=new TextField();
        filepathField.setPromptText("File Path");
        root.add(filepathField,0,7);
        Button chooseFileButton=new Button("Choose File");
        root.add(chooseFileButton,1,7);
        chooseFileButton.setOnAction(e -> {

            FileChooser file=new FileChooser();
            file.setTitle("Choose book File");
            File file1=file.showOpenDialog(null);
            if(file1 !=null)
                filepathField.setText(file1.getAbsolutePath());
        });

        Button update=new Button("UPDATE");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(update);
        root.add(hbBtn1,0,8);

        Button delete = new Button("   Delete    ");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(delete);
        root.add(hbBtn, 1, 8);

        Button add = new Button("   ADD    ");
        HBox butn11 = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(add);
        root.add(butn11, 2, 8);

        Button back = new Button("   back to home    ");
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.BOTTOM_RIGHT);
        hb.getChildren().add(back);
        root.add(back, 1, 9);

        final Text actiontarget2 = new Text();
        root.add(actiontarget2, 1,10);
        actiontarget2.setId("action target2");

        back.setOnAction(e -> {AdminStage();
            primaryStage.close();
        });

        update.setOnAction(e -> {

            String userTextField1 = String.valueOf(userTextField.getText());
            String titleField1 = String.valueOf(titleField.getText());
            String authorField1 = String.valueOf(authorField.getText());
            String editionField1 = String.valueOf(editionField.getText());
            String publisherField1 = String.valueOf(publisherField.getText());
            String PBField1 = String.valueOf(PBField.getValue());
            String filepathField1 = String.valueOf(filepathField.getText());
            if ((userTextField.getText().isEmpty()) || (titleField.getText().isEmpty()) && (authorField.getText().isEmpty()) && (editionField.getText().isEmpty()) && (publisherField.getText().isEmpty())) {
                actiontarget2.setText("You have to fill the fields");
            }
            {
                int rest = JDBC.UpdateBooks(userTextField1, titleField1, authorField1, editionField1, publisherField1, PBField1,filepathField1);
                if (rest == 1) {
                    actiontarget2.setText("UPDATED SUCCESSFULLY ");


                } else {
                    actiontarget2.setText("INSERT VALID BOOK_ID");
                }
            }
            primaryStage.close();
        });

        add.setOnAction(e -> {

            String userTextField1 = String.valueOf(userTextField.getText());
            String titleField1 = String.valueOf(titleField.getText());
            String authorField1 = String.valueOf(authorField.getText());
            String editionField1 = String.valueOf(editionField.getText());
            String publisherField1 = String.valueOf(publisherField.getText());
            String PBField1 = String.valueOf(PBField.getValue());
            String filepathField1 = String.valueOf(filepathField.getText());
            System.out.println("bookID " + userTextField1 + " Title "
                    + titleField1 + " Author " + authorField1 +" Edition " + editionField1 + " publisher "+ publisherField1
                    + " publicationDate "   + PBField1);
            if ((userTextField.getText().isEmpty()) || (titleField.getText().isEmpty()) || (authorField.getText().isEmpty()) || (publisherField.getText().isEmpty()) || (editionField.getText().isEmpty())) {
                showAlert("Form Error", "Please enter valid attributes.");
            } else {
                int rest = JDBC.addBooks(userTextField1, titleField1, authorField1, editionField1, publisherField1, PBField1, filepathField1);
                if (rest == 0)
                    showAlert("Form Error", "Please enter valid id.");

                else {
                    actiontarget2.setText("ADDED SUCCESSFULLY");

                }
            }
            primaryStage.close();
        });

        delete.setOnAction(e -> {
            String deleteID = String.valueOf(userTextField.getText());
            System.out.println("bookID: " + deleteID);
            if (userTextField.getText().isEmpty()) {
                showAlert("Form Error", "empty entity invalid.");
            } else {
                int rest = JDBC.DeleteBooks(deleteID);
                if (rest == 1) {
                    actiontarget2.setText("bookID: " + deleteID);
                    System.out.println("DELETED SUCCESSFULLY");

                    try {
                        Thread.sleep(10000);
                        System.out.println("your time is up");
                    } catch (InterruptedException e1) {
                        e1.fillInStackTrace();
                    }
                    primaryStage.close();
                    start(primaryS);
                } else {
                    showAlert("Form Error", "Please enter valid book id.");
                }
            }
            primaryStage.close();
        });

        Scene scene = new Scene(root, 600, 500);
        scene.setCursor(Cursor.HAND);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styling.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayBooks() {

        Stage secondStage = new Stage();
        secondStage.setTitle("Second Stage");

        GridPane root=new GridPane();
        Button home = new Button("   back to home    ");
        HBox butn11 = new HBox(10);
        butn11.setAlignment(Pos.BOTTOM_RIGHT);
        butn11.getChildren().add(home);
        root.add(butn11, 0, 4);

        Button login = new Button("   back to login    ");
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(login);
        root.add(hbox, 0, 5);

        login.setOnAction(e ->
                start(secondStage));

        ObservableList<BookStore> BookStore= FXCollections.observableArrayList();
        TableView<BookStore> table=new TableView<>();
        TableColumn<BookStore,String> idCol=new TableColumn<>("BookID");
        idCol.setPrefWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        TableColumn<BookStore,String> titleCol=new TableColumn<>("Title");
        titleCol.setPrefWidth(100);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<BookStore,String> AuthorCol=new TableColumn<>("Author");
        AuthorCol.setPrefWidth(100);
        AuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<BookStore,String> EditionCol=new TableColumn<>("Edition");
        EditionCol.setPrefWidth(100);
        EditionCol.setCellValueFactory(new PropertyValueFactory<>("edition"));
        TableColumn<BookStore,String> PublisherCol=new TableColumn<>("Publisher");
        PublisherCol.setPrefWidth(100);
        PublisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        TableColumn<BookStore,String> PBcol=new TableColumn<>("PublicationDate");
        PBcol.setPrefWidth(100);
        PBcol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        TableColumn<BookStore,String> PBcol1=new TableColumn<>("File");
        PBcol1.setPrefWidth(100);
        PBcol1.setCellValueFactory(new PropertyValueFactory<>("file"));

        Connection conn=JDBC.getConnection();


        try {
            String query="select * from books";
            PreparedStatement pStatement = conn.prepareStatement(query);
            ResultSet rs= pStatement.executeQuery();
            while (rs.next()){
                BookStore.add(new BookStore(rs.getInt("BookID"),rs.getString("Title"),rs.getString("Author"),rs.getInt("Edition"),rs.getString("Publisher"),rs.getString("PublicationDate"),rs.getBlob("File")));


            }
        }catch(Exception e){
            e.fillInStackTrace();
        }

        table.setItems(BookStore);
        table.getColumns().addAll(idCol,titleCol,AuthorCol,EditionCol,PublisherCol,PBcol);

          root.add(table,0,2);
        home.setOnAction(e -> {AdminStage();
            secondStage.close();
        });

        scene1 = new Scene(root, 600, 500);
        scene1.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styling.css")).toExternalForm());

        secondStage.setScene(scene1);
        secondStage.show();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
