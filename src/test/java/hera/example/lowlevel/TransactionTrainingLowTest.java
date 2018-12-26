package hera.example.lowlevel;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import hera.api.model.Account;
import hera.api.model.AccountAddress;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.example.AbstractTestCase;
import hera.example.lowlevel.AccountTrainingLow;
import hera.example.lowlevel.TransactionTrainingLow;
import org.junit.Test;

public class TransactionTrainingLowTest extends AbstractTestCase {

  protected AccountTrainingLow accountTraining = new AccountTrainingLow();
  protected TransactionTrainingLow transactionTraining = new TransactionTrainingLow();

  @Test
  public void testCommitWithNotInServerKeystore() throws Exception {
    final Account account = accountTraining.createWithKey();
    final AccountAddress recipient = accountTraining.createWithKey().getAddress();

    final TxHash txHash =
        transactionTraining.commitWithNotInServerKeystore(account, recipient);
    assertNotNull(txHash);

    final Transaction mempoolQueried = transactionTraining.getTransaction(txHash);
    assertTrue(!mempoolQueried.isConfirmed());

    waitingForNextBlockToGenerate();

    final Transaction confirmedQueried = transactionTraining.getTransaction(txHash);
    assertTrue(confirmedQueried.isConfirmed());
  }

  @Test
  public void testCommitWithInServerKeystore() throws Exception {
    final String password = randomUUID().toString();
    final Account account = accountTraining.createOnServerKeyStore(password);
    final AccountAddress recipient = accountTraining.createWithKey().getAddress();

    final TxHash txHash =
        transactionTraining.commitWithInServerKeystore(account, password, recipient);
    assertNotNull(txHash);

    final Transaction mempoolQueried = transactionTraining.getTransaction(txHash);
    assertTrue(!mempoolQueried.isConfirmed());

    waitingForNextBlockToGenerate();

    final Transaction confirmedQueried = transactionTraining.getTransaction(txHash);
    assertTrue(confirmedQueried.isConfirmed());
  }

}
