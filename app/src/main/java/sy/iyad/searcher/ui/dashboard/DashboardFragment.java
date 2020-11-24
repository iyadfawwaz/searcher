package sy.iyad.searcher.ui.dashboard;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import sy.iyad.searcher.R;
import sy.iyad.searcher.exception.GeneratorException;
import sy.iyad.searcher.models.Generator;
import sy.iyad.searcher.models.RecAdapter;


public class DashboardFragment extends Fragment {


    EditText address,admin,length,st,en;
    Button start,sp;
    TextView textView,total;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        address= view.findViewById(R.id.address);
         admin = view.findViewById(R.id.admin);
         length = view.findViewById(R.id.length);
         start = view.findViewById(R.id.starter);
         total = view.findViewById(R.id.total);

         sp = view.findViewById(R.id.sp);
         st  = view.findViewById(R.id.st);
         en = view.findViewById(R.id.en);

         textView = view.findViewById(R.id.warn);

        init();

        length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                dothis();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                total.setText("انتهت عملية البحث");
                String add = address.getText().toString();
                String ad = admin.getText().toString();
                String le = length.getText().toString();
                if (!add.isEmpty() && !ad.isEmpty() && !le.isEmpty()) {

                    int x = (int) Math.pow(26, Integer.parseInt(le));

                    startTesting(add,ad,Integer.parseInt(le));
                }else {
                    Toast.makeText(getContext(),"يرجى ادخال المعلومات المطلوبة ",Toast.LENGTH_LONG).show();
                }
            }
        });

        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total.setText("انتهت عملية البحث");
                String s1 = st.getText().toString();
                String s2 = en.getText().toString();
                String add = address.getText().toString();
                String ad = admin.getText().toString();
                String le = length.getText().toString();
                if (!s1.isEmpty() && !s2.isEmpty() && !add.isEmpty() && !ad.isEmpty() && !le.isEmpty()) {
                    char ch1 = s1.charAt(0);
                    char ch2 = s2.charAt(0);
                    startSpecialSearch(add,ad,Integer.parseInt(le),ch1,ch2);
                }else {
                    Toast.makeText(getContext(),"يرجى ادخال المعلومات المطلوبة ",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void startSpecialSearch(@NonNull String address, @NonNull String admin, int length, char start, char end) {

        Generator generator = new Generator(address,admin);
        try {
            generator.generateSpecial(length,start,end);
            generator.addOnCompleteListener(new Generator.OnCompleteListener() {
                @Override
                public void onComplete(@NotNull String password) {
                    textView.setText(password);
                }
            });
        } catch (GeneratorException e) {
            System.out.println(e.getMessage());
        }

    }

    private void dothis(){
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                String d = length.getText().toString();
                if (!d.isEmpty()){
               int f = (int) Math.pow(26,Integer.parseInt(d));
               total.setText("مجموع العمليات المحتملة"+f);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void init(){
        address.setText("2.2.2.2");
        admin.setText("admin");
    }
    private void startTesting(@NonNull String add,@NonNull String ad, int parseInt) {

        Generator generator = new Generator(add,ad);
        try {
            generator.generate(parseInt);
            generator.addOnCompleteListener(new Generator.OnCompleteListener() {
                @Override
                public void onComplete(@NotNull String password) {
                    textView.setText(password);
                }
            });
        } catch (GeneratorException e) {
            System.out.println(e.getMessage());
        }

    }
}