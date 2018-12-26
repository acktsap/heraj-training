package hera.example.lowlevel;

import hera.api.model.Account;
import hera.api.model.AccountAddress;
import hera.api.model.AccountState;

public class AccountTrainingLow {

  public AccountState getState(final AccountAddress accountAddress) {
    // TODO : make and use aergoClient to get it
    return null;
  }



  public Account createWithKey() {
    // TODO : use AccountFactory
    return null;
  }

  public Account createWithEncryptedKey(final String encodedEncryptedPrivateKey,
      final String password) {
    // TODO : use AergoKey
    return null;
  }

  public String exportWithKeyHoldingAccount(final Account account, final String password) {
    // TODO : use account
    return null;
  }



  public Account createWithAddress(final AccountAddress accountAddress) {
    // TODO : use AccountFactory
    return null;
  }



  public Account createOnServerKeyStore(final String password) {
    // TODO : make and use aergoClient
    return null;
  }

  public Account createByImportingToServerKeyStore(final String encodedEncryptedPrivateKey,
      final String password) {
    // TODO : make and use aergoClient
    return null;
  }

  public String exportFromServer(final AccountAddress accountAddress, final String password) {
    // TODO : make and use aergoClient
    return null;
  }

}

