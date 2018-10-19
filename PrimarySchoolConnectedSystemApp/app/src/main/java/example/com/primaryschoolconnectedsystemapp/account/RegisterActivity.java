package example.com.primaryschoolconnectedsystemapp.account;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import example.com.primaryschoolconnectedsystemapp.R;

/**
 * A login screen that offers login via account/password.
 */
public class RegisterActivity extends AppCompatActivity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mAuthTask = null;

    // UI references.
    private EditText mAccountView;
    private EditText mNameView;
    private EditText mNickView;
    private EditText mPasswordView;
    private EditText mPassword2View;
    private View mProgressView;
    private View mRegisterFormView;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the login form.
        mAccountView = (EditText) findViewById(R.id.user_account);
        mNameView = (EditText) findViewById(R.id.user_password);
        mNickView = (EditText) findViewById(R.id.user_xuehao);

        mPasswordView = (EditText) findViewById(R.id.user_name);
        mPassword2View = (EditText) findViewById(R.id.user_nick);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_btn);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

        initUserPass();
    }

    void initUserPass() {
        String account = helper.getSharedPreferencesString(getApplication(), "account");
        String pass = helper.getSharedPreferencesString(getApplication(), "password");

        if (!account.isEmpty())
            mAccountView.setText(account);
        else
            mAccountView.setText("demo");//用户

        if (!pass.isEmpty())
            mPasswordView.setText(pass);
        else
            mPasswordView.setText("123456");//密码
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid account, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mAccountView.setError(null);
        mPasswordView.setError(null);
        mPassword2View.setError(null);

        // Store values at the time of the login attempt.
        String account = mAccountView.getText().toString();
        String name = mNameView.getText().toString();
        String nick = mNickView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPassword2View.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password2) || !isPasswordValid(password2)) {
            mPassword2View.setError(getString(R.string.error_invalid_password));
            focusView = mPassword2View;
            cancel = true;
        }

        if (!password2.equals(password)) {
            mPassword2View.setError(getString(R.string.error_invalid_password2));
            focusView = mPassword2View;
            cancel = true;
        }

        // Check for a valid account address.
        if (TextUtils.isEmpty(account)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            cancel = true;
        } else if (!isUnameValid(account)) {
            mAccountView.setError(getString(R.string.error_invalid_account));
            focusView = mAccountView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserRegisterTask(account, name, nick, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUnameValid(String account) {
        //TODO: Replace this with your own logic
        return account.length() >= 3;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final String account;
        private final String name;
        private final String nick;
        private final String password;
        String response = "";

        UserRegisterTask(String account, String name, String nick, String password) {
            this.account = account;
            this.name = name;
            this.nick = nick;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(1000);

                User user = new User();
                user.setAccount(account);
                user.setPassword(password);
                user.setName(name);
                if (user.register()) {
                    return true;
                } else {
                    return false;
                }
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: register the new account here.
            //return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                helper.putSharedPreferencesString(getApplication(), "account", mAccountView.getText().toString());
                helper.putSharedPreferencesString(getApplication(), "password", mPasswordView.getText().toString());

                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                i.putExtra("account", account);
                startActivity(i);
                // close this activity
                finish();
            } else {
                mAccountView.setError(getString(R.string.error_dup_account));
                mAccountView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

