/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import db.DBconnector;

/**
 * FXML Controller class
 *
 * @author Basel, Duy, Jacob, Ismail
 */
public class UserViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private ObservableList<ObservableList> data;

    @FXML
    private TableView<ObservableList> tableUser;
    
    Connection c = DBconnector.connect();

    private String selectedItem;
    private String sql = "select CAR_BRAND as Brand, CAR_YEAR as Year, CAR_COLOR as Color, " +
                                "CAR_FUEL_EFFICIENCY as \"Fuel Eff\", CAR_BODYSTYLE as Bodystyle, CAR_TRANSMISSION as Transmission, "+
                                "CAR_ENGINE as Engine, CAR_TRIM as Trim, CAR_RENT as Cost " +
                            "from CAR " +
                            "where CAR_AVAILABILITY = 1";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColumn();
        refreshTable();
        tableUser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println(tableUser.getSelectionModel().getSelectedItem().get(0));
            }
        });
    }    
    
    public void refreshTable(){
        data = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.createStatement().executeQuery(sql);
            
            while (rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }

                System.out.println("Row added "+ row);
                data.add(row);
            }

            tableUser.setItems(data);
            System.out.println("insertion completed");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setColumn(){
        try{
            ResultSet rs = c.createStatement().executeQuery(sql);
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {    
                        if (param.getValue().get(j) == null)
                            return new SimpleObjectProperty("");
                        else                                                                     
                            return new SimpleStringProperty(param.getValue().get(j).toString());                      
                    }                    
                });  
                tableUser.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }   
}
