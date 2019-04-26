package br.com.freeleft.agenda.api

import br.com.freeleft.agenda.model.Contato
import retrofit2.Call
import retrofit2.http.*
import okhttp3.ResponseBody



interface AgendaAPI {

    @GET("/api_agenda/contatos")
    fun getContatos() : Call<List<Contato>>

    @POST("/api_agenda/contatos")
    fun salvar(@Body contato: Contato): Call<Contato>

    @DELETE("/api_agenda/contatos/{id}")
    fun apagar(@Path("id") _id: String): Call<Contato>

    @PUT("/api_agenda/contatos/{id}")
    fun editar(@Path("id") _id: String, @Body contato: Contato): Call<Contato>

}