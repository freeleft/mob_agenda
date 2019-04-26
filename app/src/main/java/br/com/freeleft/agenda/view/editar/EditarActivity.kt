package br.com.freeleft.agenda.view.editar

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import br.com.freeleft.agenda.R
import br.com.freeleft.agenda.model.ResponseStatus
import br.com.freeleft.agenda.view.editar.EditarViewModel
import kotlinx.android.synthetic.main.activity_editar.*
import kotlinx.android.synthetic.main.activity_editar.view.*
import kotlinx.android.synthetic.main.contato_item.*
import kotlinx.android.synthetic.main.loading.*

class EditarActivity : AppCompatActivity() {

    private lateinit var editarViewModel: EditarViewModel
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        editarViewModel = ViewModelProviders.of(this)
            .get(EditarViewModel::class.java)

        id = intent.getStringExtra("ID")

        inputNome.editText?.setText(intent.getStringExtra("NOME"))
        inputTelefone.editText?.setText(intent.getStringExtra("TELEFONE"))
        inputEmail.editText?.setText(intent.getStringExtra("EMAIL"))


        btSalvar.setOnClickListener {
            val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
            editarViewModel.editar(
                id,
                inputNome.editText?.text.toString(),
                inputTelefone.editText?.text.toString(),
                inputEmail.editText?.text.toString()
            )
        }

        registerObserver()
    }

    private fun registerObserver() {
        editarViewModel.responseStatus.observe(this, responseObserver)
        editarViewModel.isLoading.observe(this, loadingObserver)
    }

    private var loadingObserver = Observer<Boolean> {
        if (it == true) {
            containerLoading.visibility = View.VISIBLE
        } else {
            containerLoading.visibility = View.GONE
        }
    }

    private var responseObserver = Observer<ResponseStatus> {
        Toast.makeText(this, it?.mensagem, Toast.LENGTH_SHORT).show()
        if (it?.sucesso == true) {
            setResult(Activity.RESULT_OK)
            finish()
        }
        //melhorar
        finish()


    }

}

