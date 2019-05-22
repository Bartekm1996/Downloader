
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import java.net.*;
import java.util.*;
import javafx.scene.media.MediaPlayer;
import java.util.stream.Collectors;

public class Main extends Application {


    private String fileName;

    private void setFileName(String fileName){
        this.fileName = fileName;
    }

    private String getFileName() {
        return this.fileName;
    }

    private ObservableList<Group> group;
    private Hashtable<Group, ArrayList<Channel>> groups;

    private TableView<Group> tableView = new TableView<>();
    private TableView<Channel> channelView = new TableView<>();

    public static void main(String args[]){
        launch(args);

    }

    public void start(Stage primaryStage)throws Exception{
        BorderPane pane = new BorderPane();
                   pane.setCenter(downloaderPane(primaryStage));
                   pane.setPadding(new Insets(10));
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }


    private Button fileOpenerPane(Stage mainStage){


        FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Choose file to Open");

        Button openButton  = new Button("Open File");
               openButton.setOnAction(event -> {
                   File selectedFile = fileChooser.showOpenDialog(mainStage);

                   groups = sortChannels(selectedFile);
                   group = FXCollections.observableArrayList(groups.keySet().stream().collect(Collectors.toList()));
                   tableView.setItems(group);

               });

        return openButton;
    }

    private void tableViewPane(){

        TableColumn<Group, String> columnName = new TableColumn<>("Group Name");
        columnName.setCellValueFactory(new PropertyValueFactory<>("groupName"));

        TableColumn<Group, String> columnSize = new TableColumn<>("Last Name");
        columnSize.setCellValueFactory(new PropertyValueFactory<>("groupSize"));
        tableView.getColumns().addAll(columnName,columnSize);



        TableColumn<Channel, String> channelName = new TableColumn<>("Channel Name");
        channelName.setCellValueFactory(new PropertyValueFactory<>("channelName"));

        TableColumn<Channel, String> channelId = new TableColumn<>("Channel Id");
        channelId.setCellValueFactory(new PropertyValueFactory<>("channelId"));
        channelView.getColumns().addAll(channelName,channelId);

        tableView.setItems(group);

        tableView.setOnMouseClicked(event2 -> {
            channelView.setItems(FXCollections.observableArrayList(groups.get(tableView.getSelectionModel().getSelectedItem())));
        });

    }


    private void saveFile(File fileToSave){

        try{

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(fileToSave.getPath() + ".m3u")));

            Set<Group> keys = groups.keySet();

            for(Group group : keys){
                for(int i = 0; i < groups.get(group).size(); i++){
                    Channel channel = groups.get(group).get(i);
                    bufferedWriter.write("#EXTINF:-1 " + "tvg-id=" + "\"" + channel.getChannelId() + "\"" + " tvg-name=" + "\" " + channel.getChannelTvgName() + "\" " + "tvg-logo=" + "\"" + channel.getChannelPhoto() + "\" " + "group-title=" + "\"" + channel.getChannelGroup()
                                         + "\"" + "," + channel.getChannelName() + "\n" + channel.getChannelLink());
                }
            }

