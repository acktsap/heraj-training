package hera.example.highlevel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import hera.api.model.AccountAddress;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.example.AbstractTestCase;
import hera.example.highlevel.TransactionTrainingHigh;
import hera.example.highlevel.WalletTrainingHigh;
import hera.wallet.Wallet;
import org.junit.Test;

public class TransactionTrainingHighTest extends AbstractTestCase {

  protected WalletTrainingHigh walletTraining = new WalletTrainingHigh();
  protected TransactionTrainingHigh transactionTraining = new TransactionTrainingHigh();

  protected final AccountAddress accountAddress =
      AccountAddress.of("AmLbHdVs4dNpRzyLirs8cKdV26rPJJxpVXG1w2LLZ9pKfqAHHdyg");

  @Test
  public void testSignAndCommit() throws Exception {
    final Wallet wallet = walletTraining.createAndSaveKeyAndUnlock();
    final AccountAddress recipient = accountAddress;

    final TxHash txHash = transactionTraining.signAndCommit(wallet, recipient);
    assertNotNull(txHash);

    final Transaction mempoolQueried = transactionTraining.getTransaction(wallet, txHash);
    assertTrue(!mempoolQueried.isConfirmed());

    waitingForNextBlockToGenerate();

    final Transaction confirmedQueried = transactionTraining.getTransaction(wallet, txHash);
    assertTrue(confirmedQueried.isConfirmed());

    wallet.close();
  }

  @Test
  public void testSendAergo() throws Exception {
    final Wallet wallet = walletTraining.createAndSaveKeyAndUnlock();
    final AccountAddress recipient = accountAddress;

    final TxHash txHash = transactionTraining.sendAergo(wallet, recipient);
    assertNotNull(txHash);

    final Transaction mempoolQueried = transactionTraining.getTransaction(wallet, txHash);
    assertTrue(!mempoolQueried.isConfirmed());

    waitingForNextBlockToGenerate();

    final Transaction confirmedQueried = transactionTraining.getTransaction(wallet, txHash);
    assertTrue(confirmedQueried.isConfirmed());

    wallet.close();
  }

}
