package hera.example.highlevel;

import static java.util.UUID.randomUUID;

import hera.api.model.Authentication;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;
import hera.wallet.Wallet;
import hera.wallet.WalletBuilder;
import hera.wallet.WalletType;
import java.util.concurrent.TimeUnit;

public class WalletTrainingHigh {

  public Wallet createAndSaveKeyAndUnlock() {
    final Wallet wallet = new WalletBuilder()
        .withEndpoint("localhost:7845")
        .withNonBlockingConnect()
        .withNonceRefresh(2, 1, TimeUnit.SECONDS)
        .build(WalletType.Naive);
    final AergoKey key = new AergoKeyGenerator().create();
    final String password = randomUUID().toString();
    wallet.saveKey(key, password);
    wallet.unlock(Authentication.of(key.getAddress(), password));
    return wallet;
  }

  public Wallet createSecureWalletAndSaveKeyAndUnlock(final java.security.KeyStore keyStore) {
    final Wallet wallet = new WalletBuilder()
        .withEndpoint("localhost:7845")
        .withNonBlockingConnect()
        .withNonceRefresh(2, 1, TimeUnit.SECONDS)
        .build(WalletType.Secure);
    wallet.bindKeyStore(keyStore);
    final AergoKey key = new AergoKeyGenerator().create();
    final String password = randomUUID().toString();
    wallet.saveKey(key, password);
    wallet.unlock(Authentication.of(key.getAddress(), password));
    return wallet;
  }

  public void createAndSaveAndStoreWithJKS(final String keyStorePath, final String keyStorePassword,
      final AergoKey key, final String authPassword) throws Exception {
    final java.security.KeyStore keyStore = java.security.KeyStore.getInstance("PKCS12");
    keyStore.load(null, null);

    final Wallet wallet = new WalletBuilder()
        .withEndpoint("localhost:7845")
        .withNonBlockingConnect()
        .withNonceRefresh(2, 1, TimeUnit.SECONDS)
        .build(WalletType.Secure);
    wallet.bindKeyStore(keyStore);
    wallet.saveKey(key, authPassword);
    wallet.storeKeyStore(keyStorePath, keyStorePassword);
  }

}
