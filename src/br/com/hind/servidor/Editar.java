package br.com.hind.servidor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.hind.DAO.ServidorDAO;
import br.com.hind.VO.ServidorVO;

import com.br.hind.R;

public class Editar extends Activity {

	private int ID = 0;
	private EditText txtID;
	private Button btnAtualizar;
	private Button btnApagar;
	private EditText txtNomeServidor;
	private EditText txtNrIp;
	private EditText txtDescricao;
	private EditText txtEmail;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar);	
				
		Intent it = getIntent();
		ID = it.getIntExtra("codigo", 1);
		
		btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
		btnApagar = (Button)findViewById(R.id.btnApagar);
		
		final ServidorDAO dao = new ServidorDAO(getBaseContext());
		final ServidorVO vo = dao.getById(ID);
		
		txtID = (EditText) findViewById(R.id.txtID);
		txtNomeServidor = (EditText) findViewById(R.id.txtNomeServidor);
		txtNrIp = (EditText) findViewById(R.id.txtNrIp);
		txtDescricao = (EditText) findViewById(R.id.txtDescricao);
		txtEmail = (EditText) findViewById(R.id.txtEmail);

		
		txtID.setText(vo.getId().toString());
		txtNomeServidor.setText(vo.getNmServidor());
		txtNrIp.setText(vo.getIp());
		txtDescricao.setText(vo.getDescricao());
		txtEmail.setText(vo.getEmail());
		
		txtID.setEnabled(false);
		txtNomeServidor.setEnabled(false);
		txtNrIp.setEnabled(false);
		txtDescricao.setEnabled(false);
		txtEmail.setEnabled(false);
		
		btnApagar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(btnApagar.getText().toString().equals("Cancelar")){
					txtID.setEnabled(false);
					txtNomeServidor.setEnabled(false);
					txtNrIp.setEnabled(false);
					txtDescricao.setEnabled(false);
					txtEmail.setEnabled(false);
					btnApagar.setText("Apagar");					
					btnAtualizar.setText("Editar");
					btnApagar.setTextColor(Color.BLACK);
					btnAtualizar.setTextColor(Color.BLACK);
				}else{
					Builder msg = new Builder(Editar.this);
					msg.setMessage("Deseja Excluir o Servidor " + vo.getNmServidor() + "?");
					msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(dao.delete(vo)){
								
								AlertDialog.Builder alerta = new AlertDialog.Builder(Editar.this);
								alerta.setMessage("Excluído com Sucesso!");
								alerta.setTitle("Apagar");
								alerta.setNeutralButton("OK", null);
								alerta.show();
								
								//Toast.makeText(getBaseContext(), "Sucesso!!!", Toast.LENGTH_SHORT).show();
								//finish();
								txtDescricao.setText("");
								txtEmail.setText("");
								txtID.setText("");
								txtNomeServidor.setText("");
								txtNrIp.setText("");
								
								btnApagar.setEnabled(false);
								btnAtualizar.setEnabled(false);
							}
							
						}
					});
					
					msg.setNegativeButton("Não", null);
					msg.show();
					
				}
				
			}
		});
		
		btnAtualizar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (btnAtualizar.getText().toString().equals("Editar")) {

					txtID.setEnabled(false);
					txtNomeServidor.setEnabled(true);
					txtNrIp.setEnabled(true);
					txtDescricao.setEnabled(true);
					txtEmail.setEnabled(true);
					
					btnApagar.setTextColor(Color.RED);
					btnAtualizar.setText("Atualizar");
					
					btnApagar.setText("Cancelar");
					btnAtualizar.setTextColor(Color.RED);
					
				} else {

						ServidorVO vo = new ServidorVO();
						
						vo.setId(ID);
						vo.setNmServidor(txtNomeServidor.getText().toString());
						vo.setIp(txtNrIp.getText().toString());
						vo.setDescricao(txtDescricao.getText().toString());
						vo.setEmail(txtEmail.getText().toString());

						ServidorDAO dao = new ServidorDAO(getBaseContext());

						if (dao.update(vo)) {
							AlertDialog.Builder alerta = new AlertDialog.Builder(Editar.this);
							alerta.setMessage("Atualização com sucesso!");
							alerta.setTitle("Atualizar");
							alerta.setNeutralButton("OK", null);
							alerta.show();
							
							//Toast.makeText(getBaseContext(), "Atualização com Sucesso",
								//	Toast.LENGTH_SHORT).show();
							//finish();
							
							txtNomeServidor.setEnabled(false);
							txtNrIp.setEnabled(false);
							txtDescricao.setEnabled(false);
							txtEmail.setEnabled(false);
							
							btnAtualizar.setText("Editar");
							btnAtualizar.setTextColor(Color.BLACK);
							
							btnApagar.setText("Apagar");
							btnApagar.setTextColor(Color.BLACK);
							
							//btnApagar.setEnabled(false);
							
						}else {
							
							AlertDialog.Builder alerta = new AlertDialog.Builder(Editar.this);
							alerta.setMessage("Erro ao Atualizar!");
							alerta.setTitle("Erro!");
							alerta.setNeutralButton("OK", null);
							alerta.show();
							//Toast.makeText(getBaseContext(),"Erro ao Atualizar o Servidor.", Toast.LENGTH_SHORT).show();
							
						}

					}
				}
			
		});

	}

}
