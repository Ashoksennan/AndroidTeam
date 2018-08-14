package chennaicitytrafficapplication.prematix.com.etownpublic.common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Admin on 8/1/2018.
 */

public class DateSelect {
    int year,month,day;
    Context context;

    public DateSelect(Context context) {
        this.context = context;
    }


    public void getDate(final EditText date_field){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        if(String.valueOf(dayOfMonth).length() > 1){
                            if(String.valueOf(monthOfYear).length()>1){
                                date_field.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }else{
                                date_field.setText(dayOfMonth + "/" +"0"+(monthOfYear + 1) + "/" + year);
                            }
                        }else{
                            if(String.valueOf(monthOfYear).length()>1){
                                date_field.setText("0"+dayOfMonth + "/" + (monthOfYear + 1) +"/" + year);
                            }else{
                                date_field.setText( "0"+dayOfMonth + "/" +"0"+(monthOfYear + 1) + "/" + year);
                            }
                        }
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()- 1000);
        datePickerDialog.show();
    }

}

