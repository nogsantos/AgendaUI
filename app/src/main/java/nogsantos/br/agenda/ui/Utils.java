package nogsantos.br.agenda.ui;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.logging.Handler;

/**
 * Created by nogsantos on 8/15/14.
 */
public final class Utils {
    /*
     * Progress
     */
    private static ProgressDialog progress;

    public static void progressDialogShow(Context context){
        progress = ProgressDialog.show(
            context,
            "Processando",
            "Processando a operação, por favor aguarde...",
            false,
            true
        );
    }

    public static void progressDialogDimiss(){
        progress.dismiss();
    }


}
