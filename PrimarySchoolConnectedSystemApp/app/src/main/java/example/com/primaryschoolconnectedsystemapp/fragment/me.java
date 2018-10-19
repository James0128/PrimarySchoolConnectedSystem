package example.com.primaryschoolconnectedsystemapp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import example.com.primaryschoolconnectedsystemapp.BuildConfig;
import example.com.primaryschoolconnectedsystemapp.R;
import example.com.primaryschoolconnectedsystemapp.account.HttpPost;
import example.com.primaryschoolconnectedsystemapp.account.InfoModifyActivity;
import example.com.primaryschoolconnectedsystemapp.account.LoginActivity;
import example.com.primaryschoolconnectedsystemapp.account.PassModifyActivity;
import example.com.primaryschoolconnectedsystemapp.account.helper;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static example.com.primaryschoolconnectedsystemapp.account.helper.user;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link me.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link me#newInstance} factory method to
 * create an instance of this fragment.
 */
public class me extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_AUTHOR
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView user_acc;
    TextView user_name;
    TextView user_nick;
    TextView user_xh;
    TextView user_class;

    private static final int REQUEST_CROP_PICTURE = 100;
    private static final int REQUEST_TAKE_PICTURE = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 102;
    private ImageView imageView = null;
    private File photo = null;
    private OnFragmentInteractionListener mListener;

    private ListView listView = null;
    private listViewAdapter listViewAdapter = null;
    private JSONArray listArray;

    @Override
    public void onResume() {
        super.onResume();
        query();
        updateInfo();
    }

    void query() {
        new quertList().execute((Void) null);//後臺,查詢數據
    }

    public class quertList extends AsyncTask<Void, Void, Boolean> {
        quertList() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            OkHttpClient okHttpClient = new OkHttpClient();
            //Form表单格式的参数传递
            FormBody formBody = new FormBody
                    .Builder()
                    .add("cmd", "feeList")//设置参数名称和参数值
                    .add("account", user.getAccount())//设置参数名称和参数值
                    .build();
            Request request = new Request
                    .Builder().
                    post(formBody)//Post请求的参数传递，此处是和Get请求相比，多出的一句代码</font>
                    .url(HttpPost.POST_URL)
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                String result = response.body().string();//回来的云数据

                if (helper.getStatusCode(result, "status") == HttpPost.STATUS_OK) {
                    String json = new String(result.getBytes(), "UTF-8");
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject != null && jsonObject.has("list")) {
                        listArray = jsonObject.getJSONArray("list");
                        return true;
                    }
                }
            } catch (Exception e) {
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                listViewAdapter.setData(listArray);
                listViewAdapter.notifyDataSetChanged();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
        }
    }

    public class listViewAdapter extends BaseAdapter {
        private JSONArray list;
        private Context context;

        listViewAdapter(Context context, JSONArray list) {
            this.context = context;
            this.list = list;
        }

        void setData(JSONArray list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return (list != null) ? list.length() : 0;
        }

        @Override
        public Object getItem(int position) {
            try {
                return (list != null) ? list.get(position) : null;
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            listViewAdapter.ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.fragment_me_listview, parent, false);
                viewHolder = new listViewAdapter.ViewHolder();
                viewHolder.imageLogo = (ImageView) view.findViewById(R.id.imageLogo);
                viewHolder.feeNumber = (TextView) view.findViewById(R.id.feeNumber);
                viewHolder.className = (TextView) view.findViewById(R.id.className);
                viewHolder.classFee = (TextView) view.findViewById(R.id.classFee);
                viewHolder.classFeeRecord = (TextView) view.findViewById(R.id.classFeeRecord);
                viewHolder.classFeeDetail = (TextView) view.findViewById(R.id.classFeeDetail);
                viewHolder.order = (Button) view.findViewById(R.id.order);
                view.setTag(viewHolder);
            } else {
                viewHolder = (listViewAdapter.ViewHolder) view.getTag();
            }

            try {
                final JSONObject obj = (JSONObject) list.get(position);
                viewHolder.feeNumber.setText(obj.getString("feeNumber"));
                viewHolder.className.setText(obj.getString("className"));
                viewHolder.classFee.setText("应缴费：" + obj.getString("classFee"));
                viewHolder.classFeeRecord.setText("已缴费：" + obj.getString("classFeeRecord"));
                viewHolder.classFeeDetail.setText(obj.getString("classFeeDetail"));

                Double fee = obj.getDouble("classFee") - obj.getDouble("classFeeRecord");
                if (fee > 0) {
                    viewHolder.order.setText("缴费");
                    viewHolder.order.setEnabled(true);
                } else {
                    viewHolder.order.setText("已缴费");
                    viewHolder.order.setEnabled(false);
                }

                viewHolder.order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//下单缴费啦
                        try {
                            new feeOrder(view.getContext(), obj.getString("id")).execute((Void) null);//後臺,查詢數據
                            query();//缴费后，数据更新一下
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }

            return view;
        }

        private class ViewHolder {
            ImageView imageLogo;
            TextView feeNumber;
            TextView className;
            TextView classFee;
            TextView classFeeRecord;
            TextView classFeeDetail;
            Button order;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            updateInfo();
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_CROP_PICTURE) {
                } else if (requestCode == REQUEST_TAKE_PICTURE) {
                    Glide.with(this).load(photo.getPath()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
                }
            } else if (resultCode == RESULT_CANCELED) {
                if (requestCode == REQUEST_CROP_PICTURE) {
                } else if (requestCode == REQUEST_TAKE_PICTURE) {
                }
            } else {
            }
        } catch (Exception e) {
        }
    }

    public void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            //new ConfirmationDialog().show(getChildFragmentManager(), FRAGMENT_DIALOG);
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.getActivity().getApplicationContext(), getResources().getString(R.string.request_camera_permission), Toast.LENGTH_LONG).show();
            } else {
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //android.os.FileUriExposedException: file:///storage/emulated/0/DCIM/IMG_1041503431.jpg exposed beyond app through ClipData.Item.getUri()
    private void captureImageUri() {
        try {
            photo = new File(getActivity().getExternalFilesDir(null), helper.getSharedPreferencesString(this.getActivity(), "account") + ".jpg");
            int permission = ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                if (android.os.Build.VERSION.SDK_INT < 24) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                } else {
                    //external-path change to root-path to fix can't read sdcard photos problem
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity().getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", photo));
                }

                startActivityForResult(intent, REQUEST_TAKE_PICTURE);
            } else if (permission == PackageManager.PERMISSION_DENIED) {
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + ".jpg");
        }
    }

    public me() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Me.
     */
    // TODO: Rename and change types and number of parameters
    public static me newInstance(String param1, String param2) {
        me fragment = new me();
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
        requestCameraPermission();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        user_acc = (TextView) view.findViewById(R.id.user_acc);
        user_name = (TextView) view.findViewById(R.id.user_name);
        user_nick = (TextView) view.findViewById(R.id.user_nick);
        user_xh = (TextView) view.findViewById(R.id.user_xh);
        user_class = (TextView) view.findViewById(R.id.user_class);

        LinearLayout change_pass = (LinearLayout) view.findViewById(R.id.change_pass);
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), PassModifyActivity.class);
                i.putExtra("account", helper.getSharedPreferencesString(view.getContext(), "account"));
                startActivityForResult(i, 0);
            }
        });
        LinearLayout change_info = (LinearLayout) view.findViewById(R.id.change_info);
        change_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), InfoModifyActivity.class);
                i.putExtra("account", helper.getSharedPreferencesString(view.getContext(), "account"));
                startActivityForResult(i, 0);
            }
        });

        imageView = (ImageView) view.findViewById(R.id.user_logo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImageUri();
            }
        });

        showLogo();

        listView = view.findViewById(R.id.listView);
        listViewAdapter = new listViewAdapter(view.getContext(), listArray);
        listView.setAdapter(listViewAdapter);
        query();

        TextView logout = (TextView) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), LoginActivity.class);
                i.putExtra("account", helper.user.getAccount());
                startActivity(i);
                me.this.getActivity().finish();
            }
        });

        updateInfo();
        return view;
    }

    void updateInfo() {
        user_acc.setText(helper.getSharedPreferencesString(this.getActivity(), "account"));
        user_name.setText(helper.user.getName());
        user_nick.setText(helper.user.userNick);
        user_xh.setText(helper.user.userXuehao);
        user_class.setText(helper.user.userClass);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {//相当于onResume
            showLogo();
            query();
        } else {//相当于onPause
        }
    }

    void showLogo() {
        photo = new File(this.getActivity().getExternalFilesDir(null), helper.getSharedPreferencesString(this.getActivity(), "account") + ".jpg");
        if (photo != null && new File(photo.getPath()).exists()) {
            Glide.with(this).load(photo.getPath()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class feeOrder extends AsyncTask<Void, Void, Boolean> {
        Context context;
        String id;

        feeOrder(Context context, String id) {
            this.context = context;
            this.id = id;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            OkHttpClient okHttpClient = new OkHttpClient();
            //Form表单格式的参数传递
            FormBody formBody = new FormBody
                    .Builder()
                    .add("cmd", "feeOrder")//设置参数名称和参数值
                    .add("account", user.getAccount())//设置参数名称和参数值
                    .add("id", id)//设置参数名称和参数值
                    .build();
            Request request = new Request
                    .Builder().
                    post(formBody)//Post请求的参数传递，此处是和Get请求相比，多出的一句代码</font>
                    .url(HttpPost.POST_URL)
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                String result = response.body().string();//回来的云数据

                if (helper.getStatusCode(result, "status") == HttpPost.STATUS_OK) {
                    return true;
                }
            } catch (Exception e) {
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Toast.makeText(context, "缴费成功!", Toast.LENGTH_SHORT).show();
                query();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
