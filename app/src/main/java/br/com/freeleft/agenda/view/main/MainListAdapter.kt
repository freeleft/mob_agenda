package br.com.freeleft.agenda.view.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.freeleft.agenda.R
import br.com.freeleft.agenda.model.Contato
import kotlinx.android.synthetic.main.contato_item.view.*

class MainListAdapter(
    val context: Context,
    val contatos: List<Contato>,
    val acaoEditarContato: (Contato) -> Unit,
    val acaoApagarContato: (Contato) -> Unit,
    val acaoLigarContato: (Contato) -> Unit,
    val acaoEnviarEmailContato: (Contato) -> Unit

) :
    RecyclerView.Adapter<MainListAdapter.ContatoViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContatoViewHolder {

        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.contato_item, p0, false)

        return ContatoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return contatos.size
    }

    override fun onBindViewHolder(p0: ContatoViewHolder, position: Int) {
        val contato = contatos[position]
        p0.bindView(contato, acaoEditarContato, acaoApagarContato, acaoLigarContato,acaoEnviarEmailContato)
    }

    class ContatoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(
            contato: Contato,
            editar: (Contato) -> Unit,
            apagar: (Contato) -> Unit,
            ligar: (Contato) -> Unit,
            email: (Contato) -> Unit
        ) = with(itemView) {
            tvNome.text = contato.nome
            tvTelefone.text = contato.telefone
            tvEmail.text = contato.email
            tvID.text = contato._id

            tvNome.setOnClickListener{
                editar(contato)
            }
            btnExcluir.setOnClickListener{
                apagar(contato)
            }
            tvTelefone.setOnClickListener{
                ligar(contato)
            }
            tvEmail.setOnClickListener{
                email(contato)
            }
            /*
            setOnClickListener {
            }
            */

        }

    }

    fun apagaContato(position: Int) {
        notifyItemRemoved(position)
    }

}