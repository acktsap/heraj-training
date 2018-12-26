package hera.example.highlevel;

import hera.key.AergoKey;
import hera.wallet.Wallet;

public class WalletTrainingHigh {

  public Wallet createAndSaveKeyAndUnlock() {
    // TODO : create Naive or ServerKeyStore wallet
    return null;
  }

  public Wallet createSecureWalletAndSaveKeyAndUnlock(final java.security.KeyStore keyStore) {
    // TODO : create Secure wallet
    return null;
  }

  public void createAndSaveAndStoreWithJKS(final String keyStorePath,
      final String keyStorePassword, final AergoKey key, final String authPassword) {
    // TODO : use Secure wallet
  }

}
