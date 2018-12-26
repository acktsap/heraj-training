package hera.example.highlevel;

import hera.api.model.AccountAddress;
import hera.api.model.Aer;
import hera.api.model.Aer.Unit;
import hera.api.model.Fee;
import hera.api.model.RawTransaction;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.wallet.Wallet;

public class TransactionTrainingHigh {

  public Transaction getTransaction(final Wallet wallet, final TxHash txHash) {
    return wallet.getTransaction(txHash);
  }

  public TxHash signAndCommit(final Wallet wallet,
      final AccountAddress recipient) {
    final RawTransaction rawTransaction = RawTransaction.newBuilder()
        .from(wallet.getCurrentAccount())
        .to(recipient)
        .amount("10000", Unit.AER)
        .nonce(wallet.incrementAndGetNonce())
        .fee(Fee.of(Aer.of("100", Unit.AER), 5))
        .build();
    final Transaction signed = wallet.sign(rawTransaction);
    final TxHash txHash = wallet.commit(signed);
    return txHash;
  }

  public TxHash sendAergo(final Wallet wallet,
      final AccountAddress recipient) {
    return wallet.send(recipient, Aer.of("100", Unit.GAER), Fee.getDefaultFee());
  }

}
