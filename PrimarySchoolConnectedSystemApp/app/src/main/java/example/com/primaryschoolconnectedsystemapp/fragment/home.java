package example.com.primaryschoolconnectedsystemapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import example.com.primaryschoolconnectedsystemapp.R;
import example.com.primaryschoolconnectedsystemapp.account.HttpPost;
import example.com.primaryschoolconnectedsystemapp.account.helper;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static example.com.primaryschoolconnectedsystemapp.account.helper.user;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView listView = null;
    private listViewAdapter listViewAdapter = null;
    private JSONArray listArray;

    public class quertList extends AsyncTask<Void, Void, Boolean> {
        quertList() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            OkHttpClient okHttpClient = new OkHttpClient();
            //Form表单格式的参数传递
            FormBody formBody = new FormBody
                    .Builder()
                    .add("cmd", "scoreList")//设置参数名称和参数值
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

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.listView);
        listViewAdapter = new listViewAdapter(view.getContext(), listArray);
        listView.setAdapter(listViewAdapter);
        query();
        return view;
    }

    void query() {
        new quertList().execute((Void) null);//後臺,查詢數據
    }

    @Override
    public void onResume() {
        super.onResume();
        query();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {//相当于onResume
            query();
        } else {//相当于onPause
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
            ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.fragment_home_listview, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.imageLogo = (ImageView) view.findViewById(R.id.imageLogo);
                viewHolder.studentName = (TextView) view.findViewById(R.id.studentName);
                viewHolder.studentNumber = (TextView) view.findViewById(R.id.studentNumber);
                viewHolder.studentClass = (TextView) view.findViewById(R.id.studentClass);
                viewHolder.subjectName = (TextView) view.findViewById(R.id.subjectName);
                viewHolder.examinationScore = (TextView) view.findViewById(R.id.examinationScore);
                viewHolder.examinationRank = (TextView) view.findViewById(R.id.examinationRank);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            try {
                final JSONObject obj = (JSONObject) list.get(position);
                viewHolder.studentName.setText(obj.getString("studentName"));
                viewHolder.studentNumber.setText(obj.getString("studentNumber"));
                viewHolder.studentClass.setText(obj.getString("studentClass"));
                viewHolder.subjectName.setText(obj.getString("subjectName"));
                viewHolder.examinationScore.setText(obj.getString("examinationScore"));
                viewHolder.examinationRank.setText("排名：" + obj.getString("examinationRank"));
            } catch (Exception e) {
            }

            return view;
        }

        private class ViewHolder {
            ImageView imageLogo;
            TextView studentName;
            TextView studentNumber;
            TextView studentClass;
            TextView subjectName;
            TextView examinationScore;
            TextView examinationRank;
        }
    }
}
