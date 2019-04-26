package br.com.freeleft.agenda.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.freeleft.agenda.model.Contato
import br.com.freeleft.agenda.repository.ContatoRepository

class MainViewModel : ViewModel() {

    val contatoRepository = ContatoRepository()

    val contatos : MutableLiveData<List<Contato>> = MutableLiveData()
    val mensagemErro : MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()


    fun buscarTodos () {
        isLoading.value = true
        contatoRepository.buscarTodos(
            onComplete = {
                isLoading.value = false
                contatos.value = it

            },
            onError = {
                isLoading.value = false
                mensagemErro.value = it?.message
            }
        )
    }

}