package com.example.da_android;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

import androidx.annotation.NonNull;

import com.example.da_android.model.CategoryItem;
import com.example.da_android.model.Icon;
import com.example.da_android.model.Transaction;
import com.example.da_android.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DB {
    protected DatabaseReference databaseReference;

    public DB() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void addUser(User user) {
        databaseReference.child("USER").child(user.getEmail()).setValue(user);
    }
    void loginUser(User user, onCheckUserExistenceListener listener) {
        databaseReference.child("USER").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isExist = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User u = dataSnapshot.getValue(User.class);
                    if (user.getEmail().equals(u.getEmail()) && user.getPassword().equals(u.getPassword())) {
                        isExist = true;
                        break;
                    }
                }
                listener.onResult(isExist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onResult(false);
            }
        });
    }
    void checkUserExistence(User user, onCheckUserExistenceListener listener) {
        databaseReference.child("USER").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isExist = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User u = dataSnapshot.getValue(User.class);
                    if (user.getEmail().equals(u.getEmail())) {
                        isExist = true;
                        break;
                    }
                }
                listener.onResult(isExist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onResult(false);
            }
        });
    }
    public void addTransaction(Transaction transaction) {
        databaseReference.child("TRANSACTION")
                .child(transaction.getIdTrx())
                .setValue(transaction);
    }
    public void deleteTransaction(Transaction transaction) {
        databaseReference.child("TRANSACTION")
                .child(transaction.getIdTrx())
                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public void deleteAllTransaction(){
        databaseReference.child("TRANSACTION")
                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public void updateTransaction(Transaction transaction) {
        databaseReference.child("TRANSACTION")
                .child(transaction.getIdTrx())
                .setValue(transaction)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
    public void readDataTransaction(String username, final OnDataLoadedListener listener) {
        ArrayList<Transaction> list = new ArrayList<>();
        databaseReference.child("TRANSACTION").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Transaction item = dataSnapshot.getValue(Transaction.class);
                    if (item.getUserName().equals(username)) {
                        list.add(item);
                    }
                }
                listener.onDataLoaded(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onDataError(error.getMessage());
            }
        });
    }
    public ArrayList<Date> listDateTransaction(ArrayList<Transaction> transactions) {
        ArrayList<Date> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH);
        if(transactions != null)
        {
            try {
                dates.add(dateFormat.parse(transactions.get(0).getTrxDate()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            for (int i = 1; i<transactions.size(); i++)
            {
                Date date = null;
                try {
                    date = dateFormat.parse(transactions.get(i).getTrxDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                boolean flag = false;
                for (Date item : dates) {
                    if (item.equals(date)) {
                        flag = true;
                        break;
                    }
                }
                if(!flag)
                {
                    try {
                        dates.add(dateFormat.parse(transactions.get(i).getTrxDate()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return dates;
    }

    public interface OnDataLoadedListener {
        void onDataLoaded(ArrayList<Transaction> list);
        void onDataError(String errorMessage);
    }

    public void addAllIcon(Resources resources, String packageName) {
        ArrayList<Icon> drawableIcon = new ArrayList<>();

        Field[] drawables = R.drawable.class.getFields();

        for (Field f : drawables) {
            try {
                int resId = resources.getIdentifier(f.getName(), "drawable", packageName);
                Drawable drawable = resources.getDrawable(resId);
                if (drawable instanceof VectorDrawable && f.getName().startsWith("ctg_")) {
                    drawableIcon.add(new Icon(resId, f.getName()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Icon i : drawableIcon)
        {
            databaseReference.child("ICON")
                    .child(i.getId().toString())
                    .setValue(i);
        }
    }
    public void addCategory(CategoryItem item) {
        databaseReference.child("CATEGORY")
                .child(item.getIdCtg())
                .setValue(item);
    }
    public interface onCheckUserExistenceListener {
        void onResult(boolean isExist);
    }
    public void addAllCategory() {
        String idCtg = "CTG";
        addCategory(new CategoryItem(idCtg,"Phí đi lại", R.drawable.ctg_directions_bus_24, "Chi", R.color.medium_purple));
        addCategory(new CategoryItem(idCtg+"1","Ăn uống", R.drawable.ctg_restaurant_24, "Chi", R.color.holo_orange_dark));
        addCategory(new CategoryItem(idCtg+"2","Mua sắm", R.drawable.baseline_shopping_cart_24, "Chi", R.color.purple_700));
        addCategory(new CategoryItem(idCtg+"3","Điện thoại", R.drawable.ctg_phone_iphone_24, "Chi", R.color.black));
        addCategory(new CategoryItem(idCtg+"4","Y tế", R.drawable.ctg_medical_services_24, "Chi", R.color.aged_paper));
        addCategory(new CategoryItem(idCtg+"5","Tiền nhà", R.drawable.ctg_houses_24, "Chi", R.color.red_dark));
        addCategory(new CategoryItem(idCtg+"11","Phí giao lưu", R.drawable.ctg_local_bar_24, "Chi", R.color.limegreen));

        addCategory(new CategoryItem(idCtg+"6","Tiền lương", R.drawable.ctg_account_balance_wallet_24, "Thu", R.color.teal_200));
        addCategory(new CategoryItem(idCtg+"7","Tiền thưởng", R.drawable.ctg_card_giftcard_24, "Thu", R.color.pale_violet_red));
        addCategory(new CategoryItem(idCtg+"8","Tiền phụ cấp", R.drawable.ctg_add_box_24, "Thu", R.color.holo_orange_dark));
        addCategory(new CategoryItem(idCtg+"9","Đầu tư", R.drawable.ctg_assured_workload_24, "Thu", R.color.red_dark));
        addCategory(new CategoryItem(idCtg+"10","Thu nhập phụ", R.drawable.ctg_request_page_24, "Thu", R.color.purple_500));
    }
    public void readDataCategory(OnCategoryDataLoadedListener listener) {
        databaseReference.child("CATEGORY").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CategoryItem> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CategoryItem item = dataSnapshot.getValue(CategoryItem.class);
                    list.add(item);
                }
                listener.onCategoryDataLoaded(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onCategoryDataError(error.getMessage());
            }
        });
    }
    public void readDataCategoryChi(OnCategoryDataLoadedListener listener) {
        databaseReference.child("CATEGORY").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CategoryItem> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CategoryItem item = dataSnapshot.getValue(CategoryItem.class);
                    if (item.getType().equals("Chi")) {
                        list.add(item);
                    }
                }
                listener.onCategoryDataLoaded(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onCategoryDataError(error.getMessage());
            }
        });
    }

    public void readDataCategoryThu(OnCategoryDataLoadedListener listener) {
        databaseReference.child("CATEGORY").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CategoryItem> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CategoryItem item = dataSnapshot.getValue(CategoryItem.class);
                    if (item.getType().equals("Thu")) {
                        list.add(item);
                    }
                }
                listener.onCategoryDataLoaded(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onCategoryDataError(error.getMessage());
            }
        });
    }

    public interface OnCategoryDataLoadedListener {
        void onCategoryDataLoaded(ArrayList<CategoryItem> list);
        void onCategoryDataError(String errorMessage);
    }
    public CategoryItem searchCategorybyName(@NonNull ArrayList<CategoryItem> categoryItemArrayList, String name) {
        for (CategoryItem item : categoryItemArrayList)
        {
            if(item.getName().equals(name))
            {
                return item;
            }
        }
        return null;
    }
    public CategoryItem searchCategorybyId(@NonNull ArrayList<CategoryItem> categoryItemArrayList, String id) {
        for (CategoryItem item : categoryItemArrayList)
        {
            if(item.getIdCtg().equals(id))
            {
                return item;
            }
        }
        return null;
    }
    public String getIdTrx(){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("TRANSACTION");
        return databaseRef.push().getKey();
    }

}
