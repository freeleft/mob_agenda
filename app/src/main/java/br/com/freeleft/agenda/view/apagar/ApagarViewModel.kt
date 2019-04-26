package br.com.freeleft.agenda.view.apagar

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.freeleft.agenda.model.Contato
import br.com.freeleft.agenda.model.ResponseStatus
import br.com.freeleft.agenda.repository.ContatoRepository

class ApagarViewModel : ViewModel() {

    val contatoRepository = ContatoRepository()
    val responseStatus: MutableLiveData<ResponseStatus> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun apagar(
        id: String,
        nome: String,
        telefone: String,
        email: String
    ) {
        isLoading.value = true
        val contato = Contato(_id = id,nome = nome, email = email, telefone = telefone)
        contatoRepository.apagar(contato,
            onComplete = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    true,
                    "Dados apagados com sucesso"
                )
            }, onError = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    false,""
                    /*it?.message!!*/
                )
            })

    }

}