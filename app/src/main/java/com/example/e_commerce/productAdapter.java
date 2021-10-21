package com.example.e_commerce;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import static androidx.core.content.ContextCompat.startActivity;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class  productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder>  {

    private List<products> mData;
    private LayoutInflater mInflater;
    Context context;
    private DatabaseReference databaseReference;

    public productAdapter(Context context,List<products> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context =context;
    }

    @NonNull
    @Override
    public productAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productAdapter.ViewHolder holder, int position) {
        products t = mData.get(position);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        holder.product_title.setText(t.getTitle());
        holder.product_price.setText(t.getPrice()+" $");
        if (t.getOldPrice()==null) {
            holder.oldPrice.setVisibility(View.GONE);
            holder.discount.setVisibility(View.GONE);
        }else{
            holder.oldPrice.setText(t.getOldPrice() + " $");
            int off =(int) (100 - (Double.parseDouble(t.getPrice())/Double.parseDouble(t.getOldPrice()))*100);
            holder.discount.setText(off +"%  Off");

        }
        holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(this.context).load(t.ImagePath).into(holder.productImage);
        if(t.getQuantity()!=0){
            holder.addToCart.setVisibility(View.GONE);
            holder.product_quantity.setVisibility(View.VISIBLE);
            holder.product_quantity.append(String.valueOf(t.getQuantity()));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,cartActivity.class);
                intent.putExtra("check","productDetails");
                String [] details = {holder.product_title.getText().toString(),t.getOldPrice(),
                holder.product_price.getText().toString(),t.getDetails(),t.ImagePath.toString()};
                intent.putExtra("details",details);

                startActivity(context,intent,null);
            }
        });
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Add To Cart");

                final ImageView productImage = new ImageView(context);
                final Button plus = new Button(context);
                final EditText quantity = new EditText(context);
                final Button minus = new Button(context);

                LinearLayout lp1 = new LinearLayout(context);
                lp1.setOrientation(LinearLayout.VERTICAL);

                LinearLayout lp = new LinearLayout(context);
                lp.setOrientation(LinearLayout.HORIZONTAL);
                lp.setGravity(Gravity.CENTER);

                lp1.addView(productImage);
                lp1.addView(lp);

                lp.addView(plus);
                lp.addView(quantity);
                lp.addView(minus);

                productImage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));
                Glide.with(context).load(t.ImagePath).into(productImage);
                plus.setText("+");
                plus.setTextSize(25);
                minus.setText("-");
                minus.setTextSize(25);
                quantity.setText("1");
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quantity.setText(String.valueOf(Integer.parseInt(quantity.getText().toString())+1));
                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quantity.setText(String.valueOf(Integer.parseInt(quantity.getText().toString())-1));
                    }
                });


                alertDialog.setView(lp1);

                //alertDialog.setIcon(R.drawable.key);

                alertDialog.setPositiveButton("ADD TO CART",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("RestrictedApi")
                            public void onClick(DialogInterface dialog, int which) {
                              DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("ordered").push();
                              databaseReference.child("title").setValue(t.getTitle());
                              databaseReference.child("oldPrice").setValue(t.getOldPrice());
                              databaseReference.child("price").setValue(t.getPrice());
                              databaseReference.child("imagePath").setValue(t.getImagePath().toString());
                              databaseReference.child("quantity").setValue(quantity.getText().toString());
                            }
                        });

                alertDialog.show();

            }
        });

        if(!t.getCheck().equals("0")){
            holder.completeOrRefuseOrder.setVisibility(View.VISIBLE);
            Query orderedQuery = databaseReference.child("users").child(t.getCheck()).child("ordered").orderByChild("title").equalTo(t.getTitle());
            holder.refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Are you sure to refuse this order ?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            orderedQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot orderSnapshot: dataSnapshot.getChildren()) {
                                        orderSnapshot.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    });
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();


                }
            });

            holder.complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Are you sure this order completed ?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            orderedQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot orderSnapshot: dataSnapshot.getChildren()) {
                                        orderSnapshot.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    });
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_title;
        TextView oldPrice;
        TextView product_price;
        TextView product_quantity;
        TextView discount;
        ImageView productImage;
        LinearLayout addToCart;
        Button complete;
        Button refuse;
        CardView cardView;
        LinearLayout completeOrRefuseOrder;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_title = itemView.findViewById(R.id.product_title);
            product_price = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            addToCart = itemView.findViewById(R.id.btnAddToCart);
            product_quantity = itemView.findViewById(R.id.quantity);
            oldPrice = itemView.findViewById(R.id.product_old_price);
            cardView = itemView.findViewById(R.id.product_card);
            discount = itemView.findViewById(R.id.discount);
            complete = itemView.findViewById(R.id.complete_order);
            refuse = itemView.findViewById(R.id.refuse_order);
            completeOrRefuseOrder = itemView.findViewById(R.id.completeOrRefuse_order);


        }

    }

}

