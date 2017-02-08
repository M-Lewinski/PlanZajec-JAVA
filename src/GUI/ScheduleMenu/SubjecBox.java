package GUI.ScheduleMenu;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;

import java.util.Set;

/**
 * Created by lewin on 1/16/17.
 */
public class SubjecBox extends TextArea {

//    public double scrollH = 0.0;
//    public double scrollW = 0.0;

    public Rectangle border = null;

    public SubjecBox(DoubleProperty fontSize, double x, double y, double width, double height){
        super();
        this.setLayoutX(x);
        this.setLayoutY(y);

        this.setPrefSize(width,height);
        this.styleProperty().bind(Bindings.concat("-fx-font-size: ",fontSize.asString(),";"));
        this.setEditable(false);
        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    System.out.println("HEHEHEH");
                }
            }
        });
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
}