            bufferedWriter.close();



        }catch(IOException exception){
            exception.printStackTrace();
        }





    }


    private GridPane downloaderPane(Stage stage){

        GridPane gridPane = new GridPane();
                 gridPane.setVgap(10);
                 gridPane.setVgap(10);

        TextField linkField = new TextField();
                  linkField.setPromptText("Enter playlist url to download mu3 file");

                  Button downloadButton = new Button("Download");
                  DirectoryChooser fileChooser = new DirectoryChooser();

        TextField downloadLocation = new TextField();
                  downloadLocation.setPromptText("Enter the download location");
                  downloadLocation.setOnMouseClicked(event -> {
                      try {

                          File fileSelected = fileChooser.showDialog(stage);
                          downloadLocation.setText(fileSelected.getAbsolutePath());

                      }catch (NullPointerException exception){

                      }

                  });


                  TextField nameOfFile = new TextField();
                            nameOfFile.setPromptText("Enter name of playlist or leave blank");
                            nameOfFile.setMinWidth(200);








            downloadButton.setOnAction(event -> {

                        try {

                            this.setFileName("");
                            if (nameOfFile.getText().isEmpty()) {
                                this.setFileName(downloadLocation.getText() + "/playlist.m3u");
                            } else this.setFileName(downloadLocation.getText() + "/" + nameOfFile.getText() + ".m3u");

                            File downloadedFile = new File(this.getFileName());


                            ReadableByteChannel byteChannel = Channels.newChannel(new URL(linkField.getText()).openStream());
                            FileOutputStream outputStream = new FileOutputStream(downloadedFile.getPath());
                            FileChannel writeChannel = outputStream.getChannel();
                            writeChannel.transferFrom(byteChannel, 0, Long.MAX_VALUE);
                            writeChannel.close();


                            if (downloadedFile.exists()) {
                                messagePane("File Downloaded Successfully", "green");
                            } else messagePane("File to download file", "red");

                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }

                         groups = sortChannels(new File(this.getFileName()));
                         group = FXCollections.observableArrayList(groups.keySet().stream().collect(Collectors.toList()));
                         tableView.setItems(group);

                        channelView.setOnMouseClicked(event2 -> {
                            String link = "";
                            if (!channelView.getSelectionModel().isEmpty()) {
                                link = channelView.getSelectionModel().getSelectedItem().getChannelLink();
                                System.out.println(link);
                                if (!link.isEmpty()) {
                                    try {
                                        Stage playerStage = new Stage();
                                        MediaPlayer player = new MediaPlayer(new Media(new File(link).toURI().toURL().toExternalForm()));
                                        BorderPane playerPane = new BorderPane();
                                        playerPane.setCenter(new MediaView(player));
                                        playerStage.setScene(new Scene(playerPane));
                                        playerStage.show();
                                    } catch (MalformedURLException exception) {
                                        exception.printStackTrace();
                                    }
                                }else System.out.println("Empty link");
                            }
                        });
                    }

            );

            Button openButton = fileOpenerPane(stage);
            Button saveButton = new Button("Save");
                   saveButton.setOnAction(event -> {

                       FileChooser selectFileToSave = new FileChooser();
                       File file  = selectFileToSave.showSaveDialog(stage);
                       if(file != null){
                           saveFile(file);
                       }

                   });
            GridPane.setHalignment(openButton,HPos.RIGHT);
            GridPane.setHalignment(saveButton,HPos.RIGHT);
                tableViewPane();

                  gridPane.add(linkField,0,0,2,1);
                  gridPane.add(downloadLocation,0,1,2,1);
                  gridPane.add(nameOfFile,0,2,2,1);
                  gridPane.add(downloadButton,1,3);
                  gridPane.add(openButton,3,3);
                  gridPane.add(saveButton,3,2);
                  gridPane.add(tableView,0,4,2,2);
                  gridPane.add(channelView,3,4,2,2);
                  GridPane.setHalignment(downloadButton, HPos.RIGHT);



        return gridPane;
    }

    private void messagePane(String cause,String color){

        Stage stage = new Stage();
              stage.initStyle(StageStyle.UNDECORATED);
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        Label infoLabel = new Label(cause);
        Button closeButton = new Button("Ok");

        infoLabel.setStyle("-fx-text-fill:" + color + ";-fx-background-color: grey");
        closeButton.setOnAction(event -> stage.close());

        gridPane.setVgap(10);
        gridPane.add(infoLabel,0,0,2,1);
        GridPane.setHalignment(closeButton,HPos.RIGHT);
        gridPane.add(closeButton,1,1);

        stage.setScene(new Scene(gridPane));
        stage.show();
    }

    private Channel sortChannelsToGroups(String readLine,int channelNumber){

        String channelId = readLine.split("tvg-id=")[1].split(" ")[0];
               channelId = channelId.substring(1,channelId.length()-1);
        String channelName = readLine.split(",")[1].split("http")[0];
        String channelGroup = readLine.split("group-title=")[1].split(",")[0];
               channelGroup = channelGroup.substring(1,channelGroup.length()-1);
        String channelPhoto = readLine.split("tvg-logo=")[1].split(" ")[0];
               channelPhoto = channelPhoto.substring(1,channelPhoto.length()-1);
        String channelLink = readLine.split("http")[0];
        String channelTvgName = readLine.split("tvg-name=")[1].split("tvg-logo")[0];
               channelTvgName = channelTvgName.substring(1,channelTvgName.length()-2);

        return new Channel(channelGroup,channelName,channelId,channelPhoto,channelLink,channelTvgName,channelNumber);
    }



    private List<String> groupsInFile(File fileToRead){

        List<String> groups = new ArrayList<>();
        String thisLine = "";
        try {

            BufferedReader br = new BufferedReader(new FileReader(fileToRead));

            while ((thisLine = br.readLine()) != null) {
                if(thisLine.contains("group-title=")){
                    String splitLine = thisLine.split("group-title=")[1].split(",")[0];
                    if(!groups.contains(splitLine.substring(1,splitLine.length()-1)))groups.add(splitLine.substring(1,splitLine.length()-1));

                }
            }

            br.close();

        }catch (IOException exception){
            exception.printStackTrace();
        }

        return groups;
    }

    private Hashtable<Group,ArrayList<Channel>> sortChannels(File fileToRead){
        List<String> groups = groupsInFile(fileToRead);

        Hashtable <Group, ArrayList < Channel >> channelsSorted = new Hashtable<>();
        int channelNumber = 1;

        for(String group : groups){
            channelsSorted.put(new Group(group,Integer.toString(1)),new ArrayList<>());
        }

        Set<Group> key = channelsSorted.keySet();



            String thisLine = "",groupName = "",splitLine = "",channelLine = "";
            try {

                BufferedReader br = new BufferedReader(new FileReader(fileToRead));
                //BufferedWriter wr = new BufferedWriter(new FileWriter("/Users/bartek/Desktop/output.txt"));

                while ((thisLine = br.readLine()) != null){
                    if (thisLine.contains("group-title=")) {
                        groupName = splitLine;
                        splitLine = thisLine.split("group-title=")[1].split(",")[0];
                        if (!groupName.equals(splitLine)) {
                            for (Group group : key) {
                                if (group.getGroupName().equals(splitLine.substring(1, splitLine.length() - 1))) {
                                    group.setGroupSize(Integer.toString(channelNumber));
                                }
                            }
                            channelNumber = 1;
                        }else{
                            for(Group group : key){
                                if(group.getGroupName().equals(splitLine.substring(1,splitLine.length()-1))){
                                    channelsSorted.get(group).add(sortChannelsToGroups(thisLine, channelNumber));
                                }
                            }
                        }

                    }

                    channelNumber++;
                }

                br.close();
            }catch (IOException exception){
                exception.printStackTrace();
            }

            return channelsSorted;
    }



}
