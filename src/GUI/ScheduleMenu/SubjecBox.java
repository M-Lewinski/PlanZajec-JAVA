package GUI.ScheduleMenu;

import dataBase.subjects.Zajecie;
import dataBase.subjects.ZaplanowaneZajecie;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;

/**
 * Created by lewin on 1/16/17.
 */
public class SubjecBox extends TextArea {

//    public double scrollH = 0.0;
//    public double scrollW = 0.0;
    private Zajecie zajecie;
    private ZaplanowaneZajecie zaplanowaneZajecie;
    public Rectangle rectangle = null;
    private double x;
    private double y;
    private double widthSize;
    private double heightSize;

    public SubjecBox(DoubleProperty fontSize, double x, double y, double width, double height,Zajecie zajecie) {
        this(fontSize,x,y,width,height);
//        this.setStyle("text-area-background: #8a6d3b" + this.zajecie.getSubject().colorHex()+";");
//        this.setStyle("text-area-background: "+ "#8a6d3b" +";");
//    this.setStyle("-fx-contol-inner-background: "+ "#8a6d3b" +";");
        this.zajecie = zajecie;
        String text = this.zajecie.getPrzedmiot() +"\n" +
                this.zajecie.getRodzaj() + ", " + this.zajecie.getSala() + " " + this.zajecie.getBudynek() +"\n" +
                this.zajecie.getProfessor().toString();
        this.setText(text);
    }

    public SubjecBox(DoubleProperty fontSize, double x, double y, double width, double height,ZaplanowaneZajecie zaplanowaneZajecie) {
        this(fontSize,x,y,width,height,zaplanowaneZajecie.getZajecie());
        this.zaplanowaneZajecie = zaplanowaneZajecie;
    }

    private SubjecBox(DoubleProperty fontSize, double x, double y, double width, double height){
        super();
        this.x = x;
        this.y = y;
        this.widthSize = width;
        this.heightSize = height;
        this.setLayoutX(x);
        this.setLayoutY(y);

        this.setPrefSize(width,height);
//        this.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
//        this.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        this.styleProperty().bind(Bindings.concat("-fx-font-size: ",fontSize.asString(),";"));
        this.setEditable(false);

//        super.layoutChildren();
//        ScrollBar scrollBarv = (ScrollBar) this.lookup(".scroll-bar:vertical");
//        if (scrollBarv != null) {
//            System.out.println("test1");
//        }
//        ScrollBar scrollBarh = (ScrollBar) this.lookup(".scroll-bar:horizontal");
//        if (scrollBarh != null) {
//            System.out.println( "Hej");
//        }
    };

    public void delete(){
        if(this.zaplanowaneZajecie!=null){
            this.zaplanowaneZajecie.delete();
            this.zaplanowaneZajecie = null;
        }
        if(this.zajecie!=null){
            this.zajecie.delete();
            this.zajecie = null;
        }
    }


    public Zajecie getZajecie() {
        return zajecie;
    }

    public void setZajecie(Zajecie zajecie) {
        this.zajecie = zajecie;
    }

    public ZaplanowaneZajecie getZaplanowaneZajecie() {
        return zaplanowaneZajecie;
    }

    public void setZaplanowaneZajecie(ZaplanowaneZajecie zaplanowaneZajecie) {
        this.zaplanowaneZajecie = zaplanowaneZajecie;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidthSize() {
        return widthSize;
    }

    public void setWidthSize(double widthSize) {
        this.widthSize = widthSize;
    }

    public double getHeightSize() {
        return heightSize;
    }

    public void setHeightSize(double heightSize) {
        this.heightSize = heightSize;
    }
}
