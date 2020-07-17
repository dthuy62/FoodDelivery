package com.example.ddth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditUserActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText txt_fullname, txt_email, txt_phone;
    private TextView fullnameProfile, emailProfile;
    private Button update;
    private ImageView avatarUser;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user);
        fullnameProfile = findViewById(R.id.fullname_profile);
        emailProfile = findViewById(R.id.email_profile);
        txt_fullname = findViewById(R.id.fullname);
        txt_email = findViewById(R.id.gmail);
        txt_phone = findViewById(R.id.phone);
        update = findViewById(R.id.btn_update_user);
        avatarUser = findViewById(R.id.avatar_image);
//        String username = user.getDisplayName();
//        txt_fullname.setText(username);
        update.setOnClickListener(this);
    }
    public void loadProfile(){
        user = auth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren()){
                   String email = user.getEmail();
                   String name = ""+sn.child("name").getValue();
                   String phone = ""+sn.child("phone").getValue();
                   String image = ""+sn.child("image").getValue();
                   fullnameProfile.setText(name);
                   emailProfile.setText(email);
                   txt_email.setText(email);
                   txt_fullname.setText(name);
                   txt_phone.setText(phone);
                   try
                   {
                       Picasso.get().load(image).into(avatarUser);
                   }
                   catch (Exception e)
                   {
                       Picasso.get().load(R.drawable.profile).into(avatarUser);
                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updateProfile(){
        user = auth.getInstance().getCurrentUser();
        if(user!=null){
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("Users");
            String nameUser = txt_fullname.getText().toString();
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nameUser).build();

            if(TextUtils.isEmpty(nameUser)){
                txt_fullname.setError("Tên trống");
            }
            else {
                mUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EditUserActivity.this,"Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(EditUserActivity.this,"Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
        else
        {
            Toast.makeText(this, "No user signed in", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadProfile();
        updateProfile();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btn_update_user);
    }
}