package com.kogimobile.android.baselibrary.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by kogiandroid on 1/20/16.
 */
public class PhoneJustDashesWatcher implements TextWatcher {

    String lastText="";
    private EditText myEditText;

    public PhoneJustDashesWatcher(EditText myEditText){
        this.myEditText = myEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        int selectionEnd = myEditText.getSelectionEnd();

        boolean goToEnd = false;
        String withoutDash = s.toString().replace("-","");
        if (withoutDash.length()>=6){

            if (!((lastText.length()==8 && s.toString().length()==7)) && !((selectionEnd==7 || selectionEnd==3) && (lastText.length()-s.toString().length()==1))) {
                withoutDash = withoutDash.subSequence(0, 6) + "-" + withoutDash.subSequence(6, withoutDash.length());
                if (withoutDash.length()==7 && selectionEnd == 6 && s.toString().length()==8) {
                    goToEnd = true;
                }
                selectionEnd += 1;
            } else if (lastText.length()-s.toString().length()==1 && selectionEnd==7 && withoutDash.length()>6){
                withoutDash = withoutDash.substring(0, 5)+withoutDash.substring(6, 7)+"-" + withoutDash.substring(7, withoutDash.length());
                if (withoutDash.length()==7 && selectionEnd == 6 && s.toString().length()==8) {
                    goToEnd = true;
                }
            } else if (lastText.length()-s.toString().length()==1 && selectionEnd==3){
                withoutDash = withoutDash.substring(0, 2)+withoutDash.substring(3, withoutDash.length());
                if (withoutDash.length()>5) {
                    withoutDash = withoutDash.subSequence(0, 6) + "-" + withoutDash.subSequence(6, withoutDash.length());
                    if (withoutDash.length() == 7 && selectionEnd == 6 && s.toString().length() == 8) {
                        goToEnd = true;
                    }
                }else{
                    withoutDash = withoutDash.substring(0, 5);
                }
            } else{
                withoutDash = withoutDash.substring(0, withoutDash.length()-1);
                selectionEnd -= 1;
            }
            withoutDash = withoutDash.subSequence(0, 3) + "-" + withoutDash.subSequence(3, withoutDash.length());
        }else if(withoutDash.length()>=3 ){
            if (!((lastText.length()==4 &&s.toString().length()==3))  && !(selectionEnd==3 && (lastText.length()-s.toString().length()==1))) {
                withoutDash = withoutDash.subSequence(0, 3) + "-" + withoutDash.subSequence(3, withoutDash.length());
                if (withoutDash.length()==4 && selectionEnd == 2) {
                    goToEnd = true;
                }
                selectionEnd += 1;
            } else if (lastText.length()-s.toString().length()==1 && selectionEnd==3 && withoutDash.length()>3){
                withoutDash = withoutDash.substring(0, 3)+withoutDash.substring(6, 7)+"-" + withoutDash.substring(7, withoutDash.length());
                if (withoutDash.length()==7 && selectionEnd == 6 && s.toString().length()==8) {
                    goToEnd = true;
                }
            }else{
                withoutDash = withoutDash.substring(0, withoutDash.length()-1);
                selectionEnd -= 1;
            }
        }
        myEditText.removeTextChangedListener(this);
        myEditText.setText(withoutDash);
        if (goToEnd){
            myEditText.setSelection(withoutDash.length());
        }else {
            myEditText.setSelection(Math.min(selectionEnd, withoutDash.length()));
        }
        myEditText.addTextChangedListener(this);
        lastText = withoutDash;
    }
}