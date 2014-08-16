package nogsantos.br.agenda.ui;

import java.util.List;
import java.util.logging.Handler;

import nogsantos.br.agenda.dao.ContatoAdapter;
import nogsantos.br.agenda.dao.ContatoDAO;
import nogsantos.br.agenda.pojo.ContatoVO;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import nogsantos.br.agenda.R;
/**
 * UI - User Interface, responsável pela interação entre o usuário e o sistema.
 *
 * Primeira classe a ser executada no projeto:
 */
public class MainActivity extends ListActivity{
    private static final int INCLUIR = 0;
    private static final int ALTERAR = 1;
    /*
     * instância responsável pela persistência dos dados
     */
    private ContatoDAO objContatoDao;
    /*
     * lista de contatos cadastrados no BD
     */
    List<ContatoVO> lstContatos;
    /*
     * Adapter responsável por apresentar os contatos na tela
     */
    ContatoAdapter adapter;

    boolean blnShort = false;
    /*
     * determinar a posição do contato dentro da lista lstContatos
     */
    int Posicao = 0;

    /**
     * Create
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.progressDialogShow(this);

        /*
         * Retorna a lista com os contatos cadastrados
         */
        objContatoDao = new ContatoDAO(this);
        objContatoDao.open();
        lstContatos = objContatoDao.Consultar();
        adapter     = new ContatoAdapter(this, lstContatos);
        setListAdapter(adapter);

        registerForContextMenu(getListView());

    }
    /*
     * Este evento será chamado pelo atributo onClick
     * que está definido no botão criado no arquivo main.xml
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                InserirContato();
                break;
        }
    }
    /**
     * Rotina executada quando finalizar a Activity ContatoUI
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ContatoVO lAgendaVO = null;

        try{
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK){
                /*
                 * obtem dados inseridos/alterados na Activity ContatoUI
                 */
                lAgendaVO = (ContatoVO)data.getExtras().getSerializable("agenda");
                /*
                 * o valor do requestCode foi definido na função startActivityForResult
                 */
                if (requestCode == INCLUIR){
                    /*
                     * verifica se digitou algo no nome do contato
                     */
                    if (!lAgendaVO.getNome().equals("")){
                        /*
                         * necessário abrir novamente o BD pois ele foi fechado no método onPause()
                         */
                        objContatoDao.open();
                        /*
                         * insere o contato no Banco de Dados SQLite
                         */
                        objContatoDao.Inserir(lAgendaVO);
                        /*
                         * insere o contato na lista de contatos em memória
                         */
                        lstContatos.add(lAgendaVO);
                    }
                }else if (requestCode == ALTERAR){
                    objContatoDao.open();
                    /*
                     * atualiza o contato no Banco de Dados SQLite
                     */
                    objContatoDao.Alterar(lAgendaVO);
                    /*
                     * atualiza o contato na lista de contatos em memória
                     */
                    lstContatos.set(Posicao, lAgendaVO);
                }
                /*
                 * método responsável pela atualiza da lista de dados na tela
                 */
                adapter.notifyDataSetChanged();
            }
        }
        catch (Exception e) {
            Toast.makeText (getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show ();
            e.printStackTrace();
        }
    }
    /**
     * Inserir contato pelo clique do botão
     */
    private void InserirContato(){
        try{
            /*
             * a variável "tipo" tem a função de definir o comportamento da Activity
             * ContatoUI, agora a variável tipo está definida com o valor "0" para
             * informar que será uma inclusão de Contato
             */
            Intent it = new Intent(this, Contato.class);
            it.putExtra("tipo", INCLUIR);
            /*
             * chama a tela e incusão
             */
            startActivityForResult(it, INCLUIR);
        }
        catch (Exception e) {
            Toast.makeText (getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show ();
            e.printStackTrace();
        }
    }
    /**
     * Quando a Activity main receber o foco novamente abre-se novamente a conexão
     */
    @Override
    protected void onResume() {
        objContatoDao.open();
        super.onResume();
    }
    /**
     * Toda vez que o programa peder o foco fecha-se a conexão com o BD
     */
    @Override
    protected void onPause() {
        objContatoDao.close();
        super.onPause();
    }
    /**
     * Criação do popup menu com as opções que termos sobre
     * nossos Contatos
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        try{
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            if (!blnShort){
                Posicao = info.position;
            }
            blnShort = false;
            menu.setHeaderTitle("Selecione:");
            /**
             * a origem dos dados do menu está definido no arquivo arrays.xml
             */
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }catch (Exception e) {
            Toast.makeText (getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show ();
            e.printStackTrace();
        }
    }
    /**
     * Este método é disparado quando o usuário clicar em um item do ContextMenu
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ContatoVO lAgendaVO = null;
        try{
            int menuItemIndex = item.getItemId();
            /*
             * Carregar a instância POJO com a posição selecionada na tela
             */
            lAgendaVO = (ContatoVO) getListAdapter().getItem(Posicao);

            if (menuItemIndex == 0){
                /*
                 * Carregar a Activity ContatoUI com o registro selecionado na tela
                 */
                Intent it = new Intent(this, Contato.class);
                it.putExtra("tipo", ALTERAR);
                it.putExtra("agenda", lAgendaVO);
                /*
                 * chama a tela de alteração
                 */
                startActivityForResult(it, ALTERAR);
            }else if (menuItemIndex == 1){
                /*
                 * Excluir do Banco de Dados e da tela o registro selecionado
                 */
                objContatoDao.Excluir(lAgendaVO);
                lstContatos.remove(lAgendaVO);
                /*
                 * atualiza a tela
                 */
                adapter.notifyDataSetChanged();
            }else{
                onResume();
            }
        }catch (Exception e) {
            Toast.makeText (getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show ();
            e.printStackTrace();
        }
        return true;
    }
    /**
     * Por padrão o ContextMenu, só é executado através de LongClick, mas
     * nesse caso toda vez que executar um ShortClick, abriremos o menu
     * e também guardaremos qual a posição do itm selecionado
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Posicao  = position;
        blnShort = true;
        this.openContextMenu(l);
    }
}