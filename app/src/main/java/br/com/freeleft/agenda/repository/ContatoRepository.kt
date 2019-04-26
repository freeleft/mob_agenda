package br.com.freeleft.agenda.repository

import br.com.freeleft.agenda.api.getAgendaAPI
import br.com.freeleft.agenda.model.Contato
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContatoRepository {

    fun buscarTodos(
        onComplete:(List<Contato>?) -> Unit,
        onError: (Throwable?) -> Unit
    ) {

        getAgendaAPI()
            .getContatos()
            .enqueue(object : Callback<List<Contato>>{
                override fun onFailure(call: Call<List<Contato>>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<List<Contato>>, response: Response<List<Contato>>) {
                    if(response.isSuccessful) {
                        onComplete(response.body())
                    } else {
                        onError(Throwable("Erro ao buscar os dados"))
                    }
                }
            })

    }


    fun salvar(contato: Contato,
               onComplete: (Contato) -> Unit,
               onError: (Throwable?) -> Unit) {
        getAgendaAPI()
            .salvar(contato)
            .enqueue(object : Callback<Contato>{
                override fun onFailure(call: Call<Contato>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<Contato>, response: Response<Contato>) {
                    onComplete(response.body()!!)
                }
            })
    }

    fun editar(contato: Contato,
               onComplete: (Contato) -> Unit,
               onError: (Throwable?) -> Unit) {
        getAgendaAPI()
            .editar(contato._id!!,contato)
            .enqueue(object : Callback<Contato>{
                override fun onFailure(call: Call<Contato>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<Contato>, response: Response<Contato>) {
                    onComplete(response.body()!!)
                }
            })
    }

    fun apagar(contato: Contato,
               onComplete: (Contato) -> Unit,
               onError: (Throwable?) -> Unit) {
        getAgendaAPI()
            .apagar(contato._id!!)
            .enqueue(object : Callback<Contato>{
                override fun onFailure(call: Call<Contato>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<Contato>, response: Response<Contato>) {
                    onComplete(response.body()!!)
                }
            })
    }

}