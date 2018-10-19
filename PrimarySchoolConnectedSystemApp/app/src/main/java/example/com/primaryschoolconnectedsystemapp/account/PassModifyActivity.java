package example.com.primaryschoolconnectedsystemapp.account;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import example.com.primaryschoolconnectedsystemapp.R;

/**
 * A login screen that offers login via email/password.
 */
public class PassModifyActivity extends AppCompatActivity {
    private UserModifyTask mAuthTask = null;

    // UI references.
    private EditText mAccountView;
    private EditText mPasswordOldView;
    private EditText mPasswordView;
    private EditText mPassword2View;
    private View mProgressView;
    private View mModifyFormView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();//返回
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_modify);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set up the login form.
        mAccountView = (EditText) findViewById(R.id.user_account);
        mAccountView.setText(helper.user.getAccount());
        mAccountView.setEnabled(false);

        mPasswordOldView = (EditText) findViewById(R.id.password_old);
        mPasswordView = (EditText) findViewById(R.id.user_name);
        mPassword2View = (EditText) findViewById(R.id.user_nick);
        mPasswordOldView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mUserModifyButton = (Button) findViewById(R.id.user_modify_button);
        mUserModifyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mModifyFormView = findViewById(R.id.modify_form);
        mProgressView = findViewById(R.id.modify_progress);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mAccountView.setError(null);
        mPasswordOldView.setError(null);

        // Store values at the time of the login attempt.
        String account = mAccountView.getText().toString();
        String password_old = mPasswordOldView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPassword2View.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password_old) || !isPasswordValid(password_old)) {
            mPasswordOldView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordOldView;
            cancel = true;
        }
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

        // Check for a valid email address.
        if (TextUtils.isEmpty(account)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            cancel = true;
        } else if (!isUserValid(account)) {
            mAccountView.setError(getString(R.string.error_invalid_user));
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
            mAuthTask = new UserModifyTask(account, password_old, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUserValid(String account) {
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

            mModifyFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mModifyFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mModifyFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mModifyFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserModifyTask extends AsyncTask<Void, Void, Boolean> {
        private final String account;
        private final String password;
        private final String password2;
        String response = "";

        UserModifyTask(String account, String password, String password2) {
            this.account = account;
            this.password = password;
            this.password2 = password2;
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
                if (user.modifyPassword(password2)) {
                    return true;
                } else {
                    return false;
                }
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: register the new account here.
            //return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordOldView.setError(getString(R.string.error_incorrect_password));
                mPasswordOldView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

