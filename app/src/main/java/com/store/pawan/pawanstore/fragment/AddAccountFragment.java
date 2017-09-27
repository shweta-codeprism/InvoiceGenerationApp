package com.store.pawan.pawanstore.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.store.pawan.pawanstore.Adapter.FinaltemAdapter;
import com.store.pawan.pawanstore.Adapter.ItemAdapter;
import com.store.pawan.pawanstore.CustomWidgets.PStoreEditTextBold;
import com.store.pawan.pawanstore.CustomWidgets.PStoreEditTextItalic;
import com.store.pawan.pawanstore.CustomWidgets.PStoreTextViewItalic;
import com.store.pawan.pawanstore.R;
import com.store.pawan.pawanstore.model.EntryItem;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;


public class AddAccountFragment extends Fragment {



    ImageButton new_list;
    ImageButton show_bill;

    PStoreTextViewItalic no_item_text;
    RecyclerView item_list;
    ItemAdapter itemAdapter;


    ImageButton add_item;

    //Add Dialog
    Dialog add_account_dialog;




    public static List<EntryItem> items=new LinkedList<>();

    public static AddAccountFragment newInstance() {
        AddAccountFragment fragment = new AddAccountFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_item, container, false);
        no_item_text= view.findViewById(R.id.no_item_text);

        new_list=view.findViewById(R.id.refresh);
        new_list.setOnClickListener(click->{
            if(items!=null && !items.isEmpty()){
                items.clear();
                if(itemAdapter!=null){
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });

        show_bill = view.findViewById(R.id.show_bill);
        show_bill.setOnClickListener(aView-> {

        });
        item_list= view.findViewById(R.id.item_list);
        add_item= view.findViewById(R.id.add_item);
        add_item.setOnClickListener(view1 -> {
            if(add_account_dialog==null) {
                showAddItemDialog(new EntryItem());
            }


        });


        add_item.setOnClickListener(view12 -> showAddItemDialog(new EntryItem()));

        item_list.setLayoutManager(new GridLayoutManager(getContext(),1));

        itemAdapter=new ItemAdapter(getContext(), items, pos1 -> {
            if(add_account_dialog==null){
                showAddItemDialog(items.get(pos1));
            }
        });
        item_list.setAdapter(itemAdapter);
        return  view;
    }






    boolean select_instrument_show=true;
    int item_no=0;

    boolean inc=false,dec=false;
    void showAddItemDialog(EntryItem item){
        add_account_dialog=new Dialog(getActivity(),R.style.MyDialogTheme);
        add_account_dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        add_account_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        add_account_dialog.setContentView(R.layout.purchase_entry_dialog);

        final EntryHolder holder=new EntryHolder(add_account_dialog);


        Observable<CharSequence> name_obv= RxTextView.textChanges(holder.account_name);
        Observable<CharSequence> tot_amnt_obv= RxTextView.textChanges(holder.total_amnt);
        Observable<CharSequence> new_amnt_obv= RxTextView.textChanges(holder.update_amnt);


       Observable.combineLatest(name_obv,tot_amnt_obv,new_amnt_obv,(charSequence, charSequence2, charSequence3) -> {
           if(charSequence.length()!=0 && charSequence2.length()!=0){
               if(charSequence3.length()!=0 && !inc && !dec){
                   return false;
               }else{
                   return true;
               }
           }return false;
       }).subscribe(aBoolean -> {
           holder.add.setEnabled(true);
           holder.add.setTextColor(getResources().getColor(R.color.colorPrimary));
       });

       holder.cancel.setOnClickListener(view -> {
           add_account_dialog.dismiss();
       }
       );

       holder.add.setOnClickListener(view -> {
           add_account_dialog.dismiss();

       });



       holder.inc_amnt_btn.setOnClickListener(click->{
           if(inc){
               inc=false;
               holder.inc_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green);
           }else{
               inc=true;
               dec=false;
               holder.dec_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red_ring);
               holder.inc_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green_ring);
           }
       });

        holder.dec_amnt_btn.setOnClickListener(click->{
            if(dec){
                dec=false;
                holder.dec_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red);
            }else{
                dec=true;
                inc=false;
                holder.inc_amnt_btn.setBackgroundResource(R.drawable.bg_circle_green_ring);
                holder.dec_amnt_btn.setBackgroundResource(R.drawable.bg_circle_red_ring);
            }
        });

       add_account_dialog.setOnDismissListener(view->{

       });



       add_account_dialog.show();
       add_account_dialog.setCancelable(true);
    }




    class EntryHolder{


        EditText account_name;
        EditText total_amnt;
        EditText paid_amnt;
        EditText remaining_amnt;
        EditText update_amnt;
        LinearLayout inc_amnt;
        LinearLayout dec_amnt;
        ImageButton inc_amnt_btn;
        ImageButton dec_amnt_btn;
        Button add;
        Button cancel;



        EntryHolder(Dialog dialog){

            account_name=dialog.findViewById(R.id.account_name);
            total_amnt=dialog.findViewById(R.id.net_amnt);
            paid_amnt=dialog.findViewById(R.id.ret_amnt);
            remaining_amnt=dialog.findViewById(R.id.remain_amnt);
            update_amnt= dialog.findViewById(R.id.new_amnt);
            inc_amnt=dialog.findViewById(R.id.inc_amnt);
            dec_amnt=dialog.findViewById(R.id.dec_amnt);
            inc_amnt_btn=dialog.findViewById(R.id.inc_amnt_btn);
            dec_amnt_btn=dialog.findViewById(R.id.dec_amnt_bnt);
            add=dialog.findViewById(R.id.add);
            add.setEnabled(false);
            cancel=dialog.findViewById(R.id.cancel);

        }

    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("ACCOUNTS");
    }
}