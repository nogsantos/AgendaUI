package nogsantos.br.agenda.ui;

import nogsantos.br.agenda.R;
import nogsantos.br.agenda.pojo.ContatoVO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/**
 * UI - User Interface, responsável pela interação entre o usuário e o sistema.
 *
 * Classe que irá controlar a tela de cadastro de contato
 */
public class Contato extends Activity {
    private static final int INCLUIR = 0;
    /*
     * private static final int ALTERAR = 1;
     */
    ContatoVO lContatoVO;
    EditText  txtNome;
    EditText  txtEndereco;
    EditText  txtTelefone;
    /**
     * Create
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        try{
            final Bundle data = (Bundle) getIntent().getExtras();
            int lint = data.getInt("tipo");
            if (lint == INCLUIR){
                /*
                 * quando for incluir um contato criamos uma nova instância
                 */
                lContatoVO = new ContatoVO();
            }else{
                /*
                 * quando for alterar o contato carregamos a classe que veio por Bundle
                 */
                lContatoVO = (ContatoVO)data.getSerializable("agenda");
            }
            /*
             * Criação dos objetos da Activity
             */
            txtNome     = (EditText)findViewById(R.id.edtNome);
            txtEndereco = (EditText)findViewById(R.id.edtEndereco);
            txtTelefone = (EditText)findViewById(R.id.edtTelefone);
            /*
             * Carregando os objetos com os dados do Contato
             * caso seja uma inclusão ele virá carregado com os atributos text
             * definido no arquivo main.xml
             */
            txtNome.setText(lContatoVO.getNome());
            txtEndereco.setText(lContatoVO.getEndereco());
            txtTelefone.setText(lContatoVO.getTelefone());
        }catch (Exception e) {
            Toast.makeText (getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show ();
            e.printStackTrace();
        }
    }
    /**
     * Quando confirmar a inclusão ou alteração deve-se devolver
     * o registro com os dados preenchidos na tela e informar
     * o RESULT_OK e em seguida finalizar a Activity
     */
    public void btnConfirmar_click(View view){
        try{
            Intent data = new Intent();
            lContatoVO.setNome(txtNome.getText().toString());
            lContatoVO.setEndereco(txtEndereco.getText().toString());
            lContatoVO.setTelefone(txtTelefone.getText().toString());
            data.putExtra("agenda", lContatoVO);
            setResult(Activity.RESULT_OK, data);
            finish();
        }catch (Exception e) {
            Toast.makeText (getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show ();
            e.printStackTrace();
        }
    }
    /**
     * Quando for simplesmente cancelar a operação de inclusão
     * ou alteração deve-se apenas informar o RESULT_CANCELED
     * e em seguida finalizar a Activity
     */
    public void btnCancelar_click(View view){
        try{
            setResult(Activity.RESULT_CANCELED);
            finish();
        }catch (Exception e) {
            Toast.makeText (getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show ();
            e.printStackTrace();
        }
    }
}