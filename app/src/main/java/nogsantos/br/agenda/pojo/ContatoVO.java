package nogsantos.br.agenda.pojo;

import java.io.Serializable;
/**
 * POJO - Plain Old Java Objects, responsável pelo transporte dos dados entre o BD, regras de negócio e o usuário.
 *
 * será a classe responsável pelo transporte dos dados do Contato entre o usuário e o Banco de Dados SQLite
 */
public class ContatoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String nome;
    private String endereco;
    private String telefone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String value) {
        this.nome = value;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String value) {
        this.endereco = value;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String value) {
        this.telefone = value;
    }
    /*
     * Will be used by the ArrayAdapter in the ListView
     */
    @Override
    public String toString() {
        return nome;
    }
}
