package hera.example;

import hera.api.model.Account;
import hera.api.model.ContractInterface;
import hera.api.model.ContractTxReceipt;

public class ContractTraining extends AbstractTraining {

  public ContractInterface deploy(Account accountWithKey, String contractPayload) {
    // TODO : implement it
    return null;
  }

  public ContractTxReceipt execute(Account accountWithKey, ContractInterface contractInterface,
      String functionName, Object... args) {
    // TODO : implement it
    return null;
  }

  public Data query(ContractInterface contractInterface, String functionName,
      Object... args) {
    // TODO : implement it
    return null;
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }

}
