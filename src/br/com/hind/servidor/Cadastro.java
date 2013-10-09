package br.com.hind.servidor;

import br.com.hind.DAO.ServidorDAO;
import br.com.hind.VO.ServidorVO;

import com.br.hind.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastro extends Activity implements Runnable {

	private Button btnCadServidor;
	private EditText txtNomeServidor;
	private EditText txtNrIp;
	private EditText txtDescricao;
	private EditText txtEmail;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro);
		
		btnCadServidor = (Button) findViewById(R.id.btnCadServidor);

		txtNomeServidor = (EditText) findViewById(R.id.txtNomeServidor);
		txtNrIp = (EditText) findViewById(R.id.txtNrIp);
		txtDescricao = (EditText) findViewById(R.id.txtDescricao);
		txtEmail = (EditText) findViewById(R.id.txtEmail);

		txtNomeServidor.setEnabled(false);
		txtNrIp.setEnabled(false);
		txtDescricao.setEnabled(false);
		txtEmail.setEnabled(false);
				
		btnCadServidor.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (btnCadServidor.getText().toString().equals("Novo")) {

					txtNomeServidor.setText("");
					txtNrIp.setText("");
					txtDescricao.setText("");
					txtEmail.setText("");

					txtNomeServidor.setEnabled(true);
					txtNrIp.setEnabled(true);
					txtDescricao.setEnabled(true);
					txtEmail.setEnabled(true);

					btnCadServidor.setText("Cadastrar");
					btnCadServidor.setTextColor(Color.RED);
					txtNomeServidor.setFocusable(true);

				} else {

					if ((txtNomeServidor.getText().toString().equals(""))
							|| (txtNrIp.getText().toString().equals(""))
							|| (txtDescricao.getText().toString().equals(""))
							|| (txtEmail.getText().toString().equals(""))) {
						
						AlertDialog.Builder alerta = new AlertDialog.Builder(Cadastro.this);
						alerta.setMessage("Cadastre todos os campos");
						alerta.setTitle("Cadastro");
						alerta.setNeutralButton("OK", null);
						alerta.show();
						//Toast.makeText(getBaseContext(), "Cadastre todos os campos", Toast.LENGTH_SHORT).show();
						//btnCadServidor.setTextColor(Color.BLACK);
						
					} else {

						ServidorVO vo = new ServidorVO();

						vo.setNmServidor(txtNomeServidor.getText().toString());
						vo.setIp(txtNrIp.getText().toString());
						vo.setDescricao(txtDescricao.getText().toString());
						vo.setEmail(txtEmail.getText().toString());

						ServidorDAO dao = new ServidorDAO(getBaseContext());

						if (dao.insert(vo)) {
							AlertDialog.Builder alerta = new AlertDialog.Builder(Cadastro.this);
							alerta.setMessage("Cadastro com sucesso!");
							alerta.setTitle("Cadastro");
							alerta.setNeutralButton("OK", null);
							alerta.show();
							//Toast.makeText(getBaseContext(), "Cadastro com Sucesso",
							//		Toast.LENGTH_SHORT).show();
							txtNomeServidor.setEnabled(false);
							txtNrIp.setEnabled(false);
							txtDescricao.setEnabled(false);
							txtEmail.setEnabled(false);
							
							btnCadServidor.setText("Novo");
							btnCadServidor.setTextColor(Color.BLACK);
						}else {
							Toast.makeText(getBaseContext(),
									"Erro ao Cadastrar", Toast.LENGTH_SHORT)
									.show();
						}

					}
				}
			}
		});

	}

	// verifica a conexão
		@SuppressWarnings("unused")
		private boolean checkConnection() {
			
			ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

			if ((conn.getNetworkInfo(0).getState() == State.CONNECTED)
					|| (conn.getNetworkInfo(1).getState() == State.CONNECTED)) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void run() {
					
				/*StringBuilder strURL = new StringBuilder();
				strURL.append("http://192.168.56.1/inserir.php?servidor=");
				strURL.append(txtNmServidor.getText().toString());
										
				strURL.append("&nrIp=");
				strURL.append(txtEmail.getText().toString());
				
				strURL.append("&descricao=");
				strURL.append(txtDescricao.getText().toString());
				
				strURL.append("&email=");
				strURL.append(txtEmail.getText().toString());
				
				//transforma string em URL
				try {															
					URL url = new URL(strURL.toString());								
					HttpURLConnection http = (HttpURLConnection) url.openConnection();
					http.setConnectTimeout(20000);
					InputStreamReader ipr = new InputStreamReader(http.getInputStream());
					BufferedReader bf = new BufferedReader(ipr);
					
					if(bf.readLine().equals("Y")){
						handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(1);
				}*/
			}
			
		
		/*private Handler handler = new Handler(){
			
			@Override
			public void handleMessage(Message msg){
				// se der erro o handle manter acesso
				if(msg.what == 0){
					pg.dismiss();
					Toast.makeText(getBaseContext(), "Sucesso ao cadastrar no servidor.", Toast.LENGTH_LONG).show();
					finish();
				}else if(msg.what == 1){
					pg.dismiss();
					Toast.makeText(getBaseContext(), "Erro ao cadastrar no servidor, tente novamente", Toast.LENGTH_LONG).show();
				}
				
			}
		};*/
}
