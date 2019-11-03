package com.example.tugasbesar2.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tugasbesar2.R;

public class EndDialog extends DialogFragment {
    private TextView tv_score;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.end_page,container,false);
        this.tv_score = view.findViewById(R.id.tv_your_score);
        String akhir = "Your Score : "+this.getTag();
        this.tv_score.setText(akhir);
        this.button = view.findViewById(R.id.btn_restart);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().recreate();
                dismiss();
            }
        });
        return view;
    }

}
