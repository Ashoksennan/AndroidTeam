package chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Assessment_Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;


/**
 * Created by priyadharshini on 31/07/2018.
 */

public class NewAssessment_Third_Fragment extends Fragment {
    View v;
    Button btn_next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_new_ass_third,container,false);

        btn_next = v.findViewById(R.id.btn_next);

        onClicks();

        return v;
    }

    public void onClicks()
    {

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Completed", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
