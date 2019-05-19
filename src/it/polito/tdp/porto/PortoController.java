package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	private Model model;
	
	public void setModel(Model model) {
		this.model = model;
		boxPrimo.getItems().addAll(model.getAuthor());
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	model.creaGrafo();
    	Author source = boxPrimo.getValue();
    	List<Author> coautori = model.trovaCoautori(source);
    	
    	txtResult.setText("Lista di co-autori:\n\n");
    	for (Author author : coautori)
    		txtResult.appendText(author+"\n");
    	
    	List<Author> nonCoautori = model.nonCoautori(coautori);
    	nonCoautori.remove(source);
    	boxSecondo.getItems().addAll(nonCoautori);
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	boxSecondo.getValue();
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
    }
    
}