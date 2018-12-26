package hera.example.lowlevel;

import hera.api.model.Account;
import hera.api.model.AccountAddress;
import hera.api.model.Transaction;
import hera.api.model.TxHash;

public class TransactionTrainingLow {

  public Transaction getTransaction(final TxHash txHash) {
    // TODO : make and use aergoClient to get it
    return null;
  }

  public TxHash commitWithNotInServerKeystore(final Account account,
      final AccountAddress recipient) {
    // TODO : make and use aergoClient to get it
    return null;
  }

  public TxHash commitWithInServerKeystore(final Account account, final String password,
      AccountAddress recipient) {
    // TODO : make and use aergoClient to get it
    return null;
  }

}
