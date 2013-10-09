package br.com.hind.servidor;

import com.br.hind.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Principal extends Activity {

	private Button btnCadastro;
	private Button btnMonitorar;
	private Button btnTransferir;
	private Button btnSair;

	private static int MENU_CONFIG = 1;
	private static int MENU_CHECK_CONNECTION = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);

		btnCadastro = (Button) findViewById(R.id.btnCadastro);
		btnMonitorar = (Button) findViewById(R.id.btnMonitorar);
		btnTransferir = (Button) findViewById(R.id.btnTransferir);
		btnSair = (Button) findViewById(R.id.btnSair);

		btnTransferir.setTextColor(Color.RED);

		btnCadastro.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), Cadastro.class));

			}
		});

		btnMonitorar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), Listar.class));

			}
		});

		btnTransferir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), Replicar.class));

			}
		});

		btnSair.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Builder msg = new Builder(Principal.this);
				msg.setMessage("Deseja Sair?");
				msg.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.exit(0);
								/*
								 * AlertDialog.Builder alerta = new
								 * AlertDialog.Builder(Principal.this);
								 * alerta.setMessage("Sucesso!");
								 * alerta.setTitle("Apagar");
								 * alerta.setNeutralButton("OK", null);
								 * alerta.show();
								 * 
								 * //Toast.makeText(getBaseContext(),
								 * "Sucesso!!!", Toast.LENGTH_SHORT).show();
								 * //finish();
								 */
							}

						});

				msg.setNegativeButton("Não", null);
				msg.show();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item = menu.add(0, MENU_CONFIG, 0, "Configuração");
		// item.setIcon(R.drawable.config);

		item = menu.add(0, MENU_CHECK_CONNECTION, 0, "Verificar Conexão");
		// item.setIcon(R.drawable.config);

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureID, MenuItem menu) {

		if (menu.getItemId() == MENU_CONFIG) {
			startActivity(new Intent(getBaseContext(), Config.class));
		}

		if (menu.getItemId() == MENU_CHECK_CONNECTION) {
			// checkConnection();
		}
		return true;
	}
}
