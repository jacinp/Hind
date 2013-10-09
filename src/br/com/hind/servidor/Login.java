package br.com.hind.servidor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.hind.R;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		final EditText txtLogin = (EditText) findViewById(R.id.txtLogin);
		final EditText txtSenha = (EditText)findViewById(R.id.txtSenha);
				
		final SharedPreferences preferencia = getSharedPreferences("config", 0);
		
		Button btnLogar = (Button) findViewById(R.id.btnLogar);
		
		btnLogar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String login = preferencia.getString("login", "admin");
				String senha = preferencia.getString("senha", "123");
				
				if((txtLogin.getText().equals("") || (txtSenha.getText().equals("")))){
					Toast.makeText(getBaseContext(), "Login ou Senha Inválido!", Toast.LENGTH_SHORT).show();
					txtLogin.setText("");
					txtSenha.setText("");
					txtLogin.setFocusable(true);
				}else if(!login.equals(txtLogin.getText().toString()) || (!senha.equals(txtSenha.getText().toString()))){
					Toast.makeText(getBaseContext(), "Login ou Senha Inválido!", Toast.LENGTH_SHORT).show();
					txtLogin.setText("");
					txtSenha.setText("");
					txtLogin.setFocusable(true);
				}else{
						startActivity(new Intent(getBaseContext(), Principal.class));
				}
			}
		});
		
	}
	
}
