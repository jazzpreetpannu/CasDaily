/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmltest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author jazzpreet
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private CheckBox chck;
    private DatePicker dp;
    @FXML
    private Button regBtn,modBtn,ClearAll;
    @FXML
    private TableView<Resulti> resultId ;
    @FXML
    private TableView<Resulti> osdId ;
    @FXML
    private TableView<Resulti> errorId;
    @FXML
    private TableView<Resulti> livSubId;
    @FXML
    private TableView<Resulti> misId ;
    @FXML
    private TableView<Resulti> offlineSubId;
    @FXML 
    private final TableColumn comand= new TableColumn("Comand");
    @FXML 
    private final TableColumn osd= new TableColumn("Comand");
    @FXML 
    private final TableColumn error= new TableColumn("Error");;
    @FXML 
    private final TableColumn subsciber= new TableColumn("Live Subsciber");;
    @FXML 
    private final TableColumn off_subscriber= new TableColumn("OffLine Subscriber");;
    @FXML 
    private final TableColumn mis = new TableColumn("  ");;
    //private final TableColumn comand;
    @FXML
    private final TableColumn count = new TableColumn("Count");
    @FXML
    private final TableColumn count_osd = new TableColumn("Count");
    @FXML
    private final TableColumn count_err = new TableColumn("Count");
    @FXML
    private final TableColumn count_sub = new TableColumn("Count");
    @FXML
    private final TableColumn count_offsub = new TableColumn("Count");
    @FXML
    private final TableColumn count_mis = new TableColumn("Count");
    @FXML
    DatePicker datepick = new DatePicker();
    Map<String,Integer> db = new HashMap<>();
    
    private final ObservableList<Resulti> data_gen = FXCollections.observableArrayList();
    private final ObservableList<Resulti> data_osd = FXCollections.observableArrayList();
    private final ObservableList<Resulti> data_err = FXCollections.observableArrayList();
    private final ObservableList<Resulti> data_livSub = FXCollections.observableArrayList();
    private final ObservableList<Resulti> data_offSub = FXCollections.observableArrayList();
    private final ObservableList<Resulti> data_mis = FXCollections.observableArrayList();
    
    XSSFWorkbook errorxls;
    XSSFSheet sheet;
    Resulti entry;
    LocalDate ld = LocalDate.now() ;
    //String datee = ld.minusDays(1).toString();
     
    //        Month month = ld.getMonth();
    //  String datee1 = month;
    @FXML
    private Label helo;
    //private Label chck;
    //private DatePicker datee = new DatePicker();
    //private Object datee;

    public FXMLDocumentController() {
        //this.comand = new TableColumn("comand");
    }
        
    /**
     *
     * @param event
     */
    public void handledateButton(Event event) 
    {
        
        ld = datepick.getValue();
        System.out.println(ld);    
    }
    
    
    public void handleImportReport(ActionEvent event) 
    {
        try{
            Object source = event.getSource();
        Button clicked = (Button) source;
        //System.out.println(clicked.getId());
        String str_loc,str_default;
        final String id = clicked.getId();
        switch(id)
        {
            case "frm_loc":{
                FileChooser filechoose = new FileChooser();
                File file1= filechoose.showOpenDialog(new Stage());
                //ld = datepick.getValue();
                //System.out.println(ld);
                str_loc = file1.getAbsolutePath();
                    if(str_loc.contains("sub_count"))
                        bring(str_loc,1);
                    else
                        bring(str_loc);
                break;}
            case "frm_def":{
                
                str_loc = "D:\\Daily Reports\\";
                    if(str_loc.contains("sub_count"))
                        bring(str_loc,1);
                    else
                        bring(str_loc);
                break;
                }
                case "ClearAll":
                    {
                        db.clear();
                
                        data_gen.clear();
                        data_osd.clear();
                        data_offSub.clear();
                        data_mis.clear();
                        data_livSub.clear();
                        data_err.removeAll();
                        data_err.clear();
                    }
            
        }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getStackTrace(),"alert" , JOptionPane.ERROR_MESSAGE);     
        }
        
    }
   
    public void handleReadButton(ActionEvent event) 
    {
        Object source = event.getSource();
        Button clicked = (Button) source;
        //System.out.println(clicked.getId());
        
        String id = clicked.getId();
        switch(id)
        {
            case "modBtn_gen":{
                //System.out.println("modGen");
                data_gen.clear();
                readWrite("Complete Daily Report.xlsx","Complete Daily Report",(HashMap<String, Integer>) db,data_gen,0);
                break;}
            case "modBtn_osd":{
                //System.out.println("writeOsd");
                data_osd.clear();
                readWrite("Complete Daily Report.xlsx","OSD BIT set & unset",(HashMap<String, Integer>) db,data_osd,0);
                break;}
            case "modBtn_err":{
                //System.out.println("ModErr");
                data_err.clear();
                readWrite("Errorcount_details.xlsx","Error",(HashMap<String, Integer>) db,data_err,0);
                break;}
            case "modBtn_livsub":{
                //System.out.println("ModLive");
                data_livSub.clear();
                readWrite("Subscriber Count.xlsx","Subscriber count",(HashMap<String, Integer>) db,data_livSub,0);
                break;}
            case "modBtn_offsub":{
                //System.out.println("ModOffline");
                data_offSub.clear();
                readWrite("Subscriber Count.xlsx","Offline Sub Count",(HashMap<String, Integer>) db,data_offSub,0);
                break;}
            case "modBtn_mis":{
                //System.out.println("ModMIS");
                data_mis.clear();
                readWrite("Complete Daily Report.xlsx","MIS",(HashMap<String, Integer>) db,data_mis,0);
                break;}
        }
         
    }
    
    public void handleWriteButton(ActionEvent event) 
    {
        Object source = event.getSource();
        //if(source instanceof Button)
        
            Button clicked = (Button) source;
            //System.out.println(clicked.getId());
        
        String id = clicked.getId();
        switch(id)
        {
            case "writeBtn_gen":{
                //System.out.println("modGen");
                readWrite("Complete Daily Report.xlsx","Complete Daily Report",(HashMap<String, Integer>) db,data_gen,1);
                break;}
            case "writeBtn_osd":{
                //System.out.println("writeOsd");
                readWrite("Complete Daily Report.xlsx","OSD BIT set & unset",(HashMap<String, Integer>) db,data_osd,1);
                break;}
            case "writeBtn_err":{
                //System.out.println("ModErr");
                readWrite("Errorcount_details.xlsx","Error",(HashMap<String, Integer>) db,data_err,1);
                break;}
            case "writeBtn_livsub":{
                //System.out.println("ModLive");
                readWrite("Subscriber Count.xlsx","Subscriber count",(HashMap<String, Integer>) db,data_livSub,1);
                break;}
            case "writeBtn_offsub":{
                //System.out.println("ModOffline");
                readWrite("Subscriber Count.xlsx","Offline Sub Count",(HashMap<String, Integer>) db,data_offSub,1);
                break;}
            case "writeBtn_mis":{
                //System.out.println("ModMIS");
                readWrite("Complete Daily Report.xlsx","MIS",(HashMap<String, Integer>) db,data_mis,1);
                break;}
        }
     
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //resultId = new TableView<Resulti>();
        //resultId.getColumns().addAll(Resulti.getColumn(resultId));
        //
        comand.setCellValueFactory(new PropertyValueFactory<>("comand"));
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        osd.setCellValueFactory(new PropertyValueFactory<>("comand"));
        count_osd.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        error.setCellValueFactory(new PropertyValueFactory<Resulti, String>("comand"));
        count_err.setCellValueFactory(new PropertyValueFactory<Resulti, Integer>("count"));
        
        subsciber.setCellValueFactory(new PropertyValueFactory<Resulti, String>("comand"));
        count_sub.setCellValueFactory(new PropertyValueFactory<Resulti, Integer>("count"));
        
        off_subscriber.setCellValueFactory(new PropertyValueFactory<Resulti, String>("comand"));
        count_offsub.setCellValueFactory(new PropertyValueFactory<Resulti, Integer>("count"));
        
        mis.setCellValueFactory(new PropertyValueFactory<Resulti, String>("comand"));
        count_mis.setCellValueFactory(new PropertyValueFactory<Resulti, Integer>("count"));
        
        resultId.getColumns().setAll(comand,count);
        resultId.setItems(data_gen); 
        
        osdId.getColumns().setAll(osd,count_osd);
        osdId.setItems(data_osd);
        
        errorId.getColumns().setAll(error,count_err);
        errorId.setItems(data_err); 
        
        livSubId.getColumns().setAll(subsciber,count_sub);
        livSubId.setItems(data_livSub); 
        
        offlineSubId.getColumns().setAll(off_subscriber,count_offsub);
        offlineSubId.setItems(data_offSub); 
        
        misId.getColumns().setAll(mis,count_mis);
        misId.setItems(data_mis); 
        
        DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("MMM");
        ld.minusDays(1);
        datepick.setValue(ld.minusDays(1));
        // TODO
    }    
    public void bring(String file_str)
    {   
        try{
        FileReader file = new FileReader(file_str);
        BufferedReader buf = new BufferedReader(file);
        Reader filw;
        int check = 1,etc=0;
        while(check==1)
            {
              String st = buf.readLine();
              if((st==null))
                  check++;
              else if(st.isEmpty())
              {}
              
              else
                { 
                    int data1 = Integer.parseInt(buf.readLine());
                    etc++;
                    
                    db.put(st.toUpperCase(), data1);

                }
            }
        //System.out.println(helo);
        if(db.containsKey("OSD BIT")&db.containsKey("OSD BIT UNSET"))
        db.putIfAbsent("OSD BIT SET", db.get("OSD BIT")-db.get("OSD BIT UNSET"));
        if(db.containsKey("HEAVY REFRESH")&db.containsKey("UNIQUE HEAVY REFRESH"))
        db.putIfAbsent("DUPLICATE HEAVY REFRESH",db.get("HEAVY REFRESH")-db.get("UNIQUE HEAVY REFRESH"));
        if(db.containsKey("ACCOUNT BALANCE")&db.containsKey("UNIQUE ACCOUNT BALANCE"))
        db.putIfAbsent("DUPLICATE ACCOUNT BALANCE",db.get("ACCOUNT BALANCE")-db.get("UNIQUE ACCOUNT BALANCE"));
        //System.out.println(db.toString());
        file.close();
        } catch (Exception e) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, e);
        //}//catch(Exception e)
        //{
            //System.out.println("File not found"+e);
            JOptionPane.showMessageDialog(null,e,"alert" , JOptionPane.ERROR_MESSAGE);     
        }
    }

    /**
     *
     * @param file_str
     * @param i
     */
    public void bring(String file_str, int i) 
    {
        try{
        FileReader file = new FileReader(file_str);
        BufferedReader buf = new BufferedReader(file);
        int check = 1,etc=0;
        while(i==1)
            {
                String st = buf.readLine();
                
                if((st==null))
                  i++;
              else if(st.isEmpty())
              {}
              
              else
                { 
                    buf.readLine();
                    String s=buf.readLine().replaceAll(" ", "");
                int data1 = Integer.parseInt(s);
                etc++;
                //System.out.println(st);
                //System.out.println(data1);
                String s1=st.replaceAll(" ", "");
                db.put(s1.toUpperCase(), data1);
                    
                }
            }
        
        file.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e,"alert" , JOptionPane.ERROR_MESSAGE);     
        }
        
    }
    
    public void script(String file_str)
    {   
        try{
        FileReader file = new FileReader(file_str);
        BufferedReader buf = new BufferedReader(file);
        PrintWriter filw = new PrintWriter(new File("script.txt"));
        int check = 1, etc=0;
        while(check==1)
            {
              String st = buf.readLine();
              //entry.comand.set(st);
              //System.out.println(st);
              if((st==null))
                  check++;
              else if(st.isEmpty())
              {}
                else
                { 
                    filw.println("open sftp://root:"+buf.readLine()+"@"+st+":22");
                    filw.println("cd "+buf.readLine());
                    filw.println("lcd d:/daily_data");
                    filw.println("get "+buf.readLine());
                    filw.println("close");

                }
              
            }
        filw.println("exit");
        file.close();
        filw.close();
        }catch(Exception e)
        {
            
            JOptionPane.showMessageDialog(null,e,"alert" , JOptionPane.ERROR_MESSAGE);       
        }
    }
    
    /**
     *
     * @param book
     * @param shet
     * @param db
     * @param check
     */
    public void readWrite(String book, String shet, HashMap<String, Integer> db,ObservableList<Resulti> data, int check)
    {   
        try{
        if(db.isEmpty())
        {
            //System.out.println(db.toString());
        }
        else
        {
        XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream(book));
        XSSFSheet sheet = workbook.getSheet(shet);
        CellStyle st_data = workbook.createCellStyle();
        CellStyle st_date = workbook.createCellStyle();
        CellStyle st_copy = workbook.createCellStyle();
        
        st_data.setBorderBottom(CellStyle.BORDER_THIN);
        st_data.setBorderLeft(CellStyle.BORDER_THIN);
        st_data.setBorderRight(CellStyle.BORDER_THIN);
        st_data.setBorderTop(CellStyle.BORDER_THIN);
        st_data.setAlignment(CellStyle.ALIGN_CENTER);
        st_data.setFillForegroundColor(IndexedColors.YELLOW.index);
        
            int last_row = sheet.getLastRowNum()+1;
            int last_col = sheet.getRow(0).getLastCellNum();
            int small, large,out_data;
            
            //ld = datepick.getValue();
                System.out.println(ld); 
                
            int date_check = 2;
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MMM-YY");
            String str_date = ld.format(df);
            
            if(last_row<last_col)
            {
                small=last_row;
                large = last_col;
                
                if(check==1)
                {
                    String chk_date = sheet.getRow(0).getCell(large-1).getStringCellValue();
                    System.out.println(chk_date);
                    if(str_date.equalsIgnoreCase(chk_date))
                    {
                        date_check = JOptionPane.showConfirmDialog(null,"Values with Date: "+str_date+" already exist. \nWant to Overide the value??", "Warning", JOptionPane.YES_NO_OPTION);
                        if(date_check==0)
                        {

                            sheet.getRow(0).getCell(large-1).setCellValue(str_date);
                        }
                        if(date_check==1)
                        {
                           check=2;
                        }
                    }
                    else
                    {
                        st_copy = sheet.getRow(0).getCell(large-2).getCellStyle();
                            st_date.cloneStyleFrom(st_copy);
                        ////System.out.println(s);
                        sheet.getRow(0).createCell(large).setCellStyle(st_date);
                        sheet.getRow(0).getCell(large).setCellValue(str_date);
                    }
                }
                System.out.println(date_check);
                for(int i=1;i<small;i++)
                {
                String out = sheet.getRow(i).getCell(0).getStringCellValue().toUpperCase();
                //System.out.println(out);
                //filw.println(out);
                if(db.get(out)!=null)
                {
                    out_data = db.get(out);
                    //System.out.println(out_data);
                    if(check==0)
                    {
                        entry =new Resulti(out,out_data);
                        data.add(entry);
                    }
                    if(check==1)
                    {
                        if(date_check==0)
                        {
                            sheet.getRow(i).getCell(large-1).setCellValue(out_data);
                        }
                        else
                        {
                            st_copy = sheet.getRow(0).getCell(large-2).getCellStyle();
                                st_date.cloneStyleFrom(st_copy);
                            sheet.getRow(i).createCell(large).setCellStyle(st_data);
                            sheet.getRow(i).getCell(large).setCellValue(out_data);
                        }
                    }
                        
                }
                else{
                    //System.out.println("Empty key");
                    int j;
                    //JOptionPane pane = new JOptionPane();
                    Object selectedValue = JOptionPane.showInputDialog(null,
                                            "Enter Value of   "+out);
                    
                    String st = (String)selectedValue;
                    j = Integer.parseInt(st);
                    //System.out.println(j);
                    db.put(out, j);
                    

                }
                                
                
                }
                if(check==1)
                    workbook.write(new FileOutputStream(book));
                workbook.close();
            }
            else
            {
                
                small=last_col;
                large = last_row;
                if(check==1)
                {
                    String chk_date = sheet.getRow(large-1).getCell(0).getStringCellValue();
                    if(str_date.equalsIgnoreCase(chk_date))
                    {
                        date_check = JOptionPane.showConfirmDialog(null,"Values with Date: "+str_date+" already exist. \nWant to Overide the value??", "Warning", JOptionPane.YES_NO_OPTION);
                    System.out.println(date_check);
                    if(date_check==0)
                        {   
                            sheet.getRow(large-1).getCell(0).setCellValue(str_date);
                        }
                    if(date_check==1)
                        {   
                            check=2;
                        }
                    }
                    else
                    {
                        st_copy = sheet.getRow(large-2).getCell(0).getCellStyle();
                        st_date.cloneStyleFrom(st_copy);
                        sheet.createRow(large).createCell(0).setCellStyle(st_date);
                        sheet.getRow(large).getCell(0).setCellValue(str_date);
                    }
                }
                    
                    
                for(int i=1;i<small;i++)
                {
                String out = sheet.getRow(0).getCell(i).getStringCellValue().toUpperCase();
                //System.out.println(out);
                
                if(out.equalsIgnoreCase("Activations"))
                {
                    if(check==1)
                    {
                        if(date_check==0)
                        {
                                sheet.getRow(large-1).getCell(i).setCellFormula("B"+(large)+"-B"+(large-1));
                        }
                        else
                        {   
                            st_copy = sheet.getRow(large-2).getCell(i).getCellStyle();
                              st_data.cloneStyleFrom(st_copy);
                            sheet.getRow(large).createCell(i).setCellStyle(st_data);
                            sheet.getRow(large).getCell(i).setCellFormula("B"+(large+1)+"-B"+(large));
                        }
                    }
                }
                else 
                {
                    if(db.get(out)!=null)
                    {
                    
                        out_data = db.get(out);
                        //System.out.println(out_data);
                        if(check==0)
                        {
                            entry =new Resulti(out,out_data);
                            data.add(entry);
                        }

                        if(check==1)
                        {
                            if(date_check==0)
                            {
                                sheet.getRow(large-1).getCell(i).setCellValue(out_data);
                            }
                            else
                            {  
                                st_copy = sheet.getRow(large-2).getCell(i).getCellStyle();
                                    st_data.cloneStyleFrom(st_copy);
                                sheet.getRow(large).createCell(i).setCellStyle(st_data);
                                sheet.getRow(large).getCell(i).setCellValue(out_data);
                            }
                        }
                }
                else{
                    
                    //System.out.println("Empty key");
                    int j;
                    JOptionPane pane = new JOptionPane();
                    Object selectedValue = JOptionPane.showInputDialog(null,
                                            "Enter Value of   "+out);
                    
                    String st = (String)selectedValue;
                    j = Integer.parseInt(st);
                    //System.out.println(j);
                    db.put(out, j);
                    }
                
                }}
                if(check==1)
                    workbook.write(new FileOutputStream(book));
                //filw.close();
            }
                     
            //System.out.println("end"+data.size());
            //fil1.close();
        
        }
        
        }catch(FileNotFoundException E)
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Text Files", "*.txt"));
            
            JOptionPane.showMessageDialog(null,E,"alert" , JOptionPane.ERROR_MESSAGE);     
        }
        catch(Exception E)
        {
            System.out.println("input output error"+E);
            ////System.out.println(E);
            JOptionPane.showMessageDialog(null,E.getMessage(),"alert" , JOptionPane.ERROR_MESSAGE);     
        }
    }
}
