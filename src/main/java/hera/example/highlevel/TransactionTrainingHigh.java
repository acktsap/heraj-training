package hera.example.highlevel;

import hera.api.model.AccountAddress;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.wallet.Wallet;

public class TransactionTrainingHigh {

  public Transaction getTransaction(final Wallet wallet, final TxHash txHash) {
    // TODO : use wallet to get it
    return null;
  }

  public TxHash signAndCommit(final Wallet wallet,
      final AccountAddress recipient) {
    // TODO : use wallet to sign and commit it
    return null;
  }

  public TxHash sendAergo(final Wallet wallet,
      final AccountAddress recipient) {
    // TODO : use wallet to send aergo
    return null;
  }

}
