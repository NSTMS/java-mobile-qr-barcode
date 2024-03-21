package com.example.qr;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link barcode#newInstance} factory method to
 * create an instance of this fragment.
 */
public class barcode extends Fragment {
    public ImageView generated_image;
    public EditText editText;
    public Button button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public barcode() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment barcode.
     */
    // TODO: Rename and change types and number of parameters
    public static barcode newInstance(String param1, String param2) {
        barcode fragment = new barcode();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_barcode, container, false);

        button = rootView.findViewById(R.id.generate_btn);
        generated_image = rootView.findViewById(R.id.generated_image);
        editText = rootView.findViewById(R.id.editText);

        button.setOnClickListener(v -> {
            String msg = editText.getText().toString();
            if (msg.length() == 0) return;
            Bitmap qrCodeBitmap = generateBarCode(msg);
            generated_image.setImageBitmap(qrCodeBitmap);
        });

        Bitmap qrCodeBitmap = generateBarCode("Your data to encode");
        generated_image.setImageBitmap(qrCodeBitmap);

        return rootView;
    }

    private Bitmap generateBarCode(String data) {
        MultiFormatWriter qrCodeWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    data, BarcodeFormat.CODE_128, 400, 200);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(
                    width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y,
                            bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}