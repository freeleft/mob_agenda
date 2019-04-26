package br.com.freeleft.agenda.view.main

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import br.com.freeleft.agenda.R
import br.com.freeleft.agenda.model.Contato
import br.com.freeleft.agenda.swipe.SwipeToDeleteCallback
import br.com.freeleft.agenda.view.apagar.ApagarActivity
import br.com.freeleft.agenda.view.editar.EditarActivity
import br.com.freeleft.agenda.view.formulario.FormularioActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.loading.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mainViewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)

        registerObservers()

        mainViewModel.buscarTodos()

        fab.setOnClickListener { /*view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            startActivityForResult(
                Intent(
                    this,
                    FormularioActivity::class.java
                ), 1
            )
        }

        fab2.setOnClickListener {
            mainViewModel.buscarTodos()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            mainViewModel.buscarTodos()
        }
        //Melhorar
        mainViewModel.buscarTodos()
    }

    private fun registerObservers() {
        mainViewModel.isLoading.observe(this, isLoadingObserver)
        mainViewModel.mensagemErro.observe(this, mensagemErroObserver)
        mainViewModel.contatos.observe(this, contatosObserver)
    }

    private var contatosObserver = Observer<List<Contato>> {
        rvContatos.adapter = MainListAdapter(
            this, it!!,
            {acaoContato(it,1)},
            {acaoContato(it,2)},
            {acaoContato(it,3)},
            {acaoContato(it,4)})

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rvContatos.adapter as MainListAdapter
                acaoContato(adapter.contatos[viewHolder.adapterPosition],2)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rvContatos)

        rvContatos.layoutManager = LinearLayoutManager(this)
        //rvContatos.layoutManager = GridLayoutManager(this, 2)

    }

    private var mensagemErroObserver = Observer<String> {
        if (it!!.isNotEmpty()) {
            Toast.makeText(
                this,
                it, Toast.LENGTH_LONG
            ).show()
        }
    }

    private var isLoadingObserver = Observer<Boolean> {
        if (it == true) {
            containerLoading.visibility = View.VISIBLE
        } else {
            containerLoading.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                finish();
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun acaoContato(contato: Contato, operation: Int)
    {
        var nextScreenIntent = Intent(this, MainActivity::class.java)

        when (operation) {
            1 -> nextScreenIntent = Intent(this, EditarActivity::class.java)
            2 -> nextScreenIntent = Intent(this, ApagarActivity::class.java)
            3 -> {
                    val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)

                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            42
                        )
                    } else {
                        startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + contato.telefone)))
                    }
                    return
            }
            4 -> {
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = Uri.parse("mailto:" + contato.email) // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_SUBJECT,"EES UFSCAR 2019")
                    startActivity(intent)
                    return
            }
        }

        nextScreenIntent.putExtra("ID", contato._id)
        nextScreenIntent.putExtra("NOME", contato.nome)
        nextScreenIntent.putExtra("TELEFONE", contato.telefone)
        nextScreenIntent.putExtra("EMAIL", contato.email)
        startActivityForResult(nextScreenIntent, 1)
    }

}
