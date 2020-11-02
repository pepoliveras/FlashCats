package com.flashcats.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.AsyncTask;
import android.util.Patterns;

import com.flashcats.data.LoginRepository;
import com.flashcats.data.Result;
import com.flashcats.data.model.LoggedInUser;
import com.flashcats.R;

import java.util.concurrent.ExecutionException;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    private Result<LoggedInUser> result;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        System.out.println("loginViewModel: Abans de cridar (Async)loginRepository.login");
        try {
            result = new login_async().execute(username, password).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("loginViewModel: Després de cridar (Async)loginRepository.login");

        //esperem aquí ?


        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName(),data.getUserId())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 3;
    }

    public void logout(String clau_sessio) {
        // can be launched in a separate asynchronous job
        System.out.println("loginViewModel: Abans de cridar (Async)loginRepository.logout");
        new logout_async().execute(clau_sessio);
        System.out.println("loginViewModel: Després de cridar (Async)loginRepository.logout");
    }

    private class login_async extends AsyncTask<String, Void, Result<LoggedInUser> > {

        @Override
        protected Result<LoggedInUser> doInBackground(String... params) {
            return loginRepository.login(params[0],params[1]) ;
        }

        @Override
        protected void onPostExecute(Result<LoggedInUser> login_result) {
            result = login_result;
        }


    }

    private class logout_async extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            loginRepository.logout(params[0]);
            return null;
        }
    }
}