/* This file is part of Gralog, Copyright (c) 2016-2018 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later. */
package gralog.gralogfx.views;

import gralog.algorithm.StringAlgorithmParametersList;
import gralog.parser.SyntaxChecker;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.function.Consumer;

/**
 * Asks the user for two strings with live syntax checking.
 */
@ViewDescription(forClass = StringAlgorithmParametersList.class)
public class StringAlgorithmParametersListView extends GridPaneView<StringAlgorithmParametersList> {

    @Override
    public void setObject(StringAlgorithmParametersList params,
        Consumer<Boolean> submitPossible) {
        this.getChildren().clear();
        if (params == null)
            return;
        
      
        setVgap(5.0);
        for (int i = 0; i < params.parameters.size(); i++) {
        	if (params.parameters.get(i).equals("true") || params.parameters.get(i).equals("false")) {
        		CheckBox valueField = new CheckBox();
                if (params.parameters.get(i).equals("true")){
                    valueField.setSelected(true);
                }
                int finalI = i;
                valueField.selectedProperty().addListener(e -> {
                	if (valueField.isSelected()) {
                		params.parameters.set(finalI, "true");
                	}
                	else {
                		params.parameters.set(finalI, "false");
                	}
                });

        		add(new Label(params.labels.get(i) + ": "), 0, 3*i);
        		add(valueField, 1, 3*i);
        	}
        	else {
        		TextField valueField = new TextField(params.parameters.get(i));
        		valueField.setPrefWidth(1000);

        		Text hint = new Text();

        		int finalI = i;
        		valueField.textProperty().addListener(e -> {
        			params.parameters.set(finalI, valueField.getText());
        			syntaxCheck(params, valueField, hint, submitPossible);
        		});
        	
        		add(new Label(params.labels.get(i) + ": "), 0, 3*i);
        		add(valueField, 1, 3*i);
        	}

            String explanation = params.explanations.get(i);
            Text explanationText = new Text(explanation);
            if (!explanation.isEmpty()) {
                add(explanationText, 0, 3 * i + 2, 2, 1);
                setMargin(explanationText, new Insets(-10.0,0,10,0));
            }

        }
    }

    private void syntaxCheck(StringAlgorithmParametersList param,
        TextField valueField, Text hint, Consumer<Boolean> submitPossible) {
        SyntaxChecker.Result syntax = param.syntaxCheck();
        if (syntax.syntaxCorrect) {
            valueField.setStyle("-fx-text-inner-color: black;");
            submitPossible.accept(true);
        } else {
            valueField.setStyle("-fx-text-inner-color: red;");
            submitPossible.accept(false);
        }
        hint.setText(syntax.hint);
    }
}
