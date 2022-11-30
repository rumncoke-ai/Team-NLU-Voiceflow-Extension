package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.Account;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.UserInfo;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Repository.AccountRepository;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Repository.ApiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private ApiRepository apiRepository;

    private AccountRepository accountRepository;

//    public API getAPIInfo() {
//        apiRepository.findAll();
//    }


    // Inserts the API information -- both apiKey and apiVersion-- in MongoDB

    /**
     * Method storeAPIInfo
     *  Inserts the API information -- both apiKey and apiVersion-- in MongoDB
     */
    public void storeAPIInfo(API api) {
        apiRepository.insert(api);
    }


    /**
     * Method storeAccountInfo
     *  Inserts the Account information -- email address, password, and diagram ID-- in MongoDB
     */
    public void storeAccountInfo(Account account) {
        accountRepository.insert(account);
    }


    /**
     * Method storeUserInfo
     *  Inserts the Account information -- email address, password, and diagram ID-- in MongoDB
     */
    public void storeUserInfo(UserInfo user) {
        storeAPIInfo(new API(user.getApiKey(), user.getApiVersion()));
        storeAccountInfo(new Account(user.getEmailAddress(), user.getPassword(), user.getDiagramID()));
    }

    public void deleteALl() {
        apiRepository.deleteAll();
        accountRepository.deleteAll();
    }

    public List<API> getAPIList() {
        return apiRepository.findAll();
    }
}
