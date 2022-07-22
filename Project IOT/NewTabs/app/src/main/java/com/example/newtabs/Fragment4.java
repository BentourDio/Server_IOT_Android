package com.example.newtabs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment4 extends Fragment {

    EditText min4,max4;
    Button set4;
    public double uvValue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment4.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment4 newInstance( String param1, String param2 ) {
        Fragment4 fragment = new Fragment4();
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
        View v = inflater.inflate( R.layout.fragment_4, container, false );

        min4 = v.findViewById( R.id.f4min );
        max4 = v.findViewById( R.id.f4max );
        set4 = v.findViewById( R.id.setf4 );

        set4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                Option1 tempUV = new Option1();

                String min4Save = min4.getText().toString();
                String max4Save = max4.getText().toString();

                Double max4Check = Double.parseDouble( max4Save );

                System.out.println("min :" + min4Save + "max:" + max4Save);

                float min4Value = Float.parseFloat( min4Save );
                float max4Value = Float.parseFloat( max4Save );

                Double random4Double = min4Value + (Double) (Math.random() * ((max4Value - min4Value) + 1.0 ) );

                if(random4Double > 6){

                    tempUV.setUVMeasurement( random4Double );
                }else{
                    random4Double = 0.0;
                    tempUV.setUVMeasurement( random4Double );

                }





                System.out.println("SENSOR MEASUREMENT: " +random4Double );


            }
        } );

        return v;

    }

    public interface OnFragmentInteractionListener {
    }
}