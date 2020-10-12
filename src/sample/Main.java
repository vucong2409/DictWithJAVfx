package sample;

import com.gtranslate.Audio;
import com.gtranslate.Language;
import com.gtranslate.Translator;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setResizable(false);

        TextField txt1 = (TextField) root.lookup("#inputWord");
        Text txt = (Text) root.lookup("#word");
        ListView lv1 = (ListView) root.lookup("#lv1");
        File txtF = new File("./src/sample/words.txt");
        ArrayList<String> listWord = new ArrayList<>();
        Scanner scanner = new Scanner(txtF);

        while (scanner.hasNextLine()) {
            String temp = scanner.nextLine();
            listWord.add(temp);
            lv1.getItems().add(temp);
        }

        scanner.close();
        txt1.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    String temp = txt1.getText();
                    Scanner scanner1 = new Scanner(new File("./src/sample/words.txt"));
                    lv1.getItems().clear();
                    for (String s : listWord) {
                        if (s.length() > temp.length()) {
                            if (s.substring(0, temp.length()).equals(temp)) {
                                lv1.getItems().add(s);
                            }
                        }
                    }
                    scanner1.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Vl loi cmnr");
                }
            }
        });
        Button btn1 = (Button) root.lookup("#btn01");
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        btn1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Voice voice;

                voice = VoiceManager.getInstance().getVoice("kevin");
                if (lv1.getSelectionModel().getSelectedItem() != null) {
                    voice.allocate();

                    voice.speak((String) lv1.getSelectionModel().getSelectedItem());
                    voice.deallocate();
                }
            }
        });
        lv1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (lv1.getSelectionModel().getSelectedItem() != null) {
                    txt.setText((String) lv1.getSelectionModel().getSelectedItem());
                }
            }
        });
       primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
