package br.com.freeleft.agenda.view.formulario

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.freeleft.agenda.model.Contato
import br.com.freeleft.agenda.model.ResponseStatus
import br.com.freeleft.agenda.repository.ContatoRepository

class FormularioViewModel : ViewModel() {

    val contatoRepository = ContatoRepository()
    val responseStatus: MutableLiveData<ResponseStatus> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun salvar(
        nome: String,
        telefone: String,
        email: String
    ) {
        isLoading.value = true
        val contato = Contato(nome = nome, telefone = telefone, email = email)
        contatoRepository.salvar(contato,
            onComplete = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    true,
                    "Dados inseridos com sucesso"
                )
            }, onError = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    false,
                    it?.message!!
                )
            })

    }

}