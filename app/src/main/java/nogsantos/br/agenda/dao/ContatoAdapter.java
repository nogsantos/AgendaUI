package nogsantos.br.agenda.dao;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import nogsantos.br.agenda.R;
import nogsantos.br.agenda.pojo.ContatoVO;
/**
 * DAO - Data Access Object, responsável pela persistência dos dados.
 *
 * classe responsável por apresentar na tela em forma de ListView os contatos que estão memória
 * que foram extraídos do Banco de Dados SQLite
 */
public class ContatoAdapter extends BaseAdapter  {
    private Context context;

    private List<ContatoVO> lstContato;
    private LayoutInflater inflater;

    public ContatoAdapter(Context context, List<ContatoVO> listAgenda) {
        this.context    = context;
        this.lstContato = listAgenda;
        inflater        = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /*
     * Atualizar ListView de acordo com o lstContato
     */
    @Override
    public void notifyDataSetChanged() {
        try{
            super.notifyDataSetChanged();
        }catch (Exception e) {
            Toast.makeText (context, e.getMessage(), Toast.LENGTH_SHORT).show ();
            e.printStackTrace();
        }
    }
    /**
     *
     */
    public int getCount() {
        return lstContato.size();
    }
    /**
     * Remover item da lista
     */
    public void remove(final ContatoVO item) {
        this.lstContato.remove(item);
    }
    /**
     * Adicionar item na lista
     */
    public void add(final ContatoVO item) {
        this.lstContato.add(item);
    }
    /**
     *
     */
    public Object getItem(int position) {
        return lstContato.get(position);
    }
    /**
     *
     */
    public long getItemId(int position) {
        return position;
    }
    /**
     *
     */
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        try{
            ContatoVO contato = lstContato.get(position);
            /*
             * O ViewHolder irá guardar a instâncias dos objetos do estado_row
             */
            ViewHolder holder;
            /*
             * Quando o objeto convertView não for nulo nós não precisaremos inflar
             * os objetos do XML, ele será nulo quando for a primeira vez que for carregado
             */
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.contato_row, null);
                /*
                 * Cria o Viewholder e guarda a instância dos objetos
                 */
                holder            = new ViewHolder();
                holder.tvNome     = (TextView) convertView.findViewById(R.id.txtNome);
                holder.tvEndereco = (TextView) convertView.findViewById(R.id.txtEndereco);
                holder.tvTelefone = (TextView) convertView.findViewById(R.id.txtTelefone);

                convertView.setTag(holder);
            } else {
                /*
                 * pega o ViewHolder para ter um acesso rápido aos objetos do XML
                 * ele sempre passará por aqui quando,por exemplo, for efetuado uma rolagem na tela
                 */
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvNome.setText(contato.getNome());
            holder.tvEndereco.setText(contato.getEndereco());
            holder.tvTelefone.setText(contato.getTelefone());

            return convertView;

        }catch (Exception e) {
            Toast.makeText (context, e.getMessage(), Toast.LENGTH_SHORT).show ();
            e.printStackTrace();
        }
        return convertView;
    }
    /**
     * Criada esta classe estática para guardar a referência dos objetos abaixo
     */
    static class ViewHolder {
        public TextView tvNome;
        public TextView tvEndereco;
        public TextView tvTelefone;
    }
}
