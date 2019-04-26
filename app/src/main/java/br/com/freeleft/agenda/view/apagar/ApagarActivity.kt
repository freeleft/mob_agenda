package br.com.freeleft.agenda.view.apagar

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import br.com.freeleft.agenda.R
import br.com.freeleft.agenda.model.ResponseStatus
import br.com.freeleft.agenda.view.apagar.ApagarViewModel
import br.com.freeleft.agenda.view.editar.EditarViewModel
import kotlinx.android.synthetic.main.activity_editar.*
import kotlinx.android.synthetic.main.activity_editar.view.*
import kotlinx.android.synthetic.main.contato_item.*
import kotlinx.android.synthetic.main.loading.*

class ApagarActivity : AppCompatActivity() {

    private lateinit var apagarViewModel: ApagarViewModel
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apagar)

        apagarViewModel = ViewModelProviders.of(this)
            .get(ApagarViewModel::class.java)

        id = intent.getStringExtra("ID")

        inputNome.editText?.setText(intent.getStringExtra("NOME"))
        inputTelefone.editText?.setText(intent.getStringExtra("TELEFONE"))
        inputEmail.editText?.setText(intent.getStringExtra("EMAIL"))


        btSalvar.setOnClickListener {
            apagarViewModel.apagar(
                id,
                inputNome.editText?.text.toString(),
                inputEmail.editText?.text.toString(),
                inputTelefone.editText?.text.toString()
            )
        }

        registerObserver()
    }

    private fun registerObserver() {
        apagarViewModel.responseStatus.observe(this, responseObserver)
        apagarViewModel.isLoading.observe(this, loadingObserver)
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
        Handler().postDelayed({
            finish()
        }, 1000)

        //Recarregadar sem piscar
        Handler().postDelayed({
            finish();
            overridePendingTransition(0, 0);
        }, 1000)


    }

}

