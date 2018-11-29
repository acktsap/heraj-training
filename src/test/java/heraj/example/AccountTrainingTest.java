package heraj.example;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import hera.api.model.Account;
import hera.api.model.AccountAddress;
import hera.api.model.ClientManagedAccount;
import hera.api.model.EncryptedPrivateKey;
import hera.api.model.ServerManagedAccount;
import hera.example.AccountTraining;
import hera.key.AergoKey;
import org.junit.Test;

public class AccountTrainingTest extends AbstractTestCase {

  protected AccountTraining accountTraining = new AccountTraining();

  @Test
  public void testCreateWithKey() throws Exception {
    final Account account = accountTraining.createWithKey();
    assertTrue(account instanceof ClientManagedAccount);

    final AergoKey key = ((ClientManagedAccount) account).getKey();
    assertNotNull(key);
    assertNotNull(key.getPrivateKey());
    assertNotNull(key.getPublicKey());
    assertNotNull(key.getAddress());
  }

  @Test
  public void testCreateWithPassword() {
    final String password = randomUUID().toString();
    final Account account = accountTraining.createWithPassword(password);
    assertTrue(account instanceof ServerManagedAccount);
    assertNotNull(account.getAddress());
  }

  @Test
  public void testGetState() throws Exception {
    final Account account = accountTraining.createWithKey();
    assertNotNull(accountTraining.getState(account.getAddress()));
  }

  @Test
  public void testImportToLocal() throws Exception {
    final String encodedEncryptedPrivateKey =
        "47pArdc5PNS9HYY9jMMC7zAuHzytzsAuCYGm5jAUFuD3amQ4mQkvyUaPnmRVSPc2iWzVJpC9Z";
    final String password = "password";
    final AergoKey imported = accountTraining.importToLocal(encodedEncryptedPrivateKey, password);
    assertEquals(AccountAddress.of(() -> "AmM25FKSK1gCqSdUPjnvESsauESNgfZUauHWp7R8Un3zHffEQgTm"),
        imported.getAddress());
  }

  @Test
  public void testImportToServer() {
    final String encodedEncryptedPrivateKey =
        "47pArdc5PNS9HYY9jMMC7zAuHzytzsAuCYGm5jAUFuD3amQ4mQkvyUaPnmRVSPc2iWzVJpC9Z";
    final String password = "password";

    try {
      final Account imported = accountTraining.importToServer(encodedEncryptedPrivateKey, password);
      assertTrue(imported instanceof ServerManagedAccount);
      assertEquals(AccountAddress.of(() -> "AmM25FKSK1gCqSdUPjnvESsauESNgfZUauHWp7R8Un3zHffEQgTm"),
          imported.getAddress());
    } catch (Exception e) {
      assertTrue(e.getMessage().contains("already exist"));
    }
  }

  @Test
  public void testExportFromLocal() throws Exception {
    final Account account = accountTraining.createWithKey();
    final AergoKey createdKey = ((ClientManagedAccount) account).getKey();
    final String password = randomUUID().toString();

    final EncryptedPrivateKey privateKey = accountTraining.exportFromLocal(createdKey, password);
    final AergoKey importedKey = accountTraining.importToLocal(privateKey.toString(), password);
    assertEquals(createdKey.getPrivateKey(), importedKey.getPrivateKey());
  }

  @Test
  public void testExportFromServer() {
    final String encodedEncryptedPrivateKey =
        "47pArdc5PNS9HYY9jMMC7zAuHzytzsAuCYGm5jAUFuD3amQ4mQkvyUaPnmRVSPc2iWzVJpC9Z";
    final String password = "password";
    try {
      final Account imported = accountTraining.importToServer(encodedEncryptedPrivateKey, password);
      assertTrue(imported instanceof ServerManagedAccount);
      assertEquals(AccountAddress.of(() -> "AmM25FKSK1gCqSdUPjnvESsauESNgfZUauHWp7R8Un3zHffEQgTm"),
          imported.getAddress());
    } catch (Exception e) {
      assertTrue("Unexpected error", e.getMessage().contains("already exist"));
    }
  }

}
