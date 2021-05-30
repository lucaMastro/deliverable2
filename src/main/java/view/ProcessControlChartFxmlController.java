package view;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import logic.boundary.ProcessControlChartBoundary;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

public class ProcessControlChartFxmlController extends ProcessControlChartAndProportionFxmlController {

    @FXML
    private Spinner<Integer> thresholdSpinner;


    @Override
    protected void submitButtonSelected(ActionEvent actionEvent) {

        var boundary = new ProcessControlChartBoundary(this.outputFileLabel.getText(),
                this.repositoryLabel.getText(),
                this.projectName.getText(),
                this.thresholdSpinner.getValue());

        this.runTask(boundary);
    }

    @Override
    protected void initialize(){
        super.initialize();

        //prevent chars value on spinner
        var format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                var parsePosition = new ParsePosition(0);
                // NumberFormat evaluates the beginning of the text
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 ||
                        parsePosition.getIndex() < c.getControlNewText().length()) {
                    // reject parsing the complete text failed
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> priceFormatter = new TextFormatter<>(
                new IntegerStringConverter(), 0, filter);
        var fact = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);
        this.thresholdSpinner.setValueFactory(fact);
        this.thresholdSpinner.getEditor().setTextFormatter(priceFormatter);
    }
}

