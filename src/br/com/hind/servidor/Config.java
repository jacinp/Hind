package br.com.hind.servidor;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

import com.br.hind.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Config extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);

		final EditText txtLogin = (EditText) findViewById(R.id.txtLogin);
		final EditText txtSenha = (EditText) findViewById(R.id.txtSenha);
		final EditText txtServidor = (EditText) findViewById(R.id.txtServidor);
		SharedPreferences preferencia = getSharedPreferences("config", Context.MODE_PRIVATE);
		
		txtLogin.setText(preferencia.getString("login", "admin"));
		txtSenha.setText(preferencia.getString("senha", "123"));
		txtServidor.setText(preferencia.getString("server", "http://localhost"));
		
		Button btnAtualSenha = (Button) findViewById(R.id.btnAtualSenha);

		btnAtualSenha.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder msg = new Builder(Config.this);
				
				msg.setMessage("Deseja atualizar essas informações?");
				msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				
					@Override
					public void onClick(DialogInterface dialog, int which) {

						SharedPreferences preferencia = getSharedPreferences("config", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = preferencia.edit();
						editor.putString("login", txtLogin.getText().toString());
						editor.putString("senha", txtSenha.getText().toString());
						editor.putString("server", txtServidor.getText().toString());
						editor.commit();
						
						AlertDialog.Builder alerta = new AlertDialog.Builder(Config.this);
						alerta.setMessage("Atualização com sucesso!");
						alerta.setTitle("Atualizar Senha");
						alerta.setNeutralButton("OK", null);
						alerta.show();
						
						//Toast.makeText(getBaseContext(), "Sucesso", Toast.LENGTH_SHORT).show();
						//finish();
					}
				});
			msg.setNegativeButton("Não", null);
			msg.show();
			}
		});
	}
	
}
