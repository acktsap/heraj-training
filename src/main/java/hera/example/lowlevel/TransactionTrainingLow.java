package hera.example.lowlevel;

import hera.api.model.Account;
import hera.api.model.AccountAddress;
import hera.api.model.Aer;
import hera.api.model.Aer.Unit;
import hera.api.model.Authentication;
import hera.api.model.Fee;
import hera.api.model.RawTransaction;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;

public class TransactionTrainingLow {

  public Transaction getTransaction(final TxHash txHash) {
    final AergoClient aergoClient = new AergoClientBuilder()
        .withNonBlockingConnect()
        .withEndpoint("localhost:7845")
        .build();
    final Transaction transaction = aergoClient.getTransactionOperation().getTransaction(txHash);
    aergoClient.close();
    return transaction;
  }

  public TxHash commitWithNotInServerKeystore(final Account account,
      final AccountAddress recipient) {
    final AergoClient aergoClient = new AergoClientBuilder()
        .withNonBlockingConnect()
        .withEndpoint("localhost:7845")
        .build();
    final RawTransaction rawTransaction = RawTransaction.newBuilder()
        .from(account)
        .to(recipient)
        .amount("10000", Unit.AER)
        .nonce(account.incrementAndGetNonce())
        .fee(Fee.of(Aer.of("100", Unit.AER), 5))
        .build();
    final Transaction signed = aergoClient.getAccountOperation().sign(account, rawTransaction);
    final TxHash txHash = aergoClient.getTransactionOperation().commit(signed);
    aergoClient.close();
    return txHash;
  }

  public TxHash commitWithInServerKeystore(final Account account, final String password,
      final AccountAddress recipient) {
    final AergoClient aergoClient = new AergoClientBuilder()
        .withNonBlockingConnect()
        .withEndpoint("localhost:7845")
        .build();
    final RawTransaction rawTransaction = RawTransaction.newBuilder()
        .from(account)
        .to(recipient)
        .amount("10000", Unit.AER)
        .nonce(account.incrementAndGetNonce())
        .fee(Fee.of(Aer.of("100", Unit.AER), 5))
        .build();

    aergoClient.getKeyStoreOperation().unlock(Authentication.of(account.getAddress(), password));
    final Transaction signed = aergoClient.getAccountOperation().sign(account, rawTransaction);
    final TxHash txHash = aergoClient.getTransactionOperation().commit(signed);
    aergoClient.close();
    return txHash;
  }

}
