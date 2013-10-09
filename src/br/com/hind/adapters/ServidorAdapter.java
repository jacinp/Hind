package br.com.hind.adapters;

import java.util.List;

import br.com.hind.VO.ServidorVO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServidorAdapter extends BaseAdapter {

	private Context ctx;
	private List<ServidorVO> lista;
	
	public ServidorAdapter(Context ctx, List<ServidorVO> lista){
		this.ctx = ctx;
		this.lista = lista;
	}
	
	@Override
	public int getCount() {

		return lista.size();
	}

	@Override
	public Object getItem(int position) {

		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		
		ServidorVO vo = (ServidorVO)getItem(position);

		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(android.R.layout.simple_list_item_checked, null);
	
		/*TextView txtID = (TextView)v.findViewById(com.br.hind.R.id.txtIdLista);
		txtID.setText(vo.getId().toString());
		*/
		
		TextView txtNomeLista = (TextView)v.findViewById(android.R.id.text1);
		txtNomeLista.setText(vo.getNmServidor().toString());
		
		
		return v;
	}

}
