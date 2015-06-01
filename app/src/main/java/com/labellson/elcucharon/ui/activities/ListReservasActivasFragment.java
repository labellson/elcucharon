package com.labellson.elcucharon.ui.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.Reserva;
import com.labellson.elcucharon.model.User;
import com.labellson.elcucharon.ui.adapter.ListReservaAdapter;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListReservasActivasFragment extends Fragment implements View.OnLongClickListener{

    private RecyclerView recView;
    private List<Reserva> mReservas;
    private ListReservaAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_reservas, container, false);

        recView = (RecyclerView) view.findViewById(R.id.rec_view_list_reserva);
        recView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ListReservaAdapter(mReservas);
        mAdapter.setOnLongClickListener(ListReservasActivasFragment.this);
        recView.setAdapter(mAdapter);

        return view;
    }

    public void setmReservas(List<Reserva> mReservas) {
        this.mReservas = mReservas;
    }

    @Override
    public boolean onLongClick(View v) {
        DeleteReservaDialogFragment df = new DeleteReservaDialogFragment();
        df.setIdReserva(mReservas.get(recView.getChildAdapterPosition(v)).getId(), recView.getChildAdapterPosition(v));
        df.show(getFragmentManager(), "DeleteReserva");
        return true;
    }

    private class DeleteReservaTask extends AsyncTask<Void, Void,Void>{

        private int idReserva;
        private int position;

        public DeleteReservaTask(int idReserva, int position) {
            this.idReserva = idReserva;
            this.position = position;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getActivity(), "Reserva Cancelada", Toast.LENGTH_SHORT).show();
            mReservas.remove(position);
            mAdapter.notifyItemRemoved(position);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                CucharonRestApi.deleteReserva(idReserva, User.load(getActivity()).getAuth());
            } catch (IOException e) {
                Log.e("IOException", "DeleteReservaTask", e);
            }
            return null;
        }
    }

    public class DeleteReservaDialogFragment extends DialogFragment {

        private int idReserva;
        private int position;

        public void setIdReserva(int idReserva, int position){
            this.idReserva = idReserva;
            this.position = position;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.cancelar_reserva)).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new DeleteReservaTask(idReserva, position).execute();
                }
            }).setNegativeButton("No", null);
            return builder.create();
        }
    }
}
