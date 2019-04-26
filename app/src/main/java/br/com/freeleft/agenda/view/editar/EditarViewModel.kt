package br.com.freeleft.agenda.view.editar

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.freeleft.agenda.model.Contato
import br.com.freeleft.agenda.model.ResponseStatus
import br.com.freeleft.agenda.repository.ContatoRepository

class EditarViewModel : ViewModel() {

    val contatoRepository = ContatoRepository()
    val responseStatus: MutableLiveData<ResponseStatus> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun editar(
        id: String,
        nome: String,
        telefone: String,
        email: String
    ) {
        isLoading.value = true
        val contato = Contato(_id = id,nome = nome, telefone = telefone, email = email)
        contatoRepository.editar(contato,
            onComplete = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    true,
                    "Dados atualizados com sucesso"
                )
            }, onError = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    false,it?.message!!
                )
            })

    }

}