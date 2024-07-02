package stark.prm.project.uiHelper;
import android.text.TextUtils;
import android.widget.EditText;

import stark.prm.project.R;

public class UiCheckError {



    public static boolean checkHomeworkElmenents(EditText editLecture, EditText dueDate, EditText editDesc){
        boolean isError = false;
        if(TextUtils.isEmpty(editLecture.getText())) {
            editLecture.setError("Thema wird benötigt!");
            isError = true;
        }
        if(TextUtils.isEmpty(dueDate.getText())) {
            dueDate.setError("Datum wird benötigt!");
            isError = true;
        }
        /*if(TextUtils.isEmpty(pageNum.getText()) ) {
            pageNum.setError("Seitenzahl wird benötigt!");
            isError = true;
        }*/
        if(TextUtils.isEmpty(editDesc.getText())) {
            editDesc.setError("Beschreibung wird benötigt!");
            isError = true;
        }
        return isError;
    }
    public static boolean checkNoteElmenents(EditText editLecture, EditText editDesc){

        boolean isError = false;
        if(TextUtils.isEmpty(editLecture.getText())) {
            editLecture.setError("Thema wird benötigt!");
            isError = true;
        }
        if(TextUtils.isEmpty(editDesc.getText())) {
            editDesc.setError("Beschreibung wird benötigt!");
            isError = true;
        }
        return isError;
    }
}
