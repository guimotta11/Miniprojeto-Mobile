package com.example.app

import android.R
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import model.Cep
import okhttp3.Response
import retrofit.RetrofitInitializer
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    var edtCEPinformado: EditText? = null
    var txtLogradouro: TextView? = null
    var txtBairro: TextView? = null
    var txtLocalidade: TextView? = null
    var txtUF: TextView? = null
    var btnPesquisar: Button? = null
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPesquisar = findViewById(R.id.btnPesquisar) as Button?
        btnPesquisar!!.setOnClickListener {
            edtCEPinformado = findViewById(R.id.edtCEPInformado) as EditText?
            val cepinformado = edtCEPinformado!!.text.toString()
            txtBairro = findViewById(R.id.txtBairro) as TextView?
            txtLocalidade = findViewById(R.id.txtLocalidade) as TextView?
            txtLogradouro = findViewById(R.id.txtLogradouro) as TextView?
            txtUF = findViewById(R.id.txtUF) as TextView?
            val call: Call<Cep> =
                RetrofitInitializer().cep.select(cepinformado.toInt())
            call.enqueue(object : Callback<Cep?> {
                override fun onResponse(
                    call: Call<Cep?>?,
                    response: Response<Cep?>
                ) {
                    val cep: Cep = response.body()
                    txtUF!!.text = cep.uf
                    txtBairro!!.text = cep.bairro
                    txtLogradouro!!.text = cep.logradouro
                    txtLocalidade!!.text = cep.localidade
                    Log.i("Retrofit", response.body().getLogradouro())
                }

                override fun onFailure(call: Call<Cep?>?, t: Throwable?) {
                    Log.i("Retrofit", "falha")
                }
            })
        }
    }
}