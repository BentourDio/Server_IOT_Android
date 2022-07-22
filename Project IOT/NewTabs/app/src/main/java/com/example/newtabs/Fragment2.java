package com.example.newtabs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.slider.Slider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    EditText min2,max2;
    Button set2;
    public double gasValue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance( String param1, String param2 ) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }



    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View v = inflater.inflate( R.layout.fragment_2, container, false );

        min2 = v.findViewById( R.id.f2min );
        max2 = v.findViewById( R.id.f2max );
        set2 = v.findViewById( R.id.setf2 );

        set2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                Option1 tempGas = new Option1();

                String min2Save = min2.getText().toString();
                String max2Save = max2.getText().toString();

                Double max2Check = Double.parseDouble( max2Save );

                System.out.println("min :" + min2Save + "max:" + max2Save);

                float min2Value = Float.parseFloat( min2Save );
                float max2Value = Float.parseFloat( max2Save );

                Double random2Double = min2Value + (Double) (Math.random() * ((max2Value - min2Value) + 1.0 ) );

                if(random2Double > 0.0915) {
                    tempGas.setGasMeasurement( random2Double );
                }else{
                    random2Double = 0.0;
                    tempGas.setGasMeasurement( random2Double );

                }

                System.out.println("SENSOR MEASUREMENT: " +random2Double );


            }
        } );

        return v;

    }

    public interface OnFragmentInteractionListener {
    }
}