package com.example.newtabs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

    EditText min,max;
    Button set;
    public double smokeValue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fragment1() {
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
    public static Fragment1 newInstance( String param1, String param2 ) {
        Fragment1 fragment = new Fragment1();
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
        View v = inflater.inflate( R.layout.fragment_1, container, false );

        min = v.findViewById( R.id.f1min );
        max = v.findViewById( R.id.f1max );
        set = v.findViewById( R.id.setf1 );

        set.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                Option1 tempSmoke = new Option1();

                String min1Save = min.getText().toString();
                String max1Save = max.getText().toString();

                Double maxCheck = Double.parseDouble( max1Save );

                System.out.println("min :" + min1Save + "max:" + max1Save);

                float minValue = Float.parseFloat( min1Save );
                float maxValue = Float.parseFloat( max1Save );

                Double randomDouble = minValue + (Double) (Math.random() * ((maxValue - minValue) + 1.0 ) );

                if(randomDouble > 0.14) {

                    tempSmoke.setSmokeMeasurement( randomDouble );
                }else{
                    randomDouble = 0.0;
                    tempSmoke.setSmokeMeasurement( randomDouble );
                }


                System.out.println("SENSOR MEASUREMENT: " +randomDouble );



            }
        } );

     return v;

   }


    public interface OnFragmentInteractionListener {
    }

}