package hera.example;

import hera.api.model.Account;
import hera.api.model.AccountAddress;
import hera.api.model.AccountState;
import hera.api.model.EncryptedPrivateKey;
import hera.key.AergoKey;

public class AccountTraining extends AbstractTraining {

  public Account createWithKey() throws Exception {
    // TODO : implement it
    return null;
  }

  public Account createWithPassword(String password) {
    // TODO : implement it
    return null;
  }

  public AccountState getState(AccountAddress account) {
    // TODO : implement it
    return null;
  }

  public AergoKey importToLocal(String encodedEncryptedPrivateKey, String password)
      throws Exception {
    // TODO : implement it
    return null;
  }

  public Account importToServer(String encodedEncryptedPrivateKey, String password) {
    // TODO : implement it
    return null;
  }

  public EncryptedPrivateKey exportFromLocal(AergoKey key, String password) {
    // TODO : implement it
    return null;
  }

  public EncryptedPrivateKey exportFromServer(AccountAddress accountAddress, String password) {
    // TODO : implement it.
    return null;
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }

}

