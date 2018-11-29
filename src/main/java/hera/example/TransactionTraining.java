package hera.example;

import hera.api.model.AccountAddress;
import hera.api.model.ClientManagedAccount;
import hera.api.model.ServerManagedAccount;
import hera.api.model.Transaction;
import hera.api.model.TxHash;

public class TransactionTraining extends AbstractTraining {

  public Transaction getTransaction(TxHash txHash) {
    // TODO : implement it
    return null;
  }

  public TxHash commitWithLocalAccount(ClientManagedAccount account, AccountAddress recipient) {
    // TODO : implement it
    return null;
  }

  public TxHash commitWithServerAccount(ServerManagedAccount account, String password,
      AccountAddress recipient) {
    // TODO : implement it
    return null;
  }

  public TxHash send(AccountAddress sender, String senderPasasword, AccountAddress recipient,
      long amount) {
    // TODO : implement it
    return null;
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }

}
