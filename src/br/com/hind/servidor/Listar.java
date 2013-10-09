package br.com.hind.servidor;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.hind.DAO.ServidorDAO;
import br.com.hind.VO.ServidorVO;
import br.com.hind.adapters.ServidorAdapter;

import com.br.hind.R;

public class Listar extends Activity {

	private int ID = 0;
	
	private static int MENU_APAGAR = 0;
	private static int MENU_EDITAR = 1;
	private static int MENU_PING = 2;	
	private static int MENU_ONLINE = 3;
	private static int MENU_LOCAL = 4;
	private int idItem = 0;
	
	private ListView ltw;
	List<ServidorVO> lista = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listar);
		
		Intent it = getIntent(); // retorna a instancia que foi invocada que é
		ID = it.getIntExtra("codigo", 1);
		
		final Button btnApagarLista = (Button) findViewById(R.id.btnApagarLista);
		
		ltw = (ListView)findViewById(R.id.ltvDados);
		
		//ltw.setCacheColorHint(Color.TRANSPARENT);
		ltw.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		
		registerForContextMenu(ltw);
		
		
		ltw.setOnItemClickListener(new OnItemClickListener() {

			ServidorDAO dao = new ServidorDAO(getBaseContext());
			ServidorVO vo = dao.getById(ID);
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//btnApagarLista.setVisibility(0);	
				btnApagarLista.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {									
		
							Builder msg = new Builder(Listar.this);
							msg.setMessage("Deseja Excluir o Servidor" + vo.getNmServidor() + "?");
							msg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									if(dao.delete(vo)){
										Toast.makeText(getBaseContext(), "Sucesso!!!", Toast.LENGTH_SHORT).show();
										finish();
									}
									
								}
							});
							
							msg.setNegativeButton("NÃO", null);
							msg.show();						
					}
				});			
			}
			
		});
	}
	
		

	//protected void onListItemClick(ListView l, View v, int position, long id){
	//	ServidorVO vo = (ServidorVO)l.getAdapter().getItem(position);
	//	startActivity(new Intent("EdicaoHind").putExtra("codigo", vo.getId()));
	//}
	
	@Override
	public void onResume(){
		super.onResume();
		ServidorDAO dao = new ServidorDAO(getBaseContext());
		lista = dao.getAll();
		ltw.setAdapter(new ServidorAdapter(getBaseContext(), lista));
	}
	
	public void Apagar_click(View v){
		String nomes = "";
		SparseBooleanArray checkeds = ltw.getCheckedItemPositions();
		
		for(int i = 0; i < checkeds.size(); i++){
			nomes += lista.get(checkeds.keyAt(i)).getNmServidor();
		}
		Toast.makeText(getBaseContext(), nomes, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		menu.setHeaderTitle(lista.get(info.position).getNmServidor());
		menu.add(Menu.NONE, MENU_EDITAR,0,"Editar");
		menu.add(Menu.NONE, MENU_APAGAR,0,"Apagar");
		menu.add(Menu.NONE, MENU_PING,0,"Monitorar");
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuItem item = menu.add(0, MENU_ONLINE, 0, "ONLINE");
		
		item = menu.add(0, MENU_LOCAL, 1, "LOCAL");
		return true;
	}
	
	/*public boolean onMenuItemSelected(int featureID, MenuItem menu){
		
		if(menu.getItemId() == MENU_ONLINE){
				StringBuilder strURL = new StringBuilder();
				strURL.append("http://192.168.56.1/listar.php");
				
				try {															
					URL url = new URL(strURL.toString());								
					HttpURLConnection http = (HttpURLConnection) url.openConnection();
					InputStreamReader ipr = new InputStreamReader(http.getInputStream());
					BufferedReader bf = new BufferedReader(ipr);
					
					List<String> lista = new ArrayList<String>();
					
					
					String linha = "";
					while((linha = bf.readLine()) != null){
						lista.add(linha.split("\\|")[0]);
					}
					
					ArrayAdapter<String> ad = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, lista);
					ltw.setAdapter(ad);
					
				} catch (Exception e) {
					
				}
		}
		
		if(menu.getItemId() == MENU_LOCAL){
			ServidorDAO dao = new ServidorDAO(getBaseContext());
			lista = dao.getAll();
			ltw.setAdapter(new ServidorAdapter(getBaseContext(), lista));
			
			
		}
		return true;
	}
	*/
	@Override
	public boolean onContextItemSelected(MenuItem item){
		
		AdapterView.AdapterContextMenuInfo info = 	(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		
		idItem = lista.get(info.position).getId();
		
		if(item.getItemId() == MENU_EDITAR){
			Intent it = new Intent(getBaseContext(), Editar.class);
			it.putExtra("codigo", idItem);
			startActivity(it);
		}else if(item.getItemId() == MENU_APAGAR){
			Builder msg = new Builder(Listar.this);
			msg.setMessage("Deseja excluir este Servidor?");
			msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ServidorDAO dao = new ServidorDAO(getBaseContext());
					ServidorVO servidor = dao.getById(idItem);
					
					if(dao.delete(servidor) == true){
						
						AlertDialog.Builder alerta = new AlertDialog.Builder(Listar.this);
						alerta.setMessage("Excluído com sucesso!");
						alerta.setTitle("Exclusão");
						alerta.setNeutralButton("OK", null);
						alerta.show();
						
						//Toast.makeText(getBaseContext(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
						ltw.setAdapter(new ServidorAdapter(getBaseContext(), dao.getAll())); //atualiza a listagem
						
					}
				}
			} );
			
			msg.setNegativeButton("Não", null);
			msg.show();
		}else{
			Intent it = new Intent(getBaseContext(), Ping.class);
			it.putExtra("codPing", idItem);
			startActivity(it);
			
		}
		return super.onContextItemSelected(item);
	}
	
}
